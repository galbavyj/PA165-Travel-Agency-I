/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.travelagency.api.facade;

import cz.muni.fi.pa165.travelagency.api.dto.CustomerAuthenticateDTO;
import cz.muni.fi.pa165.travelagency.api.dto.CustomerDTO;
import java.util.List;

/**
 *
 * @author Martin
 */
public interface CustomerFacade {

    /**
     * Register the given user with the given password.
     *
     * @param c customer to register
     * @param plainTextPassword password of customer
     */
    void registerCustomer(CustomerDTO c, String plainTextPassword);

    /**
     * Try to authenticate a user. Return true only if the hashed password
     * matches the records.
     *
     * @param c id and password of customer to authenticate
     * @return true, if authentication was successful
     */
    boolean authenticateCustomer(CustomerAuthenticateDTO c);

    /**
     * Update customer
     *
     * @param c DTO of customer to be updated
     */
    void updateCustomer(CustomerDTO c);

    /**
     * Remove customer
     *
     * @param c DTO of customer to be removed
     */
    void removeCustomer(CustomerDTO c);

    /**
     * Find all customers
     *
     * @return list of all customers
     */
    List<CustomerDTO> findAllCustomers();

    /**
     * Find customer with given email
     *
     * @param email Email of customer
     * @return customer with given email
     */
    CustomerDTO findCustomerByEmail(String email);

    /**
     * Find customer with given id
     *
     * @param customerId customer id
     * @return customer with given id
     */
    CustomerDTO findCustomerById(Long customerId);
}
