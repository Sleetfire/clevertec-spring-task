package ru.clevertec.ecl.service.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.clevertec.ecl.dto.GiftCertificate;
import ru.clevertec.ecl.dto.GiftCertificateFilter;
import ru.clevertec.ecl.dto.SingleResponseError;
import ru.clevertec.ecl.exception.EssenceNotFoundException;
import ru.clevertec.ecl.mapper.GiftCertificateMapper;
import ru.clevertec.ecl.repository.GiftCertificateRepository;
import ru.clevertec.ecl.repository.entity.GiftCertificateEntity;
import ru.clevertec.ecl.util.DateUtil;

import java.util.List;
import java.util.Optional;

@Service
public class GiftCertificateServiceImpl implements ru.clevertec.ecl.service.GiftCertificateService {

    private final GiftCertificateRepository giftCertificateRepository;
    private final GiftCertificateMapper giftCertificateMapper;
    private static final String NOT_FOUND_ERROR = "Requested resource was not found";
    private static final String NOT_FOUND_PARAMS_ERROR = "Requested resource with current params was not found";

    public GiftCertificateServiceImpl(@Qualifier("giftCertificateDecoratorRepositoryImpl")
                                      GiftCertificateRepository giftCertificateRepository,
                                      GiftCertificateMapper giftCertificateMapper) {
        this.giftCertificateRepository = giftCertificateRepository;
        this.giftCertificateMapper = giftCertificateMapper;
    }

    @Override
    public GiftCertificate create(GiftCertificate entity) {
        String currentDate = DateUtil.getCurrentDateISO8601();
        entity.setCreateDate(currentDate);
        entity.setLastUpdateDate(currentDate);
        long certificateID = giftCertificateRepository.create(giftCertificateMapper.toEntity(entity));
        return findById(certificateID);
    }

    @Override
    public GiftCertificate findById(long id) {
        Optional<GiftCertificateEntity> optionalGiftCertificate = giftCertificateRepository.findById(id);
        if (optionalGiftCertificate.isEmpty()) {
            throw new EssenceNotFoundException(SingleResponseError.of(NOT_FOUND_ERROR, 40401));
        }
        return giftCertificateMapper.toDto(optionalGiftCertificate.get());
    }

    @Override
    public List<GiftCertificate> findAll() {
        List<GiftCertificateEntity> giftCertificates = giftCertificateRepository.findAll();
        if (giftCertificates.isEmpty()) {
            throw new EssenceNotFoundException(SingleResponseError.of(NOT_FOUND_ERROR, 40402));
        }
        return giftCertificateMapper.toDto(giftCertificates);
    }

    @Override
    public List<GiftCertificate> getAll(GiftCertificateFilter filter) {
        List<GiftCertificateEntity> giftCertificates = giftCertificateRepository.getAll(filter);
        if (giftCertificates.isEmpty()) {
            throw new EssenceNotFoundException(SingleResponseError.of(NOT_FOUND_PARAMS_ERROR, 40403));
        }
        return giftCertificateMapper.toDto(giftCertificates);
    }

    @Override
    public GiftCertificate update(long id, GiftCertificate updatedEntity) {
        findById(id);
        giftCertificateRepository.update(id, giftCertificateMapper.toEntity(updatedEntity));
        return findById(id);
    }

    @Override
    public void delete(long id) {
        findById(id);
        giftCertificateRepository.delete(id);
    }

    @Override
    public void delete() {
        giftCertificateRepository.delete();
    }
}
