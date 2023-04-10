package ru.clevertec.ecl.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.clevertec.ecl.dto.GiftCertificateDto;
import ru.clevertec.ecl.dto.GiftCertificateFilter;
import ru.clevertec.ecl.dto.PageDto;
import ru.clevertec.ecl.exception.EssenceNotFoundException;
import ru.clevertec.ecl.mapper.GiftCertificateMapper;
import ru.clevertec.ecl.repository.GiftCertificateRepository;
import ru.clevertec.ecl.repository.entity.GiftCertificate;
import ru.clevertec.ecl.service.GiftCertificateService;
import ru.clevertec.ecl.util.DateUtil;
import ru.clevertec.ecl.util.SortingUtil;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private final GiftCertificateRepository giftCertificateRepository;
    private final GiftCertificateMapper giftCertificateMapper;

    public GiftCertificateServiceImpl(GiftCertificateRepository giftCertificateRepository,
                                      GiftCertificateMapper giftCertificateMapper) {
        this.giftCertificateRepository = giftCertificateRepository;
        this.giftCertificateMapper = giftCertificateMapper;
    }

    @Override
    public GiftCertificateDto create(GiftCertificateDto entity) {
        String currentDate = DateUtil.getCurrentDateISO8601();
        entity.setCreateDate(currentDate);
        entity.setLastUpdateDate(currentDate);
        GiftCertificate fromDb = giftCertificateRepository.save(giftCertificateMapper.toEntity(entity));
        return giftCertificateMapper.toDto(fromDb);
    }

    @Override
    public GiftCertificateDto findById(Long id) {
        Optional<GiftCertificate> optionalGiftCertificate = giftCertificateRepository.findById(id);
        if (optionalGiftCertificate.isEmpty()) {
            throw new EssenceNotFoundException(40401);
        }
        return giftCertificateMapper.toDto(optionalGiftCertificate.get());
    }

    @Override
    public List<GiftCertificateDto> findAll() {
        List<GiftCertificate> giftCertificates = giftCertificateRepository.findAll();
        if (giftCertificates.isEmpty()) {
            throw new EssenceNotFoundException(40402);
        }
        return giftCertificateMapper.toDto(giftCertificates);
    }

    @Override
    public PageDto<GiftCertificateDto> findAll(Pageable pageable) {
        Page<GiftCertificate> giftCertificatePage = giftCertificateRepository.findAll(pageable);
        return convertToPageDto(giftCertificatePage);
    }

    @Override
    public List<GiftCertificateDto> findAllFiltered(GiftCertificateFilter filter) {
        String pattern = "%" + filter.getFieldPart() + "%";
        String tagName = filter.getTagName();
        Sort sort = SortingUtil.getSort(filter);

        List<GiftCertificate> giftCertificates = giftCertificateRepository
                .findAllFiltered(pattern, pattern, tagName, sort);

        if (giftCertificates.isEmpty()) {
            throw new EssenceNotFoundException(40403);
        }
        return giftCertificateMapper.toDto(giftCertificates);
    }

    @Override
    public GiftCertificateDto update(Long id, GiftCertificateDto updatedEntity) {
        GiftCertificateDto giftCertificateFromDb = findById(id);

        String name = updatedEntity.getName();
        String description = updatedEntity.getDescription();
        BigDecimal price = updatedEntity.getPrice();
        Duration duration = updatedEntity.getDuration();

        if (name != null && !Objects.equals(name, giftCertificateFromDb.getName())) {
            giftCertificateFromDb.setName(name);
        }

        if (description != null && !Objects.equals(description, giftCertificateFromDb.getDescription())) {
            giftCertificateFromDb.setDescription(description);
        }

        if (price != null && giftCertificateFromDb.getPrice().compareTo(price) != 0) {
            giftCertificateFromDb.setPrice(price);
        }

        if (duration != null && !Objects.equals(duration, giftCertificateFromDb.getDuration())) {
            giftCertificateFromDb.setDuration(duration);
        }

        giftCertificateRepository.save(giftCertificateMapper.toEntity(giftCertificateFromDb));
        return findById(id);
    }

    @Override
    public void delete(Long id) {
        findById(id);
        giftCertificateRepository.deleteById(id);
    }

    private PageDto<GiftCertificateDto> convertToPageDto(Page<GiftCertificate> page) {
        List<GiftCertificate> content = page.getContent();
        if (content.isEmpty()) {
            throw new EssenceNotFoundException(40402);
        }
        return PageDto.Builder.createBuilder(GiftCertificateDto.class)
                .setNumber(page.getNumber())
                .setSize(page.getSize())
                .setTotalPages(page.getTotalPages())
                .setTotalElements(page.getTotalElements())
                .setFirst(page.isFirst())
                .setNumberOfElements(page.getNumberOfElements())
                .setLast(page.isLast())
                .setContent(giftCertificateMapper.toDto(content))
                .build();
    }

}
