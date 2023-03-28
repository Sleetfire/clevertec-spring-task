package ru.clevertec.ecl.service;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ru.clevertec.ecl.dto.Tag;
import ru.clevertec.ecl.exception.EssenceExistException;
import ru.clevertec.ecl.exception.EssenceNotFoundException;
import ru.clevertec.ecl.mapper.TagMapper;
import ru.clevertec.ecl.repository.TagRepository;
import ru.clevertec.ecl.repository.entity.TagEntity;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TagServiceTest {

    @Mock
    private TagRepository tagRepository;

    @InjectMocks
    private TagService tagService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @ParameterizedTest(name = "{index} - {0}")
    @MethodSource("getTag")
    @DisplayName("Creating tag should throw EssenceExistException")
    void checkCreateShouldThrowEssenceExistException(Tag tag) {
        String tagName = tag.getName();
        Optional<TagEntity> optionalTag = Optional.of(TagMapper.INSTANCE.toEntity(tag));
        doReturn(optionalTag).when(this.tagRepository).getByName(tagName);

        assertThatThrownBy(() -> this.tagService.create(tag))
                .isInstanceOf(EssenceExistException.class);
    }

    @ParameterizedTest(name = "{index} - {0}")
    @MethodSource("getTag")
    @DisplayName("Getting tag dto by id")
    void checkGetShouldReturnDto(Tag tag) {
        long tagId = tag.getId();
        Optional<TagEntity> optionalTag = Optional.of(TagMapper.INSTANCE.toEntity(tag));
        doReturn(optionalTag).when(this.tagRepository).getById(tagId);

        Tag tagFromDb = this.tagService.getById(tagId);

        assertThat(tagFromDb).isEqualTo(tag);
    }

    @Test
    @DisplayName("Getting tag dto by id with throwing EssenceNotFoundException")
    void checkGetShouldThrowEssenceNotFoundException() {
        doReturn(Optional.empty()).when(this.tagRepository).getById(anyLong());

        assertThatThrownBy(() -> this.tagService.getById(anyLong()))
                .isInstanceOf(EssenceNotFoundException.class);
    }

    @ParameterizedTest(name = "{index} - {0}")
    @MethodSource("getTag")
    @DisplayName("Getting tag dto by tag's name")
    void checkGetByNameShouldReturnDto(Tag tag) {
        String tagName = tag.getName();
        Optional<TagEntity> optionalTag = Optional.of(TagMapper.INSTANCE.toEntity(tag));
        doReturn(optionalTag).when(this.tagRepository).getByName(tagName);

        Tag tagFromDb = this.tagService.getByName(tagName);

        assertThat(tagFromDb).isEqualTo(tag);
    }

    @Test
    @DisplayName("Getting tag dto by id should throw EssenceNotFoundException")
    void checkGetByNameShouldThrowEssenceNotFoundException() {
        doReturn(Optional.empty()).when(this.tagRepository).getByName(anyString());
        assertThatThrownBy(() -> this.tagService.getByName(anyString()))
                .isInstanceOf(EssenceNotFoundException.class);
    }

    @ParameterizedTest(name = "{index} - {0}")
    @MethodSource("getTags")
    @DisplayName("Getting dto tag's list")
    void checkGetAllShouldReturnDtoList(List<Tag> tags) {
        List<TagEntity> tagEntities = TagMapper.INSTANCE.toEntity(tags);
        doReturn(tagEntities).when(this.tagRepository).getAll();

        List<Tag> tagsFromDb = this.tagService.getAll();
        assertThat(tagsFromDb).hasSameElementsAs(tags);
    }

    @Test
    @DisplayName("Getting dto tag's list should throw EssenceNotFoundException")
    void checkGetAllShouldThrowEssenceNotFoundException() {
        doReturn(Collections.emptyList()).when(this.tagRepository).getAll();

        assertThatThrownBy(() -> this.tagService.getAll())
                .isInstanceOf(EssenceNotFoundException.class);
    }

    @Test
    @DisplayName("Deleting dto tag should throw EssenceNotFoundException")
    void checkDeleteShouldThrowEssenceNotFoundException() {
        doReturn(Optional.empty()).when(this.tagRepository).getById(anyLong());

        assertThatThrownBy(() -> this.tagService.delete(anyLong()))
                .isInstanceOf(EssenceNotFoundException.class);
    }

    static Stream<Tag> getTag() {
        Tag tag = Tag.builder().id(1L).name("first_tag").build();
        return Stream.of(tag);
    }

    static Stream<Tag> getTagWithoutId() {
        Tag tag = Tag.builder().name("first_tag").build();
        return Stream.of(tag);
    }

    static Stream<List<Tag>> getTags() {
        Tag tag1 = Tag.builder().id(1L).name("first_tag").build();
        Tag tag2 = Tag.builder().id(2L).name("second_tag").build();
        Tag tag3 = Tag.builder().id(3L).name("third_tag").build();
        List<Tag> tags = List.of(tag1, tag2, tag3);
        return Stream.of(tags);
    }

}