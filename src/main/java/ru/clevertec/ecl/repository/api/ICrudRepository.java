package ru.clevertec.ecl.repository.api;

import java.util.List;
import java.util.Optional;

/**
 * Interface {@code ICrudRepository} defines methods for CRUD operations for db
 *
 * @param <T> entity type
 * @version 0.1
 */
public interface ICrudRepository<T> {

    /**
     * Method for creating entity
     *
     * @param entity entity to creating
     * @return created entity's id
     */
    long create(T entity);

    /**
     * Method for getting entity by id
     *
     * @param id entity's id
     * @return entity in Optional wrapper
     */
    Optional<T> get(long id);

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
     * @return updated entity's id
     */
    long update(long id, T updatedEntity);

    /**
     * Method for deleting entity by id
     *
     * @param id entity's id
     */
    void delete(long id);

}
