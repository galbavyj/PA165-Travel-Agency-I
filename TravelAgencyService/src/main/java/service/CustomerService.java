/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import cz.muni.fi.pa165.travelAgency.persistence.entity.Customer;
import java.util.List;

/**
 *
 * @author Martin
 */
public interface CustomerService {


    /**
     * Authenticate customer
     *
     * @param u customer to authenticate
     * @param password password to check
     * @return true if password is correct, false otherwise
     */
    boolean authoriseCustomer(Customer u, String password);


    /**
     * Create new customer
     * 
     * @param c Customer to be created
     * @return created customer
     */
    Customer registerCustomer(Customer c, String plainTextPassword);

    /**
     * Update customer
     * 
     * @param c Customer to be updated
     * @return updated customer
     */
    Customer updateCustomer(Customer c);
    
    /**
     * Remove customer
     * 
     * @param c Customer to be removed
     */
    void removeCustomer(Customer c);

    /**
     * Finds and returns all customers
     * 
     * @return list of all customers
     */
    List<Customer> findAll();
    
    /**
     * Finds and returns customer with specified id
     * 
     * @param customerId Customer id
     * @return customer with specified id
     */
    Customer findById(Long customerId);
    
    /**
     * Finds and returns customer with specified email
     * 
     * @param email Email of customer
     * @return list of customers with specified email
     */
    Customer findByEmail(String email);    
}
