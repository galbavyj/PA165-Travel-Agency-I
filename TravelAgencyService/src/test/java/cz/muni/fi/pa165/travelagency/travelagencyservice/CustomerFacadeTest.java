package cz.muni.fi.pa165.travelagency.travelagencyservice;

import cz.muni.fi.pa165.travelAgency.persistence.entity.Address;
import cz.muni.fi.pa165.travelAgency.persistence.entity.Customer;
import cz.muni.fi.pa165.travelagency.api.dto.AddressDTO;
import cz.muni.fi.pa165.travelagency.api.dto.CustomerAuthenticateDTO;
import cz.muni.fi.pa165.travelagency.api.dto.CustomerDTO;
import cz.muni.fi.pa165.travelagency.api.enums.CustomerRole;
import cz.muni.fi.pa165.travelagency.api.facade.CustomerFacade;
import facade.CustomerFacadeImpl;
import org.hibernate.service.spi.ServiceException;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import service.CustomerService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.*;

/**
 * @author Juraj
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class CustomerFacadeTest {

    @Mock
    private CustomerService customerService;

    @Mock
    private MappingService mappingService;


    @InjectMocks
    private CustomerFacade customerFacade = new CustomerFacadeImpl();

    private Address addressCustomer2, addressCustomer1;
    private Customer customer2, customer1;
    private AddressDTO addressDTOCustomer1, addressDTOCustomer2;
    private CustomerDTO customerDTO1, customerDTO2;
    private CustomerAuthenticateDTO customerAuthenticateDTO1;
    private List<Customer> customers;
    private List<CustomerDTO> customersDTO;

    private String pass;

    @BeforeClass
    public void setUpClass() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void setup() {
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

        addressDTOCustomer2 = new AddressDTO();
        addressDTOCustomer2.setCity("Brno");
        addressDTOCustomer2.setCountry("Česko");
        addressDTOCustomer2.setNumberOfHouse(16);
        addressDTOCustomer2.setStreet("Oblokova");

        addressDTOCustomer1 = new AddressDTO();
        addressDTOCustomer1.setCity("Prievidza");
        addressDTOCustomer1.setCountry("Slovensko");
        addressDTOCustomer1.setNumberOfHouse(10);
        addressDTOCustomer1.setStreet("Dolná");

        addressCustomer1 = new Address();
        addressCustomer1.setCity("Prievidza");
        addressCustomer1.setCountry("Slovensko");
        addressCustomer1.setNumberOfHouse(10);
        addressCustomer1.setStreet("Dolná");
        pass = "Tralala/ZloziteHeslo12345";

        customer1 = new Customer();
        customer1.setId(10l);
        customer1.setAddress(addressCustomer1);
        customer1.setFirstName("Dano");
        customer1.setLastName("Drevo");
        customer1.setEmail("dano.drevo@centrum.cz");
        customer1.setPhoneNumber("+420566487744");
        customer1.setCustomerRole(CustomerRole.CUSTOMER);
        customer1.setCreated(created1);

        customerDTO1 = new CustomerDTO();
        customerDTO1.setId(10l);
        customerDTO1.setAddress(addressDTOCustomer1);
        customerDTO1.setFirstName("Dano");
        customerDTO1.setLastName("Drevo");
        customerDTO1.setEmail("dano.drevo@centrum.cz");
        customerDTO1.setPhoneNumber("+420566487744");
        customerDTO1.setcustomerRole(CustomerRole.CUSTOMER);
        customerDTO1.setCreated(created1);

        customerAuthenticateDTO1 = new CustomerAuthenticateDTO();
        customerAuthenticateDTO1.setPasswordHash(pass);
        customerAuthenticateDTO1.setId(10L);

        customerDTO2 = new CustomerDTO();
        customerDTO2.setAddress(addressDTOCustomer2);
        customerDTO2.setFirstName("Albert");
        customerDTO2.setLastName("Forest");
        customerDTO2.setEmail("albert.forest@centrum.cz");
        customerDTO2.setPhoneNumber("+420756847374");
        customerDTO2.setcustomerRole(CustomerRole.ADMIN);
        customerDTO2.setCreated(created2);

        customer2 = new Customer();
        customer2.setAddress(addressCustomer2);
        customer2.setFirstName("Albert");
        customer2.setLastName("Forest");
        customer2.setEmail("albert.forest@centrum.cz");
        customer2.setPhoneNumber("+420756847374");
        customer2.setCustomerRole(CustomerRole.ADMIN);
        customer2.setCreated(created2);

        customers = new ArrayList<>();
        customersDTO = new ArrayList<>();

        customers.add(customer1);
        customers.add(customer2);

        customersDTO.add(customerDTO1);
        customersDTO.add(customerDTO2);
    }


    @Test
    public void testRegisterCustomer(){
        when(mappingService.mapTo(customerDTO1, Customer.class)).thenReturn(customer1);
        customerFacade.registerCustomer(customerDTO1, pass);
        verify(customerService).registerCustomer(customer1, pass);
    }

    @Test
    public void testAuthenticateCustomer(){
        when(customerService.findById(customer1.getId())).thenReturn(customer1);
        customerFacade.authenticateCustomer(customerAuthenticateDTO1);
        verify(customerService).authenticateCustomer(customer1, pass);
    }


    @Test
    public void testUpdateCustomer(){
        when(mappingService.mapTo(customerDTO1, Customer.class)).thenReturn(customer1);
        customerFacade.updateCustomer(customerDTO1);
        verify(customerService).updateCustomer(customer1);
    }

    @Test
    public void testRemoveCustomer(){
        when(mappingService.mapTo(customerDTO1, Customer.class)).thenReturn(customer1);
        customerFacade.removeCustomer(customerDTO1);
        verify(customerService).removeCustomer(customer1);
    }

    @Test
    public void testFindAllCustomers(){
        when(customerService.findAll()).thenReturn(customers);
        when(mappingService.mapTo(Matchers.anyCollection(), Matchers.eq(CustomerDTO.class))).thenReturn(customersDTO);
        assertEquals(customerFacade.findAllCustomers().size(), 2);
    }

    @Test
    public void testFindCustomerByEmail(){
        when(customerService.findByEmail("dano.drevo@centrum.cz")).thenReturn(customer1);
        when(mappingService.mapTo(customer1, CustomerDTO.class)).thenReturn(customerDTO1);
        assertEquals(customerFacade.findCustomerByEmail("dano.drevo@centrum.cz"), customerDTO1);
    }

    @Test
    public void testFindCustomerById(){
        when(customerService.findById(10l)).thenReturn(customer1);
        when(mappingService.mapTo(customer1, CustomerDTO.class)).thenReturn(customerDTO1);
        assertEquals(customerFacade.findCustomerById(10l), customerDTO1);
    }

}