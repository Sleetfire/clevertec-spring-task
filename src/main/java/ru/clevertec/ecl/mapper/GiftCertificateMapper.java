package ru.clevertec.ecl.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.clevertec.ecl.dto.GiftCertificateDto;
import ru.clevertec.ecl.repository.entity.GiftCertificateEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GiftCertificateMapper {
    GiftCertificateMapper INSTANCE = Mappers.getMapper(GiftCertificateMapper.class);

    GiftCertificateDto toDto(GiftCertificateEntity giftCertificateEntity);

    GiftCertificateEntity toEntity(GiftCertificateDto giftCertificateDto);

    List<GiftCertificateDto> toDto(List<GiftCertificateEntity> giftCertificates);

    List<GiftCertificateEntity> toEntity(List<GiftCertificateDto> giftCertificateDtos);
}
