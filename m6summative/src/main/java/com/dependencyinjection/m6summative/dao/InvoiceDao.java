package com.dependencyinjection.m6summative.dao;

import com.dependencyinjection.m6summative.dto.Invoice;


import java.util.List;

public interface InvoiceDao {

    Invoice getInvoice(int id );

    List<Invoice> getAllInvoices();

    Invoice addInvoice( Invoice invoice );

    void updateInvoice( Invoice invoice );

    void deleteInvoice( int id );

    List<Invoice> findInvoicesByCustomer( int customerId );
}
