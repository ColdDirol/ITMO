package db.repositories;

import db.DatabaseUtils;
import db.entities.PointsEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.Root;

import java.util.Collection;

public class PointRepository {
    private final EntityManager entityManager;

    public PointRepository() {
        entityManager = DatabaseUtils.getFactory().createEntityManager();
    }

    public void addNew(PointsEntity points) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(points);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public PointsEntity getByID(Long point_id) {
        return entityManager.getReference(PointsEntity.class, point_id);
    }

    public Collection<PointsEntity> getAll() {
        var cm = entityManager.getCriteriaBuilder().createQuery(PointsEntity.class);
        Root<PointsEntity> root = cm.from(PointsEntity.class);
        return entityManager.createQuery(cm.select(root)).getResultList();
    }

    public void delete(PointsEntity points) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.remove(points);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public void deleteAll() {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Query query = entityManager.createQuery("DELETE FROM PointsEntity");
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    EntityManager getEntityManager() {
        return entityManager;
    }
}