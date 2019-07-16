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
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class CustomerDaoTest {



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
    public void addGetDeleteCustomer() {

        // delete all Customers from db
        List<Customer> cList = customerDao.getAllCustomers();

        cList.stream()
                .forEach(customer -> customerDao.deleteCustomer(customer.getCustomerId()));

        List<Customer> listFromDatabase = customerDao.getAllCustomers();
        // check to see if the length of the list is 0
        Assert.assertEquals(0, listFromDatabase.size());


        // define customer object
        Customer customer = new Customer();
        customer.setFirstName("brian");
        customer.setLastName("smith");
        customer.setEmail("brian@mail");
        customer.setCompany("ezShop");
        customer.setPhone("7048887777");
        // add customer to db
        customer = customerDao.addCustomer(customer);

        // create a new customer object using the previous customerId
        Customer fromDatabase = new Customer();
        fromDatabase = customerDao.getCustomer(customer.getCustomerId());

        // compare the two customer objects
        Assert.assertEquals(customer, fromDatabase);

    }



    @Test
    public void updateCustomer() throws ParseException {
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
        // update the first name
        customer.setFirstName("jordan");

        customerDao.updateCustomer(customer);

        Customer fromDatabse = customerDao.getCustomer(customer.getCustomerId());

        assertEquals(customer, fromDatabse);

    }


}
