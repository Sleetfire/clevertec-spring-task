package ru.clevertec.ecl.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.clevertec.ecl.dto.GiftCertificateDto;
import ru.clevertec.ecl.repository.entity.GiftCertificate;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GiftCertificateMapper {
    GiftCertificateMapper INSTANCE = Mappers.getMapper(GiftCertificateMapper.class);

    GiftCertificateDto toDto(GiftCertificate giftCertificate);

    GiftCertificate toEntity(GiftCertificateDto giftCertificateDto);

    List<GiftCertificateDto> toDto(List<GiftCertificate> giftCertificates);

    List<GiftCertificate> toEntity(List<GiftCertificateDto> giftCertificateDtos);
}
