/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import cz.muni.fi.pa165.travelagency.api.dto.CustomerDTO;
import cz.muni.fi.pa165.travelAgency.persistence.entity.Customer;
import cz.muni.fi.pa165.travelagency.api.facade.CustomerFacade;
import cz.muni.fi.pa165.travelagency.travelagencyservice.MappingService;
import java.util.List;
import javax.inject.Inject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.CustomerService;
/**
 *
 * @author Martin
 */
@Service
@Transactional
public class CustomerFacadeImpl implements CustomerFacade{

    @Inject
    private MappingService mappingService;

    @Inject
    private CustomerService customerService;

    @Override
    public void updateCustomer(CustomerDTO u) {
        customerService.updateCustomer(mappingService.mapTo(u, Customer.class));
    }

    @Override
    public void removeCustomer(CustomerDTO c) {
        throw new UnsupportedOperationException("Not yet, bitch!");
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        throw new UnsupportedOperationException("Not yet, bitch!");
    }

    @Override
    public CustomerDTO getCustomerById(Long customerId) {
        throw new UnsupportedOperationException("Not yet, bitch!");
    }

    @Override
    public CustomerDTO getCustomerByEmail(String email) {
        throw new UnsupportedOperationException("Not yet, bitch!");
    }
    
}
