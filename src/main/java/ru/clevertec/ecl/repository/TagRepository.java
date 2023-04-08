package ru.clevertec.ecl.repository;

import ru.clevertec.ecl.repository.entity.TagEntity;

import java.util.List;
import java.util.Optional;

/**
 * Interface {@code ITagRepository} defines methods for CRUD operations with TagEntity
 */
public interface TagRepository extends CrudRepository<TagEntity> {

    /**
     * Method for creating TagEntity
     * @param entity entity to creating
     * @return created TagEntity's id
     */
    @Override
    long create(TagEntity entity);

    /**
     * Method for getting TagEntity by id
     * @param id entity's id
     * @return TagEntity in Optional wrapper
     */
    @Override
    Optional<TagEntity> findById(long id);

    /**
     * Method for getting all TagEntities
     * @return list of TagEntities
     */
    @Override
    List<TagEntity> findAll();

    /**
     * Method for updating entity
     * @param id            entity's id
     * @param updated entity with updated fields
     * @return updated TagEntity's id
     */
    @Override
    long update(long id, TagEntity updated);

    /**
     * Method for deleting TagEntity by id
     * @param id entity's id
     */
    @Override
    void delete(long id);

    /**
     * Method for getting TagEntity by name
     * @param name TagEntity's name
     * @return TagEntity in Optional wrapper
     */
    Optional<TagEntity> findByName(String name);

    /**
     * Method for deleting all TagEntities
     */
    void delete();

}
