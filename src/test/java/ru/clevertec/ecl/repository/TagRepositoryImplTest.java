package ru.clevertec.ecl.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.ecl.config.SpringTestJdbcConfig;
import ru.clevertec.ecl.repository.entity.TagEntity;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

@ContextConfiguration(classes = SpringTestJdbcConfig.class)
@Transactional
@Rollback
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
class TagRepositoryImplTest {

    @Autowired
    private TagRepository tagRepository;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
        this.tagRepository.delete();
    }

    @ParameterizedTest(name = "{index} - {0}")
    @MethodSource("getTagEntity")
    @DisplayName("Creating tag entity")
    void checkCreateShouldReturnId(TagEntity tagEntity) {
        long id = this.tagRepository.create(tagEntity);
        assertThat(id).isNotZero();
    }

    @ParameterizedTest(name = "{index} - {0}")
    @MethodSource("getTagEntity")
    @DisplayName("Getting tag entity by id")
    void checkGetByIdShouldReturnOptional(TagEntity tagEntity) {
        long id = this.tagRepository.create(tagEntity);
        Optional<TagEntity> optionalTagEntity = this.tagRepository.getById(id);
        tagEntity.setId(id);
        assertThat(optionalTagEntity).hasValue(tagEntity);
    }

    @Test
    @DisplayName("Getting tag entity by id should return empty Optional")
    void checkGetByIdShouldReturnEmptyOptional() {
        Optional<TagEntity> optionalTagEntity = this.tagRepository.getById(1L);
        assertThat(optionalTagEntity).isEmpty();
    }

    @ParameterizedTest(name = "{index} - {0}")
    @MethodSource("getTagEntity")
    @DisplayName("Getting tag entity by name")
    void checkGetByNameShouldReturnOptional(TagEntity tagEntity) {
        long id = this.tagRepository.create(tagEntity);
        String name = tagEntity.getName();
        Optional<TagEntity> optionalTagEntity = this.tagRepository.getByName(name);
        tagEntity.setId(id);
        assertThat(optionalTagEntity).hasValue(tagEntity);
    }

    @Test
    @DisplayName("Getting tag entity by name should return empty Optional")
    void checkGetByNameShouldReturnEmptyOptional() {
        Optional<TagEntity> optionalTagEntity = this.tagRepository.getByName("name");
        assertThat(optionalTagEntity).isEmpty();
    }

    @ParameterizedTest(name = "{index} - {0}")
    @MethodSource("getTagEntity")
    @DisplayName("Getting tag entity's list")
    void checkGetAllShouldReturnTagsList(TagEntity tagEntity) {
        long id = this.tagRepository.create(tagEntity);
        tagEntity.setId(id);
        List<TagEntity> tagEntities = this.tagRepository.getAll();
        assertThat(tagEntities).contains(tagEntity);
    }

    @Test
    @DisplayName("Getting empty tag entity's list")
    void checkGetAllShouldReturnEmptyList() {
        List<TagEntity> tagEntities = this.tagRepository.getAll();
        assertThat(tagEntities).isEmpty();
    }

    @ParameterizedTest(name = "{index} - {0}")
    @MethodSource("getTagEntity")
    @DisplayName("Updating tag entity")
    void checkUpdateShouldReturnUpdatedTagEntity(TagEntity tagEntity) {
        long id = this.tagRepository.create(tagEntity);
        tagEntity.setId(id);
        TagEntity updated = TagEntity.builder().name("last_tag").build();
        long updatedTagId = this.tagRepository.update(id, updated);
        updated.setId(updatedTagId);
        Optional<TagEntity> optionalTagEntity = this.tagRepository.getById(updatedTagId);
        assertThat(optionalTagEntity).hasValue(updated);
    }

    @ParameterizedTest(name = "{index} - {0}")
    @MethodSource("getTagEntity")
    @DisplayName("Deleting tag entity by id")
    void checkDelete(TagEntity tagEntity) {
        long id = this.tagRepository.create(tagEntity);
        this.tagRepository.delete(id);
        Optional<TagEntity> optionalTagEntity = this.tagRepository.getById(id);
        assertThat(optionalTagEntity).isEmpty();
    }

    @ParameterizedTest(name = "{index} - {0}")
    @MethodSource("getTagEntity")
    @DisplayName("Deleting all tag entities")
    void checkDeleteAll(TagEntity tagEntity) {
        long id = this.tagRepository.create(tagEntity);
        this.tagRepository.delete();
        Optional<TagEntity> optionalTagEntity = this.tagRepository.getById(id);
        assertThat(optionalTagEntity).isEmpty();
    }

    static Stream<TagEntity> getTagEntity() {
        TagEntity tagEntity = TagEntity.builder().name("first_tag").build();
        return Stream.of(tagEntity);
    }
}