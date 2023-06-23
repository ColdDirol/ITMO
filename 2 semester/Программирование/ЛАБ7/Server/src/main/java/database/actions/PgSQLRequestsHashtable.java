package database.actions;

import java.util.Hashtable;

public class PgSQLRequestsHashtable {
    private static Hashtable<String, String> usersHashtable = new Hashtable<>();
    {
        usersHashtable.put("SQL_USERS_INSERT", "INSERT INTO users (username, password) VALUES (?, ?)");
        usersHashtable.put("SQL_USERS_COUNT", "SELECT COUNT(users.username) FROM users WHERE users.username = ?");
        usersHashtable.put("SQL_USERS_GETLOGIN", "SELECT COUNT(users.username) FROM users WHERE users.username = ? AND users.password = ?");
        usersHashtable.put("SQL_USERS_GETUSER", "SELECT * FROM users WHERE users.username = ? AND users.password = ?");
        usersHashtable.put("SQL_USERS_GETID", "SELECT users.id FROM users WHERE users.username = ? AND users.password = ?");
    }

    public String getSqlUserRequest (String requestName) {
        return usersHashtable.get(requestName);
    }

    private static Hashtable<String, String> flatsHashtable = new Hashtable<>();
    {
        flatsHashtable.put("SQL_FLATS_INSERT", "INSERT INTO flats (user_id, name, x_coordinate, y_coordinate, creationdate, area, numberofrooms, furnish_e, view_e, transport_e, house_name, house_year, house_numberoflifts) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        usersHashtable.put("SQL_FLATS_COUNT", "SELECT COUNT(flats.id) FROM flats WHERE flats.id = ? AND flats.user_id = ?");
        flatsHashtable.put("SQL_FLATS_GETFLAT", "SELECT * FROM flats WHERE flats.id = ? AND flats.user_id = ?");
        flatsHashtable.put("SQL_FLATS_GETFLATS", "SELECT * FROM flats");
        flatsHashtable.put("SQL_FLATS_DELETEFLAT", "DELETE FROM flats WHERE flats.id = ?");
        flatsHashtable.put("SQL_FLATS_DELETEFLATS", "DELETE FROM flats WHERE flats.user_id = ?");
        flatsHashtable.put("SQL_FLATS_UPDATE", "UPDATE flats SET name=?, x_coordinate=?, y_coordinate=?, creationdate=?, area=?, numberofrooms=?, furnish_e=?, view_e=?, transport_e=?, house_name=?, house_year=?, house_numberoflifts=? WHERE flats.id=?");
    }

    public String getSqlFlatRequest (String requestName) {
        return flatsHashtable.get(requestName);
    }
}
