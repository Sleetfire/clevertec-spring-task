package ru.clevertec.ecl.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.clevertec.ecl.repository.entity.GiftCertificate;

import java.util.List;
import java.util.Optional;

/**
 * Interface {@code GiftCertificateRepository} defines methods for CRUD operations with GiftCertificate
 *
 * @version 2.1
 */
@Repository
public interface GiftCertificateRepository extends JpaRepository<GiftCertificate, Long> {

    @Query("select g from GiftCertificate g left join g.tags t where (g.name like ?1 or g.description like ?2) or t.name = ?3")
    List<GiftCertificate> findAllFiltered(String name, String description, String tagName, Sort sort);

    Optional<GiftCertificate> findByName(String name);

}
