package ru.clevertec.ecl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.clevertec.ecl.repository.entity.Tag;

import java.util.Optional;

/**
 * Interface {@code ITagRepository} defines methods for CRUD operations with TagEntity
 */
@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    Optional<Tag> findByName(String name);

}
