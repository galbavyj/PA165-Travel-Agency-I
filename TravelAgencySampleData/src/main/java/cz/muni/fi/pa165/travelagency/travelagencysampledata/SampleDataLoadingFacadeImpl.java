package cz.muni.fi.pa165.travelagency.travelagencysampledata;


import cz.muni.fi.pa165.travelAgency.persistence.entity.Address;
import cz.muni.fi.pa165.travelAgency.persistence.entity.Customer;
import cz.muni.fi.pa165.travelagency.api.enums.CustomerRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import javax.inject.Inject;
import service.CustomerService;


@Component
@Transactional 
public class SampleDataLoadingFacadeImpl implements SampleDataLoadingFacade {

    final static Logger log = LoggerFactory.getLogger(SampleDataLoadingFacadeImpl.class);

    @Inject
    private CustomerService customerService;
    
    private Address addressCustomer2, addressCustomer1, tripAddress;
    private Customer admin, customer1;

    @Override
    public void loadData() throws IOException {
        setupCustomers();
    }
    
    private void setupCustomers(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(2017, Calendar.JANUARY, 10, 8, 16);
        java.util.Date created1 = calendar.getTime();

        calendar.set(2017, Calendar.FEBRUARY, 10, 8, 16);
        java.util.Date created2 = calendar.getTime();

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

        admin = new Customer();
        admin.setAddress(addressCustomer2);
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
}
