/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package test;

import dao.InventoryDAO;
import model.Inventory;

import java.util.List;
/**
 *
 * @author Achintha
 */
public class InventoryDAOTest {

    public static void main(String[] args) {

        try {

            InventoryDAO dao = new InventoryDAO();

            List<Inventory> inventoryList =
                    dao.getAllInventory();

            System.out.println(
                    "Inventory Records: "
                    + inventoryList.size()
            );

            for (Inventory inventory : inventoryList) {

                System.out.println(
                        "Product ID: "
                        + inventory.getProductId()
                        + " - Quantity: "
                        + inventory.getQuantity()
                );
            }

        } catch (Exception e) {

            System.out.println(
                    "Inventory loading failed."
            );

            e.printStackTrace();
        }
    }    
}
