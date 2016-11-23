/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.travelagency.travelagencyservice;

import cz.muni.fi.pa165.travelAgency.persistence.dao.ExcursionDao;
import cz.muni.fi.pa165.travelAgency.persistence.dao.ReservationDao;
import cz.muni.fi.pa165.travelAgency.persistence.entity.Address;
import cz.muni.fi.pa165.travelAgency.persistence.entity.Customer;
import cz.muni.fi.pa165.travelAgency.persistence.entity.Excursion;
import cz.muni.fi.pa165.travelAgency.persistence.entity.Reservation;
import cz.muni.fi.pa165.travelAgency.persistence.entity.Trip;
import cz.muni.fi.pa165.travelagency.api.enums.CustomerRole;
import cz.muni.fi.pa165.travelagency.api.enums.ExcursionType;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.validation.ConstraintViolationException;
import org.hibernate.service.spi.ServiceException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;
import static org.testng.Assert.assertEquals;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import service.ExcursionService;
import service.ReservationService;

/**
 *
 * @author Lucia
 */
@ContextConfiguration(classes=ServiceConfiguration.class)
public class ReservationServiceTest extends AbstractTransactionalTestNGSpringContextTests {
    @Mock
    private ReservationDao reservationDao;
    
    @Autowired
    @InjectMocks
    private ReservationService reservationService;
    
    @BeforeClass
    public void setup() throws ServiceException
    {
        MockitoAnnotations.initMocks(this);
    }
    
    private Reservation testReservation;
    private Trip trip;
    private Excursion excursion1;
    private Excursion excursion2;
    private List<Reservation> listWithReserv;
    
    @BeforeMethod
    public void prepareTestReservation(){
        Date created = new Date();
        trip = new Trip();
        Customer cust = new Customer();
        excursion1 = new Excursion();
        excursion2 = new Excursion();
    	testReservation = new Reservation();
        
        
        Address address = new Address();
        address.setCity("Brno");
        address.setCountry("Czech republic");
        address.setNumberOfHouse(10);
        address.setStreet("Holandska");
        
        cust.setFirstName("Juraj");
        cust.setLastName("Janosik");
        cust.setAddress(address);
        cust.setEmail("janosik@gmail.com");
        try {
             created = (Date) new SimpleDateFormat("dd/MM/yyyy").parse("24/12/2017");
        } catch (ParseException ex) {
            //Logger.getLogger(ReservationServiceTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        cust.setCreated(created);
        cust.setPhoneNumber("725555666");
        cust.setCustomerRole(CustomerRole.CUSTOMER);
        
        excursion1.setCreated(created);
        excursion1.setDescription("Good enough");
        excursion1.setDurationInHours(10);
        excursion1.setFromDate(created);
        excursion1.setPlace("Greenwich");
        excursion1.setPrice(BigDecimal.valueOf(100));
        excursion1.setTrip(trip);
        excursion1.setExcursionType(ExcursionType.CULTURE);

        excursion2.setCreated(created);
        excursion2.setDescription("The best excursion");
        excursion2.setDurationInHours(30);
        excursion2.setFromDate(created);
        excursion2.setPlace("Wien");
        excursion2.setPrice(BigDecimal.valueOf(200));
        excursion2.setTrip(trip);
        excursion2.setExcursionType(ExcursionType.ENTERTAINMENT);
        
        
        Set<Excursion> excursions = new HashSet<>();
        excursions.add(excursion1);
        excursions.add(excursion2);

        trip.setId(10l);
        trip.setCreated(created);
        trip.setFrom(created);
        trip.setTo(created);
        trip.setPossibleExcursions(excursions);
        trip.setPrice(BigDecimal.valueOf(1000));
        trip.setAddressOfHotel(address);
        
        testReservation.setId(1l);
        
        testReservation.setCustomer(cust);
        testReservation.setExcursions(excursions);
        testReservation.setTrip(trip);
        
        listWithReserv= new ArrayList<Reservation>();
        listWithReserv.add(testReservation);
        when(reservationDao.findById(testReservation.getId())).thenReturn(testReservation);
        when(reservationDao.findReservationsByCustomer(testReservation.getCustomer())).thenReturn(listWithReserv);
        when(reservationDao.findReservationsByTrip(testReservation.getTrip())).thenReturn(listWithReserv);
    }
    
    @Test
    public void findReservationById() {
        assertEquals(testReservation, reservationService.findReservationById(testReservation.getId())); 
    }
    
    @Test
    public void createReservation() {
        reservationService.createReservation(testReservation);
        assertEquals(testReservation, reservationService.findReservationById(testReservation.getId())); 
    }

    @Test
    public void updateReservation() {
        reservationService.createReservation(testReservation);
        assertEquals(testReservation, reservationService.findReservationById(testReservation.getId())); 
        Excursion testExc = new Excursion();
        testExc.setPlace("Dreams");
        reservationService.addExcursionToReservation(testReservation, testExc);
        assertEquals(testReservation, reservationService.findReservationById(testReservation.getId())); 
    }

    @Test
    public void removeReservation() {
        reservationService.createReservation(testReservation);
        assertEquals(testReservation, reservationService.findReservationById(testReservation.getId()));
        reservationService.removeReservation(testReservation);
        assertEquals(reservationService.findAllReservations().size(), 0 );
    }
      

    @Test
    public void addExcursionToReservation() {
        reservationService.createReservation(testReservation);
        assertEquals(testReservation, reservationService.findReservationById(testReservation.getId()));
        Excursion testExc = new Excursion();
        testExc.setPlace("Dreams");
        reservationService.addExcursionToReservation(testReservation, testExc);
        assertEquals(testReservation.getExcursions(), reservationService.findReservationById(testReservation.getId()).getExcursions());

    }

    @Test
    public void findAllReservations() {
        assertEquals(testReservation, reservationService.findReservationById(testReservation.getId())); 
    }

    @Test
    public void findReservationsByCustomer() {
        assertEquals(listWithReserv, reservationService.findReservationsByCustomer(testReservation.getCustomer())); 
    }

    @Test
    public void findReservationsByTrip() {
        assertEquals(listWithReserv, reservationService.findReservationsByTrip(testReservation.getTrip())); 
    }

    @Test
    public void getTotalPriceTest() {
        
        BigDecimal testPrice = testReservation.getTrip().getPrice();
        for(Excursion excursion : testReservation.getExcursions()){
            testPrice = testPrice.add(excursion.getPrice());
        }
        reservationDao.create(testReservation);
        Assert.assertEquals(reservationService.getTotalPrice(testReservation), testPrice);
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void getTotalPriceWithNullTest() {
        reservationService.getTotalPrice(null);
    }
}
    
