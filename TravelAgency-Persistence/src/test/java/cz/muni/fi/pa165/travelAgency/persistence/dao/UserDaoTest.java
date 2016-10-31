/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.travelAgency.persistence.dao;

import cz.muni.fi.pa165.travelAgency.persistence.entity.Address;
import cz.muni.fi.pa165.travelAgency.persistence.entity.Customer;
import cz.muni.fi.pa165.travelAgency.persistence.entity.Reservation;
import enums.UserRole;
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
public class UserDaoTest extends AbstractTestNGSpringContextTests{
    @Autowired
    private UserDao userDao;
    
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
        cust.setUserRole(UserRole.CUSTOMER);
        
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
        cust2.setUserRole(UserRole.CUSTOMER);
        
        res = new Reservation();
        
        
    }
    
    @Test
    public void createTest(){
        userDao.create(cust);
        Customer c = userDao.findByEmail(cust.getEmail());
        assertEquals(c, cust);
    }
    
    @Test
    public void updateTest(){
        userDao.create(cust2);
        userDao.update(cust2);
        cust2.setFirstName("Fedor");
        userDao.update(cust2);
        Customer c = userDao.findByEmail(cust2.getEmail());
        assertEquals(c, cust2);
    }
    
    @Test 
    public void removeTest(){
        userDao.create(cust2);
        userDao.remove(cust2);
        assertEquals(userDao.findAllCustomers().size(), 0);
    }
    
    @Test
    public void findAllCustomersTest(){
        userDao.create(cust);
        userDao.create(cust2);
        List<Customer> found = userDao.findAllCustomers();
        assertEquals(found.size(), 2);
    }
    
    @Test
    public void findByEmailTest(){
        userDao.create(cust);
        Customer c = userDao.findByEmail(cust.getEmail());
        assertEquals(c.getEmail(), cust.getEmail());     
    }
    
    
    @Test(expectedExceptions=ConstraintViolationException.class)
     public void testNullAddress(){
        cust.setAddress(null);
        userDao.create(cust);
      }
     
     @Test(expectedExceptions=ConstraintViolationException.class)
     public void testNullfirstName(){
        cust.setFirstName(null);
        userDao.create(cust);
      }
     
     @Test(expectedExceptions=ConstraintViolationException.class)
     public void testNullLastName(){
        cust.setLastName(null);
        userDao.create(cust);
      }
     
     @Test(expectedExceptions=ConstraintViolationException.class)
     public void testNullPhoneNumber(){
        cust.setPhoneNumber(null);
        userDao.create(cust);
      }
     
     @Test(expectedExceptions=ConstraintViolationException.class)
     public void testNullCreated(){
        cust.setCreated(null);
        userDao.create(cust);
      }
     
     @Test(expectedExceptions=ConstraintViolationException.class)
     public void testNullUserRole(){
        cust.setUserRole(null);
        userDao.create(cust);
      }

	
    @Test
    public void findByIdTest(){
        userDao.create(cust);
        Customer c = userDao.findById(cust.getId());
        assertEquals(c.getId(), cust.getId());     
    }
    
}
