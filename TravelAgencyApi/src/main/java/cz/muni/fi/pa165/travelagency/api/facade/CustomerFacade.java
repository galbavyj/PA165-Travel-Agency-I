/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.travelagency.api.facade;

import cz.muni.fi.pa165.travelagency.api.dto.CustomerDTO;
import java.util.List;

/**
 *
 * @author Martin
 */
public interface CustomerFacade {

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
     * Finds and returns all customers
     * 
     * @return list of all customers
     */
    List<CustomerDTO> getAllCustomers();
    
    /**
     * Finds and returns customer with specified id
     * 
     * @param customerId customer id
     * @return customer with specified id
     */
    CustomerDTO getCustomerById(Long customerId);
    
    /**
     * Finds and returns customer with specified email
     * 
     * @param email Email of customer
     * @return list of customers with specified email
     */
    CustomerDTO getCustomerByEmail(String email);
}

