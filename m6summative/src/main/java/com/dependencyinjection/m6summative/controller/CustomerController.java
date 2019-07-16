package com.dependencyinjection.m6summative.controller;

import com.dependencyinjection.m6summative.dto.Customer;
import com.dependencyinjection.m6summative.service.ServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CustomerController {


    @Autowired
    private ServiceLayer service;

    //when using postman for the JSON format, use camelcase instead of underscores for the properties
    @RequestMapping(value="/customer", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public Customer createCustomer(@RequestBody Customer customer) {
        return service.saveCustomer(customer);
    }

    @RequestMapping(value="/customer/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Customer getCustomerById(@PathVariable(name="id") int id ) {
        return service.findCustomer(id);
    }

    @RequestMapping(value="/customers", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Customer> getAllCustomers() {
        return service.findAllCustomers();
    }

    @RequestMapping(value="/customer/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCustomer(@RequestBody Customer customer, @PathVariable(name = "id") int id ) {
        if (id != customer.getCustomerId()) {
            throw new IllegalArgumentException("Customer ID on path must match the ID in the Customer object.");
        }
        service.updateCustomer(customer);
    }

    @RequestMapping(value="/customer/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(@PathVariable(name="id") int id ) {
        service.removeCustomer(id);
    }


}
