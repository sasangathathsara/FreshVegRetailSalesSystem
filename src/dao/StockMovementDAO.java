/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import model.StockMovement;

import java.sql.Connection;
import java.sql.PreparedStatement;
/**
 *
 * @author Achintha
 */
public class StockMovementDAO {

    public void insertMovement(
            Connection con,
            StockMovement movement) throws Exception {

        String sql =
                "INSERT INTO stock_movements "
                + "(product_id, movement_type, reference_type, "
                + "reference_id, quantity, movement_date, reason, "
                + "notes, created_by) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, movement.getProductId());
            pst.setString(2, movement.getMovementType());
            pst.setString(3, movement.getReferenceType());
            pst.setInt(4, movement.getReferenceId());
            pst.setDouble(5, movement.getQuantity());

            if (movement.getMovementDate() != null) {
                pst.setTimestamp(
                        6,
                        java.sql.Timestamp.valueOf(
                                movement.getMovementDate()
                        )
                );
            } else {
                pst.setTimestamp(
                        6,
                        new java.sql.Timestamp(
                                System.currentTimeMillis()
                        )
                );
            }

            pst.setString(7, movement.getReason());
            pst.setString(8, movement.getNotes());
            pst.setInt(9, movement.getCreatedBy());

            pst.executeUpdate();
        }
    }    
}
