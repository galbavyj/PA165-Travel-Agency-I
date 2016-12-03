package cz.muni.fi.pa165.travelagency.travelagencyservice;

import cz.muni.fi.pa165.travelAgency.persistence.dao.CustomerDao;
import cz.muni.fi.pa165.travelAgency.persistence.entity.*;
import cz.muni.fi.pa165.travelagency.api.enums.CustomerRole;
import org.hibernate.service.spi.ServiceException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import service.CustomerService;

import java.math.BigDecimal;
import java.util.*;
import org.mockito.Mockito;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

/**
 * @author Juraj
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class CustomerServiceTest extends AbstractTestNGSpringContextTests {

    @Mock
    private CustomerDao customerDao;

    @Autowired
    @InjectMocks
    private CustomerService customerService;

    private Address addressCustomer2, addressCustomer1, tripAddress;
    private Customer customer2, customer1;
    private Reservation reservation;
    private Trip trip;

    private String pass;

    @BeforeClass
    public void setUpClass() throws ServiceException {
                MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void setup() {

        Calendar calendar = Calendar.getInstance();
        calendar.set(2017, Calendar.JANUARY, 10, 8, 16);
        Date created1 = calendar.getTime();

        calendar.set(2017, Calendar.FEBRUARY, 10, 8, 16);
        Date created2 = calendar.getTime();

        addressCustomer2 = new Address();
        addressCustomer2.setCity("Brno");
        addressCustomer2.setCountry("Česko");
        addressCustomer2.setNumberOfHouse(16);
        addressCustomer2.setStreet("Oblokova");

        addressCustomer1 = new Address();
        addressCustomer1.setCity("Prievidza");
        addressCustomer1.setCountry("Slovensko");
        addressCustomer1.setNumberOfHouse(10);
        addressCustomer1.setStreet("Dolná");
        pass = "Tralala/ZloziteHeslo12345";

        customer1 = new Customer();
        customer1.setId(10L);
        customer1.setAddress(addressCustomer1);
        customer1.setFirstName("Dano");
        customer1.setLastName("Drevo");
        customer1.setEmail("dano.drevo@centrum.cz");
        customer1.setPhoneNumber("+420566487744");
        customer1.setCustomerRole(CustomerRole.CUSTOMER);
        customer1.setCreated(created1);
        customer1.setReservations(new HashSet<>());

        customer2 = new Customer();
        customer2.setAddress(addressCustomer2);
        customer2.setFirstName("Albert");
        customer2.setLastName("Forest");
        customer2.setEmail("albert.forest@centrum.cz");
        customer2.setPhoneNumber("+420756847374");
        customer2.setCustomerRole(CustomerRole.ADMIN);
        customer2.setCreated(created2);

        calendar.set(2017, Calendar.APRIL, 1, 8, 0);
        Date startTrip = calendar.getTime();

        calendar.set(2017, Calendar.APRIL, 20, 8, 0);
        Date endTrip = calendar.getTime();

        trip = new Trip();
        tripAddress = new Address();
        tripAddress.setCity("London");
        tripAddress.setCountry("UK");
        tripAddress.setNumberOfHouse(15);
        tripAddress.setStreet("George Street");
        trip.setCreated(created1);
        trip.setFrom(startTrip);
        trip.setTo(endTrip);
        trip.setPrice(BigDecimal.valueOf(15000));
        trip.setAddressOfHotel(tripAddress);

        reservation = new Reservation(customer1, created1, trip, null);
    }


    @Test
    public void testRegisterAndAuthenticateCustomer(){
        customerService.registerCustomer(customer1, pass);
        verify(customerDao).create(customer1);
        assertEquals(customerService.authenticateCustomer(customer1, pass), true);
    }

    @Test
    public void testUpdateCustomer(){
        customer1.setFirstName("Jolana");
        customerService.updateCustomer(customer1);
        verify(customerDao).update(customer1);
    }

    @Test
    public void testRemoveCustomer(){
        customerService.removeCustomer(customer1);
        verify(customerDao).remove(customer1);
    }

    @Test
    public void testFindAll(){
        when(customerDao.findAllCustomers()).thenReturn(new ArrayList<>());
        assertEquals(customerService.findAll().size(), 0);
        when(customerDao.findAllCustomers()).thenReturn(Collections.singletonList(customer1));
        assertEquals(customerService.findAll().size(), 1);
        when(customerDao.findAllCustomers()).thenReturn(Arrays.asList(customer1, customer2));
        assertEquals(customerService.findAll().size(), 2);
    }

    @Test
    public void testFindById(){
        customer1.setId(5L);
        when(customerDao.findById(customer1.getId())).thenReturn(customer1);
        assertEquals(customerService.findById(customer1.getId()), customer1);
    }

    @Test
    public void testFindByEmail(){
        customer1.setEmail("expeliarmus@centrum.hu");
        when(customerDao.findByEmail(customer1.getEmail())).thenReturn(customer1);
        assertEquals(customerService.findByEmail(customer1.getEmail()), customer1);
    }

    @Test
    public void testAddReservationToCustomer(){
        customerService.addReservationToCustomer(customer1, reservation);
        when(customerDao.findById(customer1.getId())).thenReturn(customer1);
        assertEquals(customerService.findById(customer1.getId()).getReservations(), customer1.getReservations());
    }
    
    @Test
    public void testAddReservationToCustomerNull(){
        Mockito.doThrow(NullPointerException.class).when(customerDao).addReservation(null, null);
        customerService.addReservationToCustomer(customer1, reservation);        
    }

    @Test(expectedExceptions = TravelAgencyPersistenceException.class)
    public void testFindByIdNull(){
        when(customerDao.findById(null)).thenThrow(new IllegalArgumentException());
        customerService.findById(null);
    }
    
    @Test(expectedExceptions = TravelAgencyPersistenceException.class)
    public void testFindByIdInvalid(){
        Long id = new Long(-1);
        when(customerDao.findById(id)).thenThrow(IllegalArgumentException.class);
        customerService.findById(id);
    }

    @Test(expectedExceptions = TravelAgencyPersistenceException.class)
    public void testFindByEmailNull(){
        when(customerDao.findByEmail(null)).thenThrow(new IllegalArgumentException());
        customerService.findByEmail(null);
    }

    @Test(expectedExceptions = TravelAgencyPersistenceException.class)
    public void testCreateWithNull(){
        Mockito.doThrow(NullPointerException.class).when(customerDao).create(null);
        customerService.registerCustomer(null,null);
    }
    
    @Test(expectedExceptions = TravelAgencyPersistenceException.class)
    public void testRemoveWithNull(){
        Mockito.doThrow(NullPointerException.class).when(customerDao).remove(null);
        customerService.removeCustomer(null);
    }
    
    @Test(expectedExceptions = TravelAgencyPersistenceException.class)
    public void testUpdateWithNull(){
        Mockito.doThrow(NullPointerException.class).when(customerDao).update(null);
        customerService.updateCustomer(null);
    }   
}