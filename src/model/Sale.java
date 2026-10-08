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
public class Sale {

    private int saleId;
    private String invoiceNo;
    private int customerId;
    private LocalDate saleDate;

    private double grossAmount;
    private double discountAmount;
    private double netAmount;
    private double paidAmount;
    private double outstandingAmount;

    private String paymentStatus;
    private String notes;
    private int createdBy;

    public Sale() {
    }

    public Sale(int saleId, String invoiceNo, int customerId,
                LocalDate saleDate, double grossAmount,
                double discountAmount, double netAmount,
                double paidAmount, double outstandingAmount,
                String paymentStatus, String notes,
                int createdBy) {

        this.saleId = saleId;
        this.invoiceNo = invoiceNo;
        this.customerId = customerId;
        this.saleDate = saleDate;
        this.grossAmount = grossAmount;
        this.discountAmount = discountAmount;
        this.netAmount = netAmount;
        this.paidAmount = paidAmount;
        this.outstandingAmount = outstandingAmount;
        this.paymentStatus = paymentStatus;
        this.notes = notes;
        this.createdBy = createdBy;
    }

    public int getSaleId() {
        return saleId;
    }

    public void setSaleId(int saleId) {
        this.saleId = saleId;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public LocalDate getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(LocalDate saleDate) {
        this.saleDate = saleDate;
    }

    public double getGrossAmount() {
        return grossAmount;
    }

    public void setGrossAmount(double grossAmount) {
        this.grossAmount = grossAmount;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public double getNetAmount() {
        return netAmount;
    }

    public void setNetAmount(double netAmount) {
        this.netAmount = netAmount;
    }

    public double getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(double paidAmount) {
        this.paidAmount = paidAmount;
    }

    public double getOutstandingAmount() {
        return outstandingAmount;
    }

    public void setOutstandingAmount(double outstandingAmount) {
        this.outstandingAmount = outstandingAmount;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
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
