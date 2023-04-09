//package ru.clevertec.ecl.repository.impl;
//
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.MethodSource;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.transaction.annotation.Transactional;
//import config.SpringTestJdbcConfig;
//import ru.clevertec.ecl.dto.GiftCertificateFilter;
//import ru.clevertec.ecl.repository.GiftCertificateRepository;
//import ru.clevertec.ecl.repository.entity.GiftCertificate;
//import ru.clevertec.ecl.repository.entity.Tag;
//import ru.clevertec.ecl.util.DateUtil;
//
//import java.math.BigDecimal;
//import java.time.Duration;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Stream;
//
//import static org.assertj.core.api.Assertions.*;
//
//@SpringBootTest
//class GiftCertificateDecoratorRepositoryImplTest {
//
//    @Autowired
//    private GiftCertificateRepository giftCertificateRepository;
//
//    @AfterEach
//    void tearDown() {
//        giftCertificateRepository.deleteAll();
//    }
//
//    @ParameterizedTest(name = "{index} - {0}")
//    @MethodSource("getGiftCertificateEntity")
//    @DisplayName("Creating gift certificate entity")
//    void checkCreateShouldReturnGiftCertificateId(GiftCertificate giftCertificate) {
//        long id = giftCertificateRepository.save(giftCertificate);
//        assertThat(id).isNotZero();
//    }
//
//    @ParameterizedTest(name = "{index} - {0}")
//    @MethodSource("getGiftCertificateEntity")
//    @DisplayName("Getting by id gift certificate entity")
//    void checkGetByIdShouldReturnOptional(GiftCertificate giftCertificate) {
//        long id = giftCertificateRepository.create(giftCertificate);
//        giftCertificate.setId(id);
//        Optional<GiftCertificate> optionalGiftCertificate = giftCertificateRepository.findById(id);
//        assertThat(optionalGiftCertificate).hasValue(giftCertificate);
//    }
//
//    @Test
//    @DisplayName("Getting by gift certificate entity should return empty Optional")
//    void checkGetByIdShouldReturnEmptyOptional() {
//        Optional<GiftCertificate> optionalGiftCertificate = giftCertificateRepository.findById(1L);
//        assertThat(optionalGiftCertificate).isEmpty();
//    }
//
//    @ParameterizedTest(name = "{index} - {0}")
//    @MethodSource("getGiftCertificateEntity")
//    @DisplayName("Getting gift certificate entity's list")
//    void checkGetAllShouldReturnListGiftCertificateEntities(GiftCertificate giftCertificate) {
//        long id = giftCertificateRepository.create(giftCertificate);
//        giftCertificate.setId(id);
//        List<GiftCertificate> giftCertificateEntities = giftCertificateRepository.findAll();
//        assertThat(giftCertificateEntities).contains(giftCertificate);
//    }
//
//    @Test
//    @DisplayName("Getting gift certificate empty entity's list")
//    void checkGetAllShouldReturnEmptyListGiftCertificateEntities() {
//        List<GiftCertificate> giftCertificateEntities = giftCertificateRepository.findAll();
//        assertThat(giftCertificateEntities).isEmpty();
//    }
//
//    @ParameterizedTest(name = "{index} - {0}")
//    @MethodSource("getGiftCertificateEntity")
//    @DisplayName("Getting gift certificate entity's list with empty filter")
//    void checkGetAllWithEmptyFilter(GiftCertificate giftCertificate) {
//        long id = this.giftCertificateRepository.create(giftCertificate);
//        giftCertificate.setId(id);
//        GiftCertificateFilter filter = new GiftCertificateFilter();
//        List<GiftCertificate> giftCertificateEntities = giftCertificateRepository.getAll(filter);
//        assertThat(giftCertificateEntities).contains(giftCertificate);
//    }
//
//    @ParameterizedTest(name = "{index} - {0}")
//    @MethodSource("getGiftCertificateEntity")
//    @DisplayName("Getting gift certificate entity's list with filter (part of word criteria)")
//    void checkGetAllWithFilterShouldReturnGiftCertificateEntityList(GiftCertificate giftCertificate) {
//        long id = giftCertificateRepository.create(giftCertificate);
//        giftCertificate.setId(id);
//        GiftCertificateFilter filter = new GiftCertificateFilter();
//        filter.setFieldPart("gif");
//        List<GiftCertificate> giftCertificateEntities = giftCertificateRepository.getAll(filter);
//        assertThat(giftCertificateEntities).contains(giftCertificate);
//    }
//
//    @ParameterizedTest(name = "{index} - {0}")
//    @MethodSource("getGiftCertificateEntity")
//    @DisplayName("Getting empty gift certificate entity's list with filter (tag name)")
//    void checkGetAllWithFilterShouldReturnEmptyGiftCertificateEntityList(GiftCertificate giftCertificate) {
//        long id = giftCertificateRepository.create(giftCertificate);
//        giftCertificate.setId(id);
//        GiftCertificateFilter filter = new GiftCertificateFilter();
//        filter.setTagName("second_tag");
//        List<GiftCertificate> giftCertificateEntities = giftCertificateRepository.getAll(filter);
//        assertThat(giftCertificateEntities).contains(giftCertificate);
//    }
//
//    @ParameterizedTest(name = "{index} - {0}")
//    @MethodSource("getGiftCertificateEntity")
//    @DisplayName("Updating gift certificate entity")
//    void checkUpdate(GiftCertificate giftCertificate) {
//        long id = giftCertificateRepository.create(giftCertificate);
//        giftCertificate.setId(id);
//
//
//        GiftCertificate updated = GiftCertificate.builder()
//                .name("updated")
//                .build();
//
//        this.giftCertificateRepository.update(id, updated);
//
//        Optional<GiftCertificate> optionalGiftCertificate = giftCertificateRepository.findById(id);
//        if (optionalGiftCertificate.isEmpty()) {
//            return;
//        }
//        GiftCertificate fromDb = optionalGiftCertificate.get();
//        assertThat(fromDb.getLastUpdateDate()).isNotEqualTo(giftCertificate.getLastUpdateDate());
//
//    }
//
//    @ParameterizedTest(name = "{index} - {0}")
//    @MethodSource("getGiftCertificateEntity")
//    @DisplayName("Deleting gift certificate by id")
//    void checkDeleteById(GiftCertificate giftCertificate) {
//        long id = this.giftCertificateRepository.create(giftCertificate);
//        this.giftCertificateRepository.delete(id);
//        Optional<GiftCertificate> optionalGiftCertificate = giftCertificateRepository.findById(id);
//        assertThat(optionalGiftCertificate).isEmpty();
//    }
//
//    @ParameterizedTest(name = "{index} - {0}")
//    @MethodSource("getGiftCertificateEntity")
//    @DisplayName("Deleting all gift certificates")
//    void checkDeleteAll(GiftCertificate giftCertificate) {
//        long id = giftCertificateRepository.create(giftCertificate);
//        giftCertificateRepository.delete();
//        Optional<GiftCertificate> optionalGiftCertificate = giftCertificateRepository.findById(id);
//        assertThat(optionalGiftCertificate).isEmpty();
//    }
//
//    static Stream<GiftCertificate> getGiftCertificateEntity() {
//        Tag tag1 = Tag.builder().id(1L).name("first_tag").build();
//        Tag tag2 = Tag.builder().id(2).name("second_tag").build();
//        Tag tag3 = Tag.builder().id(3).name("third_tag").build();
//        List<Tag> tags = List.of(tag1, tag2, tag3);
//        GiftCertificate giftCertificate = GiftCertificate.builder()
//                .name("gift")
//                .description("certificate")
//                .price(BigDecimal.valueOf(100))
//                .duration(Duration.ofDays(10))
//                .createDate(DateUtil.getCurrentDateISO8601())
//                .lastUpdateDate(DateUtil.getCurrentDateISO8601())
//                .tags(tags)
//                .build();
//        return Stream.of(giftCertificate);
//    }
//}