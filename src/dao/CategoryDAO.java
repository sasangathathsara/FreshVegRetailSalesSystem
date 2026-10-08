/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import model.Category;
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
public class CategoryDAO {

    public List<Category> getAllActiveCategories() throws Exception {

        List<Category> categories = new ArrayList<>();

        String sql =
                "SELECT category_id, category_name, "
                + "description, status "
                + "FROM categories "
                + "WHERE status = 'ACTIVE' "
                + "ORDER BY category_name";

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery()
        ) {

            while (rs.next()) {

                Category category = new Category();

                category.setCategoryId(
                        rs.getInt("category_id")
                );

                category.setCategoryName(
                        rs.getString("category_name")
                );

                category.setDescription(
                        rs.getString("description")
                );

                category.setStatus(
                        rs.getString("status")
                );

                categories.add(category);
            }
        }

        return categories;
    }  
}
