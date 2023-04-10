package ru.clevertec.ecl.service;

import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.dto.GiftCertificateDto;
import ru.clevertec.ecl.dto.GiftCertificateFilter;
import ru.clevertec.ecl.dto.PageDto;

import java.util.List;

/**
 * Interface {@code IGiftCertificateService} defines methods for CRUD operations with GiftCertificates
 *
 * @version 0.1
 */
public interface GiftCertificateService extends CrudService<GiftCertificateDto, Long> {

    /**
     * Method for creating GiftCertificate entity
     *
     * @param entity entity to creating
     * @return created GiftCertificate
     */
    @Override
    GiftCertificateDto create(GiftCertificateDto entity);

    /**
     * Method for getting GiftCertificate entity by id
     *
     * @param id entity's id
     * @return GiftCertificate entity
     */
    @Override
    GiftCertificateDto findById(Long id);

    /**
     * Method for getting all GiftCertificate entities
     *
     * @return list of GiftCertificate entities
     */
    @Override
    List<GiftCertificateDto> findAll();

    /**
     * Method for getting page of gift certificates
     *
     * @param pageable for pagination
     * @return dto page of gift certificates
     */
    PageDto<GiftCertificateDto> findAll(Pageable pageable);

    /**
     * Method for updating GiftCertificate entity
     *
     * @param id            entity's id
     * @param updatedEntity entity with updated fields
     * @return updated GiftCertificate entity
     */
    @Override
    GiftCertificateDto update(Long id, GiftCertificateDto updatedEntity);

    /**
     * Method for deleting GiftCertificate entity by id
     *
     * @param id entity's id
     */
    @Override
    void delete(Long id);

    /**
     * Method for getting GiftCertificate entities with filter
     *
     * @param filter filter with search fields
     * @return list of GiftCertificate entities
     */
    List<GiftCertificateDto> findAllFiltered(GiftCertificateFilter filter);

}
