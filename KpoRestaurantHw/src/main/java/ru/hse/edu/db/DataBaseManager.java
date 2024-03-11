package ru.hse.edu.db;

import ru.hse.edu.consoleui.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataBaseManager {
    private  static final String CONNECTION_ERROR = "Не удалось подключиться к базе данных," +
            " проверьте настройки.";
    private static final String URL_KEY = "db.url";
    private static final String DB_USER_KEY = "db.user";
    private static final String DB_PASSWORD_KEY = "db.password";
    private Connection getConnection() throws SQLException {
        //System.out.println(DbPropertiesLoader.get(URL_KEY) + DbPropertiesLoader.get(DB_USER_KEY) +
        //        DbPropertiesLoader.get(DB_PASSWORD_KEY));
         return DriverManager.getConnection(DbPropertiesLoader.get(URL_KEY),
                DbPropertiesLoader.get(DB_USER_KEY), DbPropertiesLoader.get(DB_PASSWORD_KEY));

    }

    /**
     * Used for create/drop requests.
     * @param sql
     * @return
     * @throws SQLException
     */
    public Boolean performExecute(String sql) throws SQLException {
        Connection connection = getConnection();
        try(connection) {
            var query =connection.prepareStatement(sql);
            return query.execute();
        } catch (Exception e) {
            Logger.log(CONNECTION_ERROR);
            Logger.log(e.getMessage());
            return false;
        }
    }

    /**
     * Used for select requests.
     * @param sql
     * @return
     * @throws SQLException
     */
    public ResultSet performExecuteQuery(String sql) throws SQLException {
        Connection connection = getConnection();
        try(connection) {
            var query =connection.prepareStatement(sql);
            return query.executeQuery();
        } catch (Exception e) {
            Logger.log(CONNECTION_ERROR);
            Logger.log(e.getMessage());
            return null;
        }
    }

    /**
     * Used for update requests.
     * @param sql
     * @return
     * @throws SQLException
     */
    public int performExecuteUpdate(String sql) throws SQLException {
        Connection connection = getConnection();
        try(connection) {
            var query = connection.prepareStatement(sql);
            return query.executeUpdate();
        } catch (Exception e) {
            Logger.log(CONNECTION_ERROR);
            Logger.log(e.getMessage());
            return 1;
        }
    }
}
