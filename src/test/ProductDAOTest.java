/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package test;

import dao.ProductDAO;
import model.Product;

import java.util.List;

/**
 *
 * @author Achintha
 */
public class ProductDAOTest {

    public static void main(String[] args) {

        try {

            ProductDAO dao = new ProductDAO();

            List<Product> products =
                    dao.getAllActiveProducts();

            System.out.println(
                    "Active Products: " + products.size()
            );

            for (Product product : products) {

                System.out.println(
                        product.getProductCode()
                        + " - "
                        + product.getProductName()
                        + " - "
                        + product.getUnit()
                        + " - Rs. "
                        + product.getSellingPrice()
                );
            }

        } catch (Exception e) {

            System.out.println(
                    "Product loading failed."
            );

            e.printStackTrace();
        }
    }    
}
