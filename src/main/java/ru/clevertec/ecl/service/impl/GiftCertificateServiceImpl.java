package ru.clevertec.ecl.service.impl;

import org.springframework.stereotype.Service;
import ru.clevertec.ecl.dto.GiftCertificate;
import ru.clevertec.ecl.dto.GiftCertificateFilter;
import ru.clevertec.ecl.dto.SingleResponseError;
import ru.clevertec.ecl.exception.EssenceNotFoundException;
import ru.clevertec.ecl.mapper.GiftCertificateMapper;
import ru.clevertec.ecl.repository.GiftCertificateRepository;
import ru.clevertec.ecl.repository.entity.GiftCertificateEntity;
import ru.clevertec.ecl.service.GiftCertificateService;
import ru.clevertec.ecl.util.DateUtil;

import java.util.List;
import java.util.Optional;

import static ru.clevertec.ecl.util.ErrorMessage.NOT_FOUND_ERROR;
import static ru.clevertec.ecl.util.ErrorMessage.NOT_FOUND_WITH_PARAMS;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private final GiftCertificateRepository giftCertificateDecoratorRepositoryImpl;
    private final GiftCertificateMapper giftCertificateMapper;

    public GiftCertificateServiceImpl(GiftCertificateRepository giftCertificateDecoratorRepositoryImpl,
                                      GiftCertificateMapper giftCertificateMapper) {
        this.giftCertificateDecoratorRepositoryImpl = giftCertificateDecoratorRepositoryImpl;
        this.giftCertificateMapper = giftCertificateMapper;
    }

    @Override
    public GiftCertificate create(GiftCertificate entity) {
        String currentDate = DateUtil.getCurrentDateISO8601();
        entity.setCreateDate(currentDate);
        entity.setLastUpdateDate(currentDate);
        long certificateID = this.giftCertificateDecoratorRepositoryImpl.create(giftCertificateMapper.toEntity(entity));
        return this.getById(certificateID);
    }

    @Override
    public GiftCertificate getById(long id) {
        Optional<GiftCertificateEntity> optionalGiftCertificate = this.giftCertificateDecoratorRepositoryImpl.getById(id);
        if (optionalGiftCertificate.isEmpty()) {
            throw new EssenceNotFoundException(SingleResponseError.of(NOT_FOUND_ERROR, 40401));
        }
        return giftCertificateMapper.toDto(optionalGiftCertificate.get());
    }

    @Override
    public List<GiftCertificate> getAll() {
        List<GiftCertificateEntity> giftCertificates = this.giftCertificateDecoratorRepositoryImpl.getAll();
        if (giftCertificates.isEmpty()) {
            throw new EssenceNotFoundException(SingleResponseError.of(NOT_FOUND_ERROR, 40402));
        }
        return giftCertificateMapper.toDto(giftCertificates);
    }

    @Override
    public List<GiftCertificate> getAll(GiftCertificateFilter filter) {
        List<GiftCertificateEntity> giftCertificates = this.giftCertificateDecoratorRepositoryImpl.getAll(filter);
        if (giftCertificates.isEmpty()) {
            throw new EssenceNotFoundException(SingleResponseError.of(NOT_FOUND_WITH_PARAMS, 40403));
        }
        return giftCertificateMapper.toDto(giftCertificates);
    }

    @Override
    public GiftCertificate update(long id, GiftCertificate updatedEntity) {
        this.getById(id);
        this.giftCertificateDecoratorRepositoryImpl.update(id, giftCertificateMapper.toEntity(updatedEntity));
        return this.getById(id);
    }

    @Override
    public void delete(long id) {
        this.getById(id);
        this.giftCertificateDecoratorRepositoryImpl.delete(id);
    }

    @Override
    public void delete() {
        this.giftCertificateDecoratorRepositoryImpl.delete();
    }
}
