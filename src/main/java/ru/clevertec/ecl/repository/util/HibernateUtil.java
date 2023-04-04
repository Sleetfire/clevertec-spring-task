package ru.clevertec.ecl.repository.util;

import lombok.experimental.UtilityClass;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@UtilityClass
public class HibernateUtil {

    public SessionFactory getSessionFactory() {
        Configuration configuration = new Configuration();
        configuration.configure();
        return configuration.buildSessionFactory();
    }

    public static <T> Long saveEntity(T entity) {
        Transaction transaction = null;
        Long id = (long) -1;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            id = (Long) session.save(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return id;
    }

    public static <T> void deleteEntity(Session session, CriteriaDelete<T> criteriaDelete) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.createQuery(criteriaDelete).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    public <T> Optional<T> getSingleEntity(Session session, CriteriaQuery<T> entityCriteriaQuery) {
        T entity;
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            entity = session.createQuery(entityCriteriaQuery).getSingleResult();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            return Optional.empty();
        }
        return Optional.ofNullable(entity);
    }

    public <T> void updateEntity(Session session, CriteriaUpdate<T> entityCriteriaQuery) {
        Transaction transaction = null;
        try (session) {
            transaction = session.beginTransaction();
            session.createQuery(entityCriteriaQuery).executeUpdate();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    public <T> List<T> getAllEntities(Session session, CriteriaQuery<T> entityCriteriaQuery) {
        List<T> list = new ArrayList<>();
        Transaction transaction = null;
        try (session) {
            transaction = session.beginTransaction();
            list = session.createQuery(entityCriteriaQuery).getResultList();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return list;
    }

}
