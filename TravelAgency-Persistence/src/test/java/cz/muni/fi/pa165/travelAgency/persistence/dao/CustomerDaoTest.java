/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.travelAgency.persistence.dao;

import cz.muni.fi.pa165.travelAgency.persistence.entity.Address;
import cz.muni.fi.pa165.travelAgency.persistence.entity.Customer;
import cz.muni.fi.pa165.travelAgency.persistence.entity.Reservation;
import cz.muni.fi.pa165.travelagency.api.enums.CustomerRole;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import static org.testng.Assert.assertEquals;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import travelAgency.TravelAgencyPersistenceContext;

/**
 *
 * @author Lucia
 */

@ContextConfiguration(classes = TravelAgencyPersistenceContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class CustomerDaoTest extends AbstractTestNGSpringContextTests{
    @Autowired
    private CustomerDao customerDao;
    
    private Customer cust;
    private Customer cust2;
    private Address address;
    private Date created;
    private Reservation res;
    
    @BeforeMethod
    public void setup(){
        address = new Address();
        address.setCity("Brno");
        address.setCountry("Czech republic");
        address.setNumberOfHouse(10);
        address.setStreet("Holandska");
        
        cust = new Customer();
        cust.setFirstName("Juraj");
        cust.setLastName("Janosik");
        cust.setAddress(address);
        cust.setEmail("janosik@gmail.com");
        try {
            created = (Date) new SimpleDateFormat("dd/MM/yyyy").parse("31/10/2016");
        } catch (ParseException ex) {
            Logger.getLogger(TripDaoTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        cust.setCreated(created);
        cust.setPhoneNumber("725555666");
        cust.setCustomerRole(CustomerRole.CUSTOMER);
        
        cust2 = new Customer();
        cust2.setFirstName("Jozko");
        cust2.setLastName("Mrkvicka");
        cust2.setAddress(address);
        cust2.setEmail("mrkvickak@gmail.com");
        try {
            created = (Date) new SimpleDateFormat("dd/MM/yyyy").parse("31/10/2016");
        } catch (ParseException ex) {
            Logger.getLogger(TripDaoTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        cust2.setCreated(created);
        cust2.setPhoneNumber("725555777");
        cust2.setCustomerRole(CustomerRole.CUSTOMER);
        
        res = new Reservation();
        
        
    }
    
    @Test
    public void createTest(){
        customerDao.create(cust);
        Customer c = customerDao.findByEmail(cust.getEmail());
        assertEquals(c, cust);
    }
    
    @Test
    public void updateTest(){
        customerDao.create(cust2);
        customerDao.update(cust2);
        cust2.setFirstName("Fedor");
        customerDao.update(cust2);
        Customer c = customerDao.findByEmail(cust2.getEmail());
        assertEquals(c, cust2);
    }
    
    @Test 
    public void removeTest(){
        customerDao.create(cust2);
        customerDao.remove(cust2);
        assertEquals(customerDao.findAllCustomers().size(), 0);
    }
    
    @Test
    public void findAllCustomersTest(){
        customerDao.create(cust);
        customerDao.create(cust2);
        List<Customer> found = customerDao.findAllCustomers();
        assertEquals(found.size(), 2);
    }
    
    @Test
    public void findByEmailTest(){
        customerDao.create(cust);
        Customer c = customerDao.findByEmail(cust.getEmail());
        assertEquals(c, cust);     
    }
    
    
    @Test(expectedExceptions=ConstraintViolationException.class)
     public void testNullAddress(){
        cust.setAddress(null);
        customerDao.create(cust);
      }
     
     @Test(expectedExceptions=ConstraintViolationException.class)
     public void testNullfirstName(){
        cust.setFirstName(null);
        customerDao.create(cust);
      }
     
     @Test(expectedExceptions=ConstraintViolationException.class)
     public void testNullLastName(){
        cust.setLastName(null);
        customerDao.create(cust);
      }
     
     @Test(expectedExceptions=ConstraintViolationException.class)
     public void testNullPhoneNumber(){
        cust.setPhoneNumber(null);
        customerDao.create(cust);
      }
     
     @Test(expectedExceptions=ConstraintViolationException.class)
     public void testNullCreated(){
        cust.setCreated(null);
        customerDao.create(cust);
      }
     
     @Test(expectedExceptions=ConstraintViolationException.class)
     public void testNullcustomerRole(){
        cust.setCustomerRole(null);
        customerDao.create(cust);
      }

	
    @Test
    public void findByIdTest(){
        customerDao.create(cust);
        Customer c = customerDao.findById(cust.getId());
        assertEquals(c, cust);     
    }
    
}
