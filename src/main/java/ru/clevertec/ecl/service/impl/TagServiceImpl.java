package ru.clevertec.ecl.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.ecl.dto.PageDto;
import ru.clevertec.ecl.dto.TagDto;
import ru.clevertec.ecl.exception.EssenceExistException;
import ru.clevertec.ecl.exception.EssenceNotFoundException;
import ru.clevertec.ecl.mapper.TagMapper;
import ru.clevertec.ecl.repository.TagRepository;
import ru.clevertec.ecl.repository.entity.Tag;
import ru.clevertec.ecl.service.TagService;

import java.util.List;
import java.util.Optional;

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
    public TagDto create(TagDto tagDto) {
        String name = tagDto.getName();
        Optional<Tag> optionalTag = tagRepository.findByName(name);
        if (optionalTag.isPresent()) {
            throw new EssenceExistException(40001);
        }
        Tag fromDb = tagRepository.save(tagMapper.toEntity(tagDto));
        return tagMapper.toDto(fromDb);
    }

    @Override
    public TagDto findById(Long id) {
        Optional<Tag> optionalTag = this.tagRepository.findById(id);
        if (optionalTag.isEmpty()) {
            throw new EssenceNotFoundException(40401);
        }
        return tagMapper.toDto(optionalTag.get());
    }

    @Override
    public TagDto findByName(String name) {
        Optional<Tag> optionalTag = tagRepository.findByName(name);
        if (optionalTag.isEmpty()) {
            throw new EssenceNotFoundException(40401);
        }
        return tagMapper.toDto(optionalTag.get());
    }

    @Override
    public List<TagDto> findAll() {
        List<Tag> tags = this.tagRepository.findAll();
        if (tags.isEmpty()) {
            throw new EssenceNotFoundException(40402);
        }
        return tagMapper.toDto(tags);
    }

    @Override
    public PageDto<TagDto> findPage(Pageable pageable) {
        Page<Tag> tagPage = tagRepository.findAll(pageable);
        return convertToPageDto(tagPage);
    }

    @Override
    @Transactional
    public TagDto update(Long id, TagDto updated) {
        TagDto fromDb = findById(id);
        fromDb.setName(updated.getName());
        Tag saved = tagRepository.save(tagMapper.toEntity(fromDb));
        return tagMapper.toDto(saved);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        findById(id);
        tagRepository.deleteById(id);
    }

    private PageDto<TagDto> convertToPageDto(Page<Tag> page) {
        List<Tag> content = page.getContent();
        if (content.isEmpty()) {
            throw new EssenceNotFoundException(40402);
        }
        return PageDto.Builder.createBuilder(TagDto.class)
                .setNumber(page.getNumber())
                .setSize(page.getSize())
                .setTotalPages(page.getTotalPages())
                .setTotalElements(page.getTotalElements())
                .setFirst(page.isFirst())
                .setNumberOfElements(page.getNumberOfElements())
                .setLast(page.isLast())
                .setContent(tagMapper.toDto(content))
                .build();
    }

}
