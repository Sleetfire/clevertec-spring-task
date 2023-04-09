package ru.clevertec.ecl.service.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.clevertec.ecl.dto.GiftCertificateDto;
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

    public GiftCertificateServiceImpl(@Qualifier("giftCertificateDecoratorRepositoryImpl")
                                      GiftCertificateRepository giftCertificateRepository,
                                      GiftCertificateMapper giftCertificateMapper) {
        this.giftCertificateRepository = giftCertificateRepository;
        this.giftCertificateMapper = giftCertificateMapper;
    }

    @Override
    public GiftCertificateDto create(GiftCertificateDto entity) {
        String currentDate = DateUtil.getCurrentDateISO8601();
        entity.setCreateDate(currentDate);
        entity.setLastUpdateDate(currentDate);
        long certificateID = giftCertificateRepository.create(giftCertificateMapper.toEntity(entity));
        return findById(certificateID);
    }

    @Override
    public GiftCertificateDto findById(long id) {
        Optional<GiftCertificateEntity> optionalGiftCertificate = giftCertificateRepository.findById(id);
        if (optionalGiftCertificate.isEmpty()) {
            throw new EssenceNotFoundException( 40401);
        }
        return giftCertificateMapper.toDto(optionalGiftCertificate.get());
    }

    @Override
    public List<GiftCertificateDto> findAll() {
        List<GiftCertificateEntity> giftCertificates = giftCertificateRepository.findAll();
        if (giftCertificates.isEmpty()) {
            throw new EssenceNotFoundException(40402);
        }
        return giftCertificateMapper.toDto(giftCertificates);
    }

    @Override
    public List<GiftCertificateDto> getAll(GiftCertificateFilter filter) {
        List<GiftCertificateEntity> giftCertificates = giftCertificateRepository.getAll(filter);
        if (giftCertificates.isEmpty()) {
            throw new EssenceNotFoundException(40403);
        }
        return giftCertificateMapper.toDto(giftCertificates);
    }

    @Override
    public GiftCertificateDto update(long id, GiftCertificateDto updatedEntity) {
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
