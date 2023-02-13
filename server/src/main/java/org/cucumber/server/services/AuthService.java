package org.cucumber.server.services;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.cucumber.common.so.LoggerStatus;
import org.cucumber.common.utils.Logger;
import org.cucumber.server.core.Database;

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

        Connection c = Database.getInstance().getConnection();
        Statement st = c.createStatement();
        st.executeUpdate(String.format(
                "INSERT INTO public.user (username, password) VALUES ('%s', '%s')",
                username,
                BCrypt.withDefaults().hashToString(12, password.toCharArray())
        ));
        st.close();
        Logger.log(LoggerStatus.INFO, "added new user " + username);
    }
}
