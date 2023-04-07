package ru.clevertec.ecl.repository.impl;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.clevertec.ecl.dto.GiftCertificateFilter;
import ru.clevertec.ecl.repository.api.GiftCertificateRepository;
import ru.clevertec.ecl.repository.mappers.GiftCertificateSQLMapper;
import ru.clevertec.ecl.repository.util.EntityUtil;
import ru.clevertec.ecl.repository.util.QueryUtil;
import ru.clevertec.ecl.repository.entity.GiftCertificateEntity;
import ru.clevertec.ecl.util.DateUtil;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class GiftCertificateRepositoryImpl implements GiftCertificateRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private static final Logger logger = LogManager.getLogger(GiftCertificateRepositoryImpl.class);

    public GiftCertificateRepositoryImpl(@Qualifier("dataSource") DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public long create(GiftCertificateEntity entity) {
        MapSqlParameterSource paramMap = new MapSqlParameterSource();
        paramMap.addValue("name", entity.getName());
        paramMap.addValue("description", entity.getDescription());
        paramMap.addValue("price", entity.getPrice());
        paramMap.addValue("duration", entity.getDuration().toDays());
        paramMap.addValue("create_date", entity.getCreateDate());
        paramMap.addValue("last_update_date", entity.getLastUpdateDate());
        String sqlInsertQuery = "insert into ecl.gift_certificates " +
                "(name, description, price, duration, create_date, last_update_date)" +
                "values (:name, :description, :price, :duration, :create_date, :last_update_date)";
        long certificateId = QueryUtil.executeQuery(sqlInsertQuery, paramMap, this.namedParameterJdbcTemplate);
        logger.info("Created gift certificate with id " + certificateId);
        return certificateId;
    }

    @Override
    public Optional<GiftCertificateEntity> getById(long id) {
        MapSqlParameterSource paramMap = new MapSqlParameterSource();
        paramMap.addValue("id", id);
        String sqlSelectQuery = "select * from find_certificates()" + "\nWHERE id IN (\n" +
                "SELECT gift_certificates.id\n" +
                "FROM ecl.gift_certificates\n" +
                "LEFT JOIN ecl.gift_certificates_tags ON ecl.gift_certificates.id = " +
                "ecl.gift_certificates_tags.gift_certificate_id\n" +
                "LEFT JOIN ecl.tags ON ecl.gift_certificates_tags.tag_id = ecl.tags.id\n" +
                "WHERE ecl.gift_certificates.id = :id\n" +
                ")";
        return Optional.ofNullable(this.getEntity(sqlSelectQuery, paramMap));
    }

    @Override
    public List<GiftCertificateEntity> getAll() {
        String sqlSelectQuery = "select * from find_certificates()";
        try {
            return this.namedParameterJdbcTemplate.query(sqlSelectQuery, new GiftCertificateSQLMapper()).get(0);
        } catch (DataAccessException e) {
            return Collections.emptyList();
        }
    }

    @Override
    public List<GiftCertificateEntity> getAll(GiftCertificateFilter filter) {
        return QueryUtil.executeFilteredQuery(filter, this.namedParameterJdbcTemplate);
    }

    @Override
    public long update(long id, GiftCertificateEntity updatedEntity) {
        updatedEntity.setLastUpdateDate(DateUtil.getCurrentDateISO8601());
        Map<String, Object> values = EntityUtil.getMap(updatedEntity);
        String sqlUpdateQuery = QueryUtil.getUpdateQuery(values, "ecl.gift_certificates");
        MapSqlParameterSource paramMap = new MapSqlParameterSource();
        values.forEach(paramMap::addValue);
        paramMap.addValue("id", id);
        long certificateId = QueryUtil.executeQuery(sqlUpdateQuery, paramMap, this.namedParameterJdbcTemplate);
        logger.info("Updated gift certificate with id" + certificateId);
        return certificateId;
    }

    @Override
    public void delete(long id) {
        MapSqlParameterSource paramMap = new MapSqlParameterSource();
        paramMap.addValue("id", id);
        String sqlDeleteQuery = "delete from ecl.gift_certificates where id = :id";
        long certificateId = QueryUtil.executeQuery(sqlDeleteQuery, paramMap, this.namedParameterJdbcTemplate);
        logger.info("Deleted gift certificate with id" + certificateId);
    }

    @Override
    public void delete() {
        String sqlDeleteQuery = "delete from ecl.gift_certificates_tags;\ndelete from ecl.gift_certificates;";
        QueryUtil.executeQuery(sqlDeleteQuery, new MapSqlParameterSource(), this.namedParameterJdbcTemplate);
    }

    private GiftCertificateEntity getEntity(String sqlSelectQuery, MapSqlParameterSource paramMap) {
        try {
            List<GiftCertificateEntity> content = this.namedParameterJdbcTemplate.queryForObject(sqlSelectQuery,
                    paramMap, new GiftCertificateSQLMapper());
            if (content != null && content.size() == 1) {
                return content.get(0);
            } else {
                return null;
            }
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
            return null;
        }
    }

}
