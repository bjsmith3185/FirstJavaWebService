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

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ItemDaoTest {

    @Autowired
    protected CustomerDao customerDao;

    @Autowired InvoiceDao invoiceDao;

    @Autowired InvoiceItemDao invoiceItemDao;

    @Autowired
    protected ItemDao itemDao;

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
    public void addGetDeleteItem() {

        // define customer object
        Item item = new Item();
        item.setDailyRate(22.5);
        item.setDescription("The best");
        item.setName("The Best Item");
        // add customer to db
        item = itemDao.addItem(item);

        // create a new item object using the previous itemId
        Item fromDatabase = new Item();
        fromDatabase = itemDao.getItem(item.getItemId());

        // compare the two item objects
        Assert.assertEquals(item, fromDatabase);

        itemDao.deleteItem(item.getItemId());

        fromDatabase = itemDao.getItem(item.getItemId());

        assertNull(fromDatabase);


    }

    @Test
    public void updateItem() {

        // define customer object
        Item item = new Item();
        item.setName("The Best Item");
        item.setDescription("The best");
        item.setDailyRate(22.5);

        // add customer to db
        item = itemDao.addItem(item);

        item.setName("The Even Better Name");

        itemDao.updateItem(item);

        Item item1 = itemDao.getItem(item.getItemId());

        assertEquals(item1, item);
    }

    @Test
    public void getAllItems() {
        Item item = new Item();
        item.setName("The Best Item");
        item.setDescription("The best");
        item.setDailyRate(22.5);
        itemDao.addItem(item);

        Item item1 = new Item();
        item1.setName("The Second Item");
        item1.setDescription("The second");
        item1.setDailyRate(22.5);

        itemDao.addItem(item1);

        List<Item> iList = itemDao.getAllItems();

        assertEquals(2, iList.size());


    }



}
