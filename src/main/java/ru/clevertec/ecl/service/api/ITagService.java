package ru.clevertec.ecl.service.api;

import ru.clevertec.ecl.dto.Tag;

import java.util.List;

/**
 * Interface {@code ITagService} defines methods for CRUD operations with Tags
 *
 * @version 0.1
 */
public interface ITagService extends ICrudService<Tag> {

    /**
     * Method for creating Tag entity
     *
     * @param tag entity to creating
     * @return creating Tag entity
     */
    @Override
    Tag create(Tag tag);

    /**
     * Method for getting Tag entity by id
     * @param id entity's id
     * @return Tag entity
     */
    @Override
    Tag findById(long id);

    /**
     * Method for getting all Tag entities
     * @return list of Tag entities
     */
    @Override
    List<Tag> findAll();

    /**
     * Method for updating Tag entity
     * @param id            entity's id
     * @param updated entity with updated fields
     * @return updated Tag entity
     */
    @Override
    Tag update(long id, Tag updated);

    /**
     * Method for deleting Tag entity by id
     * @param id entity's id
     */
    @Override
    void delete(long id);

    /**
     * Method for getting Tag entity by Tag name
     * @param name Tag name
     * @return Tag entity
     */
    Tag findByName(String name);

    /**
     * Method for delete all Tag entities
     */
    void delete();

}
