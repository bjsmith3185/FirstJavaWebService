package com.dependencyinjection.m6summative.dao;

import com.dependencyinjection.m6summative.dto.Customer;
import com.dependencyinjection.m6summative.dto.Invoice;
import com.dependencyinjection.m6summative.dto.InvoiceItem;
import com.dependencyinjection.m6summative.dto.Item;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class InvoiceDaoTest {

    @Autowired
    protected InvoiceDao invoiceDao;

    @Autowired
    protected CustomerDao customerDao;

    @Autowired
    protected ItemDao itemDao;

    @Autowired
    protected InvoiceItemDao invoiceItemDao;

    @Before
    public void setUp() throws Exception {


        // clean out the invoiceItem table
        List<InvoiceItem> invoiceItemList = invoiceItemDao.getAllInvoiceItems();
        invoiceItemList.stream()
                .forEach(inv -> invoiceItemDao.deleteInvoiceItem(inv.getInvoiceItemId()));

        // clean out the invoice table
        List<Invoice> invoiceList = invoiceDao.getAllInvoices();
        invoiceList.stream()
                .forEach(inv -> invoiceDao.deleteInvoice(inv.getInvoiceId()));


        // clean out the customer table
        List<Customer> cList = customerDao.getAllCustomers();
        cList.stream()
                .forEach(customer -> customerDao.deleteCustomer(customer.getCustomerId()));


        // clean out the item table
        List<Item> itemList = itemDao.getAllItems();
        itemList.stream()
                .forEach(inv -> itemDao.deleteItem(inv.getItemId()));


    }

//    @Test
//    public void testme() {
//        List<Customer> cList = customerDao.getAllCustomers();
//
//        cList.stream()
//                .forEach(customer -> customerDao.deleteCustomer(customer.getCustomerId()));
//
//
//
//    }


    @Test
    public void addGetDeleteInvoice() throws Exception {
        // create a SimpleDateFormat object to use
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        // define customer object
        Customer customer = new Customer();
        customer.setFirstName("brian");
        customer.setLastName("smith");
        customer.setEmail("brian@mail");
        customer.setCompany("ezShop");
        customer.setPhone("7048887777");
        // add customer to db and get the id
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
        // add customer to db
        invoice = invoiceDao.addInvoice(invoice);

        Invoice temp = invoice;


        Invoice fromServiceInvoice = invoiceDao.getInvoice(invoice.getInvoiceId());

        Assert.assertEquals(invoice, fromServiceInvoice);



//
//        // create a new invoice object using the previous customerId
//        Invoice fromTheDatabase = new Invoice();
//        fromTheDatabase = invoiceDao.getInvoice(invoice.getInvoiceId());
//
//        // compare the two customer objects
//        Assert.assertEquals(invoice, fromTheDatabase);
//
//        // Maybe the customer id is not equal, need to use debugger


    }

    @Test
    public void updateInvoice() throws ParseException {
        // create a SimpleDateFormat object to use
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        // define customer object
        Customer customer = new Customer();
        customer.setFirstName("brian");
        customer.setLastName("smith");
        customer.setEmail("brian@mail");
        customer.setCompany("ezShop");
        customer.setPhone("7048887777");
        // add customer to db and get the id
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
        // add customer to db
        invoice = invoiceDao.addInvoice(invoice);

        invoice.setLateFee(5.00);

        invoiceDao.updateInvoice(invoice);

        Invoice invoice1 = invoiceDao.getInvoice(invoice.getInvoiceId());

        assertEquals(invoice1, invoice);
    }

}
