/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import model.CustomerPayment;

import java.sql.Connection;
import java.sql.PreparedStatement;
/**
 *
 * @author Achintha
 */
public class CustomerPaymentDAO {

    public void insertPayment(
            Connection con,
            CustomerPayment payment) throws Exception {

        String sql =
                "INSERT INTO customer_payments "
                + "(sale_id, payment_date, amount, payment_method, "
                + "reference_no, notes, created_by) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, payment.getSaleId());
            pst.setDate(
                    2,
                    java.sql.Date.valueOf(
                            payment.getPaymentDate()
                    )
            );
            pst.setDouble(3, payment.getAmount());
            pst.setString(4, payment.getPaymentMethod());
            pst.setString(5, payment.getReferenceNo());
            pst.setString(6, payment.getNotes());
            pst.setInt(7, payment.getCreatedBy());

            pst.executeUpdate();
        }
    }    
}
