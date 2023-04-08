package ru.clevertec.ecl.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.clevertec.ecl.dto.TagDto;
import ru.clevertec.ecl.repository.entity.TagEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TagMapper {
    TagMapper INSTANCE = Mappers.getMapper(TagMapper.class);

    TagDto toDto(TagEntity tagEntity);

    TagEntity toEntity(TagDto tagDto);

    List<TagDto> toDto(List<TagEntity> tags);

    List<TagEntity> toEntity(List<TagDto> tags);
}
