/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.travelAgency.persistence.dao;

import cz.muni.fi.pa165.travelAgency.persistence.entity.Customer;
import java.util.List;

/**
 *
 * @author Martin
 */
public interface CustomerDao {

    /**
     * Add new customer into database
     *
     * @param customer customer to be added into database
     */
    public void create(Customer customer);

    /**
     * Removes customer from database
     *
     * @param customer customer to be removed from database
     * @throws IllegalArgumentException in case customer is not found
     */
    public void remove(Customer customer);

    /**
     * Add new customer into database
     *
     * @param customer customer to be added into database
     * @return updated Customer
     * @throws IllegalArgumentException in case customer is not found
     */
    public Customer update(Customer customer);

    
    /**
     * find all customers
     *
     * @return all customers
     */
    public List<Customer> findAllCustomers();
    
    /**
     * find customer by email in database
     *
     * @param email email of customer
     * @return customer with given email
     * @throws IllegalArgumentException in case customer is not found
     */
    public Customer findByEmail(String email);
    
    /**
     * find customer by id in database
     *
     * @param id id of customer
     * @return customer with given id
     * @throws IllegalArgumentException in case customer is not found
     */
    public Customer findById(Long id);

}
