package ru.clevertec.ecl.service.impl;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.dto.PageDto;
import ru.clevertec.ecl.dto.TagDto;
import ru.clevertec.ecl.exception.EssenceExistException;
import ru.clevertec.ecl.exception.EssenceNotFoundException;
import ru.clevertec.ecl.mapper.TagMapper;
import ru.clevertec.ecl.repository.TagRepository;
import ru.clevertec.ecl.repository.entity.Tag;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {

    @MockBean
    private TagRepository tagRepository;

    @Autowired
    @InjectMocks
    private TagServiceImpl tagServiceImpl;

    @Autowired
    private TagMapper tagMapper;

    @Test
    @DisplayName("Creating tag should throw EssenceExistException")
    void checkCreateShouldThrowEssenceExistException() {
        TagDto tagDto = getTagDto();
        String tagName = tagDto.getName();
        Optional<Tag> optionalTag = getOptionalTag();

        doReturn(optionalTag)
                .when(tagRepository).findByName(tagName);

        assertThatThrownBy(() -> this.tagServiceImpl.create(tagDto))
                .isInstanceOf(EssenceExistException.class);
    }

    @Test
    @DisplayName("Getting tag dto by id")
    void checkFindShouldReturnDto() {
        TagDto tagDto = getTagDto();
        long tagId = tagDto.getId();
        Optional<Tag> optionalTag = getOptionalTag();

        doReturn(optionalTag)
                .when(this.tagRepository).findById(tagId);

        TagDto tagDtoFromDb = this.tagServiceImpl.findById(tagId);

        assertThat(tagDtoFromDb).isEqualTo(tagDto);
    }

    @Test
    @DisplayName("Getting tag dto by id with throwing EssenceNotFoundException")
    void checkFindShouldThrowEssenceNotFoundException() {
        Long id = 1L;

        doReturn(Optional.empty())
                .when(this.tagRepository).findById(id);

        assertThatThrownBy(() -> this.tagServiceImpl.findById(id))
                .isInstanceOf(EssenceNotFoundException.class);
    }

    @Test
    @DisplayName("Getting tag dto by tag's name")
    void checkFindByNameShouldReturnDto() {
        TagDto tagDto = getTagDto();
        String tagName = tagDto.getName();
        Optional<Tag> optionalTag = getOptionalTag();

        doReturn(optionalTag)
                .when(this.tagRepository).findByName(tagName);

        TagDto tagDtoFromDb = this.tagServiceImpl.findByName(tagName);

        assertThat(tagDtoFromDb).isEqualTo(tagDto);
    }

    @Test
    @DisplayName("Getting tag dto by id should throw EssenceNotFoundException")
    void checkFindByNameShouldThrowEssenceNotFoundException() {
        String name = "name";

        doReturn(Optional.empty())
                .when(this.tagRepository).findByName(name);

        assertThatThrownBy(() -> this.tagServiceImpl.findByName(name))
                .isInstanceOf(EssenceNotFoundException.class);
    }

    @Test
    @DisplayName("Getting dto tag's list")
    void checkFindAllShouldReturnDtoList() {
        List<Tag> tagList = getTags();
        List<TagDto> tagDtoList = getDtoTags();

        doReturn(tagList)
                .when(this.tagRepository).findAll();

        List<TagDto> tagsFromDb = this.tagServiceImpl.findAll();

        assertThat(tagsFromDb).hasSameElementsAs(tagDtoList);
    }

    @Test
    @DisplayName("Getting dto tag's list should throw EssenceNotFoundException")
    void checkFindAllShouldThrowEssenceNotFoundException() {
        doReturn(Collections.emptyList())
                .when(this.tagRepository).findAll();

        assertThatThrownBy(() -> this.tagServiceImpl.findAll())
                .isInstanceOf(EssenceNotFoundException.class);
    }

    @Test
    @DisplayName("Deleting dto tag should throw EssenceNotFoundException")
    void checkDeleteShouldThrowEssenceNotFoundException() {
        Long id = 1L;

        doReturn(Optional.empty())
                .when(this.tagRepository).findById(id);

        assertThatThrownBy(() -> this.tagServiceImpl.delete(id))
                .isInstanceOf(EssenceNotFoundException.class);
    }

    @Test
    @DisplayName("Updating tag dto should throw EssenceNotFoundException")
    void checkUpdateShouldThrowEssenceNotFoundException() {
        TagDto tagDto = getTagDto();
        Long id = tagDto.getId();

        doReturn(Optional.empty())
                .when(this.tagRepository).findById(id);

        assertThatThrownBy(() -> this.tagServiceImpl.update(id, tagDto))
                .isInstanceOf(EssenceNotFoundException.class);
    }

    @Test
    @DisplayName("Updating tag dto should return updated tag dto")
    void checkUpdateShouldReturnUpdatedTag() {
        Optional<Tag> optionalTagFromDb = getOptionalTag();
        TagDto updatedTagDto = getTagDto();
        Long id = updatedTagDto.getId();
        updatedTagDto.setName("update");
        Tag tag = tagMapper.toEntity(updatedTagDto);

        doReturn(optionalTagFromDb)
                .when(tagRepository).findById(id);
        doReturn(tag)
                .when(tagRepository).save(tag);

        TagDto updatedTagDtoFromDb = tagServiceImpl.update(id, updatedTagDto);

        assertThat(updatedTagDtoFromDb).isEqualTo(updatedTagDto);
    }

    @Test
    @DisplayName("Getting tag's page should throw EssenceNotFoundException")
    void checkFindPageShouldThrowEssenceNotFoundException() {
        Pageable pageable = PageRequest.of(1, 1);
        doReturn(Page.empty())
                .when(tagRepository).findAll(pageable);

        assertThatThrownBy(() -> this.tagServiceImpl.findPage(pageable))
                .isInstanceOf(EssenceNotFoundException.class);
    }

    @Test
    @DisplayName("Getting tag's page should return tag page dto")
    void checkFindPageShouldReturnPageDto() {
        List<Tag> content = getTags();
        Pageable pageable = PageRequest.of(0, 1);
        Page<Tag> page = new PageImpl<>(content, pageable, content.size());

        doReturn(page)
                .when(tagRepository).findAll(pageable);

        PageDto<TagDto> pageDto = tagServiceImpl.findPage(pageable);

        assertThat(pageDto.getNumber()).isZero();
        assertThat(pageDto.getSize()).isEqualTo(1);
        assertThat(pageDto.getTotalPages()).isEqualTo(3);
        assertThat(pageDto.getTotalElements()).isEqualTo(3L);
        assertThat(pageDto.isFirst()).isTrue();
        assertThat(pageDto.getNumberOfElements()).isEqualTo(3);
        assertThat(pageDto.isLast()).isFalse();
        assertThat(pageDto.getContent()).contains(getTagDto());
    }

    Optional<Tag> getOptionalTag() {
        Tag tag = getTag();
        return Optional.of(tag);
    }

    Tag getTag() {
        return Tag.builder().id(1L).name("first_tag").build();
    }

    TagDto getTagDto() {
        return tagMapper.toDto(getTag());
    }

    List<Tag> getTags() {
        Tag tag1 = Tag.builder().id(1L).name("first_tag").build();
        Tag tag2 = Tag.builder().id(2L).name("second_tag").build();
        Tag tag3 = Tag.builder().id(3L).name("third_tag").build();
        return List.of(tag1, tag2, tag3);
    }

    List<TagDto> getDtoTags() {
        return tagMapper.toDto(getTags());
    }

}