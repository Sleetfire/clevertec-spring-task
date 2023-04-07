package ru.clevertec.ecl.service.api;

import ru.clevertec.ecl.dto.GiftCertificate;
import ru.clevertec.ecl.dto.GiftCertificateFilter;

import java.util.List;

/**
 * Interface {@code IGiftCertificateService} defines methods for CRUD operations with GiftCertificates
 *
 * @version 0.1
 */
public interface IGiftCertificateService extends ICrudService<GiftCertificate> {

    /**
     * Method for creating GiftCertificate entity
     *
     * @param entity entity to creating
     * @return created GiftCertificate
     */
    @Override
    GiftCertificate create(GiftCertificate entity);

    /**
     * Method for getting GiftCertificate entity by id
     *
     * @param id entity's id
     * @return GiftCertificate entity
     */
    @Override
    GiftCertificate findById(long id);

    /**
     * Method for getting all GiftCertificate entities
     *
     * @return list of GiftCertificate entities
     */
    @Override
    List<GiftCertificate> findAll();

    /**
     * Method for updating GiftCertificate entity
     *
     * @param id            entity's id
     * @param updatedEntity entity with updated fields
     * @return updated GiftCertificate entity
     */
    @Override
    GiftCertificate update(long id, GiftCertificate updatedEntity);

    /**
     * Method for deleting GiftCertificate entity by id
     *
     * @param id entity's id
     */
    @Override
    void delete(long id);

    /**
     * Method for getting GiftCertificate entities with filter
     *
     * @param filter filter with search fields
     * @return list of GiftCertificate entities
     */
    List<GiftCertificate> getAll(GiftCertificateFilter filter);

    /**
     * Method for delete all GiftCertificate entities
     */
    void delete();
}
