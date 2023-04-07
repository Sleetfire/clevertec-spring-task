package ru.clevertec.ecl.service.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.clevertec.ecl.dto.GiftCertificate;
import ru.clevertec.ecl.dto.GiftCertificateFilter;
import ru.clevertec.ecl.dto.SingleResponseError;
import ru.clevertec.ecl.exception.EssenceNotFoundException;
import ru.clevertec.ecl.mapper.GiftCertificateMapper;
import ru.clevertec.ecl.repository.api.GiftCertificateRepository;
import ru.clevertec.ecl.repository.entity.GiftCertificateEntity;
import ru.clevertec.ecl.util.DateUtil;

import java.util.List;
import java.util.Optional;

@Service
public class GiftCertificateServiceImpl implements ru.clevertec.ecl.service.GiftCertificateService {

    private final GiftCertificateRepository giftCertificateRepository;

    public GiftCertificateServiceImpl(@Qualifier("giftCertificateDecoratorRepositoryImpl")
                                              GiftCertificateRepository giftCertificateRepository) {
        this.giftCertificateRepository = giftCertificateRepository;
    }

    @Override
    public GiftCertificate create(GiftCertificate entity) {
        String currentDate = DateUtil.getCurrentDateISO8601();
        entity.setCreateDate(currentDate);
        entity.setLastUpdateDate(currentDate);
        long certificateID = this.giftCertificateRepository.create(GiftCertificateMapper.INSTANCE.toEntity(entity));
        return this.findById(certificateID);
    }

    @Override
    public GiftCertificate findById(long id) {
        Optional<GiftCertificateEntity> optionalGiftCertificate = this.giftCertificateRepository.getById(id);
        if (optionalGiftCertificate.isEmpty()) {
            throw new EssenceNotFoundException(SingleResponseError.of("Requested resource was not found",
                    40401));
        }
        return GiftCertificateMapper.INSTANCE.toDto(optionalGiftCertificate.get());
    }

    @Override
    public List<GiftCertificate> findAll() {
        List<GiftCertificateEntity> giftCertificates = this.giftCertificateRepository.getAll();
        if (giftCertificates.isEmpty()) {
            throw new EssenceNotFoundException(SingleResponseError.of("Requested resource was not found",
                    40402));
        }
        return GiftCertificateMapper.INSTANCE.toDto(giftCertificates);
    }

    @Override
    public List<GiftCertificate> getAll(GiftCertificateFilter filter) {
        List<GiftCertificateEntity> giftCertificates = this.giftCertificateRepository.getAll(filter);
        if (giftCertificates.isEmpty()) {
            throw new EssenceNotFoundException(SingleResponseError.of("Requested resource with current " +
                            "params was not found",
                    40403));
        }
        return GiftCertificateMapper.INSTANCE.toDto(giftCertificates);
    }

    @Override
    public GiftCertificate update(long id, GiftCertificate updatedEntity) {
        this.findById(id);
        this.giftCertificateRepository.update(id, GiftCertificateMapper.INSTANCE.toEntity(updatedEntity));
        return this.findById(id);
    }

    @Override
    public void delete(long id) {
        this.findById(id);
        this.giftCertificateRepository.delete(id);
    }

    @Override
    public void delete() {
        this.giftCertificateRepository.delete();
    }
}
