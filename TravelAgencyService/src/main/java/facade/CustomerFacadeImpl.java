/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import cz.muni.fi.pa165.travelAgency.persistence.entity.Customer;
import cz.muni.fi.pa165.travelAgency.persistence.entity.Reservation;
import cz.muni.fi.pa165.travelagency.api.dto.CustomerAuthenticateDTO;
import cz.muni.fi.pa165.travelagency.api.dto.CustomerDTO;
import cz.muni.fi.pa165.travelagency.api.dto.ReservationDTO;
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
    public void registerCustomer(CustomerDTO c, String plainTextPassword) {
        Customer customer = mappingService.mapTo(c, Customer.class);
        customerService.registerCustomer(customer, plainTextPassword);
    }

    @Override
    public boolean authenticateCustomer(CustomerAuthenticateDTO c) {
        return customerService.authenticateCustomer(
                customerService.findByEmail(c.getEmail()), c.getPassword());
    }

    @Override
    public void updateCustomer(CustomerDTO c) {
        Customer customer = mappingService.mapTo(c, Customer.class);
        customerService.updateCustomer(customer);
    }

    @Override
    public void removeCustomer(CustomerDTO c) {
        Customer customer = mappingService.mapTo(c, Customer.class);
        customerService.removeCustomer(customer);
    }

    @Override
    public List<CustomerDTO> findAllCustomers() {
        return mappingService.mapTo(customerService.findAll(), CustomerDTO.class);
    }

    @Override
    public CustomerDTO findCustomerByEmail(String email) {
        Customer customer = customerService.findByEmail(email);
        return (customer == null) ? null : mappingService.mapTo(customer, CustomerDTO.class);
    }

    @Override
    public CustomerDTO findCustomerById(Long customerId) {
        Customer customer = customerService.findById(customerId);
        return (customer == null) ? null : mappingService.mapTo(customer, CustomerDTO.class);
    }

    @Override
    public void addReservation(CustomerDTO c, ReservationDTO r) {
        Customer customer = mappingService.mapTo(c, Customer.class);
        Reservation reservation = mappingService.mapTo(r, Reservation.class);
        customerService.addReservationToCustomer(customer, reservation);
    }
}
