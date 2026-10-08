/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import model.SaleDetail;

import java.sql.Connection;
import java.sql.PreparedStatement;
/**
 *
 * @author Achintha
 */
public class SaleDetailDAO {

    public void insertSaleDetail(
            Connection con,
            SaleDetail detail) throws Exception {

        String sql =
                "INSERT INTO sale_details "
                + "(sale_id, product_id, unit, quantity, "
                + "unit_price, line_total) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, detail.getSaleId());
            pst.setInt(2, detail.getProductId());
            pst.setString(3, detail.getUnit());
            pst.setDouble(4, detail.getQuantity());
            pst.setDouble(5, detail.getUnitPrice());
            pst.setDouble(6, detail.getLineTotal());

            pst.executeUpdate();
        }
    }    
}
