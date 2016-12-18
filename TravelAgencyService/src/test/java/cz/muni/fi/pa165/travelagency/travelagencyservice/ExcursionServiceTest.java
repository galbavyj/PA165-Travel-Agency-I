/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.travelagency.travelagencyservice;

import cz.muni.fi.pa165.travelAgency.persistence.dao.ExcursionDao;
import cz.muni.fi.pa165.travelAgency.persistence.entity.Address;
import cz.muni.fi.pa165.travelAgency.persistence.entity.Customer;
import cz.muni.fi.pa165.travelAgency.persistence.entity.Excursion;
import cz.muni.fi.pa165.travelAgency.persistence.entity.Reservation;
import cz.muni.fi.pa165.travelAgency.persistence.entity.Trip;
import cz.muni.fi.pa165.travelagency.api.enums.CustomerRole;
import cz.muni.fi.pa165.travelagency.api.enums.ExcursionType;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import javax.inject.Inject;
import org.hibernate.service.spi.ServiceException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import static org.testng.Assert.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import service.ExcursionService;
/**
 *
 * @author Patrik Behrami
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class ExcursionServiceTest extends AbstractTestNGSpringContextTests{
    @Mock
    private ExcursionDao excursionDao;

    @Inject
    @InjectMocks
    private ExcursionService excursionService;
    
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

        Set<Excursion> excursions = new HashSet<>();    

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
        
        excursions.add(excursion1);
        excursions.add(excursion2);

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

        //excursion1.setReservations(reservations);
    }    

    @BeforeClass
    public void setupMocks() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void testCreateExcursion(){
        excursionService.createExcursion(excursion1);
        verify(excursionDao).create(excursion1);
    }
    
    @Test
    public void testRemoveExcursion(){
        excursionService.createExcursion(excursion1);
        
        //excursionService.removeExcursion(excursion1);
        //verify(excursionDao).remove(excursion1);
    }
    
    @Test
    public void testUpdateExcursion(){
        excursion1.setPrice(BigDecimal.valueOf(1000));
        excursionService.updateExcursion(excursion1);
        verify(excursionDao).update(excursion1);
    }
    
    @Test
    public void testFindAll(){
        when(excursionDao.findAllExcursions()).thenReturn(Arrays.asList(excursion1,excursion2));
        excursionService.findAllExcursions();
        verify(excursionDao).findAllExcursions();
        assertEquals(excursionService.findAllExcursions(),Arrays.asList(excursion1,excursion2));
    }
    
    @Test
    public void testFindById() {
        excursion1.setId(new Long(1));
        when(excursionDao.findExcursionById(excursion1.getId())).thenReturn(excursion1);
        excursionService.findExcursionById(excursion1.getId());
        verify(excursionDao).findExcursionById(excursion1.getId());
        assertEquals(excursion1, excursionService.findExcursionById(excursion1.getId()));
    }
    
    @Test
    public void testChangeDescription(){
        String newDescription = "Tour around polish museum";
        excursionService.changeDescription(excursion1, newDescription);
        verify(excursionDao).update(excursion1);
        Assert.assertEquals(newDescription,excursion1.getDescription());
    }
    
    @Test
    public void testChangePrice(){
        BigDecimal newPrice = BigDecimal.valueOf(500);
        excursionService.changePrice(excursion1, newPrice);
        verify(excursionDao).update(excursion1);
        Assert.assertEquals(newPrice,excursion1.getPrice());
    }
    
    
    @Test(expectedExceptions = TravelAgencyPersistenceException.class)
    public void testCreateWithNull(){
        Mockito.doThrow(NullPointerException.class).when(excursionDao).create(null);
        excursionService.createExcursion(null);
    }
    
    @Test(expectedExceptions = TravelAgencyPersistenceException.class)
    public void testRemoveWithNull(){
        Mockito.doThrow(NullPointerException.class).when(excursionDao).remove(null);
        excursionService.removeExcursion(null);
    }
    
    @Test(expectedExceptions = TravelAgencyPersistenceException.class)
    public void testUpdateWithNull(){
        Mockito.doThrow(NullPointerException.class).when(excursionDao).update(null);
        excursionService.updateExcursion(null);
    }
    
    @Test(expectedExceptions = TravelAgencyPersistenceException.class)
    public void testFindByIdWithInvalidArgument(){
        Long id = new Long(-1);
        when(excursionDao.findExcursionById(id)).thenThrow(IllegalArgumentException.class);
        excursionService.findExcursionById(id);
    }
    
    @Test(expectedExceptions = TravelAgencyPersistenceException.class)
    public void testUpdateWithInvalidPrice(){
        BigDecimal price = new BigDecimal(-200);
        excursionService.createExcursion(excursion1);
        excursion1.setPrice(price);
        when(excursionDao.update(excursion1)).thenThrow(IllegalArgumentException.class);
        excursionService.updateExcursion(excursion1);
    }

    
    
  
    
}
