import collection.flatcollection.ServerCollection;
import database.DatabaseManager;
import database.actions.FlatActions;
import database.actions.PgSQLRequestsHashtable;
import database.actions.UserActions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import serverlogic.TCPServer;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.SQLException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Server {
    private static Logger logger = LogManager.getLogger(Server.class.getSimpleName());
    private static String jdbcURL = "jdbc:postgresql://localhost:1088/studs";
    //private static String jdbcURL = "jdbc:postgresql://pg:5432/studs";
    private static final int PORT = 8080;

    public static void main(String[] args) throws ClassNotFoundException, FileNotFoundException, SQLException {
        FlatActions flatActions = new FlatActions();
        // > debug

        Scanner credentials = null;
        String username = null;
        String password = null;
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Драйвер postgresql не найден!");
            logger.info("Драйвер postgresql не найден!");
            System.exit(-1);
        }
        try {
            credentials = new Scanner(new FileReader("credentials"));
        } catch (FileNotFoundException e) {
            System.out.println("Отсутствует файл credentials с данными для входа в базу данных.");
            logger.info("Отсутствует файл credentials с данными для входа в базу данных.");
            System.exit(-1);
        }
        try {
            username = credentials.nextLine().trim();
            password = credentials.nextLine().trim();
        } catch (NoSuchElementException e) {
            System.out.println("Не найдены данные для входа в базу данных.");
            logger.info("Не найдены данные для входа в базу данных.");
            System.exit(-1);
        }

        DatabaseManager databaseManager = new DatabaseManager(jdbcURL, username, password);
        databaseManager.connectToDB();
        {
            flatActions.parseTheCollectionFromDB();
            System.out.println("Collection has been parsed!");
        }

        TCPServer tcpServer = new TCPServer(PORT, databaseManager);
        tcpServer.run();
    }
}