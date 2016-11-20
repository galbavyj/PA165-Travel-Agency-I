/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.travelagency.travelagencyservice;

import cz.muni.fi.pa165.travelAgency.persistence.entity.Address;
import cz.muni.fi.pa165.travelAgency.persistence.entity.Trip;
import cz.muni.fi.pa165.travelagency.api.dto.AddressDTO;
import cz.muni.fi.pa165.travelagency.api.dto.TripDTO;
import cz.muni.fi.pa165.travelagency.api.facade.TripFacade;
import facade.TripFacadeImpl;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.service.spi.ServiceException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import static org.testng.Assert.assertEquals;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import service.CustomerService;
import service.ExcursionService;
import service.ReservationService;
import service.TripService;

/**
 *
 * @author Martin
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class TripFacadeTest extends AbstractTransactionalTestNGSpringContextTests {

    @Mock
    private TripService tripService;
    
    @Mock
    private MappingService mappingService;

    @InjectMocks
    private TripFacade tripFacade = new TripFacadeImpl();

    private Address address1;
    private Address address2;
    private Trip trip1;
    private Trip trip2;
    private Date from;
    private Date to;
    private Date created;

    private AddressDTO address1DTO;
    private AddressDTO address2DTO;
    private TripDTO trip1DTO;
    private TripDTO trip2DTO;

    @BeforeClass
    public void setUpClass() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
        address1 = new Address();
        address1.setCity("Brno");
        address1.setCountry("Czech republic");
        address1.setNumberOfHouse(68);
        address1.setStreet("Botanicka");

        address2 = new Address();
        address2.setCity("Athens");
        address2.setCountry("Greece");
        address2.setNumberOfHouse(69);
        address2.setStreet("Oidipus street");

        address1DTO = new AddressDTO();
        address1DTO.setCity(address1.getCity());
        address1DTO.setCountry(address1.getCountry());
        address1DTO.setStreet(address1.getStreet());
        address1DTO.setNumberOfHouse(address1.getNumberOfHouse());

        address2DTO = new AddressDTO();
        address2DTO.setCity(address2.getCity());
        address2DTO.setCountry(address2.getCountry());
        address2DTO.setStreet(address2.getStreet());
        address2DTO.setNumberOfHouse(address2.getNumberOfHouse());

        trip1 = new Trip();
        trip1.setAddressOfHotel(address1);
        try {
            from = (Date) new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2017");
            to = (Date) new SimpleDateFormat("dd/MM/yyyy").parse("10/01/2017");
            created = (Date) new SimpleDateFormat("dd/MM/yyyy").parse("30/10/2016");
        } catch (ParseException ex) {
            Logger.getLogger(TripFacadeTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        trip1.setCreated(created);
        trip1.setFrom(from);
        trip1.setTo(to);
        trip1.setPrice(BigDecimal.TEN);
        trip1.setFilePathToPicture("C:/petrov");

        trip2 = new Trip();
        trip2.setAddressOfHotel(address2);
        trip2.setCreated(created);
        trip2.setFrom(from);
        trip2.setTo(to);
        trip2.setPrice(new BigDecimal(1000));
        trip2.setFilePathToPicture("C:/sea");

        trip1DTO = mappingService.mapTo(trip1, TripDTO.class);
        trip2DTO = mappingService.mapTo(trip2, TripDTO.class);
    }

    @Test
    public void testCreateTrip(TripDTO trip) {
        when(mappingService.mapTo(trip1DTO, Trip.class)).thenReturn(trip1);
        tripFacade.createTrip(trip1DTO);
        verify(tripService).createTrip(trip1);
    }

    @Test
    public void testRemoveTrip(TripDTO trip) {
        when(mappingService.mapTo(trip1DTO, Trip.class)).thenReturn(trip1);
        tripFacade.removeTrip(trip1DTO);
        verify(tripService).removeTrip(trip1);
    }

    @Test
    public void testUpdateTrip(TripDTO trip) {
        when(mappingService.mapTo(trip1DTO, Trip.class)).thenReturn(trip1);
        tripFacade.updateTrip(trip1DTO);
        verify(tripService).updateTrip(trip1);
    }

    @Test
    public void testFindTripById(Long id) {
        tripFacade.createTrip(trip1DTO);
        tripFacade.createTrip(trip2DTO);
        assertEquals(tripFacade.findTripById(trip1DTO.getId()), trip1DTO);
        verify(tripService).findTripById(trip1DTO.getId());
    }

    @Test
    public void testFindAllTrips() {
        List<Trip> allTrips = Arrays.asList(trip1, trip2);
        List<TripDTO> allDTOTrips = Arrays.asList(trip1DTO, trip2DTO);
        when(tripService.findAllTrips()).thenReturn(allTrips);
        when(mappingService.mapTo(allTrips, TripDTO.class)).thenReturn(allDTOTrips);
        tripFacade.createTrip(trip1DTO);
        tripFacade.createTrip(trip2DTO);
        assertEquals(tripFacade.findAllTrips(), allDTOTrips);
    }

    @Test
    public void testFindTripsByCountry(String country) {
        List<Trip> allTripsToGreece = Arrays.asList(trip1, trip2);
        List<TripDTO> allDTOTripsToGreece = Arrays.asList(trip1DTO, trip2DTO);
        when(tripService.findTripsByCountry("Greece")).thenReturn(allTripsToGreece);
        when(mappingService.mapTo(allTripsToGreece, TripDTO.class)).thenReturn(allDTOTripsToGreece);
        tripFacade.createTrip(trip1DTO);
        tripFacade.createTrip(trip2DTO);
        assertEquals(tripFacade.findTripsByCountry("Greece"), allDTOTripsToGreece);
    }
}
