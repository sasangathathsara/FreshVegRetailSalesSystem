/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package test;

import dao.CategoryDAO;
import model.Category;

import java.util.List;

/**
 *
 * @author Achintha
 */
public class CategoryDAOTest {

    public static void main(String[] args) {

        try {

            CategoryDAO dao = new CategoryDAO();

            List<Category> categories =
                    dao.getAllActiveCategories();

            System.out.println(
                    "Active Categories: " + categories.size()
            );

            for (Category category : categories) {

                System.out.println(
                        category.getCategoryId()
                        + " - "
                        + category.getCategoryName()
                        + " - "
                        + category.getDescription()
                );
            }

        } catch (Exception e) {

            System.out.println(
                    "Category loading failed."
            );

            e.printStackTrace();
        }
    }    
}
