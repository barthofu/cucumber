package org.cucumber.db;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.cucumber.common.so.LoggerStatus;
import org.cucumber.common.utils.Logger;

import java.sql.*;
import java.util.HashMap;

public class AuthService {

    public static boolean checkAuth(String username, String password) {
        try {

            String hp = BCrypt.withDefaults().hashToString(12,password.toCharArray());

            Connection c = DAO.getConnection();
            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM public.user;");
            while (rs.next()) {
                if(
                    username.equals(rs.getString("username")) &&
                    hp.equals(rs.getString("username"))
                ) return true;
            }
            st.close();
        } catch (Exception e) {
            Logger.log(LoggerStatus.SEVERE, e.getMessage());
        }
        return false;
    }

    public static void register(String username, String password) throws SQLException {
            Connection c = DAO.getConnection();
            Statement st = c.createStatement();
            st.executeUpdate(String.format(
                    "INSERT INTO public.user (username, password) VALUES ('%s', '%s')",
                    username,
                    BCrypt.withDefaults().hashToString(12,password.toCharArray())
            ));
            st.close();
            Logger.log(LoggerStatus.INFO, "added new user "+ username);
    }
}
