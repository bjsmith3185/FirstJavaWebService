package com.dependencyinjection.m6summative.viewmodel;

import com.dependencyinjection.m6summative.dto.Invoice;
import com.dependencyinjection.m6summative.dto.InvoiceItem;

import java.util.List;
import java.util.Objects;

public class RequestInvoice {


    private Invoice invoice;
    private List<InvoiceItem> items;

    // getters /setters

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public List<InvoiceItem> getItems() {
        return items;
    }

    public void setItems(List<InvoiceItem> items) {
        this.items = items;
    }


    // equals / hash


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestInvoice that = (RequestInvoice) o;
        return invoice.equals(that.invoice) &&
                items.equals(that.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(invoice, items);
    }
}
