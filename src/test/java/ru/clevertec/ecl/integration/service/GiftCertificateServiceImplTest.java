package ru.clevertec.ecl.integration.service;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.dto.CreateGiftCertificateDto;
import ru.clevertec.ecl.dto.GiftCertificateDto;
import ru.clevertec.ecl.dto.GiftCertificateFilter;
import ru.clevertec.ecl.dto.PageDto;
import ru.clevertec.ecl.exception.EssenceNotFoundException;
import ru.clevertec.ecl.integration.BaseIntegrationTest;
import ru.clevertec.ecl.service.impl.GiftCertificateDecoratorServiceImpl;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class GiftCertificateServiceImplTest extends BaseIntegrationTest {

    @Autowired
    private GiftCertificateDecoratorServiceImpl giftCertificateDecoratorService;

    @Autowired
    private EntityManager entityManager;

    @Test
    void checkCreateShouldReturnGiftCertificateDtoWithId() {
        CreateGiftCertificateDto createGiftCertificateDto = CreateGiftCertificateDto.builder()
                .name("gym")
                .description("one month in the gym")
                .price(BigDecimal.valueOf(105.0))
                .duration(Duration.of(30, ChronoUnit.DAYS))
                .tags(List.of("sport", "gym", "superb"))
                .build();
        GiftCertificateDto giftCertificateDtoFromDb = giftCertificateDecoratorService.create(createGiftCertificateDto);
        assertThat(giftCertificateDtoFromDb.getId()).isNotZero();
    }

    @Test
    void checkFindByIdShouldReturnGiftCertificateDtoWithId1() {
        long expectedId = 1L;

        GiftCertificateDto giftCertificateDtoFromDb = giftCertificateDecoratorService.findById(expectedId);

        assertThat(giftCertificateDtoFromDb.getId()).isEqualTo(expectedId);
    }

    @Test
    void checkFindByIdShouldThrowEssenceNotFoundException() {
        long giftCertificateId = 100L;

        assertThatThrownBy(() -> giftCertificateDecoratorService.findById(giftCertificateId))
                .isInstanceOf(EssenceNotFoundException.class);
    }

    @Test
    void checkFindByNameShouldReturnGiftCertificateDtoWithNamePizza() {
        String expectedGiftCertificateName = "pizza";

        GiftCertificateDto giftCertificateDtoFromDb = giftCertificateDecoratorService
                .findByName(expectedGiftCertificateName);

        assertThat(giftCertificateDtoFromDb.getName()).isEqualTo(expectedGiftCertificateName);
    }

    @Test
    void checkFindAllShouldReturnGiftCertificateDtoListWithSize2() {
        int expectedGiftCertificateDtoCount = 2;

        List<GiftCertificateDto> giftCertificateDtoList = giftCertificateDecoratorService.findAll();

        assertThat(giftCertificateDtoList).hasSize(expectedGiftCertificateDtoCount);
    }

    @Test
    void checkFindAllShouldReturnGiftCertificateDtoPageWithSize2() {
        int expectedGiftCertificateDtoCount = 2;
        Pageable pageable = PageRequest.of(0, 1);

        PageDto<GiftCertificateDto> page = giftCertificateDecoratorService.findAll(pageable);

        assertThat(page.getTotalElements()).isEqualTo(expectedGiftCertificateDtoCount);
    }

    @Test
    void checkUpdateNameShouldReturnUpdatedGiftCertificateDto() {
        long giftCertificateId = 1L;
        String updatedName = "updated-name";
        GiftCertificateDto updateGiftCertificateDto = GiftCertificateDto.builder()
                .name(updatedName)
                .build();

        GiftCertificateDto giftCertificateDtoFromDb = giftCertificateDecoratorService
                .update(giftCertificateId, updateGiftCertificateDto);

        assertThat(giftCertificateDtoFromDb.getName()).isEqualTo(updatedName);
    }

    @Test
    void checkUpdateDescriptionShouldReturnUpdatedGiftCertificateDto() {
        long giftCertificateId = 1L;
        String updatedDescription = "updated-description";
        GiftCertificateDto updateGiftCertificateDto = GiftCertificateDto.builder()
                .description(updatedDescription)
                .build();

        GiftCertificateDto giftCertificateDtoFromDb = giftCertificateDecoratorService
                .update(giftCertificateId, updateGiftCertificateDto);

        assertThat(giftCertificateDtoFromDb.getDescription()).isEqualTo(updatedDescription);
    }

    @Test
    void checkUpdatePriceShouldReturnUpdatedGiftCertificateDto() {
        long giftCertificateId = 1L;
        BigDecimal price = BigDecimal.valueOf(200.0);
        GiftCertificateDto updateGiftCertificateDto = GiftCertificateDto.builder()
                .price(price)
                .build();

        GiftCertificateDto giftCertificateDtoFromDb = giftCertificateDecoratorService
                .update(giftCertificateId, updateGiftCertificateDto);

        assertThat(giftCertificateDtoFromDb.getPrice()).isEqualTo(price);
    }

    @Test
    void checkUpdateDurationShouldReturnUpdatedGiftCertificateDto() {
        long giftCertificateId = 1L;
        Duration duration = Duration.of(20, ChronoUnit.DAYS);
        GiftCertificateDto updateGiftCertificateDto = GiftCertificateDto.builder()
                .duration(duration)
                .build();

        GiftCertificateDto giftCertificateDtoFromDb = giftCertificateDecoratorService
                .update(giftCertificateId, updateGiftCertificateDto);

        assertThat(giftCertificateDtoFromDb.getDuration()).isEqualTo(duration);
    }

    @Test
    void checkDeleteShouldThrowEssenceNotFoundException() {
        long giftCertificateDtoId = 2L;

        giftCertificateDecoratorService.delete(giftCertificateDtoId);
        //entityManager.flush();

        assertThatThrownBy(() -> giftCertificateDecoratorService.findById(giftCertificateDtoId))
                .isInstanceOf(EssenceNotFoundException.class);
    }

    @Test
    void checkFindAllFilteredByFieldPartShouldReturnGiftCertificateDtoListWithSize1() {
        int expectedGiftCertificateDtoCount = 1;
        GiftCertificateFilter filter = GiftCertificateFilter.builder()
                .fieldPart("piz")
                .tagNames(Collections.emptyList())
                .build();

        List<GiftCertificateDto> giftCertificateDtoList = giftCertificateDecoratorService.findAllFiltered(filter);

        assertThat(giftCertificateDtoList).hasSize(expectedGiftCertificateDtoCount);
    }

    @Test
    void checkFindAllFilteredByTagNameShouldReturnGiftCertificateDtoListWithSize1() {
        int expectedGiftCertificateDtoCount = 1;
        GiftCertificateFilter filter = GiftCertificateFilter.builder()
                .tagNames(List.of("food"))
                .build();

        List<GiftCertificateDto> giftCertificateDtoList = giftCertificateDecoratorService.findAllFiltered(filter);

        assertThat(giftCertificateDtoList).hasSize(expectedGiftCertificateDtoCount);
    }

}
