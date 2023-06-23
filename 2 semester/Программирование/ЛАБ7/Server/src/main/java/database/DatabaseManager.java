package database;

import database.actions.FlatActions;
import database.actions.PgSQLRequestsHashtable;
import database.actions.UserActions;

import java.sql.*;


public class DatabaseManager {
    private String DB_URL;
    private String DB_username;
    private String DB_password;
    private static Connection connection;
    UserActions userActions = new UserActions();

    PgSQLRequestsHashtable pgSQLRequestsHashtable = new PgSQLRequestsHashtable();

    public DatabaseManager(String URL, String username, String password) {
        this.DB_URL = URL;
        this.DB_username = username;
        this.DB_password = password;
    }

    public void connectToDB() {
        try {
            connection = DriverManager.getConnection(DB_URL, DB_username, DB_password);
            UserActions.setConnection(connection);
            FlatActions.setConnection(connection);
            System.out.println("You was connected to the Database!");
        } catch (SQLException e) {
            System.out.println("Ты юзаешь не тот url, даун");
            System.err.println("ERROR with connection to the Database!");
            System.exit(-1);
        }
    }
}