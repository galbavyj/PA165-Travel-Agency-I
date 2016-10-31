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
import cz.muni.fi.pa165.travelAgency.persistence.entity.Customer;
import cz.muni.fi.pa165.travelAgency.persistence.entity.Reservation;
import cz.muni.fi.pa165.travelAgency.persistence.entity.Trip;
import enums.UserRole;
import java.math.BigDecimal;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
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
public class ReservationDaoTest extends AbstractTestNGSpringContextTests{
    
    @Autowired
    private TripDao tripDao;
    
    @Autowired
    private ReservationDao reservationDao;
    
    @Autowired
    private UserDao userDao;
    
    private Address addressHotelBrno, addressHotelDolany,addressCustomer;
    private Trip tripBrno, tripDolany;
    private Reservation reservationBrno, reservationDolany;
    private Customer customerMilan,customerPetr;
    
    @BeforeMethod
    public void setUp(){
        addressHotelBrno = new Address();
        addressHotelBrno.setCity("Brno");
        addressHotelBrno.setCountry("Czech republic");
        addressHotelBrno.setNumberOfHouse(3);
        addressHotelBrno.setStreet("Lidicka");
        
        addressHotelDolany = new Address();
        addressHotelDolany.setCity("Dolany");
        addressHotelDolany.setCountry("Czech republic");
        addressHotelDolany.setNumberOfHouse(9);
        addressHotelDolany.setStreet("U hospody");
        
        Date from = new Date();
        Date to = new Date();
        Date created = new Date();
        
        tripBrno = new Trip();
        tripBrno.setAddressOfHotel(addressHotelBrno);
        try {
            from = (Date) new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2017");
            to = (Date) new SimpleDateFormat("dd/MM/yyyy").parse("10/01/2017");
            created = (Date) new SimpleDateFormat("dd/MM/yyyy").parse("30/10/2016");
        } catch (ParseException ex) {
            Logger.getLogger(TripDaoTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        tripBrno.setCreated(created);
        tripBrno.setFrom(from);
        tripBrno.setTo(to);
        tripBrno.setPrice(BigDecimal.TEN);
        
        tripDolany = new Trip();
        tripDolany.setAddressOfHotel(addressHotelDolany);
        try {
            from = (Date) new SimpleDateFormat("dd/MM/yyyy").parse("02/05/2017");
            to = (Date) new SimpleDateFormat("dd/MM/yyyy").parse("09/05/2017");
        } catch (ParseException ex) {
            Logger.getLogger(TripDaoTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        tripDolany.setCreated(created);
        tripDolany.setFrom(from);
        tripDolany.setTo(to);
        tripDolany.setPrice(BigDecimal.ONE);
        
        addressCustomer = new Address();
        addressCustomer.setCity("Tirane");
        addressCustomer.setCountry("Albania");
        addressCustomer.setNumberOfHouse(10);
        addressCustomer.setStreet("Rozsygo");
        
        customerMilan = new Customer();
        customerMilan.setAddress(addressCustomer);
        customerMilan.setFirstName("Milan");
        customerMilan.setLastName("Peterka");
        customerMilan.setEmail("milan.peterka@seznam.cz");
        customerMilan.setPhoneNumber("+420777852974");
        customerMilan.setUserRole(UserRole.CUSTOMER);
        customerMilan.setCreated(created);

        customerPetr = new Customer();
        customerPetr.setAddress(addressCustomer);
        customerPetr.setFirstName("Petr");
        customerPetr.setLastName("Milanka");
        customerPetr.setEmail("petr.milanka@seznam.cz");
        customerPetr.setPhoneNumber("+420608999974");
        customerPetr.setUserRole(UserRole.CUSTOMER);
        customerPetr.setCreated(created);
        
        reservationBrno = new Reservation();
        reservationBrno.setTrip(tripBrno);
        reservationBrno.setCustomer(customerMilan);
        reservationBrno.setCreated(created);    
        
        reservationDolany = new Reservation();
        reservationDolany.setTrip(tripDolany);
        reservationDolany.setCustomer(customerPetr);
        reservationDolany.setCreated(created);   
        
        
        userDao.create(customerMilan);
        userDao.create(customerPetr);
    }

    @Test
    public void create(){
        reservationDao.create(reservationBrno);
    }
    
    @Test
    public void testUpdate(){
       reservationDao.create(reservationBrno);
       reservationBrno.setTrip(tripDolany);
       reservationDao.update(reservationBrno);
       Reservation r = reservationDao.findById(reservationBrno.getId());
       assertEquals(reservationBrno, r);  
    }
    
    @Test
    public void findAllReservations(){
        reservationDao.create(reservationBrno);
        reservationDao.create(reservationDolany);
        
        List<Reservation> reservationList = new ArrayList<>();
        reservationList.add(reservationBrno);
        reservationList.add(reservationDolany);
        
        List<Reservation> expectedList = reservationDao.findAllReservations();
       
        assertEquals(reservationList, expectedList);
    }
    
}