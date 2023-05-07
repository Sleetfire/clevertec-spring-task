package ru.clevertec.ecl.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.clevertec.ecl.dto.TagDto;
import ru.clevertec.ecl.repository.entity.Tag;

import java.util.List;

@Mapper
public interface TagMapper {
    TagMapper INSTANCE = Mappers.getMapper(TagMapper.class);

    TagDto toDto(Tag tag);

    Tag toEntity(TagDto tagDto);

    List<TagDto> toDto(List<Tag> tags);

    List<Tag> toEntity(List<TagDto> tags);
}
