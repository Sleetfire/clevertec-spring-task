package ru.clevertec.ecl.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.ecl.dto.CreateGiftCertificateDto;
import ru.clevertec.ecl.dto.GiftCertificateDto;
import ru.clevertec.ecl.dto.GiftCertificateFilter;
import ru.clevertec.ecl.dto.PageDto;
import ru.clevertec.ecl.exception.IllegalRequestParamException;
import ru.clevertec.ecl.service.impl.GiftCertificateDecoratorServiceImpl;

import java.util.List;

@RestController
@RequestMapping(value = "/certificates")
public class GiftCertificateController {

    private final GiftCertificateDecoratorServiceImpl giftCertificateService;

    public GiftCertificateController(GiftCertificateDecoratorServiceImpl giftCertificateService) {
        this.giftCertificateService = giftCertificateService;
    }

    @PostMapping(value = "/",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<GiftCertificateDto> create(@RequestBody CreateGiftCertificateDto giftCertificateDto) {
        return new ResponseEntity<>(this.giftCertificateService.create(giftCertificateDto), HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<GiftCertificateDto> get(@PathVariable long id) {
        return new ResponseEntity<>(this.giftCertificateService.findById(id), HttpStatus.OK);
    }

    @GetMapping(value = "/", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<GiftCertificateDto>> getAll() {
        return new ResponseEntity<>(this.giftCertificateService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/page", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<PageDto<GiftCertificateDto>> getPage(@RequestParam(defaultValue = "0", required = false) int page,
                                                               @RequestParam(defaultValue = "1", required = false) int size) {
        if (page < 0) {
            throw new IllegalRequestParamException(40001);
        }
        if (size < 1) {
            throw new IllegalRequestParamException(40001);
        }
        Pageable pageable = PageRequest.of(page, size);
        return new ResponseEntity<>(giftCertificateService.findAll(pageable), HttpStatus.OK);

    }

    @GetMapping(value  = "/filter", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<GiftCertificateDto>> getAllFiltered(@RequestParam MultiValueMap<String, String> multiValueMap) {
        GiftCertificateFilter filter = GiftCertificateFilter.builder()
                .tagName(multiValueMap.get("tag_name") != null ? multiValueMap.get("tag_name").get(0) : null)
                .fieldPart(multiValueMap.get("search_word") != null ? multiValueMap.get("search_word").get(0) : null)
                .sortName(multiValueMap.get("sort_name") != null ? multiValueMap.get("sort_name").get(0) : null)
                .sortDate(multiValueMap.get("sort_date") != null ? multiValueMap.get("sort_date").get(0) : null)
                .build();
        return new ResponseEntity<>(this.giftCertificateService.findAllFiltered(filter), HttpStatus.OK);
    }

    @PatchMapping(value = "/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<GiftCertificateDto> update(@PathVariable long id, @RequestBody GiftCertificateDto certificate) {
        return new ResponseEntity<>(this.giftCertificateService.update(id, certificate), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        this.giftCertificateService.delete(id);
    }

}
