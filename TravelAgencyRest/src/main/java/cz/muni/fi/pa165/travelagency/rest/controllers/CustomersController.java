package cz.muni.fi.pa165.travelagency.rest.controllers;


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
    private CustomerFacade cusFacade;
    
    /**
     * Get list of Customers curl -i -X GET
     * http://localhost:8080/travel-rest/customers
     *
     * @return List<CustomerDTO> 
     */
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<CustomerDTO> findCustomers() {
        logger.debug("rest findCustomers()");
        return cusFacade.findAllCustomers();
    }
    
    /**
     *
     * Get Product by identifier id
     * http://localhost:8080/travel-rest/customers/2
     *
     * @param id identifier for a customer
     * @return CustomerDTO
     * @throws NotFoundException
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final CustomerDTO getCustomer(@PathVariable("id") long id) throws Exception {
        logger.debug("rest getCustomer({})", id);
        CustomerDTO productDTO = cusFacade.findCustomerById(id);
        if (productDTO != null) {
            return productDTO;
        } else {
            throw new NotFoundException();
        }
    }
    
    /**
     * Delete one customer by id c
     * http://localhost:8080/travel-rest/customers/2
     *
     * @param id identifier for customer
     * @throws NotFoundException
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public final void deleteCustomer(@PathVariable("id") long id) throws Exception {
        logger.debug("rest deleteProduct({})", id);
        try {
            cusFacade.removeCustomer(cusFacade.findCustomerById(id));
        } catch (Exception ex) {
            throw new NotFoundException();
        }
    }
    
}
