package ru.clevertec.ecl.repository.api;

import ru.clevertec.ecl.dto.GiftCertificateFilter;
import ru.clevertec.ecl.repository.entity.GiftCertificateEntity;

import java.util.List;
import java.util.Optional;

/**
 * Interface {@code IGiftCertificateRepository} defines methods for CRUD operations with GiftCertificateEntity
 *
 * @version 1.0
 */
public interface IGiftCertificateRepository extends ICrudRepository<Long, GiftCertificateEntity> {

    /**
     * Method for creating GiftCertificateEntity
     *
     * @param entity entity to creating
     * @return created GiftCertificateEntity's id
     */
    @Override
    Long create(GiftCertificateEntity entity);

    /**
     * Method for getting GiftCertificateEntity by id
     *
     * @param id entity's id
     * @return GiftCertificateEntity in Optional wrapper
     */
    @Override
    Optional<GiftCertificateEntity> getById(Long id);

    /**
     * Method for getting all GiftCertificateEntities
     *
     * @return list of GiftCertificateEntities
     */
    @Override
    List<GiftCertificateEntity> getAll();

    /**
     * Method for updating entity
     *
     * @param id            entity's id
     * @param updatedEntity entity with updated fields
     * @return updated GiftCertificateEntity's id
     */
    @Override
    Long update(Long id, GiftCertificateEntity updatedEntity);

    /**
     * Method for deleting GiftCertificateEntity by id
     *
     * @param id entity's id
     */
    @Override
    void delete(Long id);

    /**
     * Method for getting all GiftCertificateEntities with filter
     *
     * @param filter filter with search fields
     * @return list of GiftCertificateEntities
     */
    List<GiftCertificateEntity> getAll(GiftCertificateFilter filter);

    /**
     * Method for deleting all GiftCertificateEntities
     */
    void delete();
}
