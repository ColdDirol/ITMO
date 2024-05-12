package db.repositories;

import beans.Point;
import db.entities.PointsEntity;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.*;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class PointRepositoryTest {
    private PointRepository pointRepository;
    private PointsEntity pointsEntity;

    @BeforeEach
    void setup() {
        pointRepository = new PointRepository();
        pointsEntity = new PointsEntity(new Point(1.0, 2.0, 3.0, true));
        pointRepository.addNew(pointsEntity);
    }

    @AfterEach
    void tearDown() {
        pointRepository.deleteAll();
    }

    @Test
    @DisplayName("Test for adding a point to the database")
    void testAddNew() {
        PointsEntity savedEntity = pointRepository.getByID(pointsEntity.getId());
        assertNotNull(savedEntity);
        assertEquals(pointsEntity.getX(), savedEntity.getX());
    }

    @Test
    @DisplayName("Test of taking a point into the database by ID")
    void testGetByID() {
        PointsEntity savedEntity = pointRepository.getByID(pointsEntity.getId());
        assertNotNull(savedEntity);
        assertEquals(pointsEntity.getX(), savedEntity.getX());
    }

    @Test
    @DisplayName("Test of taking all points from the database")
    void testGetAll() {
        Collection<PointsEntity> allEntities = pointRepository.getAll();
        assertNotNull(allEntities);
        assertEquals(1, allEntities.size());
    }

    @Test
    @DisplayName("Test for deleting a point from the database")
    void testDelete() {
        PointsEntity savedEntity = pointRepository.getByID(pointsEntity.getId()); // Use the stored ID to retrieve the entity
        assertNotNull(savedEntity);
        pointRepository.delete(savedEntity);
        assertThrows(EntityNotFoundException.class, () -> pointRepository.getByID(pointsEntity.getId()));
    }

    @Test
    @DisplayName("Test of deleting all points from the database")
    void testDeleteAll() {
        pointRepository.deleteAll();
        Collection<PointsEntity> allEntities = pointRepository.getAll();
        assertTrue(allEntities.isEmpty());
    }
}