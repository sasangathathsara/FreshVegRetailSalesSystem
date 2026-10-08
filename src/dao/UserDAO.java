/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import model.User;
import util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Achintha
 */
public class UserDAO {

    public List<User> getAllActiveUsers() throws Exception {

        List<User> users = new ArrayList<>();

        String sql =
                "SELECT user_id, username, password, "
                + "full_name, role, status "
                + "FROM users "
                + "WHERE status = 'ACTIVE' "
                + "ORDER BY full_name";

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery()
        ) {

            while (rs.next()) {

                User user = new User();

                user.setUserId(rs.getInt("user_id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setFullName(rs.getString("full_name"));
                user.setRole(rs.getString("role"));
                user.setStatus(rs.getString("status"));

                users.add(user);
            }
        }

        return users;
    }    
}
