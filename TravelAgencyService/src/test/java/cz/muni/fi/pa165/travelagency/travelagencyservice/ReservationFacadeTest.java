/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.travelagency.travelagencyservice;

import cz.muni.fi.pa165.travelAgency.persistence.entity.Address;
import cz.muni.fi.pa165.travelAgency.persistence.entity.Customer;
import cz.muni.fi.pa165.travelAgency.persistence.entity.Excursion;
import cz.muni.fi.pa165.travelAgency.persistence.entity.Reservation;
import cz.muni.fi.pa165.travelAgency.persistence.entity.Trip;
import cz.muni.fi.pa165.travelagency.api.dto.AddressDTO;
import cz.muni.fi.pa165.travelagency.api.dto.CustomerDTO;
import cz.muni.fi.pa165.travelagency.api.dto.ExcursionDTO;
import cz.muni.fi.pa165.travelagency.api.dto.ReservationDTO;
import cz.muni.fi.pa165.travelagency.api.dto.TripDTO;
import cz.muni.fi.pa165.travelagency.api.enums.CustomerRole;
import cz.muni.fi.pa165.travelagency.api.enums.ExcursionType;
import cz.muni.fi.pa165.travelagency.api.facade.CustomerFacade;
import cz.muni.fi.pa165.travelagency.api.facade.ReservationFacade;
import cz.muni.fi.pa165.travelagency.api.facade.TripFacade;
import facade.CustomerFacadeImpl;
import facade.ReservationFacadeImpl;
import facade.TripFacadeImpl;
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
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import service.ReservationService;
import static org.mockito.Mockito.verify;
import static org.testng.Assert.assertEquals;
import service.CustomerService;
import service.ExcursionService;
import service.TripService;

/**
 *
 * @author Lucia
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class ReservationFacadeTest extends AbstractTransactionalTestNGSpringContextTests {
    @Mock
    private ReservationService reservationService;

    @Mock
    private CustomerService customerService;

    @Mock
    private TripService tripService;

    @Mock
    private ExcursionService excursionService;


    @Mock
    private MappingService mappingService;

    @InjectMocks
    private ReservationFacade reservationFacade = new ReservationFacadeImpl();

    @InjectMocks
    private CustomerFacade customerFacade = new CustomerFacadeImpl();

    @InjectMocks
    private TripFacade tripFacade = new TripFacadeImpl();

    @BeforeClass
    public void setup() throws ServiceException
    {
        MockitoAnnotations.initMocks(this);
    }

    private ReservationDTO testReservationDTO;
    private Reservation testReservation;

    private TripDTO tripDTO;
    private Trip trip;
    private ExcursionDTO excursion1DTO;
    private ExcursionDTO excursion2DTO;
    private Excursion excursion1;
    private Excursion excursion2;
    private List<ReservationDTO> listWithReserv;
    private CustomerDTO custDTO;
    private Customer cust;

    @BeforeMethod
    public void prepareTestReservation(){
        Date created = new Date();
        tripDTO = new TripDTO();
        custDTO = new CustomerDTO();
        excursion1DTO = new ExcursionDTO();
        excursion2DTO = new ExcursionDTO();
        trip = new Trip();
        excursion1 = new Excursion();
        excursion2 = new Excursion();
        cust = new Customer();
        testReservationDTO = new ReservationDTO();
        testReservation = new Reservation();



        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setCity("Brno");
        addressDTO.setCountry("Czech republic");
        addressDTO.setNumberOfHouse(10);
        addressDTO.setStreet("Holandska");

        Address address = new Address();
        address.setCity("Brno");
        address.setCountry("Czech republic");
        address.setNumberOfHouse(10);
        address.setStreet("Holandska");


        custDTO.setId(2l);
        custDTO.setFirstName("Juraj");
        custDTO.setLastName("Janosik");
        custDTO.setAddress(addressDTO);
        custDTO.setEmail("janosik@gmail.com");
        try {
            created = (Date) new SimpleDateFormat("dd/MM/yyyy").parse("24/12/2017");
        } catch (ParseException ex) {
            Logger.getLogger(ReservationServiceTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        custDTO.setCreated(created);
        custDTO.setPhoneNumber("725555666");
        custDTO.setcustomerRole(CustomerRole.CUSTOMER);

        cust.setId(2l);
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

        excursion1DTO.setId(15l);
        excursion1DTO.setCreated(created);
        excursion1DTO.setDescription("Good enough");
        excursion1DTO.setDurationInHours(10);
        excursion1DTO.setFromDate(created);
        excursion1DTO.setPlace("Greenwich");
        excursion1DTO.setPrice(BigDecimal.valueOf(100));
        excursion1DTO.setTrip(tripDTO);
        excursion1DTO.setExcursionType(ExcursionType.CULTURE);

        excursion1.setId(15l);
        excursion1.setCreated(created);
        excursion1.setDescription("Good enough");
        excursion1.setDurationInHours(10);
        excursion1.setFromDate(created);
        excursion1.setPlace("Greenwich");
        excursion1.setPrice(BigDecimal.valueOf(100));
        excursion1.setTrip(trip);
        excursion1.setExcursionType(ExcursionType.CULTURE);

        excursion2DTO.setId(16l);
        excursion2DTO.setCreated(created);
        excursion2DTO.setDescription("The best excursion");
        excursion2DTO.setDurationInHours(30);
        excursion2DTO.setFromDate(created);
        excursion2DTO.setPlace("Wien");
        excursion2DTO.setPrice(BigDecimal.valueOf(200));
        excursion2DTO.setTrip(tripDTO);
        excursion2DTO.setExcursionType(ExcursionType.ENTERTAINMENT);

        excursion2.setId(16l);
        excursion2.setCreated(created);
        excursion2.setDescription("The best excursion");
        excursion2.setDurationInHours(30);
        excursion2.setFromDate(created);
        excursion2.setPlace("Wien");
        excursion2.setPrice(BigDecimal.valueOf(200));
        excursion2.setTrip(trip);
        excursion2.setExcursionType(ExcursionType.ENTERTAINMENT);

        Set<ExcursionDTO> excursionsDTO = new HashSet<>();
        excursionsDTO.add(excursion1DTO);
        excursionsDTO.add(excursion2DTO);
        Set<Long> exIds = new HashSet<>();
        exIds.add(excursion1DTO.getId());
        exIds.add(excursion2DTO.getId());
        exIds.add(17l);

        Set<Excursion> excursions = new HashSet<>();
        excursions.add(excursion1);
        excursions.add(excursion2);

        tripDTO.setId(10l);
        tripDTO.setCreatedDate(created);
        tripDTO.setFromDate(created);
        tripDTO.setToDate(created);
        tripDTO.setPossibleExcursions(excursionsDTO);
        tripDTO.setPrice(BigDecimal.valueOf(1000));
        tripDTO.setAddressOfHotel(addressDTO);

        trip.setId(10l);
        trip.setCreatedDate(created);
        trip.setFromDate(created);
        trip.setToDate(created);
        trip.setPossibleExcursions(excursions);
        trip.setPrice(BigDecimal.valueOf(1000));
        trip.setAddressOfHotel(address);

        testReservationDTO.setId(1l);
        testReservationDTO.setCustomer(custDTO);
        testReservationDTO.setExcursions(excursionsDTO);
        testReservationDTO.setTrip(tripDTO);

        testReservation.setId(0l);
        testReservation.setCustomer(cust);
        testReservation.setExcursions(excursions);
        testReservation.setTrip(trip);

        tripDTO.addPossibleExcursion(excursion1DTO);
        trip.addPossibleExcursion(excursion1);


        listWithReserv= new ArrayList<ReservationDTO>();
        listWithReserv.add(testReservationDTO);

        when(mappingService.mapTo(testReservationDTO, Reservation.class)).thenReturn(testReservation);
        //when(mappingService.mapTo(custDTO, CustomerDTO.class)).thenReturn(cust);
        when(customerService.findById(cust.getId())).thenReturn(cust);
        when(tripService.findTripById(trip.getId())).thenReturn(trip);

        //when(excursionService.createExcursion(trip.getId())).thenReturn(trip);


    }

    @Test
    public void testCreateReservation() {
        when(mappingService.mapTo(testReservationDTO, Reservation.class)).thenReturn(testReservation);
        reservationFacade.createReservation(testReservationDTO);
        verify(reservationService).createReservation(testReservation);
    }

    @Test
    public void testRemoveReservation() {
        when(mappingService.mapTo(testReservationDTO, Reservation.class)).thenReturn(testReservation);
        reservationFacade.removeReservation(testReservationDTO);
        verify(reservationService).removeReservation(testReservation);
    }

    @Test
    public void testUpdateReservation() {
        when(mappingService.mapTo(testReservationDTO, Reservation.class)).thenReturn(testReservation);
        reservationFacade.updateReservation(testReservationDTO);
        verify(reservationService).updateReservation(testReservation);
    }

    @Test
    public void testReservationById() {
        when(reservationService.findReservationById(1l)).thenReturn(testReservation);
        when(mappingService.mapTo(testReservation, ReservationDTO.class)).thenReturn(testReservationDTO);
        assertEquals(reservationFacade.getReservationsById(1l), testReservationDTO);
    }

    @Test
    public void testGetAllReservations() {
        List<Reservation> allRes = Arrays.asList(testReservation);
        List<ReservationDTO> allDTORes = Arrays.asList(testReservationDTO);
        when(reservationService.findAllReservations()).thenReturn(allRes);
        when(mappingService.mapTo(allRes, ReservationDTO.class)).thenReturn(allDTORes);
        reservationFacade.createReservation(testReservationDTO);
        assertEquals(reservationFacade.getAllReservations(), allDTORes);
    }

    @Test
    public void testFindReservationsByCustomer() {
        List<Reservation> allRes = Arrays.asList(testReservation);
        List<ReservationDTO> allDTORes = Arrays.asList(testReservationDTO);
        when(reservationService.findReservationsByCustomer(cust)).thenReturn(allRes);
        when(mappingService.mapTo(allRes, ReservationDTO.class)).thenReturn(allDTORes);
        reservationFacade.createReservation(testReservationDTO);
        assertEquals(reservationFacade.getReservationsByCustomer(cust.getId()), allDTORes);       }

    @Test
    public void testFindReservationsByTrip() {
        List<Reservation> allRes = Arrays.asList(testReservation);
        List<ReservationDTO> allDTORes = Arrays.asList(testReservationDTO);
        when(reservationService.findReservationsByTrip(trip)).thenReturn(allRes);
        when(mappingService.mapTo(allRes, ReservationDTO.class)).thenReturn(allDTORes);
        reservationFacade.createReservation(testReservationDTO);
        assertEquals(reservationFacade.getReservationsByTrip(trip.getId()), allDTORes);
    }

    @Test
    public void testAddExcursionToReservation() {
        testReservation.setId(1l);
        when(reservationService.findReservationById(testReservation.getId())).thenReturn(testReservation);
        Excursion testExc = new Excursion();
        testExc.setPlace("Dreams");
        testExc.setId(5l);
        when(excursionService.findExcursionById(testExc.getId())).thenReturn(testExc);
        reservationFacade.addExcursion(testReservation.getId(), testExc.getId());
        verify(reservationService).addExcursionToReservation(testReservation, testExc);
    }

}
