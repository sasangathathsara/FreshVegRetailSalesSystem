/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package test;

import model.CustomerPayment;
import model.Sale;
import model.SaleDetail;
import service.SalesService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Achintha
 */
public class SalesServiceTest {

    public static void main(String[] args) {

        try {

            Sale sale = new Sale();

            sale.setInvoiceNo("TEST-" + System.currentTimeMillis());
            sale.setCustomerId(1);
            sale.setSaleDate(LocalDate.now());

            sale.setGrossAmount(300.00);
            sale.setDiscountAmount(0.00);
            sale.setNetAmount(300.00);
            sale.setPaidAmount(300.00);

            sale.setNotes("SalesService test");
            sale.setCreatedBy(1);

            SaleDetail detail = new SaleDetail();

            detail.setProductId(1);
            detail.setUnit("KG");
            detail.setQuantity(1.0);
            detail.setUnitPrice(300.00);
            detail.setLineTotal(300.00);

            List<SaleDetail> details = new ArrayList<>();
            details.add(detail);

            CustomerPayment payment = new CustomerPayment();

            payment.setPaymentDate(LocalDate.now());
            payment.setAmount(300.00);
            payment.setPaymentMethod("CASH");
            payment.setReferenceNo("");
            payment.setNotes("Test payment");
            payment.setCreatedBy(1);

            SalesService salesService = new SalesService();

            salesService.saveSale(
                    sale,
                    details,
                    payment
            );

            System.out.println("================================");
            System.out.println("SALE TEST SUCCESSFUL");
            System.out.println("Sale ID: " + sale.getSaleId());
            System.out.println("Invoice: " + sale.getInvoiceNo());
            System.out.println("Payment Status: " + sale.getPaymentStatus());
            System.out.println("Outstanding: " + sale.getOutstandingAmount());
            System.out.println("================================");

        } catch (Exception e) {

            System.out.println("SALE TEST FAILED.");
            e.printStackTrace();
        }
    }    
}
