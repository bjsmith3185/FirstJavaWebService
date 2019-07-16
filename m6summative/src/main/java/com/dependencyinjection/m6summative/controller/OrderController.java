package com.dependencyinjection.m6summative.controller;

import com.dependencyinjection.m6summative.service.ServiceLayer;
import com.dependencyinjection.m6summative.viewmodel.RequestInvoice;
import com.dependencyinjection.m6summative.viewmodel.TotalInvoiceViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrderController {


    @Autowired
    private ServiceLayer service;

    // Create a new order
    @RequestMapping(value="/order", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public RequestInvoice createOrder(@RequestBody RequestInvoice requestInvoice) {
        return service.saveOrder(requestInvoice);
    }

    // find an order by customer id
    @RequestMapping(value="/order/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public TotalInvoiceViewModel findOrderByCustomerId( @PathVariable(name="id") int id ) {
        return service.getOrderByCustomerId(id);
    }

    // delete an order by id
    @RequestMapping(value="/order/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder( @PathVariable(name="id") int id ) {
        service.removeOrder(id);
    }


}
