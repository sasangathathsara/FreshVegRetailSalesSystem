/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import model.Inventory;
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
public class InventoryDAO {

    public List<Inventory> getAllInventory() throws Exception {

        List<Inventory> inventoryList = new ArrayList<>();

        String sql =
                "SELECT inventory_id, product_id, quantity "
                + "FROM inventory "
                + "ORDER BY product_id";

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery()
        ) {

            while (rs.next()) {

                Inventory inventory = new Inventory();

                inventory.setInventoryId(
                        rs.getInt("inventory_id")
                );

                inventory.setProductId(
                        rs.getInt("product_id")
                );

                inventory.setQuantity(
                        rs.getDouble("quantity")
                );

                inventoryList.add(inventory);
            }
        }

        return inventoryList;
    }
    
    public void reduceStock(
            Connection con,
            int productId,
            double quantity) throws Exception {

        String sql =
                "UPDATE inventory "
                + "SET quantity = quantity - ?, "
                + "updated_at = CURRENT_TIMESTAMP "
                + "WHERE product_id = ? "
                + "AND quantity >= ?";

        try (PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setDouble(1, quantity);
            pst.setInt(2, productId);
            pst.setDouble(3, quantity);

            int rowsUpdated = pst.executeUpdate();

            if (rowsUpdated == 0) {
                throw new Exception(
                        "Insufficient stock for product ID: "
                        + productId
                );
            }
        }
    }    
}
