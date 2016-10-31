package cz.muni.fi.pa165.travelAgency.persistence.dao;

/**
 *
 * @author Patrik Behrami
 */

import cz.muni.fi.pa165.travelAgency.persistence.entity.Address;
import cz.muni.fi.pa165.travelAgency.persistence.entity.Customer;
import cz.muni.fi.pa165.travelAgency.persistence.entity.Excursion;
import cz.muni.fi.pa165.travelAgency.persistence.entity.Reservation;
import cz.muni.fi.pa165.travelAgency.persistence.entity.Trip;
import enums.ExcursionType;
import enums.UserRole;
import java.math.BigDecimal;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
    
    @Autowired
    private ExcursionDao excursionDao;
    
    private Address addressHotelBrno, addressHotelDolany,addressCustomer;
    private Trip tripBrno, tripDolany;
    private Reservation reservation1, reservation2;
    private Customer customerMilan,customerPetr;
    private Excursion excursionMuseum, excursionFootballMatch;
    
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

        excursionMuseum = new Excursion();
        excursionMuseum.setCreated(created);
        try {
            from = (Date) new SimpleDateFormat("dd/MM/yyyy").parse("05/01/2017");
        } catch (ParseException ex) {
            Logger.getLogger(TripDaoTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        excursionMuseum.setFromDate(from);
        excursionMuseum.setDurationInHours(3);
        excursionMuseum.setPlace("Musem of Science");
        excursionMuseum.setDescription("Visit the best museum in Brno");
        excursionMuseum.setPrice(BigDecimal.valueOf(35));
        excursionMuseum.setExcursionType(ExcursionType.CULTURE);
       
        excursionFootballMatch = new Excursion();
        excursionFootballMatch.setCreated(created);
        try {
            from = (Date) new SimpleDateFormat("dd/MM/yyyy").parse("07/05/2017");
        } catch (ParseException ex) {
            Logger.getLogger(TripDaoTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        excursionFootballMatch.setFromDate(from);
        excursionFootballMatch.setDurationInHours(3);
        excursionFootballMatch.setPlace("Football Stadion Dolany");
        excursionFootballMatch.setDescription("Thrilling match");
        excursionFootballMatch.setPrice(BigDecimal.valueOf(20));
        excursionFootballMatch.setExcursionType(ExcursionType.ENTERTAINMENT);
        
        Set<Excursion> excursionsBrno = new HashSet<>();
        excursionsBrno.add(excursionMuseum);
        
        Set<Excursion> excursionsDolany = new HashSet<>();
        excursionsDolany.add(excursionFootballMatch);
       
        tripBrno.setPossibleExcursions(excursionsBrno);
        tripDolany.setPossibleExcursions(excursionsDolany);
        
        
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
        
        reservation1 = new Reservation();
        reservation1.setTrip(tripBrno);
        reservation1.setCustomer(customerMilan);
        reservation1.setCreated(created);    
        
        reservation2 = new Reservation();
        reservation2.setTrip(tripDolany);
        reservation2.setCustomer(customerPetr);
        reservation2.setCreated(created);        
        
        userDao.create(customerMilan);
        userDao.create(customerPetr);
        
        tripDao.create(tripBrno);
        tripDao.create(tripDolany);
        
        excursionDao.create(excursionMuseum);
        excursionDao.create(excursionFootballMatch);
    }

    @Test(expectedExceptions=ConstraintViolationException.class)
    public void testCreateWithNullTrip(){
        reservation1.setTrip(null);
        
        reservationDao.create(reservation1);
    }
    
    @Test(expectedExceptions=ConstraintViolationException.class)
    public void testCreateWithNullCreated(){
        reservation1.setCreated(null);
        
        reservationDao.create(reservation1);
    }
    
    @Test(expectedExceptions=ConstraintViolationException.class)
    public void testCreateWithNullCustomer(){
        reservation1.setCustomer(null);
        
        reservationDao.create(reservation1);
    }
    
    @Test
    public void testUpdate(){
       reservationDao.create(reservation1);
       reservation1.setTrip(tripDolany);
       reservationDao.update(reservation1);
       Reservation r = reservationDao.findById(reservation1.getId());
       assertEquals(reservation1, r);  
    }
    
    @Test
    public void testFindAllReservations(){
        reservationDao.create(reservation1);
        reservationDao.create(reservation2);
        
        List<Reservation> reservationList = new ArrayList<>();
        reservationList.add(reservation1);
        reservationList.add(reservation2);
        
        List<Reservation> expectedList = reservationDao.findAllReservations();
       
        assertEquals(reservationList, expectedList);
    }
    
    @Test
    public void testRemove(){
        reservationDao.create(reservation1);
        reservationDao.create(reservation2);
        
        List<Reservation> reservationList = new ArrayList<>();
        reservationList.add(reservation1);
        
        reservationDao.remove(reservation2);
        List<Reservation> expectedList = reservationDao.findAllReservations();
        
        assertEquals(reservationList, expectedList);
    }
    
    @Test
    public void testFindByCustomer(){
        reservationDao.create(reservation1);
        
        reservation2.setCustomer(customerMilan);
        
        reservationDao.create(reservation2);
        
        List<Reservation> reservationList = new ArrayList<>();
        reservationList.add(reservation1);
        reservationList.add(reservation2);
        
        List<Reservation> expectedList = reservationDao.findReservationsByCustomer(customerMilan);
        
        assertEquals(reservationList, expectedList);
    }
    
    @Test
    public void testFindByTrip(){
        reservationDao.create(reservation1);
        
        reservation2.setTrip(tripBrno);
        
        reservationDao.create(reservation2);
        
        List<Reservation> reservationList = new ArrayList<>();
        reservationList.add(reservation1);
        reservationList.add(reservation2);
        
        List<Reservation> expectedList = reservationDao.findReservationsByTrip(tripBrno);
        
        assertEquals(reservationList, expectedList);
    }
    
    @Test
    public void testFindById(){
        reservationDao.create(reservation1);
        reservationDao.create(reservation2);
        
        Reservation expected1 = reservationDao.findById(reservation1.getId());
        Reservation expected2 = reservationDao.findById(reservation2.getId());
        
        assertEquals(reservation1, expected1);
        assertEquals(reservation2, expected2); 
    }   
}