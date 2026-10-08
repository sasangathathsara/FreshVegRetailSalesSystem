/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Achintha
 */
public class SaleDetail 
{

    private int saleDetailId;
    private int saleId;
    private int productId;
    private String unit;
    private double quantity;
    private double unitPrice;
    private double lineTotal;

    public SaleDetail() {
    }

    public SaleDetail(int saleDetailId,int saleId,int productId, String unit,double quantity, double unitPrice,double lineTotal) 
    {

        this.saleDetailId = saleDetailId;
        this.saleId = saleId;
        this.productId = productId;
        this.unit = unit;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.lineTotal = lineTotal;
    }

    public int getSaleDetailId() 
    {
        return saleDetailId;
    }

    public void setSaleDetailId(int saleDetailId) 
    {
        this.saleDetailId = saleDetailId;
    }

    public int getSaleId() 
    {
        return saleId;
    }

    public void setSaleId(int saleId) 
    {
        this.saleId = saleId;
    }

    public int getProductId() 
    {
        return productId;
    }

    public void setProductId(int productId) 
    {
        this.productId = productId;
    }

    public String getUnit() 
    {
        return unit;
    }

    public void setUnit(String unit) 
    {
        this.unit = unit;
    }

    public double getQuantity() 
    {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) 
    {
        this.unitPrice = unitPrice;
    }

    public double getLineTotal() 
    {
        return lineTotal;
    }

    public void setLineTotal(double lineTotal) 
    {
        this.lineTotal = lineTotal;
    }    
}
