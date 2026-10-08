/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDateTime;

/**
 *
 * @author Achintha
 */
public class StockMovement {

    private int movementId;
    private int productId;
    private String movementType;
    private String referenceType;
    private int referenceId;
    private double quantity;
    private LocalDateTime movementDate;
    private String reason;
    private String notes;
    private int createdBy;

    public StockMovement() {
    }

    public StockMovement(int movementId, int productId,
                         String movementType, String referenceType,
                         int referenceId, double quantity,
                         LocalDateTime movementDate,
                         String reason, String notes,
                         int createdBy) {

        this.movementId = movementId;
        this.productId = productId;
        this.movementType = movementType;
        this.referenceType = referenceType;
        this.referenceId = referenceId;
        this.quantity = quantity;
        this.movementDate = movementDate;
        this.reason = reason;
        this.notes = notes;
        this.createdBy = createdBy;
    }

    public int getMovementId() {
        return movementId;
    }

    public void setMovementId(int movementId) {
        this.movementId = movementId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getMovementType() {
        return movementType;
    }

    public void setMovementType(String movementType) {
        this.movementType = movementType;
    }

    public String getReferenceType() {
        return referenceType;
    }

    public void setReferenceType(String referenceType) {
        this.referenceType = referenceType;
    }

    public int getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(int referenceId) {
        this.referenceId = referenceId;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getMovementDate() {
        return movementDate;
    }

    public void setMovementDate(LocalDateTime movementDate) {
        this.movementDate = movementDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
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
