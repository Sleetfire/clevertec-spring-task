package ru.clevertec.ecl.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.clevertec.ecl.dto.Tag;
import ru.clevertec.ecl.repository.entity.TagEntity;

import java.util.List;

@Mapper
public interface TagMapper {
    TagMapper INSTANCE = Mappers.getMapper(TagMapper.class);

    Tag toDto(TagEntity tagEntity);

    TagEntity toEntity(Tag tag);

    List<Tag> toDto(List<TagEntity> tags);

    List<TagEntity> toEntity(List<Tag> tags);
}
