package org.cucumber.db;

import io.github.cdimascio.dotenv.Dotenv;
import org.postgresql.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DAO {

    private static Connection connection = null;

    private DAO() throws SQLException {
    }

    public static Connection getConnection() throws SQLException {
        if (connection != null) return connection;
        String url = (Dotenv.load().get("DATABASE_URL"));
        String user = (Dotenv.load().get("DATABASE_USER"));
        String password = (Dotenv.load().get("DATABASE_PASSWORD"));

        connection = DriverManager.getConnection(url, user, password);
        return connection;
    }

}
