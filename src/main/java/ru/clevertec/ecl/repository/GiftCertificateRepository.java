package ru.clevertec.ecl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.clevertec.ecl.repository.entity.GiftCertificate;

import java.util.List;

/**
 * Interface {@code IGiftCertificateRepository} defines methods for CRUD operations with GiftCertificateEntity
 *
 * @version 0.1
 */
@Repository
public interface GiftCertificateRepository extends JpaRepository<GiftCertificate, Long> {

    List<GiftCertificate> findAllByNameLikeAndDescriptionLikeAndTagsContains(String name, String description, String tagName);

}
