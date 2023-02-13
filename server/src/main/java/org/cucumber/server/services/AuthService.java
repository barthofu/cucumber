package org.cucumber.server.services;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.cucumber.common.so.LoggerStatus;
import org.cucumber.common.utils.Logger;
import org.cucumber.server.core.Database;
import org.cucumber.server.models.bo.User;
import org.cucumber.server.repositories.Repositories;
import org.cucumber.server.repositories.impl.UserRepository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AuthService {

    public static boolean checkAuth(String username, String password) {
        try {

            String hashedPassword = BCrypt.withDefaults().hashToString(12, password.toCharArray());

            Connection conn = Database.getInstance().getConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM public.user;");
            while (rs.next()) {
                if (
                        username.equals(rs.getString("username")) &&
                                hashedPassword.equals(rs.getString("username"))
                ) return true;
            }
            st.close();
        } catch (Exception e) {
            Logger.log(LoggerStatus.SEVERE, e.getMessage());
        }
        return false;
    }

    public static void register(String username, String password) throws SQLException {

        String hashedPassword = BCrypt.withDefaults().hashToString(12, password.toCharArray());

        User user = User.builder()
                .username(username)
                .password(hashedPassword)
                .build();

        Repositories
                .get(UserRepository.class)
                .create(user);

        Logger.log(LoggerStatus.INFO, "Added new user: " + username);
    }
}
