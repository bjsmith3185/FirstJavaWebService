package com.dependencyinjection.m6summative.service;

import com.dependencyinjection.m6summative.dao.*;
import com.dependencyinjection.m6summative.dto.Customer;
import com.dependencyinjection.m6summative.dto.Invoice;
import com.dependencyinjection.m6summative.dto.InvoiceItem;
import com.dependencyinjection.m6summative.dto.Item;
import com.dependencyinjection.m6summative.dto.viewModelDto.CompleteInvoice;
import com.dependencyinjection.m6summative.dto.viewModelDto.InvoiceWithItem;
import com.dependencyinjection.m6summative.viewmodel.RequestInvoice;
import com.dependencyinjection.m6summative.viewmodel.TotalInvoiceViewModel;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;

public class ServiceLayerTest {

    // create a SimpleDateFormat object to use
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    ServiceLayer service;
    CustomerDao customerDao;
    InvoiceDao invoiceDao;
    InvoiceItemDao invoiceItemDao;
    ItemDao itemDao;


    @Before
    public void setUp() throws Exception {
        setUpCustomerMock();
        setUpInvoiceMock();
        setUpInvoiceItemMock();
        setUpItemMock();

        service = new ServiceLayer(customerDao, invoiceDao, invoiceItemDao, itemDao);

    }


    // ServiceLayer Tests
@Test
    public void saveFindOrder() throws Exception {
//               // create a few date variables
//        java.util.Date orderDate = sdf.parse("2019-07-26");
//        java.util.Date pickUpDate = sdf.parse("2019-07-27");
//        java.util.Date returnDate = sdf.parse("2019-07-28");
//
//        // create an invoiceViewModel object
//        RequestInvoice ri = new RequestInvoice();
//
//        Invoice invoice = new Invoice();
//        invoice.setCustomerId(1);
//        invoice.setOrderDate(orderDate);
//        invoice.setPickupDate(pickUpDate);
//        invoice.setReturnDate(returnDate);
//        invoice.setLateFee(2.00);
//        // use service layer to add the invoice_id
//        invoice = service.saveInvoice(invoice);
//
//        // create invoiceItems and put in a list
//        InvoiceItem invoiceItem = new InvoiceItem();
//        invoiceItem.setInvoiceItemId(1);
//        invoiceItem.setItemId(1);
//        invoiceItem.setQuantity(1);
//        invoiceItem.setUnitRate(2.00);
//        invoiceItem.setDiscount(.50);
//        // use service layer to add the invoiceItem_id
//        invoiceItem = service.saveInvoiceItem(invoiceItem);
//
//        List<InvoiceItem> invoiceItemList = new ArrayList<>();
//        invoiceItemList.add(invoiceItem);
//
//        ri.setInvoice(invoice);
//        ri.setInvoice((Invoice) invoiceItemList);
//        // ----- RequestInvoice object is created above
//
//
//        // find the invoice object by id
//        Invoice fromServiceInvoice = service.findInvoice(ri.getInvoice().getInvoiceId());
//
//        // find the invocieItem object by invoice_id
//        InvoiceItem fromServiceInvoiceItem = service.findInvoiceItem(ri.getInvoice().getInvoiceId());
//
//        // compare the invoice and invoiceItem objects that we created to the ones in the mock
//
//        assertEquals(ri.getInvoice(), fromServiceInvoice);
//        assertEquals(ri.getItems(), fromServiceInvoiceItem);


        // create a few date variables
        java.util.Date orderDate = sdf.parse("2019-07-26");
        java.util.Date pickUpDate = sdf.parse("2019-07-27");
        java.util.Date returnDate = sdf.parse("2019-07-28");

        // create an invoiceViewModel object
        RequestInvoice ri = new RequestInvoice();

        Invoice invoice = new Invoice();
        invoice.setCustomerId(1);
        invoice.setOrderDate(orderDate);
        invoice.setPickupDate(pickUpDate);
        invoice.setReturnDate(returnDate);
        invoice.setLateFee(2.00);
        // use service layer to add the invoice_id

        // create invoiceItems and put in a list
        InvoiceItem invoiceItem = new InvoiceItem();
        invoiceItem.setInvoiceItemId(1);
        invoiceItem.setItemId(1);
        invoiceItem.setQuantity(1);
        invoiceItem.setUnitRate(2.00);
        invoiceItem.setDiscount(.50);
        // use service layer to add the invoiceItem_id

        List<InvoiceItem> invoiceItemList = new ArrayList<>();
        invoiceItemList.add(invoiceItem);


        ri.setInvoice(invoice);
        ri.setItems(invoiceItemList);
        // ----- RequestInvoice object is created above

        // use the save method from serviceLayer
        ri = service.saveOrder(ri);

        assertEquals(1, ri.getInvoice().getInvoiceId());



    }

    @Test
    public void getOrderByCustomer() throws Exception {

        TotalInvoiceViewModel tivm = new TotalInvoiceViewModel();
        // define customer object
        Customer customer = new Customer();
        customer.setCustomerId(1);
        customer.setFirstName("brian");
        customer.setLastName("smith");
        customer.setEmail("brian@mail");
        customer.setCompany("ezShop");
        customer.setPhone("7048887777");
        tivm.setCustomer(customer);
        // define CompleteInvoice object

        CompleteInvoice completeInvoice = new CompleteInvoice();
        // set invoiceId
        completeInvoice.setInvoiceId(1);

        // define InvoiceWithItem object
        InvoiceWithItem invoiceWithItem = new InvoiceWithItem();

        invoiceWithItem.setInvoiceItemId(1);
        invoiceWithItem.setQuantity(2);
        invoiceWithItem.setUnitRate(2.00);
        invoiceWithItem.setDiscount(.50);
        invoiceWithItem.setName("drill");
        invoiceWithItem.setDescription("super powerful drill");
        invoiceWithItem.setDailyRate(4.00);
        // pub the invoiceWithItem into a list
        List<InvoiceWithItem> listWithOrder = new ArrayList<>();
        listWithOrder.add(invoiceWithItem);

        // save invoiceWithItem to CompleteInvoice
        completeInvoice.setOrder(listWithOrder);
        // save completeInvoice to a list
        List<CompleteInvoice> completeInvoicesList = new ArrayList<>();
        completeInvoicesList.add(completeInvoice);

        // save completeInvoice to tivm
        tivm.setInvoices(completeInvoicesList);

        // Finally, the tivm object is built..


        // use customer_id 1 to access the serviceLayer and find corresponding invoice

        int customerId = 1;

        // create a new TotalInvoiceViewModel object to populate using the serviceLayer
        TotalInvoiceViewModel fromServiceTivm = service.getOrderByCustomerId(customerId);

       // TotalInvoiceViewModel getone = fromServiceTivm;

        // dont compare object to object since they are composite objects

        assertEquals(tivm.getCustomer().getFirstName(), fromServiceTivm.getCustomer().getFirstName());
//        assertEquals(tivm, fromServiceTivm);


    }
//
//    @Test
//    public void removeOrder() throws Exception {
//        // create and save an order
//        // delete that order by id
//        // get orders by customer
//        // this size should be 0
//
//        // create a few date variables
//        java.util.Date orderDate = sdf.parse("2019-07-26");
//        java.util.Date pickUpDate = sdf.parse("2019-07-27");
//        java.util.Date returnDate = sdf.parse("2019-07-28");
//
//        // create an invoiceViewModel object
//        RequestInvoice ri = new RequestInvoice();
//
//        Invoice invoice = new Invoice();
//        invoice.setCustomerId(1);
//        invoice.setOrderDate(orderDate);
//        invoice.setPickupDate(pickUpDate);
//        invoice.setReturnDate(returnDate);
//        invoice.setLateFee(2.00);
//        // use service layer to add the invoice_id
//        invoice = service.saveInvoice(invoice);
//
//        // create invoiceItems and put in a list
//        InvoiceItem invoiceItem = new InvoiceItem();
//        invoiceItem.setInvoiceItemId(1);
//        invoiceItem.setItemId(1);
//        invoiceItem.setQuantity(1);
//        invoiceItem.setUnitRate(2.00);
//        invoiceItem.setDiscount(.50);
//        // use service layer to add the invoiceItem_id
//        invoiceItem = service.saveInvoiceItem(invoiceItem);
//
//        List<InvoiceItem> invoiceItemList = new ArrayList<>();
//        invoiceItemList.add(invoiceItem);
//
//        ri.setInvoice(invoice);
//        ri.setInvoice((Invoice) invoiceItemList);
//        // ----- RequestInvoice object is created above
//
//        // use the save method from serviceLayer
//        ri = service.saveOrder(ri);
//
//        // use the remove method from serviceLayer
//        service.removeOrder(ri.getInvoice().getInvoiceId());
//
//        // use getOrderByCustomerId method from serviceLayer
//        TotalInvoiceViewModel fromServiceList = service.getOrderByCustomerId(1);
//        // List<TotalInvoiceViewModel> fromServiceList = (List<TotalInvoiceViewModel>) service.getOrderByCustomerId(1);
//
//
//        assertNull(fromServiceList);
//
//
//    }


//    @Test
//    public void saveFindFindAllCustomer() {
//
//        Customer customer = new Customer();
//        customer.setFirstName("brian");
//        customer.setLastName("smith");
//        customer.setEmail("brian@mail");
//        customer.setCompany("ezShop");
//        customer.setPhone("7048887777");
//
//        customer = service.saveCustomer(customer);
//        Customer fromService = service.findCustomer(customer.getCustomerId());
//        assertEquals(customer, fromService);
//
//        List<Customer> cList = service.findAllCustomers();
//        assertEquals(1, cList.size());
//        assertEquals(customer, cList.get(0));
//
//    }


//    @Test
//    public void saveFindFindAllItem() {
//
//        Item item = new Item();
//        item.setName("drill");
//        item.setDescription("super powerful drill");
//        item.setDailyRate(4.00);
//
//        item = service.saveItem(item);
//        Item fromService = service.findItem(item.getItemId());
//        assertEquals(item, fromService);
//
//        List<Item> itemList = service.findAllItems();
//        assertEquals(1, itemList.size());
//        assertEquals(item, itemList.get(0));
//
//
//    }

    @Test
    public void saveUpdateFindItem() {

        Item item = new Item();
        item.setName("drill");
        item.setDescription("super powerful drill");
        item.setDailyRate(4.00);

        item = service.saveItem(item);
        Item fromService = service.findItem(item.getItemId());
        assertEquals(item, fromService);

        List<Item> itemList = service.findAllItems();
        assertEquals(1, itemList.size());
        assertEquals(item, itemList.get(0));


    }


    // Helper Methods
    private void setUpCustomerMock() {

        customerDao = mock(CustomerDaoJdbsTemplateImpl.class);

        Customer customer = new Customer();
        customer.setCustomerId(1);
        customer.setFirstName("brian");
        customer.setLastName("smith");
        customer.setEmail("brian@mail");
        customer.setCompany("ezShop");
        customer.setPhone("7048887777");

        Customer updatedCustomer = new Customer();
        updatedCustomer.setFirstName("brian");
        updatedCustomer.setLastName("smith");
        updatedCustomer.setEmail("brian@mail");
        updatedCustomer.setCompany("ezShop");
        updatedCustomer.setPhone("7048887777");


        List<Customer> cList = new ArrayList<>();
        cList.add(customer);

        doReturn(customer).when(customerDao).addCustomer(updatedCustomer);
        doReturn(customer).when(customerDao).getCustomer(1);
        doReturn(cList).when(customerDao).getAllCustomers();
    }

    private void setUpInvoiceMock() throws ParseException {
        invoiceDao = mock(InvoiceDaoJdbsTemplaceImpl.class);
        java.util.Date orderDate = sdf.parse("2019-07-26");
        java.util.Date pickUpDate = sdf.parse("2019-07-27");
        java.util.Date returnDate = sdf.parse("2019-07-28");

        Invoice invoice = new Invoice();
        invoice.setInvoiceId(1);
        invoice.setCustomerId(1);
        invoice.setOrderDate(orderDate);
        invoice.setPickupDate(pickUpDate);
        invoice.setReturnDate(returnDate);
        invoice.setLateFee(2.00);

        Invoice invoice2 = new Invoice();
        invoice2.setCustomerId(1);
        invoice2.setOrderDate(orderDate);
        invoice2.setPickupDate(pickUpDate);
        invoice2.setReturnDate(returnDate);
        invoice2.setLateFee(2.00);

        List<Invoice> invList = new ArrayList<>();
        invList.add(invoice);

        doReturn(invoice).when(invoiceDao).addInvoice(invoice2);
        doReturn(invoice).when(invoiceDao).getInvoice(1);
        doReturn(invList).when(invoiceDao).getAllInvoices();
        // add findInvoiceByCustomer method
        doReturn(invList).when(invoiceDao).findInvoicesByCustomer(1);

    }

    private void setUpInvoiceItemMock() {
        invoiceItemDao = mock(InvoiceItemDao.class);
        InvoiceItem invoiceItem = new InvoiceItem();
        invoiceItem.setInvoiceItemId(1);
        invoiceItem.setInvoiceId(1);
        invoiceItem.setItemId(1);
        invoiceItem.setQuantity(1);
        invoiceItem.setUnitRate(2.00);
        invoiceItem.setDiscount(.50);

        InvoiceItem invoiceItem2 = new InvoiceItem();
        invoiceItem2.setInvoiceId(1);
        invoiceItem2.setItemId(1);
        invoiceItem2.setQuantity(1);
        invoiceItem2.setUnitRate(2.00);
        invoiceItem2.setDiscount(.50);

        List<InvoiceItem> invoiceItemList = new ArrayList<>();
        invoiceItemList.add(invoiceItem);

        doReturn(invoiceItem).when(invoiceItemDao).addInvoiceItem(invoiceItem2);
        doReturn(invoiceItem).when(invoiceItemDao).getInvoiceItem(1);
        doReturn(invoiceItemList).when(invoiceItemDao).getAllInvoiceItems();

        doReturn(invoiceItemList).when(invoiceItemDao).getInvoiceItemByInvoice(1);

    }

    private void setUpItemMock() {
        itemDao = mock(ItemDao.class);
        Item item = new Item();
        item.setItemId(1);
        item.setName("drill");
        item.setDescription("super powerful drill");
        item.setDailyRate(4.00);

        Item item2 = new Item();
        item2.setName("drill");
        item2.setDescription("super powerful drill");
        item2.setDailyRate(4.00);

        Item updatedItem = new Item();
        updatedItem.setItemId(1);
        updatedItem.setName("hammer");
        updatedItem.setDescription("super powerful drill");
        updatedItem.setDailyRate(4.00);

        List<Item> itemList = new ArrayList<>();
        itemList.add(item);

        doReturn(item).when(itemDao).addItem(item2);
        doReturn(item).when(itemDao).getItem(1);
        doReturn(itemList).when(itemDao).getAllItems();
    }


//    private void setUpIGetInvoiceMock() throws Exception {
//        // Add item
//        itemDao = mock(ItemDao.class);
//        Item item = new Item();
//        item.setItemId(1);
//        item.setName("drill");
//        item.setDescription("super powerful drill");
//        item.setDailyRate(4.00);
//
//        doReturn(item).when(itemDao).getItem(1);
//
//        // Add invoice_item
//
//
//        invoiceItemDao = mock(InvoiceItemDao.class);
//        InvoiceItem invoiceItem = new InvoiceItem();
//        invoiceItem.setInvoiceItemId(1);
//        invoiceItem.setInvoiceId(1);
//        invoiceItem.setItemId(1);
//        invoiceItem.setQuantity(1);
//        invoiceItem.setUnitRate(2.00);
//        invoiceItem.setDiscount(.50);
//
//        doReturn(invoiceItem).when(invoiceItemDao).getInvoiceItem(1);
//
//        // Add invoice
//        invoiceDao = mock(InvoiceDaoJdbsTemplaceImpl.class);
//        java.util.Date orderDate = sdf.parse("2019-07-26");
//        java.util.Date pickUpDate = sdf.parse("2019-07-27");
//        java.util.Date returnDate = sdf.parse("2019-07-28");
//
//        Invoice invoice = new Invoice();
//        invoice.setInvoiceId(1);
//        invoice.setCustomerId(1);
//        invoice.setOrderDate(orderDate);
//        invoice.setPickupDate(pickUpDate);
//        invoice.setReturnDate(returnDate);
//        invoice.setLateFee(2.00);
//
//        doReturn(invoice).when(invoiceDao).getInvoice(1);
//
//
//
//
//
//
//
//    }


}
