package ru.clevertec.ecl.service.impl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.ecl.dto.GiftCertificateDto;
import ru.clevertec.ecl.dto.GiftCertificateFilter;
import ru.clevertec.ecl.dto.TagDto;
import ru.clevertec.ecl.exception.EssenceNotFoundException;
import ru.clevertec.ecl.mapper.GiftCertificateMapper;
import ru.clevertec.ecl.repository.impl.GiftCertificateDecoratorRepositoryImpl;
import ru.clevertec.ecl.repository.entity.GiftCertificateEntity;
import ru.clevertec.ecl.service.impl.GiftCertificateServiceImpl;
import ru.clevertec.ecl.util.DateUtil;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GiftCertificateServiceImplTest {

    @Mock
    private GiftCertificateDecoratorRepositoryImpl giftCertificateDecoratorRepositoryImpl;

    @InjectMocks
    private GiftCertificateServiceImpl giftCertificateServiceImpl;

    @ParameterizedTest(name = "{index} - {0}")
    @MethodSource("getGiftCertificate")
    @DisplayName("Getting gift certificate dto by id")
    void checkGetByIdShouldReturnDto(GiftCertificateDto giftCertificateDto) {
        Optional<GiftCertificateEntity> optionalGiftCertificate = Optional
                .of(GiftCertificateMapper.INSTANCE.toEntity(giftCertificateDto));
        doReturn(optionalGiftCertificate)
                .when(giftCertificateDecoratorRepositoryImpl).findById(1L);

        GiftCertificateDto giftCertificateDtoFromDb = giftCertificateServiceImpl.findById(1L);
        assertThat(giftCertificateDtoFromDb).isEqualTo(giftCertificateDto);
    }

    @Test
    @DisplayName("Getting gift certificate dto by id should throw EssenceNotFoundException")
    void checkGetShouldByIdShouldThrowEssenceNofFoundException() {
        doReturn(Optional.empty())
                .when(giftCertificateDecoratorRepositoryImpl).findById(anyLong());

        assertThatThrownBy(() -> giftCertificateServiceImpl.findById(anyLong()))
                .isInstanceOf(EssenceNotFoundException.class);
    }

    @ParameterizedTest(name = "{index} - {0}")
    @MethodSource("getGiftCertificates")
    @DisplayName("Getting dto gift certificate's list")
    void checkGetAllShouldReturnDtoList(List<GiftCertificateDto> giftCertificateDtos) {
        List<GiftCertificateEntity> giftCertificateEntities = GiftCertificateMapper.INSTANCE.toEntity(giftCertificateDtos);
        doReturn(giftCertificateEntities)
                .when(giftCertificateDecoratorRepositoryImpl).findAll();

        List<GiftCertificateDto> giftCertificatesFromDbDto = giftCertificateServiceImpl.findAll();
        assertThat(giftCertificatesFromDbDto).hasSameElementsAs(giftCertificateDtos);
    }

    @Test
    @DisplayName("Getting dto gift certificate's list should throw EssenceNotFoundException")
    void checkGetAllShouldThrowEssenceNotFoundException() {
        doReturn(Collections.emptyList())
                .when(giftCertificateDecoratorRepositoryImpl).findAll();

        assertThatThrownBy(() -> giftCertificateServiceImpl.findAll())
                .isInstanceOf(EssenceNotFoundException.class);
    }

    @ParameterizedTest(name = "{index} - {0}")
    @MethodSource("getGiftCertificates")
    @DisplayName("Getting dto gift certificate's list with filter")
    void checkGetAllWithFilter(List<GiftCertificateDto> giftCertificateDtos) {
        GiftCertificateFilter filter = new GiftCertificateFilter();
        List<GiftCertificateEntity> giftCertificateEntities = GiftCertificateMapper.INSTANCE.toEntity(giftCertificateDtos);
        doReturn(giftCertificateEntities)
                .when(giftCertificateDecoratorRepositoryImpl).getAll(filter);

        List<GiftCertificateDto> giftCertificatesFromDbDto = giftCertificateServiceImpl.getAll(filter);

        assertThat(giftCertificatesFromDbDto).hasSameElementsAs(giftCertificateDtos);
    }

    @Test
    @DisplayName("Getting dto gift certificate's list with filter should throw EssenceNotFoundException")
    void checkGetAllWithFilterShouldThrowEssenceNotFoundException() {
        GiftCertificateFilter filter = new GiftCertificateFilter();
        doReturn(Collections.emptyList())
                .when(giftCertificateDecoratorRepositoryImpl).getAll(filter);

        assertThatThrownBy(() -> giftCertificateServiceImpl.getAll(filter))
                .isInstanceOf(EssenceNotFoundException.class);
    }

    @Test
    @DisplayName("Deleting dto gift certificate should throw EssenceNotFoundException")
    void checkDeleteShouldReturnEssenceNotFoundException() {
        doReturn(Optional.empty())
                .when(giftCertificateDecoratorRepositoryImpl).findById(anyLong());

        assertThatThrownBy(() -> giftCertificateServiceImpl.delete(anyLong()))
                .isInstanceOf(EssenceNotFoundException.class);
    }

    @Test
    @DisplayName("Deleting all gift certificates should call delete method from repository")
    void checkDeleteShouldCallMethodFromRepository() {
        doNothing()
                .when(giftCertificateDecoratorRepositoryImpl).delete();

        giftCertificateServiceImpl.delete();

        verify(giftCertificateDecoratorRepositoryImpl).delete();

    }


    static Stream<GiftCertificateDto> getGiftCertificate() {
        TagDto tagDto1 = TagDto.builder().id(1L).name("first_tag").build();
        TagDto tagDto2 = TagDto.builder().id(2L).name("second_tag").build();
        TagDto tagDto3 = TagDto.builder().id(3L).name("third_tag").build();
        List<TagDto> tagDtos = List.of(tagDto1, tagDto2, tagDto3);
        GiftCertificateDto giftCertificateDto = GiftCertificateDto.builder()
                .id(1L)
                .name("gift")
                .description("certificate")
                .price(BigDecimal.valueOf(100))
                .duration(Duration.ofDays(10))
                .createDate(DateUtil.getCurrentDateISO8601())
                .lastUpdateDate(DateUtil.getCurrentDateISO8601())
                .tags(tagDtos)
                .build();
        return Stream.of(giftCertificateDto);
    }

    static Stream<List<GiftCertificateDto>> getGiftCertificates() {
        List<GiftCertificateDto> giftCertificates = getGiftCertificate().toList();
        return Stream.of(giftCertificates);
    }
}