package ru.clevertec.ecl.service;

import ru.clevertec.ecl.dto.TagDto;

import java.util.List;

/**
 * Interface {@code ITagService} defines methods for CRUD operations with Tags
 *
 * @version 0.1
 */
public interface TagService extends CrudService<TagDto, Long> {

    /**
     * Method for creating Tag entity
     *
     * @param tagDto entity to creating
     * @return creating Tag entity
     */
    @Override
    TagDto create(TagDto tagDto);

    /**
     * Method for getting Tag entity by id
     * @param id entity's id
     * @return Tag entity
     */
    @Override
    TagDto findById(Long id);

    /**
     * Method for getting all Tag entities
     * @return list of Tag entities
     */
    @Override
    List<TagDto> findAll();

    /**
     * Method for updating Tag entity
     * @param id            entity's id
     * @param updated entity with updated fields
     * @return updated Tag entity
     */
    @Override
    TagDto update(Long id, TagDto updated);

    /**
     * Method for deleting Tag entity by id
     * @param id entity's id
     */
    @Override
    void delete(Long id);

    /**
     * Method for getting Tag entity by Tag name
     * @param name Tag name
     * @return Tag entity
     */
    TagDto findByName(String name);

}
