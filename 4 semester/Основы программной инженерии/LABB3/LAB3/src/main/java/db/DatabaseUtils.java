package db;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.Properties;
public class DatabaseUtils {
    private static final EntityManagerFactory factory;

    public static EntityManagerFactory getFactory() {
        return factory;
    }

    static {
        try {
            Properties info = new Properties();
            info.load(DatabaseUtils.class.getClassLoader().getResourceAsStream("/db.cfg"));
            factory = Persistence.createEntityManagerFactory("default", info);
        } catch (Throwable ex) {
            System.err.println("Something went wrong during initializing EclipseLink: " + ex);
            throw new ExceptionInInitializerError();
        }
    }


}