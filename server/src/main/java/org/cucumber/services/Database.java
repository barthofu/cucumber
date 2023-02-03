package org.cucumber.services;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private final String URL;
    private final String USER;
    private final String PASSWORD;

    private Connection connection;

    public Database() {

        Dotenv dotenv = Dotenv.load();

        this.URL = dotenv.get("DATABASE_URL");
        this.USER = dotenv.get("DATABASE_USER");
        this.PASSWORD = dotenv.get("DATABASE_PASSWORD");
    }

    public void connect() throws SQLException {
        this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public void disconnect() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
