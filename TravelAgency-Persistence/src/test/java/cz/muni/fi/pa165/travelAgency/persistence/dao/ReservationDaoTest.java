/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.travelAgency.persistence.dao;

import cz.muni.fi.pa165.travelAgency.persistence.entity.Address;
import cz.muni.fi.pa165.travelAgency.persistence.entity.Customer;
import cz.muni.fi.pa165.travelAgency.persistence.entity.Excursion;
import cz.muni.fi.pa165.travelAgency.persistence.entity.Reservation;
import cz.muni.fi.pa165.travelAgency.persistence.entity.Trip;
import enums.UserRole;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author Patrik Behrami
 */
public class ReservationDaoTest {
    @Autowired
    public UserDao userDao;
    
    @Autowired
    public TripDao tripDao;
    
    @Autowired
    public ExcursionDao excursionDao;
    
    @Autowired
    public ReservationDao reservationDao;
    
    private Customer u1,u2;
    private Reservation r1,r2;
    private Trip t1;
    private Excursion e1;
    private Address a1;
    private Address a2;
    
    @BeforeMethod 
    public void prepare() throws ParseException{
        a1 = new Address();
        a1.setCountry("Czech Republic");
        a1.setCity("Brno");
        a1.setStreet("Lidicka");
        a1.setNumberOfHouse(3);
        
        a2 = new Address();
        a2.setCountry("Albania");
        a1.setCity("Tirane");
        a1.setStreet("Rruga Myslym Shyri");
        a1.setNumberOfHouse(10);
        
        Calendar cal = Calendar.getInstance();
        cal.set(2016, 1, 1);
        Date date1 = cal.getTime();
        
        u1 = new Customer();
        u1.setAddress(a1);
        u1.setFirstName("Milan");
        u1.setLastName("Peterka");
        u1.setEmail("milan.peterka@seznam.cz");
        u1.setPhoneNumber("+420777852974");
        u1.setUserRole(UserRole.CUSTOMER);
        u1.setCreated(date1);
        
        cal.set(2016, 2, 1);
        date1 = cal.getTime();
        
        u2 = new Customer();
        u2.setAddress(a1);
        u2.setFirstName("Petr");
        u2.setLastName("Petrenec");
        u2.setEmail("petr.petrenec@seznam.cz");
        u2.setPhoneNumber("+420777842974");
        u2.setUserRole(UserRole.CUSTOMER);
        u2.setCreated(date1);
        
        cal.set(2016, 10, 29);
        Date date2 = cal.getTime();
        
        e1 = new Excursion();
        e1.setCreated(date2);
        e1.setFrom((Date) new SimpleDateFormat("dd/MM/yyyy").parse("05/11/2016"));
        e1.setDuration(2);
        e1.setPlace("National Science Museum in Tirane");
        e1.setDescription("Visit the biggest museum in whole Albania");
        e1.setPrice(2000);
        
        Set<Excursion> excursionSet = new HashSet<>();
        excursionSet.add(e1);
        
        cal.set(2016, 10, 15);
        Date date3 = cal.getTime();
       
        t1 = new Trip();
        t1.setAddressOfHotel(a2);
        t1.setCreated((Date) new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2016"));
        t1.setFrom((Date) new SimpleDateFormat("dd/MM/yyyy").parse("31/10/2016"));
        t1.setTo((Date) new SimpleDateFormat("dd/MM/yyyy").parse("10/11/2016"));
        t1.setPrice(BigDecimal.TEN);
        

        cal.set(2016, 10, 20);
        Date date4 = cal.getTime();
        
        r1 = new Reservation();
        r1.setCreated(date4);
        r1.setExcursions(excursionSet);
        r1.setTrip(t1);
        r1.setCustomer(u1);
        
        
        userDao.create(u1);
     //   tripDao.create(t1);
    }
    
    @Test
    public void create(){
        reservationDao.create(r1);
    }
}
