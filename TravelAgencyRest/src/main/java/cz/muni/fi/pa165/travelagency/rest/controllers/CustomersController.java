package cz.muni.fi.pa165.travelagency.rest.controllers;


import cz.muni.fi.pa165.travelagency.api.dto.AddressDTO;
import cz.muni.fi.pa165.travelagency.api.dto.CustomerAuthenticateDTO;
import cz.muni.fi.pa165.travelagency.api.dto.CustomerCreateDTO;
import cz.muni.fi.pa165.travelagency.api.dto.CustomerDTO;
import cz.muni.fi.pa165.travelagency.api.facade.CustomerFacade;
import cz.muni.fi.pa165.travelagency.rest.exceptions.NotFoundException;
import java.util.List;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Lucia
 */

@RestController
@RequestMapping("/customers")
public class CustomersController {
    
    final static Logger logger = LoggerFactory.getLogger(CustomersController.class);
 
    @Inject
    private CustomerFacade customerFacade;
    
    /**
     * Get list of Customers
     *
     * @return List of customers
     */
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<CustomerDTO> findCustomers() {
        logger.debug("rest findCustomers()");
        return customerFacade.findAllCustomers();
    }
    
    /**
     *
     * Get Customer by id
     * 
     * @param id identifier for a customer
     * @return CustomerDTO
     * @throws NotFoundException
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final CustomerDTO getCustomer(@PathVariable("id") long id) throws Exception {
        logger.debug("rest getCustomer({})", id);
        CustomerDTO customerDTO = customerFacade.findCustomerById(id);
        if (customerDTO != null) {
            return customerDTO;
        } else {
            throw new NotFoundException();
        }
    }
    
    /**
     * Delete one customer by its id 
     * 
     * @param id identifier for customer
     * @throws NotFoundException
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public final void deleteCustomer(@PathVariable("id") long id) throws Exception {
        logger.debug("rest deleteCustomer({})", id);
        try {
            customerFacade.removeCustomer(customerFacade.findCustomerById(id));
        } catch (Exception ex) {
            throw new NotFoundException();
        }
    }
    
    
    /**
     * Create a new customer
     * 
     * @param customer CustomerDTO with required fields for creation
     * @throws java.lang.Exception
     * @throws NotFoundException()
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final String createCustomer(@RequestBody CustomerCreateDTO customer) throws Exception {

        logger.debug("rest createCustomer()");
        CustomerDTO customerDTO = createCustomerDTO(customer);
           
        try {
            customerFacade.registerCustomer(customerDTO, customer.getPassword());
            return "success";
        } catch (Exception ex) {
            throw new NotFoundException();
        }
    }
    
    /**
     * Update the sent customer
     * @param customerDto DTO of the customer
     * @throws NotFoundException When customer is not available
     */
    @RequestMapping(value = "/update", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final void update(@RequestBody CustomerDTO customerDto) throws Exception {
        try {
            customerFacade.updateCustomer(customerDto);            
        } catch (Exception ex) {
            throw new NotFoundException();
        }        
    }
    
    private CustomerDTO createCustomerDTO(CustomerCreateDTO customerCreate){
        CustomerDTO customerDto = new CustomerDTO();
        AddressDTO address = new AddressDTO();
        address.setCity("mesto");
        address.setCountry("Slovakia");
        address.setStreet("ulica");
        address.setNumberOfHouse(10);
        customerDto.setAddress(address);
        customerDto.setCreated(customerCreate.getCreated());
        customerDto.setEmail(customerCreate.getEmail());
        customerDto.setFirstName(customerCreate.getFirstName());
        customerDto.setLastName(customerCreate.getLastName());
        customerDto.setPhoneNumber(customerCreate.getPhoneNumber());
        customerDto.setcustomerRole(customerCreate.getcustomerRole());
        customerDto.setPasswordHash(customerCreate.getPassword());        
        return customerDto;      
    }
    
}
