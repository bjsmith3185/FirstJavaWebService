package com.dependencyinjection.m6summative.dao;


import com.dependencyinjection.m6summative.dto.InvoiceItem;

import java.util.List;

public interface InvoiceItemDao {

    InvoiceItem getInvoiceItem(int id );

    List<InvoiceItem> getAllInvoiceItems();

    InvoiceItem addInvoiceItem( InvoiceItem invoiceItem );

    void updateInvoiceItem( InvoiceItem invoiceItem );

    void deleteInvoiceItem( int id );

    // this one was added to get invoice_items by invoice_id

    List<InvoiceItem> getInvoiceItemByInvoice( int id );

}
