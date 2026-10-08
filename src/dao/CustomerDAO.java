/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import model.Customer;
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
public class CustomerDAO {

    public List<Customer> getAllActiveCustomers() throws Exception {

        List<Customer> customers = new ArrayList<>();

        String sql =
                "SELECT customer_id, customer_code, customer_name, "
                + "phone, address, status "
                + "FROM customers "
                + "WHERE status = 'ACTIVE' "
                + "ORDER BY customer_name";

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery()
        ) {

            while (rs.next()) {

                Customer customer = new Customer();

                customer.setCustomerId(
                        rs.getInt("customer_id")
                );

                customer.setCustomerCode(
                        rs.getString("customer_code")
                );

                customer.setCustomerName(
                        rs.getString("customer_name")
                );

                customer.setPhone(
                        rs.getString("phone")
                );

                customer.setAddress(
                        rs.getString("address")
                );

                customer.setStatus(
                        rs.getString("status")
                );

                customers.add(customer);
            }
        }

        return customers;
    }    
}
