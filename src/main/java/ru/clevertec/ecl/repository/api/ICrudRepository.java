package ru.clevertec.ecl.repository.api;

import java.util.List;
import java.util.Optional;

/**
 * Interface {@code ICrudRepository} defines methods for CRUD operations for db
 *
 * @param <T> entity's id type
 * @param <E> entity type
 * @version 1.0
 */
public interface ICrudRepository<T, E> {

    /**
     * Method for creating entity
     *
     * @param entity entity to creating
     * @return created entity's id
     */
    T create(E entity);

    /**
     * Method for getting entity by id
     *
     * @param id entity's id
     * @return entity in Optional wrapper
     */
    Optional<E> getById(T id);

    /**
     * Method for getting all entities
     *
     * @return list of entities
     */
    List<E> getAll();

    /**
     * Method for updating entity
     *
     * @param id            entity's id
     * @param updatedEntity entity with updated fields
     * @return updated entity's id
     */
    T update(T id, E updatedEntity);

    /**
     * Method for deleting entity by id
     *
     * @param id entity's id
     */
    void delete(T id);

}
