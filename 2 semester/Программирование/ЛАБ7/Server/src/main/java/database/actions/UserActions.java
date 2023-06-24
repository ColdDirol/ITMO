package database.actions;

import collection.userscollection.User;
import database.HashingMD2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserActions {
    PgSQLRequestsHashtable pgSQLRequestsHashtable = new PgSQLRequestsHashtable();
    HashingMD2 hashingMD2 = new HashingMD2();
    private static Connection connection;
    PreparedStatement preparedStatement = null;

    public static void setConnection(Connection connection) {
        UserActions.connection = connection;
    }

    public boolean registerUser(String username, String password){
        try {
            if(!userExists(username)) {
                preparedStatement = connection.prepareStatement(pgSQLRequestsHashtable.getSqlUserRequest("SQL_USERS_INSERT"));
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, hashingMD2.encodeStringMD2(password));
                preparedStatement.executeUpdate();
                return true;
            }
            else {
                return false;
            }
        } catch (SQLException e) {
            System.out.println("ERROR!");
            System.out.println("Something went wrong!");
            return false;
        }
    }

    public boolean userExists(String username){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(pgSQLRequestsHashtable.getSqlUserRequest("SQL_USERS_COUNT"));
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Integer numResult = resultSet.getInt("count");
                if (numResult > 1) System.err.println("Any problems");
                if (numResult == 0) System.out.println("User doesn't exist!");
                if (numResult.equals(1)) {
                    preparedStatement.close();
                    return true;
                }
                else {
                    preparedStatement.close();
                    return false;
                }
            } else {
                preparedStatement.close();
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("ERROR!");
            System.out.println("Something went wrong!");
            return true;
        }
    }

    public boolean getPermisionToLogin (String username, String password) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(pgSQLRequestsHashtable.getSqlUserRequest("SQL_USERS_GETLOGIN"));
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, hashingMD2.encodeStringMD2(password));
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                Integer numResult = resultSet.getInt("count");
                if (numResult > 1) System.err.println("Any problems");
                if (numResult.equals(1)) {
                    preparedStatement.close();
                    return true;
                }
                else {
                    preparedStatement.close();
                    return false;
                }
            } else {
                preparedStatement.close();
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("ERROR!");
            System.out.println("Something went wrong!");
            return false;
        }
    }

    public User getUser (String username, String password) {
        try {
            User user = new User();
            PreparedStatement preparedStatement = connection.prepareStatement(pgSQLRequestsHashtable.getSqlUserRequest("SQL_USERS_GETUSER"));
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                user.setId(resultSet.getInt("id"));
                user.setUsername(resultSet.getString("username"));
            }
            preparedStatement.close();
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("ERROR!");
            System.out.println("Something went wrong!");
            return null;
        }
    }

    public Integer getId (String username, String password) {
        try {
            Integer id = null;
            PreparedStatement preparedStatement = connection.prepareStatement(pgSQLRequestsHashtable.getSqlUserRequest("SQL_USERS_GETID"));
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, hashingMD2.encodeStringMD2(password));
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                id = resultSet.getInt("id");
            }
            preparedStatement.close();
            return id;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("ERROR!");
            System.out.println("Something went wrong!");
            return null;
        }
    }
}
