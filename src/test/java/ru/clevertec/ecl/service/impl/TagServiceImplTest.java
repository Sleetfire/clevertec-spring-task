package ru.clevertec.ecl.service.impl;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.clevertec.ecl.dto.TagDto;
import ru.clevertec.ecl.exception.EssenceExistException;
import ru.clevertec.ecl.exception.EssenceNotFoundException;
import ru.clevertec.ecl.mapper.TagMapper;
import ru.clevertec.ecl.repository.TagRepository;
import ru.clevertec.ecl.repository.entity.Tag;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {

    @Mock
    private TagRepository tagRepository;

    @Autowired
    @InjectMocks
    private TagServiceImpl tagServiceImpl;

    @ParameterizedTest(name = "{index} - {0}")
    @MethodSource("getTag")
    @DisplayName("Creating tag should throw EssenceExistException")
    void checkCreateShouldThrowEssenceExistException(TagDto tagDto) {
        String tagName = tagDto.getName();
        Optional<Tag> optionalTag = Optional.of(TagMapper.INSTANCE.toEntity(tagDto));
        lenient().doReturn(optionalTag)
                .when(this.tagRepository).findByName(tagName);

        assertThatThrownBy(() -> this.tagServiceImpl.create(tagDto))
                .isInstanceOf(EssenceExistException.class);
    }

    @ParameterizedTest(name = "{index} - {0}")
    @MethodSource("getTag")
    @DisplayName("Getting tag dto by id")
    void checkGetShouldReturnDto(TagDto tagDto) {
        long tagId = tagDto.getId();
        Optional<Tag> optionalTag = Optional.of(TagMapper.INSTANCE.toEntity(tagDto));
        lenient().doReturn(optionalTag)
                .when(this.tagRepository).findById(tagId);

        TagDto tagDtoFromDb = this.tagServiceImpl.findById(tagId);

        assertThat(tagDtoFromDb).isEqualTo(tagDto);
    }

    @Test
    @DisplayName("Getting tag dto by id with throwing EssenceNotFoundException")
    void checkGetShouldThrowEssenceNotFoundException() {
        lenient().doReturn(Optional.empty())
                .when(this.tagRepository).findById(1L);

        assertThatThrownBy(() -> this.tagServiceImpl.findById(1L))
                .isInstanceOf(EssenceNotFoundException.class);
    }

    @ParameterizedTest(name = "{index} - {0}")
    @MethodSource("getTag")
    @DisplayName("Getting tag dto by tag's name")
    void checkGetByNameShouldReturnDto(TagDto tagDto) {
        String tagName = tagDto.getName();
        Optional<Tag> optionalTag = Optional.of(TagMapper.INSTANCE.toEntity(tagDto));
        lenient().doReturn(optionalTag)
                .when(this.tagRepository).findByName(tagName);

        TagDto tagDtoFromDb = this.tagServiceImpl.findByName(tagName);

        assertThat(tagDtoFromDb).isEqualTo(tagDto);
    }

    @Test
    @DisplayName("Getting tag dto by id should throw EssenceNotFoundException")
    void checkGetByNameShouldThrowEssenceNotFoundException() {
        lenient().doReturn(Optional.empty())
                .when(this.tagRepository).findByName(anyString());
        assertThatThrownBy(() -> this.tagServiceImpl.findByName(anyString()))
                .isInstanceOf(EssenceNotFoundException.class);
    }

    @ParameterizedTest(name = "{index} - {0}")
    @MethodSource("getTags")
    @DisplayName("Getting dto tag's list")
    void checkGetAllShouldReturnDtoList(List<TagDto> tagDtos) {
        List<Tag> tagEntities = TagMapper.INSTANCE.toEntity(tagDtos);
        lenient().doReturn(tagEntities)
                .when(this.tagRepository).findAll();

        List<TagDto> tagsFromDb = this.tagServiceImpl.findAll();
        assertThat(tagsFromDb).hasSameElementsAs(tagDtos);
    }

    @Test
    @DisplayName("Getting dto tag's list should throw EssenceNotFoundException")
    void checkGetAllShouldThrowEssenceNotFoundException() {
        lenient().doReturn(Collections.emptyList())
                .when(this.tagRepository).findAll();

        assertThatThrownBy(() -> this.tagServiceImpl.findAll())
                .isInstanceOf(EssenceNotFoundException.class);
    }

    @Test
    @DisplayName("Deleting dto tag should throw EssenceNotFoundException")
    void checkDeleteShouldThrowEssenceNotFoundException() {
        lenient().doReturn(Optional.empty())
                .when(this.tagRepository).findById(anyLong());

        assertThatThrownBy(() -> this.tagServiceImpl.delete(anyLong()))
                .isInstanceOf(EssenceNotFoundException.class);
    }

    static Stream<TagDto> getTag() {
        TagDto tagDto = TagDto.builder().id(1L).name("first_tag").build();
        return Stream.of(tagDto);
    }

    static Stream<TagDto> getTagWithoutId() {
        TagDto tagDto = TagDto.builder().name("first_tag").build();
        return Stream.of(tagDto);
    }

    static Stream<List<TagDto>> getTags() {
        TagDto tagDto1 = TagDto.builder().id(1L).name("first_tag").build();
        TagDto tagDto2 = TagDto.builder().id(2L).name("second_tag").build();
        TagDto tagDto3 = TagDto.builder().id(3L).name("third_tag").build();
        List<TagDto> tags = List.of(tagDto1, tagDto2, tagDto3);
        return Stream.of(tags);
    }

}