package com.dependencyinjection.m6summative.dto.viewModelDto;


import java.util.Objects;

public class InvoiceWithItem  {


    private int invoiceItemId;
    private int quantity;
    private double unitRate;
    private double discount;

    private String name;
    private String description;
    private double dailyRate;



    // getters / setters

    public int getInvoiceItemId() {
        return invoiceItemId;
    }

    public void setInvoiceItemId(int invoiceItemId) {
        this.invoiceItemId = invoiceItemId;
    }

//    public int getInvoiceId() {
//        return invoiceId;
//    }
//
//    public void setInvoiceId(int invoiceId) {
//        this.invoiceId = invoiceId;
//    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnitRate() {
        return unitRate;
    }

    public void setUnitRate(double unitRate) {
        this.unitRate = unitRate;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

//    public int getItemId() {
//        return itemId;
//    }
//
//    public void setItemId(int itemId) {
//        this.itemId = itemId;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getDailyRate() {
        return dailyRate;
    }

    public void setDailyRate(double dailyRate) {
        this.dailyRate = dailyRate;
    }

    // equals / hash


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InvoiceWithItem that = (InvoiceWithItem) o;
        return invoiceItemId == that.invoiceItemId &&
                quantity == that.quantity &&
                Double.compare(that.unitRate, unitRate) == 0 &&
                Double.compare(that.discount, discount) == 0 &&
                Double.compare(that.dailyRate, dailyRate) == 0 &&
                name.equals(that.name) &&
                description.equals(that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(invoiceItemId, quantity, unitRate, discount, name, description, dailyRate);
    }
}
