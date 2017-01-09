/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.travelAgency.persistence.dao;

/**
 *
 * @author Martin
 */

import cz.muni.fi.pa165.travelAgency.persistence.entity.Address;
import cz.muni.fi.pa165.travelAgency.persistence.entity.Trip;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.validation.ConstraintViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import static org.testng.Assert.assertEquals;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import travelAgency.TravelAgencyPersistenceContext;

@ContextConfiguration(classes = TravelAgencyPersistenceContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class TripDaoTest extends AbstractTestNGSpringContextTests{
    
    @Inject
    private TripDao tripDao;
    
    private Address address;
    private Address address2;
    private Trip trip;
    private Trip trip2;
    private Date from;
    private Date to;
    private Date created;
    
    @BeforeMethod
    public void setUp(){
        address = new Address();
        address.setCity("Brno");
        address.setCountry("Czech republic");
        address.setNumberOfHouse(68);
        address.setStreet("Botanicka");
        
        trip = new Trip();
        trip.setAddressOfHotel(address);
        try {
            from = (Date) new SimpleDateFormat("dd/MM/yyyy").parse("01/05/2017");
            to = (Date) new SimpleDateFormat("dd/MM/yyyy").parse("10/05/2017");
            from = (Date) new SimpleDateFormat("dd/MM/yyyy").parse("01/06/2017");
            to = (Date) new SimpleDateFormat("dd/MM/yyyy").parse("10/06/2017");
            created = (Date) new SimpleDateFormat("dd/MM/yyyy").parse("30/10/2016");
        } catch (ParseException ex) {
            Logger.getLogger(TripDaoTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        trip.setCreatedDate(created);
        trip.setFromDate(from);
        trip.setToDate(to);
        trip.setPrice(BigDecimal.TEN);
        
        trip2 = new Trip();
        address2 = new Address();
        address2.setCity("Athens");
        address2.setCountry("Greece");
        address2.setNumberOfHouse(69);
        address2.setStreet("Oidipus street");
        trip2.setAddressOfHotel(address2);
        trip2.setCreatedDate(created);
        trip2.setFromDate(from);
        trip2.setToDate(to);
        trip2.setPrice(new BigDecimal(1000));
    }
    
    @Test
    public void testCreate(){
        tripDao.create(trip);
        Trip t = tripDao.findById(trip.getId());
        assertEquals(t, trip);
    }
    
    @Test
    public void testUpdate(){
        tripDao.create(trip);
        trip.setPrice(BigDecimal.ONE);
        tripDao.update(trip);
        Trip t = tripDao.findById(trip.getId());
        assertEquals(t, trip);
    }
    
    @Test
    public void testRemove(){
        tripDao.create(trip);
        tripDao.remove(trip);
        assertEquals(tripDao.findAllTrips().size(), 0);
    }
    
    @Test
    public void testFindAll(){
        tripDao.create(trip);
        tripDao.create(trip2);
        assertEquals(tripDao.findAllTrips().size(), 2);
    }
    
    @Test
    public void testFindByCountry(){
        tripDao.create(trip);
        tripDao.create(trip2);
        assertEquals(tripDao.findTripsByCountry("Greece").size(), 1);
    }
    
    @Test
    public void testFindById(){
        tripDao.create(trip);
        tripDao.create(trip2);
        assertEquals(tripDao.findById(trip2.getId()), trip2);
    }
        
    @Test(expectedExceptions=ConstraintViolationException.class)
    public void testNullCreated(){
        trip.setCreatedDate(null);
        tripDao.create(trip);
    }
    
    @Test(expectedExceptions=InvalidDataAccessApiUsageException.class)
    public void testCreateNull(){
        tripDao.create(null);
    }
    
    @Test(expectedExceptions=InvalidDataAccessApiUsageException.class)
    public void testUpdateNull(){
        tripDao.create(trip);
        trip = null;
        tripDao.update(trip);
    }
    
    @Test(expectedExceptions=InvalidDataAccessApiUsageException.class)
    public void testRemoveNull(){
        tripDao.create(trip);
        tripDao.remove(null);
        assertEquals(tripDao.findAllTrips().size(), 0);
    }
    
    @Test(expectedExceptions=ConstraintViolationException.class)
    public void testNullFrom(){
        trip.setFromDate(null);
        tripDao.create(trip);
    }
    
    @Test(expectedExceptions=ConstraintViolationException.class)
    public void testNullTo(){
        trip.setToDate(null);
        tripDao.create(trip);
    }
    
    @Test(expectedExceptions=ConstraintViolationException.class)
    public void testNullPrice(){
        trip.setPrice(null);
        tripDao.create(trip);
    }
    
    @Test(expectedExceptions=ConstraintViolationException.class)
    public void testNegativePrice(){
        trip.setPrice(new BigDecimal("-10"));
        tripDao.create(trip);
    }
    
    @Test(expectedExceptions=ConstraintViolationException.class)
    public void testPastFromDate(){
        Date pastFrom = null;
        try {
            pastFrom = (Date) new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2010");
        } catch (ParseException ex) {
            Logger.getLogger(TripDaoTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        trip.setFromDate(pastFrom);
        tripDao.create(trip);
    }
    
    @Test(expectedExceptions=ConstraintViolationException.class)
    public void testPastToDate(){
        Date pastTo = null;
        try {
            pastTo = (Date) new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2010");
        } catch (ParseException ex) {
            Logger.getLogger(TripDaoTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        trip.setToDate(pastTo);
        tripDao.create(trip);
    }
}