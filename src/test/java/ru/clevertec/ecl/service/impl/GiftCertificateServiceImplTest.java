package ru.clevertec.ecl.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import ru.clevertec.ecl.dto.GiftCertificateDto;
import ru.clevertec.ecl.dto.GiftCertificateFilter;
import ru.clevertec.ecl.dto.PageDto;
import ru.clevertec.ecl.exception.EssenceNotFoundException;
import ru.clevertec.ecl.mapper.GiftCertificateMapper;
import ru.clevertec.ecl.repository.GiftCertificateRepository;
import ru.clevertec.ecl.repository.entity.GiftCertificate;
import ru.clevertec.ecl.repository.entity.Tag;
import ru.clevertec.ecl.util.DateUtil;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class GiftCertificateServiceImplTest {

    @MockBean
    private GiftCertificateRepository giftCertificateRepository;

    @Autowired
    @InjectMocks
    private GiftCertificateDecoratorServiceImpl giftCertificateServiceImpl;

    @Autowired
    private GiftCertificateMapper giftCertificateMapper;

    @Test
    @DisplayName("Getting gift certificate dto by id")
    void checkFindByIdShouldReturnDto() {
        Optional<GiftCertificate> optionalGiftCertificate = getOptionalGiftCertificate();
        GiftCertificateDto giftCertificateDto = getGiftCertificateDto();
        Long id = giftCertificateDto.getId();

        doReturn(optionalGiftCertificate)
                .when(giftCertificateRepository)
                .findById(id);

        GiftCertificateDto giftCertificateDtoFromDb = giftCertificateServiceImpl.findById(id);

        assertThat(giftCertificateDtoFromDb)
                .usingRecursiveComparison()
                .isEqualTo(giftCertificateDto)
                .comparingOnlyFields("name", "description", "price", "duration");
    }

    @Test
    @DisplayName("Getting gift certificate dto by id should throw EssenceNotFoundException")
    void checkFindByIdShouldThrowEssenceNofFoundException() {
        Long id = 1L;

        doReturn(Optional.empty())
                .when(giftCertificateRepository)
                .findById(id);

        assertThatThrownBy(() -> giftCertificateServiceImpl.findById(id))
                .isInstanceOf(EssenceNotFoundException.class);
    }

    @Test
    @DisplayName("Getting gift certificate dto by name")
    void checkFindByNameShouldReturnDto() {
        Optional<GiftCertificate> optionalGiftCertificate = getOptionalGiftCertificate();
        GiftCertificateDto giftCertificateDto = getGiftCertificateDto();
        String name = giftCertificateDto.getName();

        doReturn(optionalGiftCertificate)
                .when(giftCertificateRepository)
                .findByName(name);

        GiftCertificateDto giftCertificateDtoFromDb = giftCertificateServiceImpl.findByName(name);

        assertThat(giftCertificateDtoFromDb)
                .usingRecursiveComparison()
                .isEqualTo(giftCertificateDto)
                .comparingOnlyFields("name", "description", "price", "duration");
    }

    @Test
    @DisplayName("Getting gift certificate dto by name should throw EssenceNotFoundException")
    void checkFindByNameShouldThrowEssenceNofFoundException() {
        String name = "name";

        doReturn(Optional.empty())
                .when(giftCertificateRepository)
                .findByName(name);

        assertThatThrownBy(() -> giftCertificateServiceImpl.findByName(name))
                .isInstanceOf(EssenceNotFoundException.class);
    }

    @Test
    @DisplayName("Getting dto gift certificate's list")
    void checkFindAllShouldReturnDtoList() {
        List<GiftCertificate> giftCertificates = getGiftCertificateList();
        doReturn(giftCertificates)
                .when(giftCertificateRepository)
                .findAll();

        List<GiftCertificateDto> giftCertificatesFromDbDto = giftCertificateServiceImpl.findAll();
        assertThat(giftCertificatesFromDbDto).isNotEmpty();
    }

    @Test
    @DisplayName("Getting dto gift certificate's list should throw EssenceNotFoundException")
    void checkFindAllShouldThrowEssenceNotFoundException() {
        doReturn(Collections.emptyList())
                .when(giftCertificateRepository)
                .findAll();

        assertThatThrownBy(() -> giftCertificateServiceImpl.findAll())
                .isInstanceOf(EssenceNotFoundException.class);
    }

    @Test
    @DisplayName("Getting dto gift certificate's list with filter")
    void checkFindAllWithFilter() {
        GiftCertificateFilter filter = new GiftCertificateFilter();
        filter.setFieldPart("cert");
        filter.setTagNames(Collections.emptyList());
        List<GiftCertificate> giftCertificates = getGiftCertificateList();
        doReturn(giftCertificates)
                .when(giftCertificateRepository)
                .findAllFiltered("%cert%", "%cert%", Collections.emptyList(), 0, null);

        List<GiftCertificateDto> giftCertificatesFromDbDto = giftCertificateServiceImpl.findAllFiltered(filter);

        assertThat(giftCertificatesFromDbDto).isNotEmpty();
    }

    @Test
    @DisplayName("Getting dto gift certificate's list with filter should throw EssenceNotFoundException")
    void checkFindAllWithFilterShouldThrowEssenceNotFoundException() {
        GiftCertificateFilter filter = new GiftCertificateFilter();
        filter.setTagNames(Collections.emptyList());

        doReturn(Collections.emptyList())
                .when(giftCertificateRepository)
                .findAllFiltered(null, null, Collections.emptyList(), 0, null);

        assertThatThrownBy(() -> giftCertificateServiceImpl.findAllFiltered(filter))
                .isInstanceOf(EssenceNotFoundException.class);
    }

    @Test
    @DisplayName("Getting gift certificate's page should return gift certificate page dto")
    void checkFindPageShouldReturnPageDto() {
        List<GiftCertificate> content = getGiftCertificateList();
        Pageable pageable = PageRequest.of(0, 1);
        Page<GiftCertificate> page = new PageImpl<>(content, pageable, content.size());

        doReturn(page)
                .when(giftCertificateRepository)
                .findAll(pageable);

        PageDto<GiftCertificateDto> pageDto = giftCertificateServiceImpl.findAll(pageable);

        assertThat(pageDto.getNumber()).isZero();
        assertThat(pageDto.getSize()).isEqualTo(1);
        assertThat(pageDto.getTotalPages()).isEqualTo(2);
        assertThat(pageDto.getTotalElements()).isEqualTo(2L);
        assertThat(pageDto.isFirst()).isTrue();
        assertThat(pageDto.getNumberOfElements()).isEqualTo(2);
        assertThat(pageDto.isLast()).isFalse();
        assertThat(pageDto.getContent()).isNotEmpty();
    }

    @Test
    @DisplayName("Getting gift certificate's page should throw EssenceNotFoundException")
    void checkFindPageShouldThrowEssenceNotFoundException() {
        Pageable pageable = PageRequest.of(1, 1);

        doReturn(Page.empty())
                .when(giftCertificateRepository).findAll(pageable);

        assertThatThrownBy(() -> this.giftCertificateServiceImpl.findAll(pageable))
                .isInstanceOf(EssenceNotFoundException.class);
    }

    @Test
    @DisplayName("Updating gift certificate dto should throw EssenceNotFoundException")
    void checkUpdateShouldTrowEssenceNotFoundException() {
        GiftCertificateDto giftCertificateDto = getGiftCertificateDto();
        Long id = giftCertificateDto.getId();

        doReturn(Optional.empty())
                .when(giftCertificateRepository)
                .findById(id);

        assertThatThrownBy(() -> this.giftCertificateServiceImpl.update(id, giftCertificateDto))
                .isInstanceOf(EssenceNotFoundException.class);
    }

    @Test
    @DisplayName("Deleting dto gift certificate should throw EssenceNotFoundException")
    void checkDeleteShouldReturnEssenceNotFoundException() {
        Long id = 1L;

        doReturn(Optional.empty())
                .when(giftCertificateRepository).findById(id);

        assertThatThrownBy(() -> giftCertificateServiceImpl.delete(id))
                .isInstanceOf(EssenceNotFoundException.class);
    }

    List<GiftCertificate> getGiftCertificateList() {
        Tag tag1 = Tag.builder().id(1L).name("first_tag").build();
        Tag tag2 = Tag.builder().id(2L).name("second_tag").build();
        Tag tag3 = Tag.builder().id(3L).name("third_tag").build();
        GiftCertificate giftCertificate1 = GiftCertificate.builder()
                .id(1L)
                .name("certificate1")
                .description("certificate1")
                .price(BigDecimal.valueOf(100))
                .duration(Duration.ofDays(10))
                .createDate(DateUtil.getCurrentDateISO8601())
                .lastUpdateDate(DateUtil.getCurrentDateISO8601())
                .tags(List.of(tag2, tag3))
                .build();
        GiftCertificate giftCertificate2 = GiftCertificate.builder()
                .id(1L)
                .name("certificate1")
                .description("certificate1")
                .price(BigDecimal.valueOf(100))
                .duration(Duration.ofDays(10))
                .createDate(DateUtil.getCurrentDateISO8601())
                .lastUpdateDate(DateUtil.getCurrentDateISO8601())
                .tags(List.of(tag1, tag2, tag3))
                .build();
        return List.of(giftCertificate1, giftCertificate2);
    }

    List<GiftCertificateDto> getGiftCertificateDtoList() {
        return giftCertificateMapper.toDto(getGiftCertificateList());
    }

    GiftCertificate getGiftCertificate() {
        Tag tag1 = Tag.builder().id(1L).name("first_tag").build();
        Tag tag2 = Tag.builder().id(2L).name("second_tag").build();
        Tag tag3 = Tag.builder().id(3L).name("third_tag").build();
        GiftCertificate giftCertificate = GiftCertificate.builder()
                .id(1L)
                .name("certificate1")
                .description("certificate1")
                .price(BigDecimal.valueOf(100))
                .duration(Duration.ofDays(10))
                .createDate(DateUtil.getCurrentDateISO8601())
                .lastUpdateDate(DateUtil.getCurrentDateISO8601())
                .tags(List.of(tag1, tag2, tag3))
                .build();
        return giftCertificate;
    }

    GiftCertificateDto getGiftCertificateDto() {
        return giftCertificateMapper.toDto(getGiftCertificate());
    }

    Optional<GiftCertificate> getOptionalGiftCertificate() {
        return Optional.of(getGiftCertificate());
    }

}