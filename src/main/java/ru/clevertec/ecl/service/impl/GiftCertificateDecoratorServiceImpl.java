package ru.clevertec.ecl.service.impl;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.clevertec.ecl.dto.*;
import ru.clevertec.ecl.exception.EssenceNotFoundException;
import ru.clevertec.ecl.service.GiftCertificateService;
import ru.clevertec.ecl.service.TagService;

import java.util.ArrayList;
import java.util.List;

@Service
public class GiftCertificateDecoratorServiceImpl {

    private final GiftCertificateService giftCertificateService;
    private final TagService tagService;

    public GiftCertificateDecoratorServiceImpl(GiftCertificateService giftCertificateService, TagService tagService) {
        this.giftCertificateService = giftCertificateService;
        this.tagService = tagService;
    }

    public GiftCertificateDto create(CreateGiftCertificateDto giftCertificate) {
        List<TagDto> tags = new ArrayList<>();
        List<String> tagNames = giftCertificate.getTags();
        if (!tagNames.isEmpty()) {
            giftCertificate.getTags().forEach(name -> {
                try {
                    tags.add(tagService.findByName(name));
                } catch (EssenceNotFoundException e) {
                    TagDto tagDto = TagDto.builder()
                            .name(name)
                            .build();
                    tags.add(tagService.create(tagDto));
                }
            });
        }
        GiftCertificateDto giftCertificateDto = GiftCertificateDto.builder()
                .name(giftCertificate.getName())
                .description(giftCertificate.getDescription())
                .price(giftCertificate.getPrice())
                .duration(giftCertificate.getDuration())
                .tags(tags)
                .build();
        return giftCertificateService.create(giftCertificateDto);
    }

    public GiftCertificateDto findById(Long id) {
        return giftCertificateService.findById(id);
    }

    public List<GiftCertificateDto> findAll() {
        return giftCertificateService.findAll();
    }

    public PageDto<GiftCertificateDto> findAll(Pageable pageable) {
        return giftCertificateService.findAll(pageable);
    }

    public GiftCertificateDto update(Long id, GiftCertificateDto updatedEntity) {
        return giftCertificateService.update(id, updatedEntity);
    }

    public void delete(Long id) {
        giftCertificateService.delete(id);
    }

    public List<GiftCertificateDto> findAllFiltered(GiftCertificateFilter filter) {
        return giftCertificateService.findAllFiltered(filter);
    }

    public GiftCertificateDto findByName(String name) {
        return giftCertificateService.findByName(name);
    }
}
