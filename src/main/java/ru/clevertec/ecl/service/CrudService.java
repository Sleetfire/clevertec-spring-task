package ru.clevertec.ecl.service;

import java.util.List;

/**
 * Interface {@code ICrudService} defines methods for CRUD operations
 *
 * @param <T> dto type
 * @version 2.1
 */
public interface CrudService<D, T> {

    /**
     * Method for creating entity
     *
     * @param entity entity to creating
     * @return created entity
     */
    D create(D entity);

    /**
     * Method for getting entity by id
     *
     * @param id entity's id
     * @return entity
     */
    D findById(T id);

    /**
     * Method for getting all entities
     *
     * @return list of entities
     */
    List<D> findAll();

    /**
     * Method for updating entity
     *
     * @param id            entity's id
     * @param updatedEntity entity with updated fields
     * @return updated entity
     */
    D update(T id, D updatedEntity);

    /**
     * Method for deleting entity by id
     *
     * @param id entity's id
     */
    void delete(T id);

}
