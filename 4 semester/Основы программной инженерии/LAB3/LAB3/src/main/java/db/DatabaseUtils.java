package db;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceException;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

public class DatabaseUtils {
    private static final Logger LOGGER = Logger.getLogger(DatabaseUtils.class.getName());
    private static final EntityManagerFactory factory;

    static {
        factory = createEntityManagerFactory();
    }

    private static EntityManagerFactory createEntityManagerFactory() {
        try {
            Properties info = new Properties();
            info.load(DatabaseUtils.class.getClassLoader().getResourceAsStream("db.cfg"));
            return Persistence.createEntityManagerFactory("default", info);
        } catch (PersistenceException | IOException e) {
            LOGGER.severe("Error initializing EclipseLink: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static EntityManagerFactory getFactory() {
        return factory;
    }
}