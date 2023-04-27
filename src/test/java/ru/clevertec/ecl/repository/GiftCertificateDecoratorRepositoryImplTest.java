package ru.clevertec.ecl.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.ecl.config.SpringTestJdbcConfig;
import ru.clevertec.ecl.dto.GiftCertificateFilter;
import ru.clevertec.ecl.repository.entity.GiftCertificateEntity;
import ru.clevertec.ecl.repository.entity.TagEntity;
import ru.clevertec.ecl.util.DateUtil;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

@ContextConfiguration(classes = SpringTestJdbcConfig.class)
@Transactional
@Rollback
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
class GiftCertificateDecoratorRepositoryImplTest {

    @Autowired
    @Qualifier("giftCertificateDecoratorRepositoryImpl")
    private GiftCertificateRepository giftCertificateRepository;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
        this.giftCertificateRepository.delete();
    }

    @ParameterizedTest(name = "{index} - {0}")
    @MethodSource("getGiftCertificateEntity")
    @DisplayName("Creating gift certificate entity")
    void checkCreateShouldReturnGiftCertificateId(GiftCertificateEntity giftCertificate) {
        long id = this.giftCertificateRepository.create(giftCertificate);
        assertThat(id).isNotZero();
    }

    @ParameterizedTest(name = "{index} - {0}")
    @MethodSource("getGiftCertificateEntity")
    @DisplayName("Getting by id gift certificate entity")
    void checkGetByIdShouldReturnOptional(GiftCertificateEntity giftCertificateEntity) {
        long id = this.giftCertificateRepository.create(giftCertificateEntity);
        giftCertificateEntity.setId(id);
        Optional<GiftCertificateEntity> optionalGiftCertificate = this.giftCertificateRepository.getById(id);
        assertThat(optionalGiftCertificate).hasValue(giftCertificateEntity);
    }

    @Test
    @DisplayName("Getting by gift certificate entity should return empty Optional")
    void checkGetByIdShouldReturnEmptyOptional() {
        Optional<GiftCertificateEntity> optionalGiftCertificate = this.giftCertificateRepository.getById(1L);
        assertThat(optionalGiftCertificate).isEmpty();
    }

    @ParameterizedTest(name = "{index} - {0}")
    @MethodSource("getGiftCertificateEntity")
    @DisplayName("Getting gift certificate entity's list")
    void checkGetAllShouldReturnListGiftCertificateEntities(GiftCertificateEntity giftCertificateEntity) {
        long id = this.giftCertificateRepository.create(giftCertificateEntity);
        giftCertificateEntity.setId(id);
        List<GiftCertificateEntity> giftCertificateEntities = this.giftCertificateRepository.getAll();
        assertThat(giftCertificateEntities).contains(giftCertificateEntity);
    }

    @Test
    @DisplayName("Getting gift certificate empty entity's list")
    void checkGetAllShouldReturnEmptyListGiftCertificateEntities() {
        List<GiftCertificateEntity> giftCertificateEntities = this.giftCertificateRepository.getAll();
        assertThat(giftCertificateEntities).isEmpty();
    }

    @ParameterizedTest(name = "{index} - {0}")
    @MethodSource("getGiftCertificateEntity")
    @DisplayName("Getting gift certificate entity's list with empty filter")
    void checkGetAllWithEmptyFilter(GiftCertificateEntity giftCertificateEntity) {
        long id = this.giftCertificateRepository.create(giftCertificateEntity);
        giftCertificateEntity.setId(id);
        GiftCertificateFilter filter = new GiftCertificateFilter();
        List<GiftCertificateEntity> giftCertificateEntities = this.giftCertificateRepository.getAll(filter);
        assertThat(giftCertificateEntities).contains(giftCertificateEntity);
    }

    @ParameterizedTest(name = "{index} - {0}")
    @MethodSource("getGiftCertificateEntity")
    @DisplayName("Getting gift certificate entity's list with filter (part of word criteria)")
    void checkGetAllWithFilterShouldReturnGiftCertificateEntityList(GiftCertificateEntity giftCertificateEntity) {
        long id = this.giftCertificateRepository.create(giftCertificateEntity);
        giftCertificateEntity.setId(id);
        GiftCertificateFilter filter = new GiftCertificateFilter();
        filter.setFieldPart("gif");
        List<GiftCertificateEntity> giftCertificateEntities = this.giftCertificateRepository.getAll(filter);
        assertThat(giftCertificateEntities).contains(giftCertificateEntity);
    }

    @ParameterizedTest(name = "{index} - {0}")
    @MethodSource("getGiftCertificateEntity")
    @DisplayName("Getting empty gift certificate entity's list with filter (tag name)")
    void checkGetAllWithFilterShouldReturnEmptyGiftCertificateEntityList(GiftCertificateEntity giftCertificateEntity) {
        long id = this.giftCertificateRepository.create(giftCertificateEntity);
        giftCertificateEntity.setId(id);
        GiftCertificateFilter filter = new GiftCertificateFilter();
        filter.setTagName("second_tag");
        List<GiftCertificateEntity> giftCertificateEntities = this.giftCertificateRepository.getAll(filter);
        assertThat(giftCertificateEntities).contains(giftCertificateEntity);
    }

    @ParameterizedTest(name = "{index} - {0}")
    @MethodSource("getGiftCertificateEntity")
    @DisplayName("Updating gift certificate entity")
    void checkUpdate(GiftCertificateEntity giftCertificateEntity) {
        long id = this.giftCertificateRepository.create(giftCertificateEntity);
        giftCertificateEntity.setId(id);


        GiftCertificateEntity updated = GiftCertificateEntity.builder()
                .name("updated")
                .build();

        this.giftCertificateRepository.update(id, updated);

        Optional<GiftCertificateEntity> optionalGiftCertificate = this.giftCertificateRepository.getById(id);
        if (optionalGiftCertificate.isEmpty()) {
            return;
        }
        GiftCertificateEntity fromDb = optionalGiftCertificate.get();
        assertThat(fromDb.getLastUpdateDate()).isNotEqualTo(giftCertificateEntity.getLastUpdateDate());

    }

    @ParameterizedTest(name = "{index} - {0}")
    @MethodSource("getGiftCertificateEntity")
    @DisplayName("Deleting gift certificate by id")
    void checkDeleteById(GiftCertificateEntity giftCertificateEntity) {
        long id = this.giftCertificateRepository.create(giftCertificateEntity);
        this.giftCertificateRepository.delete(id);
        Optional<GiftCertificateEntity> optionalGiftCertificate = this.giftCertificateRepository.getById(id);
        assertThat(optionalGiftCertificate).isEmpty();
    }

    @ParameterizedTest(name = "{index} - {0}")
    @MethodSource("getGiftCertificateEntity")
    @DisplayName("Deleting all gift certificates")
    void checkDeleteAll(GiftCertificateEntity giftCertificateEntity) {
        long id = this.giftCertificateRepository.create(giftCertificateEntity);
        this.giftCertificateRepository.delete();
        Optional<GiftCertificateEntity> optionalGiftCertificate = this.giftCertificateRepository.getById(id);
        assertThat(optionalGiftCertificate).isEmpty();
    }

    static Stream<GiftCertificateEntity> getGiftCertificateEntity() {
        TagEntity tagEntity1 = TagEntity.builder().id(1L).name("first_tag").build();
        TagEntity tagEntity2 = TagEntity.builder().id(2L).name("second_tag").build();
        TagEntity tagEntity3 = TagEntity.builder().id(3L).name("third_tag").build();
        List<TagEntity> tags = List.of(tagEntity1, tagEntity2, tagEntity3);
        GiftCertificateEntity giftCertificateEntity = GiftCertificateEntity.builder()
                .name("gift")
                .description("certificate")
                .price(BigDecimal.valueOf(100))
                .duration(Duration.ofDays(10))
                .createDate(DateUtil.getCurrentDateISO8601())
                .lastUpdateDate(DateUtil.getCurrentDateISO8601())
                .tags(tags)
                .build();
        return Stream.of(giftCertificateEntity);
    }
}