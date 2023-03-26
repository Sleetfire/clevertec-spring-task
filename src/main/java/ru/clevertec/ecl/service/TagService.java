package ru.clevertec.ecl.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.ecl.dto.SingleResponseError;
import ru.clevertec.ecl.dto.Tag;
import ru.clevertec.ecl.exception.EssenceExistException;
import ru.clevertec.ecl.exception.EssenceNotFoundException;
import ru.clevertec.ecl.mapper.TagMapper;
import ru.clevertec.ecl.repository.api.ITagRepository;
import ru.clevertec.ecl.repository.entity.TagEntity;
import ru.clevertec.ecl.service.api.ITagService;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class TagService implements ITagService {

    private final ITagRepository tagRepository;

    public TagService(ITagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    @Transactional
    public Tag create(Tag tag) {
        String name = tag.getName();
        Optional<TagEntity> optionalTag = this.tagRepository.getByName(name);
        if (optionalTag.isPresent()) {
            throw new EssenceExistException(SingleResponseError.of("Tag with that name is already existing",
                    40001));
        }
        long tagId = this.tagRepository.create(TagMapper.INSTANCE.toEntity(tag));
        return this.get(tagId);
    }

    @Override
    public Tag get(long id) {
        Optional<TagEntity> optionalTag = this.tagRepository.get(id);
        if (optionalTag.isEmpty()) {
            throw new EssenceNotFoundException(SingleResponseError.of("Requested resource was not found",
                    40401));
        }
        return TagMapper.INSTANCE.toDto(optionalTag.get());
    }

    @Override
    public Tag getByName(String name) {
        Optional<TagEntity> optionalTag = this.tagRepository.getByName(name);
        if (optionalTag.isEmpty()) {
            throw new EssenceNotFoundException(SingleResponseError.of("Requested resource was not found",
                    40401));
        }
        return TagMapper.INSTANCE.toDto(optionalTag.get());
    }

    @Override
    public List<Tag> getAll() {
        List<TagEntity> tags = this.tagRepository.getAll();
        if (tags.isEmpty()) {
            throw new EssenceNotFoundException(SingleResponseError.of("Requested resource was not found",
                    40402));
        }
        return TagMapper.INSTANCE.toDto(tags);
    }

    @Override
    @Transactional
    public Tag update(long id, Tag updated) {
        this.get(id);
        this.tagRepository.update(id, TagMapper.INSTANCE.toEntity(updated));
        return this.get(id);
    }

    @Override
    @Transactional
    public void delete(long id) {
        this.get(id);
        this.tagRepository.delete(id);
    }

    @Override
    @Transactional
    public void delete() {
        this.delete();
    }
}
