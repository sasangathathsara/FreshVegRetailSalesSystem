/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package test;

import dao.CustomerDAO;
import model.Customer;

import java.util.List;
/**
 *
 * @author Achintha
 */
public class CustomerDAOTest {

    public static void main(String[] args) {

        try {

            CustomerDAO dao = new CustomerDAO();

            List<Customer> customers =
                    dao.getAllActiveCustomers();

            System.out.println(
                    "Active Customers: " + customers.size()
            );

            for (Customer customer : customers) {

                System.out.println(
                        customer.getCustomerCode()
                        + " - "
                        + customer.getCustomerName()
                        + " - "
                        + customer.getPhone()
                );
            }

        } catch (Exception e) {

            System.out.println(
                    "Customer loading failed."
            );

            e.printStackTrace();
        }
    }    
}
