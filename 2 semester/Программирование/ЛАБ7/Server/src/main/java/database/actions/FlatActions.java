package database.actions;

import collection.flatcollection.ServerCollection;
import collection.flatcollection.flat.Flat;
import collection.flatcollection.flat.Furnish;
import collection.flatcollection.flat.Transport;
import collection.flatcollection.flat.View;
import serverlogic.outputchannel.ResponseArrayList;

import java.sql.*;

public class FlatActions {
    private static Connection connection;
    PgSQLRequestsHashtable pgSQLRequestsHashtable = new PgSQLRequestsHashtable();
    ServerCollection serverCollection = new ServerCollection();
    ResponseArrayList responseArrayList = new ResponseArrayList();

    public static void setConnection(Connection connection) {
        FlatActions.connection = connection;
    }


    public Flat getFlatById(Integer flatId, Integer userId) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(pgSQLRequestsHashtable.getSqlFlatRequest("SQL_FLATS_GETFLAT"))) {
            if(flatExists(flatId, userId)) {
                preparedStatement.setInt(1, flatId);
                preparedStatement.setInt(2, userId);

                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    Flat flat = new Flat();
                    flat.setId(resultSet.getInt("id"));
                    flat.setUser_id(resultSet.getInt("user_id"));
                    flat.setName(resultSet.getString("name"));
                    flat.setCoordinatesX(resultSet.getLong("x_coordinate"));
                    flat.setCoordinatesY(resultSet.getLong("y_coordinate"));
                    flat.setCreationDate(resultSet.getDate("creationdate"));
                    flat.setArea(resultSet.getFloat("area"));
                    flat.setNumberOfRooms(resultSet.getLong("numberofrooms"));
                    flat.setFurnish(Furnish.getFurnishByName(resultSet.getString("furnish_e")));
                    flat.setView(View.getViewByName(resultSet.getString("view_e")));
                    flat.setTransport(Transport.getTransportByName(resultSet.getString("transport_e")));
                    flat.setHouseName(resultSet.getString("house_name"));
                    flat.setHouseYear(resultSet.getInt("house_year"));
                    flat.setHouseNumberOfLifts(resultSet.getInt("house_numberoflifts"));

                    preparedStatement.close();
                    return flat;
                } else {
                    responseArrayList.addStringToResponseArrayList("Permission denied!");
                    preparedStatement.close();
                    return null;
                }
            } else {
                responseArrayList.addStringToResponseArrayList("Permission denied!");
                preparedStatement.close();
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Failed to delete flat: " + e.getMessage());
            responseArrayList.addStringToResponseArrayList("Permission denied!");
            return null;
        }
    }


    public void parseTheCollectionFromDB () {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(pgSQLRequestsHashtable.getSqlFlatRequest("SQL_FLATS_GETFLATS"));
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Flat flat = new Flat();
                flat.setId(resultSet.getInt("id"));
                flat.setUser_id(resultSet.getInt("user_id"));
                flat.setName(resultSet.getString("name"));
                flat.setCoordinatesX(resultSet.getLong("x_coordinate"));
                flat.setCoordinatesY(resultSet.getLong("y_coordinate"));
                flat.setCreationDate(resultSet.getDate("creationdate"));
                flat.setArea(resultSet.getFloat("area"));
                flat.setNumberOfRooms(resultSet.getLong("numberofrooms"));
                flat.setFurnish(Furnish.getFurnishByName(resultSet.getString("furnish_e")));
                flat.setView(View.getViewByName(resultSet.getString("view_e")));
                flat.setTransport(Transport.getTransportByName(resultSet.getString("transport_e")));
                flat.setHouseName(resultSet.getString("house_name"));
                flat.setHouseYear(resultSet.getInt("house_year"));
                flat.setHouseNumberOfLifts(resultSet.getInt("house_numberoflifts"));

                serverCollection.addObjectToHashtable(flat.getId(), flat);
            }
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("ERROR!");
            System.out.println("Something went wrong!");
        }
    }

    public boolean addNewFlatToDB(Flat flat) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(pgSQLRequestsHashtable.getSqlFlatRequest("SQL_FLATS_INSERT"))) {
            //PreparedStatement preparedStatement = connection.prepareStatement(SQL_FLATS_INSERT);
            preparedStatement.setInt(1, flat.getUser_id());
            preparedStatement.setString(2, flat.getName());
            preparedStatement.setLong(3, flat.getCoordinates().getX());
            preparedStatement.setLong(4, flat.getCoordinates().getY());
            preparedStatement.setDate(5, java.sql.Date.valueOf(flat.getCreationDate().toLocalDate()));
            preparedStatement.setFloat(6, flat.getArea());
            preparedStatement.setLong(7, flat.getNumberOfRooms());
            preparedStatement.setObject(8, flat.getFurnish().getName(), Types.OTHER);
            preparedStatement.setObject(9, flat.getView().getName(), Types.OTHER);
            preparedStatement.setObject(10, flat.getTransport().getName(), Types.OTHER);
            preparedStatement.setString(11, flat.getHouseName());
            preparedStatement.setInt(12, flat.getHouseYear());
            preparedStatement.setInt(13, flat.getHouseNumberOfLifts());

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("-A new flat was inserted successfully!");
                preparedStatement.close();
                return true;
            }
            responseArrayList.addStringToResponseArrayList("Permission denied!");
            preparedStatement.close();
            return false;
        } catch (SQLException e) {
            System.out.println("-Failed to insert flat: " + e.getMessage());
            responseArrayList.addStringToResponseArrayList("Permission denied!");
            return false;
        }
    }

    public boolean removeFlatFromDB(Integer id, Integer userId) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(pgSQLRequestsHashtable.getSqlFlatRequest("SQL_FLATS_DELETEFLAT"))) {
            if(flatExists(id, userId)) {
                preparedStatement.setInt(1, id);
                int rowsInserted = preparedStatement.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("-A new flat was removed successfully!");
                    return true;
                }
                responseArrayList.addStringToResponseArrayList("Permission denied!");
                preparedStatement.close();
                return false;
            } else {
                responseArrayList.addStringToResponseArrayList("Permission denied!");
                preparedStatement.close();
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Failed to delete flat: " + e.getMessage());
            responseArrayList.addStringToResponseArrayList("Permission denied!");
            return false;
        }
    }

    public void removeFlatsFromDB(Integer userId) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(pgSQLRequestsHashtable.getSqlFlatRequest("SQL_FLATS_DELETEFLATS"))) {
            preparedStatement.setInt(1, userId);
            preparedStatement.executeUpdate();
            System.out.println("Flats with user_id " + userId + " has been deleted!");
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println("Failed to delete flats: " + e.getMessage());
        }
    }

    public boolean updateFlatIntDB(Flat flat, Integer userId) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(pgSQLRequestsHashtable.getSqlFlatRequest("SQL_FLATS_UPDATE"))) {
            if(flatExists(flat.getId(), userId)) {
                preparedStatement.setString(1, flat.getName());
                preparedStatement.setLong(2, flat.getCoordinates().getX());
                preparedStatement.setLong(3, flat.getCoordinates().getY());
                preparedStatement.setDate(4, java.sql.Date.valueOf(flat.getCreationDate().toLocalDate()));
                preparedStatement.setFloat(5, flat.getArea());
                preparedStatement.setLong(6, flat.getNumberOfRooms());
                preparedStatement.setObject(7, flat.getFurnish().getName(), Types.OTHER);
                preparedStatement.setObject(8, flat.getView().getName(), Types.OTHER);
                preparedStatement.setObject(9, flat.getTransport().getName(), Types.OTHER);
                preparedStatement.setString(10, flat.getHouseName());
                preparedStatement.setInt(11, flat.getHouseYear());
                preparedStatement.setInt(12, flat.getHouseNumberOfLifts());
                preparedStatement.setInt(13, userId);

                int rowsInserted = preparedStatement.executeUpdate();
                if (rowsInserted > 0) {
                    responseArrayList.addStringToResponseArrayList("Permission denied!");
                    preparedStatement.close();
                    return true;
                } else {
                    responseArrayList.addStringToResponseArrayList("Permission denied!");
                    preparedStatement.close();
                    return false;
                }
            } else {
                responseArrayList.addStringToResponseArrayList("Permission denied!");
                preparedStatement.close();
                return false;
            }
        } catch (SQLException e) {
            System.out.println("-Failed to update flat: " + e.getMessage());
            return false;
        }
    }

    public boolean flatExists(Integer flatId, Integer userId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(pgSQLRequestsHashtable.getSqlUserRequest("SQL_FLATS_COUNT"));
            preparedStatement.setInt(1, flatId);
            preparedStatement.setInt(2, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Integer numResult = resultSet.getInt("count");
                if (numResult > 1) System.err.println("Any problems");
                if (numResult == 0) System.out.println("Flat doesn't exist!");
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
}
