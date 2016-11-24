/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import cz.muni.fi.pa165.travelAgency.persistence.entity.Customer;
import cz.muni.fi.pa165.travelAgency.persistence.entity.Reservation;
import java.util.List;

/**
 *
 * @author Martin
 */
public interface CustomerService {

    /**
     * Create new customer
     * 
     * @param c Customer to be created
     * @param plainTextPassword password of Customer to be encrypted
     */
    void registerCustomer(Customer c, String plainTextPassword);

    /**
     * Authenticate customer
     *
     * @param c customer to authenticate
     * @param password password to check
     * @return true if password is correct, false if authentication failed
     */
    boolean authenticateCustomer(Customer c, String password);


    /**
     * Update customer
     * 
     * @param c Customer to be updated
     */
    void updateCustomer(Customer c);
    
    /**
     * Remove customer
     * 
     * @param c Customer to be removed
     */
    void removeCustomer(Customer c);

    /**
     * Find all customers
     * 
     * @return list of all customers
     */
    List<Customer> findAll();
    
    /**
     * Find customer with given id
     * 
     * @param customerId Customer id
     * @return customer with specified id
     */
    Customer findById(Long customerId);
    
    /**
     * Finds and returns customer with given email
     * 
     * @param email Email of customer
     * @return customer with given email
     */
    Customer findByEmail(String email);    
    
    
    /**
     * adds reservation to customer
     *
     * @param customer customer of new reservation
     * @param reservation reservation to be added
     * @throws IllegalArgumentException in case customer is not found
     */
    void addReservationToCustomer(Customer customer, Reservation reservation);
}
