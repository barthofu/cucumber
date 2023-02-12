package org.cucumber.server.services;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * The Database service is responsible for managing the database connection.
 */
public class Database {

    private final String url;
    private final String user;
    private final String password;

    private Connection connection;

    private Database() {

        Dotenv dotenv = Dotenv.load();

        this.url = dotenv.get("DATABASE_URL");
        this.user = dotenv.get("DATABASE_USER");
        this.password = dotenv.get("DATABASE_PASSWORD");
    }

    public void connect() throws SQLException {
        this.connection = DriverManager.getConnection(url, user, password);
    }

    public void disconnect() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    // ================================
    // Singleton
    // ================================

    private static Database instance;

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }
}
