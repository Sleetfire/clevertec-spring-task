package ru.clevertec.ecl.repository;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import ru.clevertec.ecl.dto.GiftCertificateFilter;
import ru.clevertec.ecl.repository.api.IGiftCertificateRepository;
import ru.clevertec.ecl.repository.util.EntityUtil;
import ru.clevertec.ecl.repository.util.HibernateUtil;
import ru.clevertec.ecl.repository.entity.GiftCertificateEntity;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class GiftCertificateRepository implements IGiftCertificateRepository {

    @Override
    public Long create(GiftCertificateEntity entity) {
        return HibernateUtil.saveEntity(entity);
    }

    @Override
    public Optional<GiftCertificateEntity> getById(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<GiftCertificateEntity> selectCriteriaQuery = cb.createQuery(GiftCertificateEntity.class);
        Root<GiftCertificateEntity> root = selectCriteriaQuery.from(GiftCertificateEntity.class);
        selectCriteriaQuery.select(root).where(
                cb.equal(root.get("id"), id)
        );
        return HibernateUtil.getSingleEntity(session, selectCriteriaQuery);
    }

    @Override
    public List<GiftCertificateEntity> getAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<GiftCertificateEntity> selectCriteriaQuery = cb.createQuery(GiftCertificateEntity.class);
        Root<GiftCertificateEntity> root = selectCriteriaQuery.from(GiftCertificateEntity.class);
        selectCriteriaQuery.select(root);
        return HibernateUtil.getAllEntities(session, selectCriteriaQuery);
    }

    @Override
    public Long update(Long id, GiftCertificateEntity updatedEntity) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaUpdate<GiftCertificateEntity> criteriaUpdate = cb.createCriteriaUpdate(GiftCertificateEntity.class);
        Root<GiftCertificateEntity> root = criteriaUpdate.from(GiftCertificateEntity.class);

        Map<String, Object> updateValue = EntityUtil.getMap(updatedEntity);
        updateValue.forEach(criteriaUpdate::set);

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
        CriteriaDelete<GiftCertificateEntity> criteriaDelete = cb.createCriteriaDelete(GiftCertificateEntity.class);
        Root<GiftCertificateEntity> root = criteriaDelete.from(GiftCertificateEntity.class);
        criteriaDelete.where(
                cb.equal(root.get("id"), id)
        );
        HibernateUtil.deleteEntity(session, criteriaDelete);
    }

    @Override
    public List<GiftCertificateEntity> getAll(GiftCertificateFilter filter) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<GiftCertificateEntity> criteriaQuery = cb.createQuery(GiftCertificateEntity.class);
        Root<GiftCertificateEntity> root = criteriaQuery.from(GiftCertificateEntity.class);
        EntityUtil.filterQuery(filter, cb, root, criteriaQuery);
        return HibernateUtil.getAllEntities(session, criteriaQuery);
    }

    @Override
    public void delete() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaDelete<GiftCertificateEntity> criteriaDelete = cb.createCriteriaDelete(GiftCertificateEntity.class);
        criteriaDelete.from(GiftCertificateEntity.class);
        HibernateUtil.deleteEntity(session, criteriaDelete);
    }

}
