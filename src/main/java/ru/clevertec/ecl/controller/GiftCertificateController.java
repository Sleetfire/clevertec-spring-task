package ru.clevertec.ecl.controller;

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
import ru.clevertec.ecl.dto.GiftCertificate;
import ru.clevertec.ecl.dto.GiftCertificateFilter;
import ru.clevertec.ecl.service.api.IGiftCertificateService;

import java.util.List;

@RestController
@RequestMapping(value = "/certificates")
public class GiftCertificateController {

    private final IGiftCertificateService giftCertificateService;

    public GiftCertificateController(IGiftCertificateService giftCertificateService) {
        this.giftCertificateService = giftCertificateService;
    }

    @PostMapping(value = "/",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<GiftCertificate> create(@RequestBody GiftCertificate giftCertificate) {
        return new ResponseEntity<>(this.giftCertificateService.create(giftCertificate), HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<GiftCertificate> get(@PathVariable long id) {
        return new ResponseEntity<>(this.giftCertificateService.findById(id), HttpStatus.OK);
    }

    @GetMapping(value = "/", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<GiftCertificate>> getAll() {
        return new ResponseEntity<>(this.giftCertificateService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value  = "/filter", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<GiftCertificate>> getAllFiltered(@RequestParam MultiValueMap<String, String> multiValueMap) {
        GiftCertificateFilter filter = GiftCertificateFilter.builder()
                .tagName(multiValueMap.get("tag_name").get(0))
                .fieldPart(multiValueMap.get("search_word").get(0))
                .sortName(multiValueMap.get("sort_name").get(0))
                .sortDate(multiValueMap.get("sort_date").get(0))
                .build();
        return new ResponseEntity<>(this.giftCertificateService.getAll(filter), HttpStatus.OK);
    }

    @PatchMapping(value = "/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<GiftCertificate> update(@PathVariable long id, @RequestBody GiftCertificate certificate) {
        return new ResponseEntity<>(this.giftCertificateService.update(id, certificate), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        this.giftCertificateService.delete(id);
    }

}
