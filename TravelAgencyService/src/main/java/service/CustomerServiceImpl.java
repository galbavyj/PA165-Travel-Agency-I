/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import cz.muni.fi.pa165.travelAgency.persistence.dao.CustomerDao;
import cz.muni.fi.pa165.travelAgency.persistence.entity.Customer;
import java.util.List;
import javax.inject.Inject;
import org.springframework.stereotype.Service;

/**
 *
 * @author Martin
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    @Inject
    private CustomerDao customerDao;
    @Override
    public boolean authoriseCustomer(Customer u, String password) {
        throw new UnsupportedOperationException("Not yet, bitch!");
    }

    @Override
    public Customer registerCustomer(Customer c, String plainTextPassword) {
        throw new UnsupportedOperationException("Not yet, bitch!");
    }

    @Override
    public Customer updateCustomer(Customer c) {
        return customerDao.update(c);
    }

    @Override
    public void removeCustomer(Customer c) {
        throw new UnsupportedOperationException("Not yet, bitch!");
    }

    @Override
    public List<Customer> findAll() {
        throw new UnsupportedOperationException("Not yet, bitch!");
    }

    @Override
    public Customer findById(Long customerId) {
        throw new UnsupportedOperationException("Not yet, bitch!");
    }

    @Override
    public Customer findByEmail(String email) {
        throw new UnsupportedOperationException("Not yet, bitch!");
    }
    
}
