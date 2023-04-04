package ru.clevertec.ecl.repository;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import ru.clevertec.ecl.repository.api.ITagRepository;
import ru.clevertec.ecl.repository.util.HibernateUtil;
import ru.clevertec.ecl.repository.entity.TagEntity;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Repository
public class TagRepository implements ITagRepository {

    @Override
    public Long create(TagEntity entity) {
        return HibernateUtil.saveEntity(entity);
    }

    @Override
    public Optional<TagEntity> getById(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<TagEntity> selectCriteriaQuery = cb.createQuery(TagEntity.class);
        Root<TagEntity> root = selectCriteriaQuery.from(TagEntity.class);
        selectCriteriaQuery.select(root).where(
                cb.equal(root.get("id"), id)
        );
        return HibernateUtil.getSingleEntity(session, selectCriteriaQuery);
    }

    @Override
    public List<TagEntity> getAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<TagEntity> selectCriteriaQuery = cb.createQuery(TagEntity.class);
        Root<TagEntity> root = selectCriteriaQuery.from(TagEntity.class);
        selectCriteriaQuery.select(root);
        return HibernateUtil.getAllEntities(session, selectCriteriaQuery);
    }

    @Override
    public Long update(Long id, TagEntity updated) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaUpdate<TagEntity> criteriaUpdate = cb.createCriteriaUpdate(TagEntity.class);
        Root<TagEntity> root = criteriaUpdate.from(TagEntity.class);

        String tagName = updated.getName();
        if (tagName != null) {
            criteriaUpdate.set("name", tagName);
        }
        criteriaUpdate.where(
                cb.equal(root.get("id"), id)
        );

        HibernateUtil.updateEntity(session, criteriaUpdate);
        return id;
    }

    @Override
    public void delete(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaDelete<TagEntity> criteriaDelete = cb.createCriteriaDelete(TagEntity.class);
        Root<TagEntity> root = criteriaDelete.from(TagEntity.class);
        criteriaDelete.where(
                cb.equal(root.get("id"), id)
        );
        HibernateUtil.deleteEntity(session, criteriaDelete);
    }

    @Override
    public Optional<TagEntity> getByName(String name) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<TagEntity> tagCriteriaQuery = cb.createQuery(TagEntity.class);
        Root<TagEntity> root = tagCriteriaQuery.from(TagEntity.class);
        tagCriteriaQuery.select(root).where(
                cb.equal(root.get("name"), name)
        );
        return HibernateUtil.getSingleEntity(session, tagCriteriaQuery);
    }

    @Override
    public void delete() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaDelete<TagEntity> criteriaDelete = cb.createCriteriaDelete(TagEntity.class);
        criteriaDelete.from(TagEntity.class);
        HibernateUtil.deleteEntity(session, criteriaDelete);
    }

}
