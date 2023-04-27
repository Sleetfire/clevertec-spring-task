package ru.clevertec.ecl.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.clevertec.ecl.dto.GiftCertificate;
import ru.clevertec.ecl.repository.entity.GiftCertificateEntity;

import java.util.List;

@Mapper
public interface GiftCertificateMapper {
    GiftCertificateMapper INSTANCE = Mappers.getMapper(GiftCertificateMapper.class);

    GiftCertificate toDto(GiftCertificateEntity giftCertificateEntity);

    GiftCertificateEntity toEntity(GiftCertificate giftCertificate);

    List<GiftCertificate> toDto(List<GiftCertificateEntity> giftCertificates);

    List<GiftCertificateEntity> toEntity(List<GiftCertificate> giftCertificates);
}
