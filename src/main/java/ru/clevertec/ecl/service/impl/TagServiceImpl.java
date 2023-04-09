package ru.clevertec.ecl.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.ecl.dto.TagDto;
import ru.clevertec.ecl.exception.EssenceExistException;
import ru.clevertec.ecl.exception.EssenceNotFoundException;
import ru.clevertec.ecl.mapper.TagMapper;
import ru.clevertec.ecl.repository.TagRepository;
import ru.clevertec.ecl.repository.entity.TagEntity;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class TagServiceImpl implements ru.clevertec.ecl.service.TagService {

    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    public TagServiceImpl(TagRepository tagRepository, TagMapper tagMapper) {
        this.tagRepository = tagRepository;
        this.tagMapper = tagMapper;
    }

    @Override
    @Transactional
    public TagDto create(TagDto tagDto) {
        String name = tagDto.getName();
        Optional<TagEntity> optionalTag = tagRepository.findByName(name);
        if (optionalTag.isPresent()) {
            throw new EssenceExistException(40001);
        }
        long tagId = tagRepository.create(tagMapper.toEntity(tagDto));
        return findById(tagId);
    }

    @Override
    public TagDto findById(long id) {
        Optional<TagEntity> optionalTag = this.tagRepository.findById(id);
        if (optionalTag.isEmpty()) {
            throw new EssenceNotFoundException(40401);
        }
        return tagMapper.toDto(optionalTag.get());
    }

    @Override
    public TagDto findByName(String name) {
        Optional<TagEntity> optionalTag = tagRepository.findByName(name);
        if (optionalTag.isEmpty()) {
            throw new EssenceNotFoundException(40401);
        }
        return tagMapper.toDto(optionalTag.get());
    }

    @Override
    public List<TagDto> findAll() {
        List<TagEntity> tags = this.tagRepository.findAll();
        if (tags.isEmpty()) {
            throw new EssenceNotFoundException(40402);
        }
        return tagMapper.toDto(tags);
    }

    @Override
    @Transactional
    public TagDto update(long id, TagDto updated) {
        findById(id);
        tagRepository.update(id, tagMapper.toEntity(updated));
        return findById(id);
    }

    @Override
    @Transactional
    public void delete(long id) {
        findById(id);
        tagRepository.delete(id);
    }

    @Override
    @Transactional
    public void delete() {
        tagRepository.delete();
    }
}
