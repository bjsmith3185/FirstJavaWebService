package com.dependencyinjection.m6summative.dao;

import com.dependencyinjection.m6summative.dto.Customer;
import com.dependencyinjection.m6summative.dto.Invoice;
import com.dependencyinjection.m6summative.dto.InvoiceItem;
import com.dependencyinjection.m6summative.dto.Item;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class InvoiceItemDaoTest {

    @Autowired
    ItemDao itemDao;

    @Autowired
    InvoiceDao invoiceDao;

    @Autowired
    CustomerDao customerDao;

    @Autowired
    InvoiceItemDao invoiceItemDao;

    @Before
    public void setUp() throws Exception {
        // clean out the test db

        List<InvoiceItem> invoiceItemList = invoiceItemDao.getAllInvoiceItems();

        invoiceItemList.stream()
                .forEach(invoiceItem -> invoiceItemDao.deleteInvoiceItem(invoiceItem.getInvoiceItemId()));

        List<Invoice> invoiceList = invoiceDao.getAllInvoices();

        invoiceList.stream()
                .forEach(invoice -> invoiceDao.deleteInvoice(invoice.getInvoiceId()));

        // delete all Customers from db
        List<Customer> cList = customerDao.getAllCustomers();

        cList.stream()
                .forEach(customer -> customerDao.deleteCustomer(customer.getCustomerId()));

        List<Item> itemsList = itemDao.getAllItems();

        itemsList.stream()
                .forEach(item -> itemDao.deleteItem(item.getItemId()));




    }


    @Test
    public void addGetDeleteInvoiceItem() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Smith");
        customer.setEmail("brian@mail");
        customer.setCompany("ezShop");
        customer.setPhone("7048887777");
        // add customer to db
        customer = customerDao.addCustomer(customer);

        // define some dates to use
        java.util.Date orderDate = sdf.parse("2019-07-26");
        java.util.Date pickUpDate = sdf.parse("2019-07-27");
        java.util.Date returnDate = sdf.parse("2019-07-28");

        // define Invoice object
        Invoice invoice = new Invoice();
        invoice.setCustomerId(customer.getCustomerId());
        invoice.setOrderDate(orderDate);
        invoice.setPickupDate(pickUpDate);
        invoice.setReturnDate(returnDate);
        invoice.setLateFee(2.00);
        // add invoice to db
        invoice = invoiceDao.addInvoice(invoice);

        Item item = new Item();
        item.setName("The Best Item");
        item.setDescription("The best");
        item.setDailyRate(22.5);
        item = itemDao.addItem(item);

        InvoiceItem invoiceItem = new InvoiceItem();
        invoiceItem.setDiscount(4);
        invoiceItem.setQuantity(10);
        invoiceItem.setUnitRate(25);
        invoiceItem.setInvoiceId(invoice.getInvoiceId());
        invoiceItem.setItemId(item.getItemId());

        invoiceItem = invoiceItemDao.addInvoiceItem(invoiceItem);

        invoiceItem.setQuantity(5);

        invoiceItemDao.updateInvoiceItem(invoiceItem);

        InvoiceItem invoiceItem1 = invoiceItemDao.getInvoiceItem(invoiceItem.getInvoiceItemId());
        assertEquals(invoiceItem1, invoiceItem);

        invoiceItemDao.deleteInvoiceItem(invoiceItem.getInvoiceItemId());

        invoiceItem1 = invoiceItemDao.getInvoiceItem(invoiceItem.getInvoiceItemId());

        assertNull(invoiceItem1);
    }

    @Test
    public void updateInvoiceItem() throws ParseException{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Smith");
        customer.setEmail("brian@mail");
        customer.setCompany("ezShop");
        customer.setPhone("7048887777");
        // add customer to db
        customer = customerDao.addCustomer(customer);

        // define some dates to use
        java.util.Date orderDate = sdf.parse("2019-07-26");
        java.util.Date pickUpDate = sdf.parse("2019-07-27");
        java.util.Date returnDate = sdf.parse("2019-07-28");

        // define Invoice object
        Invoice invoice = new Invoice();
        invoice.setCustomerId(customer.getCustomerId());
        invoice.setOrderDate(orderDate);
        invoice.setPickupDate(pickUpDate);
        invoice.setReturnDate(returnDate);
        invoice.setLateFee(2.00);
        // add invoice to db
        invoice = invoiceDao.addInvoice(invoice);

        Item item = new Item();
        item.setName("The Best Item");
        item.setDescription("The best");
        item.setDailyRate(22.5);
        item = itemDao.addItem(item);

        InvoiceItem invoiceItem = new InvoiceItem();
        invoiceItem.setDiscount(4);
        invoiceItem.setQuantity(10);
        invoiceItem.setUnitRate(25);
        invoiceItem.setInvoiceId(invoice.getInvoiceId());
        invoiceItem.setItemId(item.getItemId());

        invoiceItem = invoiceItemDao.addInvoiceItem(invoiceItem);

        invoiceItem.setQuantity(5);

        invoiceItemDao.updateInvoiceItem(invoiceItem);

        InvoiceItem invoiceItem1 = invoiceItemDao.getInvoiceItem(invoiceItem.getInvoiceItemId());

        assertEquals(invoiceItem1, invoiceItem);
    }

}
