package ru.clevertec.ecl.repository.api;

import ru.clevertec.ecl.repository.entity.TagEntity;

import java.util.List;
import java.util.Optional;

/**
 * Interface {@code ITagRepository} defines methods for CRUD operations with TagEntity
 *
 * @version 1.0
 */
public interface ITagRepository extends ICrudRepository<Long, TagEntity> {

    /**
     * Method for creating TagEntity
     *
     * @param entity entity to creating
     * @return created TagEntity's id
     */
    @Override
    Long create(TagEntity entity);

    /**
     * Method for getting TagEntity by id
     *
     * @param id entity's id
     * @return TagEntity in Optional wrapper
     */
    @Override
    Optional<TagEntity> getById(Long id);

    /**
     * Method for getting all TagEntities
     *
     * @return list of TagEntities
     */
    @Override
    List<TagEntity> getAll();

    /**
     * Method for updating entity
     *
     * @param id      entity's id
     * @param updated entity with updated fields
     * @return updated TagEntity's id
     */
    @Override
    Long update(Long id, TagEntity updated);

    /**
     * Method for deleting TagEntity by id
     *
     * @param id entity's id
     */
    @Override
    void delete(Long id);

    /**
     * Method for getting TagEntity by name
     *
     * @param name TagEntity's name
     * @return TagEntity in Optional wrapper
     */
    Optional<TagEntity> getByName(String name);

    /**
     * Method for deleting all TagEntities
     */
    void delete();

}
