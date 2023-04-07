package ru.clevertec.ecl.repository.impl;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.ecl.dto.GiftCertificateFilter;
import ru.clevertec.ecl.dto.SingleResponseError;
import ru.clevertec.ecl.exception.SqlException;
import ru.clevertec.ecl.repository.api.GiftCertificateRepository;
import ru.clevertec.ecl.repository.api.TagRepository;
import ru.clevertec.ecl.repository.util.QueryUtil;
import ru.clevertec.ecl.repository.entity.GiftCertificateEntity;
import ru.clevertec.ecl.repository.entity.TagEntity;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public class GiftCertificateDecoratorRepositoryImpl implements GiftCertificateRepository {

    private final GiftCertificateRepository giftCertificateRepository;
    private final TagRepository tagRepository;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private static final Logger logger = LogManager.getLogger(GiftCertificateDecoratorRepositoryImpl.class);

    public GiftCertificateDecoratorRepositoryImpl(@Qualifier("giftCertificateRepositoryImpl") GiftCertificateRepository giftCertificateRepository,
                                                  TagRepository tagRepository,
                                                  DataSource dataSource) {
        this.giftCertificateRepository = giftCertificateRepository;
        this.tagRepository = tagRepository;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }


    @Override
    @Transactional
    public long create(GiftCertificateEntity entity) {
        long certificateId = this.giftCertificateRepository.create(entity);

        List<String> tagsNames = entity.getTags().stream().map(TagEntity::getName).toList();
        List<Long> tagsIds = this.getTagsIds(tagsNames);

        String sqlInsertQuery = "insert into ecl.gift_certificates_tags (gift_certificate_id, tag_id)" +
                "values (:gift_certificate_id, :tag_id)";
        tagsIds.forEach(id -> {
            MapSqlParameterSource paramMap = new MapSqlParameterSource();
            paramMap.addValue("gift_certificate_id", certificateId);
            paramMap.addValue("tag_id", id);
            this.fillTable(sqlInsertQuery, paramMap);
        });
        return certificateId;
    }

    @Override
    public Optional<GiftCertificateEntity> getById(long id) {
        return this.giftCertificateRepository.getById(id);
    }

    @Override
    public List<GiftCertificateEntity> getAll() {
        return this.giftCertificateRepository.getAll();
    }

    @Override
    public List<GiftCertificateEntity> getAll(GiftCertificateFilter filter) {
        return this.giftCertificateRepository.getAll(filter);
    }

    @Override
    @Transactional
    public long update(long id, GiftCertificateEntity updatedEntity) {
        this.updateTags(updatedEntity);
        return this.giftCertificateRepository.update(id, updatedEntity);
    }

    @Override
    @Transactional
    public void delete(long id) {
        String sqlDeleteQuery = "delete from ecl.gift_certificates_tags where gift_certificate_id = :gift_certificate_id";
        MapSqlParameterSource paramMap = new MapSqlParameterSource();
        paramMap.addValue("gift_certificate_id", id);
        QueryUtil.executeQuery(sqlDeleteQuery, paramMap, namedParameterJdbcTemplate);
        this.giftCertificateRepository.delete(id);
    }

    @Override
    @Transactional
    public void delete() {
        this.tagRepository.delete();
    }

    private List<Long> getTagsIds(List<String> names) {
        List<Long> tagsIds = new ArrayList<>();
        names.forEach(name -> {
            Optional<TagEntity> optionalTag = this.tagRepository.getByName(name);
            if (optionalTag.isEmpty()) {
                TagEntity tag = TagEntity.builder()
                        .name(name)
                        .build();
                long tagId = this.tagRepository.create(tag);
                tagsIds.add(tagId);
            } else {
                tagsIds.add(optionalTag.get().getId());
            }
        });
        return tagsIds;
    }

    private void fillTable(String sqlQuery, MapSqlParameterSource paramMap) {
        try {
            this.namedParameterJdbcTemplate.update(sqlQuery, paramMap);
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
            throw new SqlException(SingleResponseError.of("Bad request to the database", 50001));
        }
    }

    private void updateTags(GiftCertificateEntity giftCertificate) {
        List<TagEntity> tags = giftCertificate.getTags();
        if (tags.isEmpty()) {
            return;
        }
        tags.forEach(tag -> {
                    long tagId = tag.getId();
                    String tagName = tag.getName();
                    if (tagId != 0) {
                        Optional<TagEntity> tagOptional = this.tagRepository.getById(tagId);
                        if (tagOptional.isPresent()) {
                            this.tagRepository.update(tagId, tag);
                        }
                    } else if (tagName != null){
                        Optional<TagEntity> tagOptional = this.tagRepository.getByName(tagName);
                        if (tagOptional.isEmpty()) {
                            this.tagRepository.create(tag);
                        }
                    }
                }
        );
    }
}
