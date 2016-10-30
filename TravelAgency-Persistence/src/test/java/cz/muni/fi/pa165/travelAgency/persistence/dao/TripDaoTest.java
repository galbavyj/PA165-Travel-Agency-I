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
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import static org.testng.Assert.assertEquals;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import travelAgency.TravelAgencyPersistenceContext;

@ContextConfiguration(classes = TravelAgencyPersistenceContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class TripDaoTest extends AbstractTestNGSpringContextTests{
    
    @Autowired
    private TripDao tripDao;
    
    private Address address;
    private Trip trip;
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
            from = (Date) new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2017");
            to = (Date) new SimpleDateFormat("dd/MM/yyyy").parse("10/01/2017");
            created = (Date) new SimpleDateFormat("dd/MM/yyyy").parse("30/10/2016");
        } catch (ParseException ex) {
            Logger.getLogger(TripDaoTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        trip.setCreated(created);
        trip.setFrom(from);
        trip.setTo(to);
        trip.setPrice(BigDecimal.TEN);
    }

    
        @Test
    public void testUpdate(){
        tripDao.create(trip);
        trip.setPrice(BigDecimal.ONE);
        tripDao.update(trip);
        Trip t = tripDao.findById(trip.getId());
        assertEquals(t, trip);
    }
}