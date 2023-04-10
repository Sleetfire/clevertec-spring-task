package ru.clevertec.ecl.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.ecl.dto.PageDto;
import ru.clevertec.ecl.dto.TagDto;
import ru.clevertec.ecl.exception.IllegalRequestParamException;
import ru.clevertec.ecl.service.TagService;

import java.util.List;

@RestController
@RequestMapping(value = "/tags")
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @PostMapping(value = "/",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<TagDto> create(@RequestBody TagDto tagDto) {
        return new ResponseEntity<>(this.tagService.create(tagDto), HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<TagDto> get(@PathVariable long id) {
        return new ResponseEntity<>(this.tagService.findById(id), HttpStatus.OK);
    }

    @GetMapping(value = "/", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<TagDto>> getAll() {
        return new ResponseEntity<>(this.tagService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/page", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<PageDto<TagDto>> getPage(@RequestParam(defaultValue = "0", required = false) int page,
                                                   @RequestParam(defaultValue = "1", required = false) int size) {
        if (page < 0) {
            throw new IllegalRequestParamException(40001);
        }
        if (size < 1) {
            throw new IllegalRequestParamException(40001);
        }
        Pageable pageable = PageRequest.of(page, size);
        return new ResponseEntity<>(tagService.findPage(pageable), HttpStatus.OK);
    }

    @PatchMapping(value = "/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<TagDto> update(@PathVariable long id, @RequestBody TagDto tagDto) {
        return new ResponseEntity<>(this.tagService.update(id, tagDto), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        this.tagService.delete(id);
    }
}
