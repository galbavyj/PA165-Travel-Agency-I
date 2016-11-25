/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.travelagency.travelagencyservice;
import cz.muni.fi.pa165.travelAgency.persistence.dao.ExcursionDao;
import cz.muni.fi.pa165.travelAgency.persistence.dao.ReservationDao;
import cz.muni.fi.pa165.travelAgency.persistence.dao.TripDao;
import cz.muni.fi.pa165.travelAgency.persistence.entity.Address;
import cz.muni.fi.pa165.travelAgency.persistence.entity.Excursion;
import cz.muni.fi.pa165.travelAgency.persistence.entity.Trip;
import cz.muni.fi.pa165.travelagency.api.enums.ExcursionType;
import org.hibernate.service.spi.ServiceException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import org.mockito.Mockito;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import static org.testng.Assert.assertEquals;
import service.TripService;
/**
 *
 * @author Martin
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class TripServiceTest extends AbstractTestNGSpringContextTests {

    @Mock
    private TripDao tripDao;

    @Mock
    private ReservationDao reservationDao;
    
    @Mock
    private ExcursionDao excursionDao;

    private Trip trip1, trip2;
    private Address address;
    private Address address2;
    private java.util.Date from;
    private java.util.Date to;
    private java.util.Date created;
    
    private Excursion excursion1, excursion2;

    @Inject
    @InjectMocks
    private TripService tripService;
   
    @BeforeClass
    public void setUpClass() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void setUpMethod() {
        address = new Address();
        address.setCity("Brno");
        address.setCountry("Czech republic");
        address.setNumberOfHouse(68);
        address.setStreet("Botanicka");
        
        trip1 = new Trip();
        trip1.setAddressOfHotel(address);
        try {
            from = (java.util.Date) new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2017");
            to = (java.util.Date) new SimpleDateFormat("dd/MM/yyyy").parse("10/01/2017");
            created = (java.util.Date) new SimpleDateFormat("dd/MM/yyyy").parse("30/10/2016");
        } catch (ParseException ex) {
            Logger.getLogger(TripServiceTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        trip1.setCreated(created);
        trip1.setFrom(from);
        trip1.setTo(to);
        trip1.setPrice(BigDecimal.TEN);
        
        trip2 = new Trip();
        address2 = new Address();
        address2.setCity("Athens");
        address2.setCountry("Greece");
        address2.setNumberOfHouse(69);
        address2.setStreet("Oidipus street");
        trip2.setAddressOfHotel(address2);
        trip2.setCreated(created);
        trip2.setFrom(from);
        trip2.setTo(to);
        trip2.setPrice(new BigDecimal(1000));
        
        trip1.setId(1L);
        trip2.setId(2L);
        
        Calendar calendar = Calendar.getInstance();
        calendar.set(2017, Calendar.JANUARY, 10, 8, 16);
        java.util.Date created1 = calendar.getTime();

        calendar.set(2017, Calendar.FEBRUARY, 10, 8, 16);
        java.util.Date created2 = calendar.getTime();

        calendar.set(2017, Calendar.APRIL, 10, 8, 0);
        java.util.Date startExcursion1 = calendar.getTime();

        calendar.set(2017, Calendar.APRIL, 15, 8, 0);
        java.util.Date startExcursion2 = calendar.getTime();

        calendar.set(2017, Calendar.APRIL, 1, 8, 0);
        java.util.Date startTrip = calendar.getTime();

        calendar.set(2017, Calendar.APRIL, 20, 8, 0);
        java.util.Date endTrip = calendar.getTime();

        excursion1 = new Excursion();
        excursion1.setCreated(created1);
        excursion1.setDescription("Good enough");
        excursion1.setDurationInHours(10);
        excursion1.setFromDate(startExcursion1);
        excursion1.setPlace("Greenwich");
        excursion1.setPrice(BigDecimal.valueOf(1500));
        excursion1.setTrip(trip1);
        excursion1.setExcursionType(ExcursionType.CULTURE);

        excursion2 = new Excursion();
        excursion2.setCreated(created2);
        excursion2.setDescription("The best excursion");
        excursion2.setDurationInHours(30);
        excursion2.setFromDate(startExcursion2);
        excursion2.setPlace("Wien");
        excursion2.setPrice(BigDecimal.valueOf(2000));
        excursion2.setTrip(trip1);
        excursion2.setExcursionType(ExcursionType.ENTERTAINMENT);
    }
    
    @Test
    public void createTrip(){
        tripService.createTrip(trip1);
        verify(tripDao).create(trip1);
    }
    
    @Test(expectedExceptions = TravelAgencyPersistenceException.class)
    public void createNullTrip(){
        tripService.createTrip(null);
    }
    
    @Test
    public void removeTrip(){
        tripService.createTrip(trip1);
        tripService.removeTrip(trip1);
        verify(tripDao).remove(trip1);
    }

    @Test
    public void updateTrip(){
        tripService.createTrip(trip1);
        trip1.setPrice(new BigDecimal("5000"));
        tripService.updateTrip(trip1);
        verify(tripDao).update(trip1);
    }
    
    @Test(expectedExceptions = TravelAgencyPersistenceException.class)
    public void updateNullTrip(){
        when(tripDao.update(null)).thenThrow(new NullPointerException());
        tripService.updateTrip(null);
    }
    
    @Test(expectedExceptions = TravelAgencyPersistenceException.class)
    public void updateTripWithNullParameter(){
        tripService.createTrip(trip1);
        trip1.setAddressOfHotel(null);
        when(tripDao.update(trip1)).thenThrow(new IllegalArgumentException());
        tripService.updateTrip(trip1);
    }
    
    @Test(expectedExceptions = TravelAgencyPersistenceException.class)
    public void updateTripWithNegativePrice(){
        tripService.createTrip(trip1);
        trip1.setPrice(new BigDecimal("-10"));
        when(tripDao.update(trip1)).thenThrow(new IllegalArgumentException());
        tripService.updateTrip(trip1);
    }
    
    @Test(expectedExceptions = TravelAgencyPersistenceException.class)
    public void updateTripWithWrongDates(){
        tripService.createTrip(trip1);
        try {
            trip1.setTo((java.util.Date) new SimpleDateFormat("dd/MM/yyyy").parse("10/01/2010"));
        } catch (ParseException ex) {
            Logger.getLogger(TripServiceTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        when(tripDao.update(trip1)).thenThrow(new IllegalArgumentException());
        tripService.updateTrip(trip1);
    }
    
    @Test
    public void  findTripById(){
        trip1.setId(10L);
        when(tripDao.findById(trip1.getId())).thenReturn(trip1);
        Trip trip = tripService.findTripById(trip1.getId());
        assertEquals(trip, trip1);
    }
    
    @Test(expectedExceptions = TravelAgencyPersistenceException.class)
    public void  findTripByInvalidId(){
        when(tripDao.findById(-10L)).thenThrow(new IllegalArgumentException());
        tripService.findTripById(-10L);
    }
    
    @Test
    public void  findAllTrips(){
        when(tripDao.findAllTrips()).thenReturn(Arrays.asList(trip1, trip2));
        assertEquals(tripService.findAllTrips(), Arrays.asList(trip1, trip2));
    }
   
    @Test
    public void  findTripsByCountry(){
        when(tripDao.findTripsByCountry("Greece")).thenReturn(Arrays.asList(trip2));
        assertEquals(tripService.findTripsByCountry("Greece"), Arrays.asList(trip2));
    }
    
    @Test
    public void testAddExcursionToTrip() {
        tripService.addExcursionToTrip(trip1, excursion1);
        verify(tripDao.update(trip1));
        verify(excursionDao.update(excursion1));
    }
}
