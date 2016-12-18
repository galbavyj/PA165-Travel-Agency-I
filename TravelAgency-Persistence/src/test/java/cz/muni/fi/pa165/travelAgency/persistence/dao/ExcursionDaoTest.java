package cz.muni.fi.pa165.travelAgency.persistence.dao;

import cz.muni.fi.pa165.travelAgency.persistence.entity.*;


import cz.muni.fi.pa165.travelagency.api.enums.CustomerRole;
import cz.muni.fi.pa165.travelagency.api.enums.ExcursionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import travelAgency.TravelAgencyPersistenceContext;

import javax.validation.ConstraintViolationException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;


/**
 * @author Juraj Galbavý
 */


@ContextConfiguration(classes = TravelAgencyPersistenceContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class ExcursionDaoTest extends AbstractTestNGSpringContextTests{

    @Autowired
    private ExcursionDao excursionDao;


    private Excursion excursion1;
    private Excursion excursion2;

    private Trip trip1;
    private Address address, addressCustomer;
    private Customer customer;
    private Reservation r1, r2;



    @BeforeMethod
    public void setup() {

        Calendar calendar = Calendar.getInstance();
        calendar.set(2017, Calendar.JANUARY, 10, 8, 16);
        Date created1 = calendar.getTime();

        calendar.set(2017, Calendar.FEBRUARY, 10, 8, 16);
        Date created2 = calendar.getTime();

        calendar.set(2017, Calendar.APRIL, 10, 8, 0);
        Date startExcursion1 = calendar.getTime();

        calendar.set(2017, Calendar.APRIL, 15, 8, 0);
        Date startExcursion2 = calendar.getTime();

        calendar.set(2017, Calendar.APRIL, 1, 8, 0);
        Date startTrip = calendar.getTime();

        calendar.set(2017, Calendar.APRIL, 20, 8, 0);
        Date endTrip = calendar.getTime();

        Set<Excursion> excursions = new HashSet<>();
        excursions.add(excursion1);
        excursions.add(excursion2);



        excursion1 = new Excursion();
        excursion1.setCreated(created1);
        excursion1.setDescription("Good enough");
        excursion1.setDurationInHours(10);
        excursion1.setFromDate(startExcursion1);
        excursion1.setPlace("Greenwich");
        excursion1.setPrice(BigDecimal.valueOf(1500));
        excursion1.setExcursionType(ExcursionType.CULTURE);


        excursion2 = new Excursion();
        excursion2.setCreated(created2);
        excursion2.setDescription("The best excursion");
        excursion2.setDurationInHours(30);
        excursion2.setFromDate(startExcursion2);
        excursion2.setPlace("Wien");
        excursion2.setPrice(BigDecimal.valueOf(2000));
        excursion2.setExcursionType(ExcursionType.ENTERTAINMENT);

        trip1 = new Trip();
        address = new Address();
        address.setCity("London");
        address.setCountry("UK");
        address.setNumberOfHouse(15);
        address.setStreet("George Street");
        trip1.setCreatedDate(created1);
        trip1.setFromDate(startTrip);
        trip1.setToDate(endTrip);
        trip1.setPossibleExcursions(excursions);
        trip1.setPrice(BigDecimal.valueOf(15000));
        trip1.setAddressOfHotel(address);


        addressCustomer = new Address();
        addressCustomer.setCity("Prievidza");
        addressCustomer.setCountry("Slovensko");
        addressCustomer.setNumberOfHouse(10);
        addressCustomer.setStreet("Dolná");

        customer = new Customer();
        customer.setAddress(addressCustomer);
        customer.setFirstName("Albert");
        customer.setLastName("Forest");
        customer.setEmail("albert.forest@centrum.cz");
        customer.setPhoneNumber("+420756847374");
        customer.setCustomerRole(CustomerRole.CUSTOMER);
        customer.setCreated(created1);

        r1 = new Reservation();
        r1.setTrip(trip1);
        r1.setCustomer(customer);
        r1.setCreated(created1);
        r1.setExcursions(excursions);

        Set<Reservation> reservations = new HashSet<>();
        reservations.add(r1);
    }


    @Test
    public void create(){
        excursionDao.create(excursion1);
        assertTrue(excursionDao.findAllExcursions().contains(excursion1));
        assertTrue(excursionDao.findAllExcursions().size() == 1);
    }

    @Test
    public void update(){
        excursionDao.create(excursion1);
        assertEquals(excursionDao.findExcursionById(excursion1.getId()).getPlace(), "Greenwich");
        excursion1.setPlace("BigBen");
        excursionDao.update(excursion1);
        assertEquals(excursionDao.findExcursionById(excursion1.getId()).getPlace(), "BigBen");
        assertFullEquals(excursionDao.findExcursionById(excursion1.getId()), excursion1);
    }


    @Test
    public void remove(){
        excursionDao.create(excursion1);
        excursionDao.remove(excursion1);
        assertEquals(excursionDao.findAllExcursions().size(), 0);
    }

    @Test
    public void testFindById() {
        excursionDao.create(excursion1);
        assertFullEquals(excursionDao.findExcursionById(excursion1.getId()), excursion1);
    }

    @Test
    public void findAllExcursions(){
        excursionDao.create(excursion1);
        excursionDao.create(excursion2);
        assertTrue(excursionDao.findAllExcursions().contains(excursion1));
        assertTrue(excursionDao.findAllExcursions().contains(excursion2));
        assertTrue(excursionDao.findAllExcursions().size() == 2);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void testCreateWithNullPlace() {
        excursion1.setPlace(null);
        excursionDao.create(excursion1);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void testCreateWithNullCreated() {
        excursion1.setCreated(null);
        excursionDao.create(excursion1);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void testCreateWithNegativePrice() {
        excursion1.setPrice(BigDecimal.valueOf(-1));
        excursionDao.create(excursion1);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void testCreateWithNullDateFrom() {
        excursion1.setFromDate(null);
        excursionDao.create(excursion1);
    }


    @Test(expectedExceptions = ConstraintViolationException.class)
    public void testCreateWithNullExcursionType() {
        excursion1.setExcursionType(null);
        excursionDao.create(excursion1);
    }

    private void assertFullEquals(Excursion excursion, Excursion excursion1) {
        assertEquals(excursion, excursion1);
        assertEquals(excursion.getId(), excursion1.getId());
        assertEquals(excursion.getCreated(), excursion1.getCreated());
        assertEquals(excursion.getDescription(), excursion1.getDescription());
        assertEquals(excursion.getDurationInHours(), excursion1.getDurationInHours());
        assertEquals(excursion.getFromDate(), excursion1.getFromDate());
        assertEquals(excursion.getPlace(), excursion1.getPlace());
        assertEquals(excursion.getPrice(), excursion1.getPrice());
    }

}
