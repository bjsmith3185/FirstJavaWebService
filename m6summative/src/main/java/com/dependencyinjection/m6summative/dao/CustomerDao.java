package com.dependencyinjection.m6summative.dao;

import com.dependencyinjection.m6summative.dto.Customer;

import java.util.List;

public interface CustomerDao {

    Customer getCustomer( int id );

    List<Customer> getAllCustomers();

    Customer addCustomer( Customer customer );

    void updateCustomer( Customer customer );

    void deleteCustomer( int id );

}
