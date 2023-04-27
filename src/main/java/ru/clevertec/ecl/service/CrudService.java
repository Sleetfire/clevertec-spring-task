package ru.clevertec.ecl.service;

import java.util.List;

/**
 * Interface {@code ICrudService} defines methods for CRUD operations
 *
 * @param <T> dto type
 * @version 0.1
 */
public interface CrudService<T> {

    /**
     * Method for creating entity
     *
     * @param entity entity to creating
     * @return created entity
     */
    T create(T entity);

    /**
     * Method for getting entity by id
     *
     * @param id entity's id
     * @return entity
     */
    T getById(long id);

    /**
     * Method for getting all entities
     *
     * @return list of entities
     */
    List<T> getAll();

    /**
     * Method for updating entity
     *
     * @param id            entity's id
     * @param updatedEntity entity with updated fields
     * @return updated entity
     */
    T update(long id, T updatedEntity);

    /**
     * Method for deleting entity by id
     *
     * @param id entity's id
     */
    void delete(long id);

}
