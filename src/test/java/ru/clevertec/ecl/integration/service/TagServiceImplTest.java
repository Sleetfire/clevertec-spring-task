package ru.clevertec.ecl.integration.service;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.dto.PageDto;
import ru.clevertec.ecl.dto.TagDto;
import ru.clevertec.ecl.exception.EssenceNotFoundException;
import ru.clevertec.ecl.integration.BaseIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;
import ru.clevertec.ecl.service.TagService;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class TagServiceImplTest extends BaseIntegrationTest {

    @Autowired
    private TagService tagService;

    @Autowired
    private EntityManager entityManager;

    @Test
    void checkCreateShouldReturnTagDtoWithId() {
        TagDto tagDto = TagDto.builder()
                .name("test-tag")
                .build();

        TagDto createdTag = tagService.create(tagDto);

        assertThat(createdTag.getId()).isNotZero();
    }

    @Test
    void checkFindByIdShouldReturnTagDtoWithId1() {
        long expectedId = 1;

        TagDto tagFromDb = tagService.findById(expectedId);

        assertThat(tagFromDb.getId()).isEqualTo(expectedId);
    }

    @Test
    void checkFindByIdShouldThrowEssenceNotFoundException() {
        long tagId = 10L;

        assertThatThrownBy(() -> tagService.findById(tagId))
                .isInstanceOf(EssenceNotFoundException.class);
    }

    @Test
    void checkFindByNameShouldReturnTagDtoWithNameSuperb() {
        String expectedName = "superb";

        TagDto tagFromDb = tagService.findByName(expectedName);

        assertThat(tagFromDb.getName()).isEqualTo(expectedName);
    }

    @Test
    void checkFindByNameShouldThrowEssenceNotFoundException() {
        String tagName = "nonexistent-name";

        assertThatThrownBy(() -> tagService.findByName(tagName))
                .isInstanceOf(EssenceNotFoundException.class);
    }

    @Test
    void checkFindAllShouldReturn3() {
        int expectedTagsCount = 3;

        List<TagDto> tagDtoList = tagService.findAll();

        assertThat(tagDtoList).hasSize(expectedTagsCount);
    }

    @Test
    void checkFindPageShouldReturnPageDto() {
        int expectedTagsCount = 3;
        Pageable pageable = PageRequest.of(0, 1);

        PageDto<TagDto> page = tagService.findPage(pageable);

        assertThat(page.getTotalElements()).isEqualTo(expectedTagsCount);
    }

    @Test
    void checkUpdateShouldReturnTagDtoWithUpdatedName() {
        String updatedName = "updated-name";
        TagDto updateTagDto = TagDto.builder()
                .name(updatedName)
                .build();
        long tagId = 1L;

        TagDto tagFromDb = tagService.update(tagId, updateTagDto);

        assertThat(tagFromDb.getName()).isEqualTo(updatedName);
    }

    @Test
    void checkDeleteShouldThrowEssenceNotFoundException() {
        long tagId = 3L;

        tagService.delete(tagId);
        entityManager.flush();

        assertThatThrownBy(() -> tagService.findById(tagId))
                .isInstanceOf(EssenceNotFoundException.class);
    }

}
