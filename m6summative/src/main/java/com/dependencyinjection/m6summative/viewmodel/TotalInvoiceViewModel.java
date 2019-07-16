package com.dependencyinjection.m6summative.viewmodel;

import com.dependencyinjection.m6summative.dto.viewModelDto.CompleteInvoice;
import com.dependencyinjection.m6summative.dto.Customer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TotalInvoiceViewModel {


    private Customer customer;
    private List<CompleteInvoice> invoices = new ArrayList<>();



    // getters / setters

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<CompleteInvoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<CompleteInvoice> invoices) {
        this.invoices = invoices;
    }

    // equals / hash


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TotalInvoiceViewModel that = (TotalInvoiceViewModel) o;
        return customer.equals(that.customer) &&
                invoices.equals(that.invoices);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customer, invoices);
    }
}
