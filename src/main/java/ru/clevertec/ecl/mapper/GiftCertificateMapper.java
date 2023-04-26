package ru.clevertec.ecl.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.clevertec.ecl.dto.GiftCertificateDto;
import ru.clevertec.ecl.repository.entity.GiftCertificate;

import java.util.List;

@Mapper
public interface GiftCertificateMapper {

    GiftCertificateDto toDto(GiftCertificate giftCertificate);

    GiftCertificate toEntity(GiftCertificateDto giftCertificateDto);

    List<GiftCertificateDto> toDto(List<GiftCertificate> giftCertificates);

    List<GiftCertificate> toEntity(List<GiftCertificateDto> giftCertificateDtos);
}
