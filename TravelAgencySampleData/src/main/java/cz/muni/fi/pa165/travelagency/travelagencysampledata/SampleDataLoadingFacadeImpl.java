package cz.muni.fi.pa165.travelagency.travelagencysampledata;

import cz.muni.fi.pa165.travelAgency.persistence.entity.Address;
import cz.muni.fi.pa165.travelAgency.persistence.entity.Customer;
import cz.muni.fi.pa165.travelAgency.persistence.entity.Excursion;
import cz.muni.fi.pa165.travelAgency.persistence.entity.Reservation;
import cz.muni.fi.pa165.travelAgency.persistence.entity.Trip;
import cz.muni.fi.pa165.travelagency.api.enums.CustomerRole;
import cz.muni.fi.pa165.travelagency.api.enums.ExcursionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import javax.inject.Inject;
import service.CustomerService;
import service.ExcursionService;
import service.ReservationService;
import service.TripService;

@Component
@Transactional
public class SampleDataLoadingFacadeImpl implements SampleDataLoadingFacade {

    final static Logger log = LoggerFactory.getLogger(SampleDataLoadingFacadeImpl.class);

    @Inject
    private CustomerService customerService;
    @Inject
    private ExcursionService excursionService;
    @Inject
    private TripService tripService;
    @Inject
    private ReservationService reservationService;

    private Address addressAdmin, addressCustomer1, tripAddress;
    private Customer admin, customer1;
    private Reservation testReservation;
    private Trip trip;
    private Excursion excursion1;
    private Excursion excursion2;
    private Excursion excursion3;

    @Override
    public void loadData() throws IOException {
        setupCustomers();
        setupReservations();
    }

    private void setupCustomers() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2017, Calendar.JANUARY, 10, 8, 16);
        java.util.Date created1 = calendar.getTime();

        calendar.set(2017, Calendar.FEBRUARY, 10, 8, 16);
        java.util.Date created2 = calendar.getTime();

        addressAdmin = new Address();
        addressAdmin.setCity("Brno");
        addressAdmin.setCountry("Česko");
        addressAdmin.setNumberOfHouse(16);
        addressAdmin.setStreet("Oblokova");

        addressCustomer1 = new Address();
        addressCustomer1.setCity("Prievidza");
        addressCustomer1.setCountry("Slovensko");
        addressCustomer1.setNumberOfHouse(10);
        addressCustomer1.setStreet("Dolná");

        admin = new Customer();
        admin.setAddress(addressAdmin);
        admin.setFirstName("Admin");
        admin.setLastName("Admin");
        admin.setEmail("admin@gmail.com");
        admin.setPhoneNumber("+420756847374");
        admin.setCustomerRole(CustomerRole.ADMIN);
        admin.setCreated(created2);

        customerService.registerCustomer(admin, "admin");

        customer1 = new Customer();
        customer1.setAddress(addressCustomer1);
        customer1.setFirstName("Dano");
        customer1.setLastName("Drevo");
        customer1.setEmail("customer@gmail.com");
        customer1.setPhoneNumber("+420566487744");
        customer1.setCustomerRole(CustomerRole.CUSTOMER);
        customer1.setCreated(created1);
        customer1.setReservations(new HashSet<>());
        customerService.registerCustomer(customer1, "customer");
    }

    public void setupReservations() {
        java.util.Date created = new java.util.Date();
        java.util.Date from = new java.util.Date();
        java.util.Date to = new java.util.Date();
        try {
            from = (java.util.Date) new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2017");
            to = (java.util.Date) new SimpleDateFormat("dd/MM/yyyy").parse("10/01/2017");
            created = (java.util.Date) new SimpleDateFormat("dd/MM/yyyy").parse("30/10/2016");
        } catch (ParseException ex) {
            java.util.logging.Logger.getLogger(SampleDataLoadingFacadeImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        trip = new Trip();
        trip.setCreatedDate(created);
        trip.setFromDate(from);
        trip.setToDate(to);
        trip.setPrice(BigDecimal.valueOf(1000));
        trip.setAddressOfHotel(addressAdmin);
        tripService.createTrip(trip);
        excursion1 = new Excursion();
        excursion2 = new Excursion();
        excursion3 = new Excursion();
        
        excursion1.setCreated(created);
        excursion1.setDescription("Good enough");
        excursion1.setDurationInHours(10);
        excursion1.setFromDate(from);
        excursion1.setPlace("Greenwich");
        excursion1.setPrice(BigDecimal.valueOf(100));
        excursion1.setExcursionType(ExcursionType.CULTURE);

        excursion2.setCreated(created);
        excursion2.setDescription("The best excursion");
        excursion2.setDurationInHours(30);
        excursion2.setFromDate(from);
        excursion2.setPlace("Wien");
        excursion2.setPrice(BigDecimal.valueOf(200));
        excursion2.setExcursionType(ExcursionType.ENTERTAINMENT);
        
        excursion3.setCreated(created);
        excursion3.setDescription("Football Match");
        excursion3.setDurationInHours(3);
        excursion3.setFromDate(from);
        excursion3.setPlace("Berlin");
        excursion3.setPrice(BigDecimal.valueOf(50));
        excursion3.setExcursionType(ExcursionType.ENTERTAINMENT);
        excursionService.createExcursion(excursion1);
        excursionService.createExcursion(excursion2);
        excursionService.createExcursion(excursion3);


        Set<Excursion> excursions = new HashSet<>();
        excursions.add(excursion1);
        excursions.add(excursion2);

        trip.setPossibleExcursions(excursions);
        tripService.updateTrip(trip);
        
        testReservation = new Reservation();
        testReservation.setCustomer(customer1);
        testReservation.setExcursions(excursions);
        testReservation.setTrip(trip);
        testReservation.setCreated(created);
        reservationService.createReservation(testReservation);
    }
}
