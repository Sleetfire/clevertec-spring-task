package ru.clevertec.ecl.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.ecl.dto.SingleResponseError;
import ru.clevertec.ecl.dto.Tag;
import ru.clevertec.ecl.exception.EssenceExistException;
import ru.clevertec.ecl.exception.EssenceNotFoundException;
import ru.clevertec.ecl.mapper.TagMapper;
import ru.clevertec.ecl.repository.TagRepository;
import ru.clevertec.ecl.repository.entity.TagEntity;
import ru.clevertec.ecl.service.TagService;

import java.util.List;
import java.util.Optional;

import static ru.clevertec.ecl.util.ErrorMessage.NOT_FOUND_ERROR;
import static ru.clevertec.ecl.util.ErrorMessage.TAG_EXISTING_ERROR;

@Service
@Transactional(readOnly = true)
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    public TagServiceImpl(TagRepository tagRepository, TagMapper tagMapper) {
        this.tagRepository = tagRepository;
        this.tagMapper = tagMapper;
    }

    @Override
    @Transactional
    public Tag create(Tag tag) {
        String name = tag.getName();
        Optional<TagEntity> optionalTag = this.tagRepository.getByName(name);
        if (optionalTag.isPresent()) {
            throw new EssenceExistException(SingleResponseError.of(TAG_EXISTING_ERROR, 40001));
        }
        long tagId = this.tagRepository.create(tagMapper.toEntity(tag));
        return this.getById(tagId);
    }

    @Override
    public Tag getById(long id) {
        Optional<TagEntity> optionalTag = this.tagRepository.getById(id);
        if (optionalTag.isEmpty()) {
            throw new EssenceNotFoundException(SingleResponseError.of(NOT_FOUND_ERROR, 40401));
        }
        return tagMapper.toDto(optionalTag.get());
    }

    @Override
    public Tag getByName(String name) {
        Optional<TagEntity> optionalTag = this.tagRepository.getByName(name);
        if (optionalTag.isEmpty()) {
            throw new EssenceNotFoundException(SingleResponseError.of(NOT_FOUND_ERROR, 40401));
        }
        return tagMapper.toDto(optionalTag.get());
    }

    @Override
    public List<Tag> getAll() {
        List<TagEntity> tags = this.tagRepository.getAll();
        if (tags.isEmpty()) {
            throw new EssenceNotFoundException(SingleResponseError.of(NOT_FOUND_ERROR, 40402));
        }
        return tagMapper.toDto(tags);
    }

    @Override
    @Transactional
    public Tag update(long id, Tag updated) {
        this.getById(id);
        this.tagRepository.update(id, tagMapper.toEntity(updated));
        return this.getById(id);
    }

    @Override
    @Transactional
    public void delete(long id) {
        this.getById(id);
        this.tagRepository.delete(id);
    }

    @Override
    @Transactional
    public void delete() {
        this.delete();
    }
}
