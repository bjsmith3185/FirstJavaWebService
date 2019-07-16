package com.dependencyinjection.m6summative.dto.viewModelDto;

import java.util.List;
import java.util.Objects;

public class CompleteInvoice {

    private int invoiceId;
    private List<InvoiceWithItem> order;





    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public List<InvoiceWithItem> getOrder() {
        return order;
    }

    public void setOrder(List<InvoiceWithItem> order) {
        this.order = order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompleteInvoice that = (CompleteInvoice) o;
        return invoiceId == that.invoiceId &&
                order.equals(that.order);
    }

    @Override
    public int hashCode() {
        return Objects.hash(invoiceId, order);
    }
}
