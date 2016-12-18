package cz.muni.fi.pa165.travelagency.travelagencyservice;

import cz.muni.fi.pa165.travelAgency.persistence.dao.CustomerDao;
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
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.service.spi.ServiceException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.verify;
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
import service.ReservationService;


/**
 *
 * @author Lucia
 */
@ContextConfiguration(classes=ServiceConfiguration.class)
public class ReservationServiceTest extends AbstractTransactionalTestNGSpringContextTests {
    @Mock
    private ReservationDao reservationDao;

    @Mock
    private CustomerDao customerDao;

    @Mock
    private ExcursionDao excursionDao;

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
    private Customer cust;

    @BeforeMethod
    public void prepareTestReservation(){
        Date created = new Date();
        trip = new Trip();
        cust = new Customer();
        excursion1 = new Excursion();
        excursion2 = new Excursion();
        testReservation = new Reservation();


        Address address = new Address();
        address.setCity("Brno");
        address.setCountry("Czech republic");
        address.setNumberOfHouse(10);
        address.setStreet("Holandska");

        cust.setId(5L);
        cust.setFirstName("Juraj");
        cust.setLastName("Janosik");
        cust.setAddress(address);
        cust.setEmail("janosik@gmail.com");
        try {
            created = (Date) new SimpleDateFormat("dd/MM/yyyy").parse("24/12/2017");
        } catch (ParseException ex) {
            Logger.getLogger(ReservationServiceTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        cust.setCreated(created);
        cust.setPhoneNumber("725555666");
        cust.setCustomerRole(CustomerRole.CUSTOMER);

        excursion1.setId(1L);
        excursion1.setCreated(created);
        excursion1.setDescription("Good enough");
        excursion1.setDurationInHours(10);
        excursion1.setFromDate(created);
        excursion1.setPlace("Greenwich");
        excursion1.setPrice(BigDecimal.valueOf(100));
        excursion1.setExcursionType(ExcursionType.CULTURE);

        excursion2.setId(2L);
        excursion2.setCreated(created);
        excursion2.setDescription("The best excursion");
        excursion2.setDurationInHours(30);
        excursion2.setFromDate(created);
        excursion2.setPlace("Wien");
        excursion2.setPrice(BigDecimal.valueOf(200));
        excursion2.setExcursionType(ExcursionType.ENTERTAINMENT);


        Set<Excursion> excursions = new HashSet<>();
        excursions.add(excursion1);
        excursions.add(excursion2);

        trip.setId(10l);
        trip.setCreatedDate(created);
        trip.setFromDate(created);
        trip.setToDate(created);
        trip.setPossibleExcursions(excursions);
        trip.setPrice(BigDecimal.valueOf(1000));
        trip.setAddressOfHotel(address);

        testReservation.setId(1l);

        testReservation.setCustomer(cust);
        testReservation.setExcursions(excursions);
        testReservation.setTrip(trip);

        listWithReserv= new ArrayList<Reservation>();
        listWithReserv.add(testReservation);

        when(reservationDao.findReservationsByCustomer(cust)).thenReturn(Arrays.asList(testReservation));
        when(reservationDao.findReservationsByTrip(trip)).thenReturn(Arrays.asList(testReservation));
        when(reservationDao.findAllReservations()).thenReturn(Arrays.asList(testReservation));
        when(reservationDao.findById(testReservation.getId())).thenReturn(testReservation);
    }

    @Test
    public void testFindReservationById() {
        reservationService.createReservation(testReservation);
        Reservation res = reservationService.findReservationById(testReservation.getId());
        assertEquals(res, testReservation);
    }

    @Test(expectedExceptions = TravelAgencyPersistenceException.class)
    public void testRemoveNull(){
        Mockito.doThrow(NullPointerException.class).when(reservationDao).remove(null);
        reservationService.removeReservation(null);
    }

    @Test(expectedExceptions = TravelAgencyPersistenceException.class)
    public void  findTripByInvalidId(){
        when(reservationDao.findById(-10L)).thenThrow(new IllegalArgumentException());
        reservationService.findReservationById(-10L);
    }

    @Test
    public void testCreateReservation() {
        when(customerDao.findById(5L)).thenReturn(cust);
        when(excursionDao.findExcursionById(1L)).thenReturn(excursion1);
        when(excursionDao.findExcursionById(2L)).thenReturn(excursion2);

        reservationService.createReservation(testReservation);
        verify(reservationDao).create(testReservation);

    }

    @Test(expectedExceptions = TravelAgencyPersistenceException.class)
    public void testCreateNullReservation(){
        reservationService.createReservation(null);
    }

    @Test
    public void testUpdateReservation() {
        Date created = new Date();
        try {
            created = (Date) new SimpleDateFormat("dd/MM/yyyy").parse("24/12/2017");
        } catch (ParseException ex) {
            Logger.getLogger(ReservationServiceTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        testReservation.setCreated(created);
        reservationService.updateReservation(testReservation);
        verify(reservationDao).update(testReservation);
    }

    @Test(expectedExceptions = TravelAgencyPersistenceException.class)
    public void testUpdateNullReservation(){
        when(reservationDao.update(null)).thenThrow(new IllegalArgumentException());
        reservationService.updateReservation(null);
    }

    @Test(expectedExceptions = TravelAgencyPersistenceException.class)
    public void testUpdateReservationWithNullParameter(){
        testReservation.setCreated(null);
        when(reservationDao.update(testReservation)).thenThrow(new IllegalArgumentException());
        reservationService.updateReservation(testReservation);
    }

    @Test
    public void testRemoveReservation() {
        reservationService.createReservation(testReservation);
        reservationService.removeReservation(testReservation);
        verify(reservationDao).remove(testReservation);
    }

    @Test
    public void testAddExcursionToReservation() {
        reservationDao.create(testReservation);
        Excursion testExc = new Excursion();
        testExc.setPlace("Dreams");
        reservationService.addExcursionToReservation(testReservation, testExc);
        assertEquals(testReservation.getExcursions(), reservationService.findReservationById(testReservation.getId()).getExcursions());

    }

    @Test
    public void testFindAllReservations() {
        assertEquals(reservationService.findAllReservations(), Arrays.asList(testReservation));
        verify(reservationDao).findAllReservations();
    }

    @Test
    public void testFindReservationsByCustomer() {
        assertEquals(reservationService.findReservationsByCustomer(testReservation.getCustomer()), Arrays.asList(testReservation));
        verify(reservationDao).findReservationsByCustomer(testReservation.getCustomer());
    }

    @Test
    public void testFindReservationsByTrip() {
        assertEquals(reservationService.findReservationsByTrip(testReservation.getTrip()), Arrays.asList(testReservation));
        verify(reservationDao).findReservationsByTrip(testReservation.getTrip());
    }

    @Test
    public void testGetTotalPriceTest() {
        BigDecimal testPrice = testReservation.getTrip().getPrice();
        for(Excursion excursion : testReservation.getExcursions()){
            testPrice = testPrice.add(excursion.getPrice());
        }
        reservationDao.create(testReservation);
        Assert.assertEquals(reservationService.getTotalPrice(testReservation), testPrice);
    }

    @Test(expectedExceptions = TravelAgencyPersistenceException.class)
    public void testGetTotalPriceWithNullTest() {
        reservationService.getTotalPrice(null);
    }
}
