package com.dependencyinjection.m6summative.service;

import com.dependencyinjection.m6summative.dao.CustomerDao;
import com.dependencyinjection.m6summative.dao.InvoiceDao;
import com.dependencyinjection.m6summative.dao.InvoiceItemDao;
import com.dependencyinjection.m6summative.dao.ItemDao;
import com.dependencyinjection.m6summative.dto.Customer;
import com.dependencyinjection.m6summative.dto.Invoice;
import com.dependencyinjection.m6summative.dto.InvoiceItem;
import com.dependencyinjection.m6summative.dto.Item;
import com.dependencyinjection.m6summative.dto.viewModelDto.CompleteInvoice;
import com.dependencyinjection.m6summative.dto.viewModelDto.InvoiceWithItem;
import com.dependencyinjection.m6summative.viewmodel.RequestInvoice;
import com.dependencyinjection.m6summative.viewmodel.TotalInvoiceViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class ServiceLayer {

    private CustomerDao customerDao;
    private InvoiceDao invoiceDao;
    private InvoiceItemDao invoiceItemDao;
    private ItemDao itemDao;


    @Autowired
    public ServiceLayer(CustomerDao customerDao, InvoiceDao invoiceDao, InvoiceItemDao invoiceItemDao, ItemDao itemDao) {
        this.customerDao = customerDao;
        this.invoiceDao = invoiceDao;
        this.invoiceItemDao = invoiceItemDao;
        this.itemDao = itemDao;
    }

    //
    // invoice API
    //

    @Transactional
//    public RequestInvoice saveInvoice(RequestInvoice requestInvoice) {
    public RequestInvoice saveOrder(RequestInvoice requestInvoice) {

        // save invoice
        Invoice invoice = new Invoice();
        invoice.setCustomerId(requestInvoice.getInvoice().getCustomerId());
        invoice.setOrderDate(requestInvoice.getInvoice().getOrderDate());
        invoice.setPickupDate(requestInvoice.getInvoice().getPickupDate());
        invoice.setReturnDate(requestInvoice.getInvoice().getReturnDate());
        invoice.setLateFee(requestInvoice.getInvoice().getLateFee());

        invoice = invoiceDao.addInvoice(invoice);
        requestInvoice.setInvoice(invoice);


        // save invoice-items with invoice id
        // for each number in this array create an invoice_item with
        // the corresponding item_id (which is the number in the array)

        List<InvoiceItem> itemsList = requestInvoice.getItems();

        for ( InvoiceItem item: itemsList) {

            InvoiceItem tempInvoiceItem = new InvoiceItem();
            tempInvoiceItem.setInvoiceId(invoice.getInvoiceId());
            tempInvoiceItem.setItemId(item.getItemId());
            tempInvoiceItem.setQuantity(item.getQuantity());
            tempInvoiceItem.setUnitRate(item.getUnitRate());
            tempInvoiceItem.setDiscount(item.getDiscount());

            invoiceItemDao.addInvoiceItem(tempInvoiceItem);
        }

        // get all invoice_items by invoice_id
        itemsList = invoiceItemDao.getInvoiceItemByInvoice(invoice.getInvoiceId());

        requestInvoice.setItems(itemsList);

        return requestInvoice;
    }



    public TotalInvoiceViewModel getOrderByCustomerId( int id ) {
        // create a TotalInvoiceViewModel to populate as we go
        TotalInvoiceViewModel tivm = new TotalInvoiceViewModel();

        // create customer object from id given
        Customer customer = new Customer();
        customer = customerDao.getCustomer( id );
        // add customer to tivm
        tivm.setCustomer(customer);

        // find invoice by customer id
        List<Invoice> allInvoiceList = invoiceDao.findInvoicesByCustomer( id );

        // if this is 0 then there are no invoices

//        if (allInvoiceList.size() == 0 ) {
//            return tivm;
//        }

        // this object hold the entire order for each invoice
        CompleteInvoice completeInvoice = new CompleteInvoice();

        for ( Invoice eachInvoice: allInvoiceList ) {

            // set invoice_id to completeInvoice
            completeInvoice.setInvoiceId(eachInvoice.getInvoiceId());

            // new list to hold each invoice_item with detailed item
            List<InvoiceWithItem> tempInvoiceWithItemList = new ArrayList<>();

            List<InvoiceItem> tempInvoiceItemList = invoiceItemDao.getInvoiceItemByInvoice(eachInvoice.getInvoiceId());
            // Now have all invoice items for each invoice

            for ( InvoiceItem eachInvoiceItem: tempInvoiceItemList ) {
                // get the related item from the item invoice list
                Item tempItem = itemDao.getItem(eachInvoiceItem.getItemId());

                // populate the tempInvoicewithitem object
                InvoiceWithItem tempInventoryWithItem = new InvoiceWithItem();
                tempInventoryWithItem.setQuantity(eachInvoiceItem.getQuantity());
                tempInventoryWithItem.setUnitRate(eachInvoiceItem.getUnitRate());
                tempInventoryWithItem.setDiscount(eachInvoiceItem.getDiscount());
                tempInventoryWithItem.setName(tempItem.getName());
                tempInventoryWithItem.setDescription(tempItem.getDescription());
                tempInventoryWithItem.setDailyRate(tempItem.getDailyRate());

               // push this object into an array
                tempInvoiceWithItemList.add(tempInventoryWithItem);

            }

            // add the array to the complete invoice object
            completeInvoice.setOrder(tempInvoiceWithItemList);

        }

            // add orders to the tivm
        List<CompleteInvoice> tempCompleteInvoiceObject = new ArrayList<>();
        tempCompleteInvoiceObject.add(completeInvoice);

        tivm.setInvoices(tempCompleteInvoiceObject);

    //    tivm.setInvoices((List<CompleteInvoice>) completeInvoice);

        return tivm;
    }


    @Transactional
    public void removeOrder( int id ) {

        // remove all associated invoice_items first
        List<InvoiceItem> inventoryItemList = invoiceItemDao.getInvoiceItemByInvoice(id);

        inventoryItemList.stream()
                .forEach(item -> invoiceDao.deleteInvoice(item.getInvoiceItemId()));

        // remove invoice
        invoiceDao.deleteInvoice(id);

    }



    //
    // Customer API
    //

    public Customer saveCustomer(Customer customer) {

        return customerDao.addCustomer(customer);
    }

    public Customer findCustomer(int id) {

        return customerDao.getCustomer(id);
    }

    public List<Customer> findAllCustomers() {

        return customerDao.getAllCustomers();
    }

    public void updateCustomer(Customer customer) {

        customerDao.updateCustomer(customer);
    }

    public void removeCustomer(int id) {

        customerDao.deleteCustomer(id);
    }


    //
    // item API
    //

    public Item saveItem(Item item) {

        return itemDao.addItem(item);
    }

    public Item findItem(int id) {

        return itemDao.getItem(id);
    }

    public List<Item> findAllItems() {

        return itemDao.getAllItems();
    }

    public void updateItem(Item item) {

        itemDao.updateItem(item);
    }

    public void removeItem(int id) {

        itemDao.deleteItem(id);
    }

//---------- need the methods below to run the serviceLayerTest ---


    //
    // invoice API
    //

    public Invoice saveInvoice(Invoice invoice) {

        return invoiceDao.addInvoice(invoice);
    }

    public Invoice findInvoice(int id) {

        return invoiceDao.getInvoice(id);
    }

    public List<Invoice> findAllInvoices() {

        return invoiceDao.getAllInvoices();
    }

    public void updateInvoice(Invoice invoice) {

        invoiceDao.updateInvoice(invoice);
    }

    public void removeInvoice(int id) {

        invoiceDao.deleteInvoice(id);
    }

    //
    // invoice_item API
    //

    public InvoiceItem saveInvoiceItem(InvoiceItem invoiceItem) {

        return invoiceItemDao.addInvoiceItem(invoiceItem);
    }

    public InvoiceItem findInvoiceItem(int id) {

        return invoiceItemDao.getInvoiceItem(id);
    }

    public List<InvoiceItem> findAllInvoiceItems() {

        return invoiceItemDao.getAllInvoiceItems();
    }

    public void updateInvoiceItem(InvoiceItem invoiceItem) {

        invoiceItemDao.updateInvoiceItem(invoiceItem);
    }

    public void removeInvoiceItem(int id) {

        invoiceItemDao.deleteInvoiceItem(id);
    }




//    // Helper Methods
//    private TotalInvoiceViewModel buildTotalInvoiceViewModel(Invoice invoice) {
//
//        // get the associated invoice
////        Invoice invoiceIvm = invoiceDao.getInvoice(invoice.getInvoiceId());
//        Invoice invoiceIvm = invoiceDao.getInvoice(invoice.getInvoiceId());
//
//        // get the associated customer
//        Customer customerIvm = customerDao.getCustomer(invoiceIvm.getCustomerId());
//
//        //
//        //  create list for items
//
//        // list of invoice_items
//        List<InvoiceItem> itemList = invoiceItemDao.getInvoiceItemByInvoice(invoice.getInvoiceId());
//
//        // create the empty Invoicewithitem array to populate in the for each loop
//        List<InvoiceWithItem> invoiceItemList = new ArrayList<>();
//
//        // loop the list and for each get and populate the item info
//
//
//        for ( InvoiceItem eachInvoice: itemList ) {
//            InvoiceWithItem tempItem = new InvoiceWithItem();
//
//            // set the invoice_item data to the InvoiceWithItem object
//            tempItem.setInvoiceItemId(eachInvoice.getInvoiceItemId());
//            tempItem.setQuantity(eachInvoice.getQuantity());
//            tempItem.setUnitRate(eachInvoice.getUnitRate());
//            tempItem.setDiscount(eachInvoice.getDiscount());
//
//            // get the specific item from the invoice_item object using item_id
//            Item item = itemDao.getItem(eachInvoice.getItemId());
//            // set the item data to the InvoiceWithItem object
//            tempItem.setName(item.getName());
//            tempItem.setDescription(item.getDescription());
//            tempItem.setDailyRate(item.getDailyRate());
//            // add each tempItem to the invocieItemList Array
//            invoiceItemList.add(tempItem);
//
//        }
//        // put the info from above into the final TotalInvoiceViewModel object
//        TotalInvoiceViewModel tivm = new TotalInvoiceViewModel();
//        tivm.setInvoiceId(invoiceIvm.getInvoiceId());
//        tivm.setOrderDate(invoiceIvm.getOrderDate());
//        tivm.setPickupDate(invoiceIvm.getPickupDate());
//        tivm.setReturnDate(invoiceIvm.getReturnDate());
//        tivm.setLateFee(invoiceIvm.getLateFee());
//        tivm.setCustomer(customerIvm);
//
//        tivm.setInvoiceItems(invoiceItemList);
//
//
//        return tivm;
//
//    }






    }
