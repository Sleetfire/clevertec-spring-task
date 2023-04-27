package ru.clevertec.ecl.repository.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.ecl.dto.GiftCertificateFilter;
import ru.clevertec.ecl.repository.GiftCertificateRepository;
import ru.clevertec.ecl.repository.TagRepository;
import ru.clevertec.ecl.repository.entity.GiftCertificateEntity;
import ru.clevertec.ecl.repository.entity.TagEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public class GiftCertificateDecoratorRepositoryImpl implements GiftCertificateRepository {

    private final GiftCertificateRepository giftCertificateRepository;
    private final TagRepository tagRepository;

    public GiftCertificateDecoratorRepositoryImpl(GiftCertificateRepository giftCertificateRepository,
                                                  TagRepository tagRepository) {
        this.giftCertificateRepository = giftCertificateRepository;
        this.tagRepository = tagRepository;
    }

    @Override
    @Transactional
    public Long create(GiftCertificateEntity entity) {
        List<TagEntity> tagEntities = entity.getTags();
        List<String> tagsNames = tagEntities.stream().map(TagEntity::getName).toList();
        List<TagEntity> tagEntitiesFromDb = this.getTags(tagsNames);
        entity.setTags(tagEntitiesFromDb);

        return this.giftCertificateRepository.create(entity);
    }

    @Override
    public Optional<GiftCertificateEntity> getById(Long id) {
        return this.giftCertificateRepository.getById(id);
    }

    @Override
    public List<GiftCertificateEntity> getAll() {
        return this.giftCertificateRepository.getAll();
    }

    @Override
    @Transactional
    public Long update(Long id, GiftCertificateEntity updatedEntity) {
        this.updateTags(updatedEntity);
        return this.giftCertificateRepository.update(id, updatedEntity);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        this.giftCertificateRepository.delete(id);
    }

    @Override
    public List<GiftCertificateEntity> getAll(GiftCertificateFilter filter) {
        return this.giftCertificateRepository.getAll(filter);
    }

    @Override
    @Transactional
    public void delete() {
        this.giftCertificateRepository.delete();
    }

    private List<TagEntity> getTags(List<String> names) {
        List<TagEntity> tagEntities = new ArrayList<>();
        names.forEach(name -> {
            Optional<TagEntity> optionalTag = this.tagRepository.getByName(name);
            if (optionalTag.isEmpty()) {
                TagEntity tag = TagEntity.builder()
                        .name(name)
                        .build();
                Long tagId = this.tagRepository.create(tag);
                TagEntity tagEntity = this.tagRepository.getById(tagId).get();
                tagEntities.add(tagEntity);
            } else {
                tagEntities.add(optionalTag.get());
            }
        });
        return tagEntities;
    }

    private void updateTags(GiftCertificateEntity giftCertificate) {
        List<TagEntity> tags = giftCertificate.getTags();
        if (tags == null || tags.isEmpty()) {
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
                    } else if (tagName != null) {
                        Optional<TagEntity> tagOptional = this.tagRepository.getByName(tagName);
                        if (tagOptional.isEmpty()) {
                            this.tagRepository.create(tag);
                        }
                    }
                }
        );
    }
}
