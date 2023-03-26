package ru.clevertec.ecl.controller.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.ecl.dto.Tag;
import ru.clevertec.ecl.service.api.ITagService;

import java.util.List;

@RestController
@RequestMapping(value = "/tags")
public class TagController {

    private final ITagService tagService;

    public TagController(ITagService tagService) {
        this.tagService = tagService;
    }

    @PostMapping(value = {"", "/"},
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Tag> create(@RequestBody Tag tag) {
        return new ResponseEntity<>(this.tagService.create(tag), HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Tag> get(@PathVariable long id) {
        return new ResponseEntity<>(this.tagService.get(id), HttpStatus.OK);
    }

    @GetMapping(value = {"", "/"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<Tag>> getAll() {
        return new ResponseEntity<>(this.tagService.getAll(), HttpStatus.OK);
    }

    @PatchMapping(value = "/update/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Tag> update(@PathVariable long id, @RequestBody Tag tag) {
        return new ResponseEntity<>(this.tagService.update(id, tag), HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        this.tagService.delete(id);
    }
}
