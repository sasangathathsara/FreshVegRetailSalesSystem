/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDate;

/**
 *
 * @author Achintha
 */
public class CustomerPayment {

    private int customerPaymentId;
    private int saleId;
    private LocalDate paymentDate;
    private double amount;
    private String paymentMethod;
    private String referenceNo;
    private String notes;
    private int createdBy;

    public CustomerPayment() {
    }

    public CustomerPayment(int customerPaymentId, int saleId,
                           LocalDate paymentDate, double amount,
                           String paymentMethod, String referenceNo,
                           String notes, int createdBy) {

        this.customerPaymentId = customerPaymentId;
        this.saleId = saleId;
        this.paymentDate = paymentDate;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.referenceNo = referenceNo;
        this.notes = notes;
        this.createdBy = createdBy;
    }

    public int getCustomerPaymentId() {
        return customerPaymentId;
    }

    public void setCustomerPaymentId(int customerPaymentId) {
        this.customerPaymentId = customerPaymentId;
    }

    public int getSaleId() {
        return saleId;
    }

    public void setSaleId(int saleId) {
        this.saleId = saleId;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getReferenceNo() {
        return referenceNo;
    }

    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }    
}
