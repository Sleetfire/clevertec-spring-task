//package ru.clevertec.ecl.repository.impl;
//
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.MethodSource;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.transaction.annotation.Transactional;
//import config.SpringTestJdbcConfig;
//import ru.clevertec.ecl.repository.TagRepository;
//import ru.clevertec.ecl.repository.entity.Tag;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Stream;
//
//import static org.assertj.core.api.Assertions.*;
//
//@ContextConfiguration(classes = SpringTestJdbcConfig.class)
//@Transactional
//@Rollback
//@ActiveProfiles("test")
//@ExtendWith(SpringExtension.class)
//class TagRepositoryImplTest {
//
//    @Autowired
//    private TagRepository tagRepository;
//
//    @AfterEach
//    void tearDown() {
//        tagRepository.delete();
//    }
//
//    @ParameterizedTest(name = "{index} - {0}")
//    @MethodSource("getTagEntity")
//    @DisplayName("Creating tag entity")
//    void checkCreateShouldReturnId(Tag tag) {
//        long id = tagRepository.create(tag);
//        assertThat(id).isNotZero();
//    }
//
//    @ParameterizedTest(name = "{index} - {0}")
//    @MethodSource("getTagEntity")
//    @DisplayName("Getting tag entity by id")
//    void checkGetByIdShouldReturnOptional(Tag tag) {
//        long id = tagRepository.create(tag);
//        Optional<Tag> optionalTagEntity = tagRepository.findById(id);
//        tag.setId(id);
//        assertThat(optionalTagEntity).hasValue(tag);
//    }
//
//    @Test
//    @DisplayName("Getting tag entity by id should return empty Optional")
//    void checkGetByIdShouldReturnEmptyOptional() {
//        Optional<Tag> optionalTagEntity = tagRepository.findById(1L);
//        assertThat(optionalTagEntity).isEmpty();
//    }
//
//    @ParameterizedTest(name = "{index} - {0}")
//    @MethodSource("getTagEntity")
//    @DisplayName("Getting tag entity by name")
//    void checkGetByNameShouldReturnOptional(Tag tag) {
//        long id = tagRepository.create(tag);
//        String name = tag.getName();
//        Optional<Tag> optionalTagEntity = tagRepository.findByName(name);
//        tag.setId(id);
//        assertThat(optionalTagEntity).hasValue(tag);
//    }
//
//    @Test
//    @DisplayName("Getting tag entity by name should return empty Optional")
//    void checkGetByNameShouldReturnEmptyOptional() {
//        Optional<Tag> optionalTagEntity = tagRepository.findByName("name");
//        assertThat(optionalTagEntity).isEmpty();
//    }
//
//    @ParameterizedTest(name = "{index} - {0}")
//    @MethodSource("getTagEntity")
//    @DisplayName("Getting tag entity's list")
//    void checkGetAllShouldReturnTagsList(Tag tag) {
//        long id = tagRepository.create(tag);
//        tag.setId(id);
//        List<Tag> tagEntities = tagRepository.findAll();
//        assertThat(tagEntities).contains(tag);
//    }
//
//    @Test
//    @DisplayName("Getting empty tag entity's list")
//    void checkGetAllShouldReturnEmptyList() {
//        List<Tag> tagEntities = tagRepository.findAll();
//        assertThat(tagEntities).isEmpty();
//    }
//
//    @ParameterizedTest(name = "{index} - {0}")
//    @MethodSource("getTagEntity")
//    @DisplayName("Updating tag entity")
//    void checkUpdateShouldReturnUpdatedTagEntity(Tag tag) {
//        long id = tagRepository.create(tag);
//        tag.setId(id);
//        Tag updated = Tag.builder().name("last_tag").build();
//        long updatedTagId = tagRepository.update(id, updated);
//        updated.setId(updatedTagId);
//        Optional<Tag> optionalTagEntity = tagRepository.findById(updatedTagId);
//        assertThat(optionalTagEntity).hasValue(updated);
//    }
//
//    @ParameterizedTest(name = "{index} - {0}")
//    @MethodSource("getTagEntity")
//    @DisplayName("Deleting tag entity by id")
//    void checkDelete(Tag tag) {
//        long id = tagRepository.create(tag);
//        tagRepository.delete(id);
//        Optional<Tag> optionalTagEntity = tagRepository.findById(id);
//        assertThat(optionalTagEntity).isEmpty();
//    }
//
//    @ParameterizedTest(name = "{index} - {0}")
//    @MethodSource("getTagEntity")
//    @DisplayName("Deleting all tag entities")
//    void checkDeleteAll(Tag tag) {
//        long id = tagRepository.create(tag);
//        tagRepository.delete();
//        Optional<Tag> optionalTagEntity = tagRepository.findById(id);
//        assertThat(optionalTagEntity).isEmpty();
//    }
//
//    static Stream<Tag> getTagEntity() {
//        Tag tag = Tag.builder().name("first_tag").build();
//        return Stream.of(tag);
//    }
//}