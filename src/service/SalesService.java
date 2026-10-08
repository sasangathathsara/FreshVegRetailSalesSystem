/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import util.DBConnection;

import dao.CustomerPaymentDAO;
import dao.InventoryDAO;
import dao.SaleDAO;
import dao.SaleDetailDAO;
import dao.StockMovementDAO;

import model.CustomerPayment;
import model.Sale;
import model.SaleDetail;
import model.StockMovement;

import java.util.List;

/**
 *
 * @author Achintha
 */
public class SalesService {

    private final SaleDAO saleDAO;
    private final SaleDetailDAO saleDetailDAO;
    private final CustomerPaymentDAO customerPaymentDAO;
    private final InventoryDAO inventoryDAO;
    private final StockMovementDAO stockMovementDAO;

    public SalesService() {

        saleDAO = new SaleDAO();
        saleDetailDAO = new SaleDetailDAO();
        customerPaymentDAO = new CustomerPaymentDAO();
        inventoryDAO = new InventoryDAO();
        stockMovementDAO = new StockMovementDAO();
    }

    public void saveSale(
            Sale sale,
            List<SaleDetail> details,
            CustomerPayment payment) throws Exception {

        java.sql.Connection con = null;

        try {

            con = util.DBConnection.getConnection();

            con.setAutoCommit(false);
            double netAmount = sale.getNetAmount();
            double paidAmount = sale.getPaidAmount();

            double outstandingAmount = netAmount - paidAmount;

            if (outstandingAmount < 0) 
            {
                outstandingAmount = 0;
            }

            sale.setOutstandingAmount(outstandingAmount);

            if (paidAmount >= netAmount) {
                sale.setPaymentStatus("PAID");
            } 
            else if (paidAmount > 0) {
                sale.setPaymentStatus("PARTIAL");
            } 
            else {
                sale.setPaymentStatus("CREDIT");
            }

            System.out.println("Sale transaction started.");
            
            int saleId = saleDAO.insertSale(con, sale);

            sale.setSaleId(saleId);

            System.out.println("Sale header inserted. Sale ID: " + saleId);
            
            for (SaleDetail detail : details) {

                detail.setSaleId(saleId);

                saleDetailDAO.insertSaleDetail(con,detail);

                inventoryDAO.reduceStock(con,detail.getProductId(),detail.getQuantity());

                StockMovement movement = new StockMovement();

                movement.setProductId(detail.getProductId());

                movement.setMovementType("SALE_OUT");

                movement.setReferenceType("SALE");

                movement.setReferenceId(saleId);

                movement.setQuantity(detail.getQuantity());

                movement.setReason("Sales transaction");

                movement.setNotes("Stock reduced for invoice: "+ sale.getInvoiceNo());

                movement.setCreatedBy(sale.getCreatedBy());

                stockMovementDAO.insertMovement(con, movement);
            }

            System.out.println("Sale details inserted.");

            if (payment != null && payment.getAmount() > 0) {

                payment.setSaleId(saleId);

                customerPaymentDAO.insertPayment(con,payment);

                System.out.println("Customer payment inserted.");
            }
            
            con.commit();

            System.out.println("Sale transaction committed.");

        } 
        catch (Exception e) 
        {

            if (con != null) 
            {
                con.rollback();
            }
            throw e;

        } 
        finally 
        {
            if (con != null) 
            {
                con.close();
            }
        }
    }
}