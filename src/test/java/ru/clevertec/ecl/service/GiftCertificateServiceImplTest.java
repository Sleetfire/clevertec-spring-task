package ru.clevertec.ecl.service;

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
import ru.clevertec.ecl.dto.GiftCertificate;
import ru.clevertec.ecl.dto.GiftCertificateFilter;
import ru.clevertec.ecl.dto.Tag;
import ru.clevertec.ecl.exception.EssenceNotFoundException;
import ru.clevertec.ecl.mapper.GiftCertificateMapper;
import ru.clevertec.ecl.repository.GiftCertificateDecoratorRepository;
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
    private GiftCertificateDecoratorRepository giftCertificateDecoratorRepository;

    @InjectMocks
    private GiftCertificateServiceImpl giftCertificateServiceImpl;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @ParameterizedTest(name = "{index} - {0}")
    @MethodSource("getGiftCertificate")
    @DisplayName("Getting gift certificate dto by id")
    void checkGetByIdShouldReturnDto(GiftCertificate giftCertificate) {
        Optional<GiftCertificateEntity> optionalGiftCertificate = Optional
                .of(GiftCertificateMapper.INSTANCE.toEntity(giftCertificate));
        doReturn(optionalGiftCertificate).when(this.giftCertificateDecoratorRepository).getById(1L);

        GiftCertificate giftCertificateFromDb = this.giftCertificateServiceImpl.findById(1L);
        assertThat(giftCertificateFromDb).isEqualTo(giftCertificate);
    }

    @Test
    @DisplayName("Getting gift certificate dto by id should throw EssenceNotFoundException")
    void checkGetShouldByIdShouldThrowEssenceNofFoundException() {
        doReturn(Optional.empty()).when(this.giftCertificateDecoratorRepository).getById(anyLong());

        assertThatThrownBy(() -> this.giftCertificateServiceImpl.findById(anyLong()))
                .isInstanceOf(EssenceNotFoundException.class);
    }

    @ParameterizedTest(name = "{index} - {0}")
    @MethodSource("getGiftCertificates")
    @DisplayName("Getting dto gift certificate's list")
    void checkGetAllShouldReturnDtoList(List<GiftCertificate> giftCertificates) {
        List<GiftCertificateEntity> giftCertificateEntities = GiftCertificateMapper.INSTANCE.toEntity(giftCertificates);
        doReturn(giftCertificateEntities).when(this.giftCertificateDecoratorRepository).getAll();

        List<GiftCertificate> giftCertificatesFromDb = this.giftCertificateServiceImpl.findAll();
        assertThat(giftCertificatesFromDb).hasSameElementsAs(giftCertificates);
    }

    @Test
    @DisplayName("Getting dto gift certificate's list should throw EssenceNotFoundException")
    void checkGetAllShouldThrowEssenceNotFoundException() {
        doReturn(Collections.emptyList()).when(this.giftCertificateDecoratorRepository).getAll();

        assertThatThrownBy(() -> this.giftCertificateServiceImpl.findAll())
                .isInstanceOf(EssenceNotFoundException.class);
    }

    @ParameterizedTest(name = "{index} - {0}")
    @MethodSource("getGiftCertificates")
    @DisplayName("Getting dto gift certificate's list with filter")
    void checkGetAllWithFilter(List<GiftCertificate> giftCertificates) {
        GiftCertificateFilter filter = new GiftCertificateFilter();
        List<GiftCertificateEntity> giftCertificateEntities = GiftCertificateMapper.INSTANCE.toEntity(giftCertificates);
        doReturn(giftCertificateEntities).when(this.giftCertificateDecoratorRepository).getAll(filter);

        List<GiftCertificate> giftCertificatesFromDb = this.giftCertificateServiceImpl.getAll(filter);

        assertThat(giftCertificatesFromDb).hasSameElementsAs(giftCertificates);
    }

    @Test
    @DisplayName("Getting dto gift certificate's list with filter should throw EssenceNotFoundException")
    void checkGetAllWithFilterShouldThrowEssenceNotFoundException() {
        GiftCertificateFilter filter = new GiftCertificateFilter();
        doReturn(Collections.emptyList()).when(this.giftCertificateDecoratorRepository).getAll(filter);

        assertThatThrownBy(() -> this.giftCertificateServiceImpl.getAll(filter))
                .isInstanceOf(EssenceNotFoundException.class);
    }

    @Test
    @DisplayName("Deleting dto gift certificate should throw EssenceNotFoundException")
    void checkDeleteShouldReturnEssenceNotFoundException() {
        doReturn(Optional.empty()).when(this.giftCertificateDecoratorRepository).getById(anyLong());

        assertThatThrownBy(() -> this.giftCertificateServiceImpl.delete(anyLong()))
                .isInstanceOf(EssenceNotFoundException.class);
    }

    static Stream<GiftCertificate> getGiftCertificate() {
        Tag tag1 = Tag.builder().id(1L).name("first_tag").build();
        Tag tag2 = Tag.builder().id(2L).name("second_tag").build();
        Tag tag3 = Tag.builder().id(3L).name("third_tag").build();
        List<Tag> tags = List.of(tag1, tag2, tag3);
        GiftCertificate giftCertificate = GiftCertificate.builder()
                .id(1L)
                .name("gift")
                .description("certificate")
                .price(BigDecimal.valueOf(100))
                .duration(Duration.ofDays(10))
                .createDate(DateUtil.getCurrentDateISO8601())
                .lastUpdateDate(DateUtil.getCurrentDateISO8601())
                .tags(tags)
                .build();
        return Stream.of(giftCertificate);
    }

    static Stream<List<GiftCertificate>> getGiftCertificates() {
        List<GiftCertificate> giftCertificates = getGiftCertificate().toList();
        return Stream.of(giftCertificates);
    }
}