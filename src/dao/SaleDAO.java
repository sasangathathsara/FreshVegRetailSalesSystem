/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import model.Sale;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
/**
 *
 * @author Achintha
 */
public class SaleDAO {

    public int insertSale(Connection con, Sale sale) throws Exception {

        String sql =
                "INSERT INTO sales "
                + "(invoice_no, customer_id, sale_date, "
                + "gross_amount, discount_amount, net_amount, "
                + "paid_amount, outstanding_amount, payment_status, "
                + "notes, created_by) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (
            PreparedStatement pst =
                    con.prepareStatement(
                            sql,
                            PreparedStatement.RETURN_GENERATED_KEYS
                    )
        ) {

            pst.setString(1, sale.getInvoiceNo());
            pst.setInt(2, sale.getCustomerId());
            pst.setDate(
                    3,
                    java.sql.Date.valueOf(sale.getSaleDate())
            );
            pst.setDouble(4, sale.getGrossAmount());
            pst.setDouble(5, sale.getDiscountAmount());
            pst.setDouble(6, sale.getNetAmount());
            pst.setDouble(7, sale.getPaidAmount());
            pst.setDouble(8, sale.getOutstandingAmount());
            pst.setString(9, sale.getPaymentStatus());
            pst.setString(10, sale.getNotes());
            pst.setInt(11, sale.getCreatedBy());

            pst.executeUpdate();

            try (ResultSet rs = pst.getGeneratedKeys()) {

                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }

        throw new Exception("Sale ID could not be generated.");
    }    
    
    public int getNextInvoiceNumber() throws Exception {

        String sql =
                "SELECT COALESCE("
                + "MAX(CAST(SUBSTRING(invoice_no, 5) AS UNSIGNED)), "
                + "0"
                + ") + 1 AS next_number "
                + "FROM sales "
                + "WHERE invoice_no LIKE 'INV-%'";

        try (
            Connection con = util.DBConnection.getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery()
        ) {

            if (rs.next()) {
                return rs.getInt("next_number");
            }
        }

        return 1;
    }
}
