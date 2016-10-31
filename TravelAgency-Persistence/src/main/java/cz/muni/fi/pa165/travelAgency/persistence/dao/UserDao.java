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
public interface UserDao {

    /**
     * Add new user into database
     *
     * @param user user to be added into database
     */
    public void create(Customer user);

    /**
     * Removes user from database
     *
     * @param user user to be removed from database
     * @throws IllegalArgumentException in case user is not found
     */
    public void remove(Customer user);

    /**
     * Add new user into database
     *
     * @param user user to be added into database
     * @return updated Customer
     * @throws IllegalArgumentException in case user is not found
     */
    public Customer update(Customer user);

    
    /**
     * find all customers
     *
     * @return all customers
     */
    public List<Customer> findAllCustomers();
    
    /**
     * find user by email in database
     *
     * @param email email of user
     * @return user with given email
     * @throws IllegalArgumentException in case user is not found
     */
    public Customer findByEmail(String email);
    
    /**
     * find user by id in database
     *
     * @param id id of user
     * @return user with given id
     * @throws IllegalArgumentException in case user is not found
     */
    public Customer findById(Long id);

}
