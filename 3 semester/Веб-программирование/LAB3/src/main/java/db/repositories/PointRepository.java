package db.repositories;

import db.DatabaseUtils;

import db.entities.PointsEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.Root;

import java.util.Collection;

public class PointRepository {
    private final EntityManager entityManager = DatabaseUtils.getFactory().createEntityManager();

    public void addNew(PointsEntity points) {
        entityManager.getTransaction().begin();
        entityManager.persist(points);
        entityManager.getTransaction().commit();
    }

    public PointsEntity getById(Long point_id) {
        return entityManager.getReference(PointsEntity.class, point_id);
    }

    public Collection<PointsEntity> getAll() {
        var cm = entityManager.getCriteriaBuilder().createQuery(PointsEntity.class);
        Root<PointsEntity> root = cm.from(PointsEntity.class);
        return entityManager.createQuery(cm.select(root)).getResultList();
    }

    // update

    public void delete(PointsEntity points) {
        entityManager.getTransaction().begin();
        entityManager.remove(points);
        entityManager.getTransaction().commit();
    }

    public void deleteAll() {
        entityManager.getTransaction().begin();
        try {
            Query query = entityManager.createQuery("DELETE FROM PointsEntity r");
            query.executeUpdate();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw e;
        } finally {
            entityManager.clear();
        }
    }
}