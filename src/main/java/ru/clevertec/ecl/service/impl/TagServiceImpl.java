package ru.clevertec.ecl.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.ecl.dto.SingleResponseError;
import ru.clevertec.ecl.dto.Tag;
import ru.clevertec.ecl.exception.EssenceExistException;
import ru.clevertec.ecl.exception.EssenceNotFoundException;
import ru.clevertec.ecl.mapper.TagMapper;
import ru.clevertec.ecl.repository.api.TagRepository;
import ru.clevertec.ecl.repository.entity.TagEntity;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class TagServiceImpl implements ru.clevertec.ecl.service.TagService {

    private final TagRepository tagRepository;

    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    @Transactional
    public Tag create(Tag tag) {
        String name = tag.getName();
        Optional<TagEntity> optionalTag = this.tagRepository.findByName(name);
        if (optionalTag.isPresent()) {
            throw new EssenceExistException(SingleResponseError.of("Tag with that name is already existing",
                    40001));
        }
        long tagId = this.tagRepository.create(TagMapper.INSTANCE.toEntity(tag));
        return this.findById(tagId);
    }

    @Override
    public Tag findById(long id) {
        Optional<TagEntity> optionalTag = this.tagRepository.findById(id);
        if (optionalTag.isEmpty()) {
            throw new EssenceNotFoundException(SingleResponseError.of("Requested resource was not found",
                    40401));
        }
        return TagMapper.INSTANCE.toDto(optionalTag.get());
    }

    @Override
    public Tag findByName(String name) {
        Optional<TagEntity> optionalTag = this.tagRepository.findByName(name);
        if (optionalTag.isEmpty()) {
            throw new EssenceNotFoundException(SingleResponseError.of("Requested resource was not found",
                    40401));
        }
        return TagMapper.INSTANCE.toDto(optionalTag.get());
    }

    @Override
    public List<Tag> findAll() {
        List<TagEntity> tags = this.tagRepository.findAll();
        if (tags.isEmpty()) {
            throw new EssenceNotFoundException(SingleResponseError.of("Requested resource was not found",
                    40402));
        }
        return TagMapper.INSTANCE.toDto(tags);
    }

    @Override
    @Transactional
    public Tag update(long id, Tag updated) {
        this.findById(id);
        this.tagRepository.update(id, TagMapper.INSTANCE.toEntity(updated));
        return this.findById(id);
    }

    @Override
    @Transactional
    public void delete(long id) {
        this.findById(id);
        this.tagRepository.delete(id);
    }

    @Override
    @Transactional
    public void delete() {
        this.delete();
    }
}
