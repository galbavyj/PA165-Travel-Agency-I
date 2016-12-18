package service;

import cz.muni.fi.pa165.travelAgency.persistence.dao.ExcursionDao;
import cz.muni.fi.pa165.travelAgency.persistence.dao.ReservationDao;
import cz.muni.fi.pa165.travelAgency.persistence.dao.TripDao;
import cz.muni.fi.pa165.travelAgency.persistence.entity.Excursion;
import cz.muni.fi.pa165.travelAgency.persistence.entity.Reservation;
import cz.muni.fi.pa165.travelAgency.persistence.entity.Trip;
import cz.muni.fi.pa165.travelagency.travelagencyservice.TravelAgencyPersistenceException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import org.springframework.stereotype.Service;

/**
 *
 * @author Patrik Behrami
 */
@Service
public class TripServiceImpl implements TripService {

    @Inject
    TripDao tripDao;
    
    @Inject
    ExcursionDao excursionDao;
    
    @Inject
    ReservationDao reservationDao;
    
    @Inject 
    ReservationService reservationService;
    
    @Override
    public void createTrip(Trip trip) {
        try{
            tripDao.create(trip);
        }
        catch(Exception ex){
            throw new TravelAgencyPersistenceException("Failed to create trip" + ex.getMessage(), ex.getCause());
        }   
    }

    @Override
    public void removeTrip(Trip trip) {
        if (reservationDao.findReservationsByTrip(trip).size() > 0){
            throw new IllegalStateException("Can't delete trips which are in active reservations");
        }
        
        try{
            Set<Excursion> excursions = trip.getPossibleExcursions();
            
            for (Excursion e : excursions){
                e.setTrip(null);
                excursionDao.update(e);
                trip.removePossibleExcursion(e);
                tripDao.update(trip);
            }
            
            trip.deleteAllPossibleExcursions();
            tripDao.update(trip);
            
            tripDao.remove(trip);
        }
        catch(Exception ex){
            throw new TravelAgencyPersistenceException("Failed to remove trip"  + ex.getMessage(), ex.getCause());
        }   
    }

    @Override
    public Trip updateTrip(Trip trip) {
        try{
            return tripDao.update(trip);
        }
        catch(Exception ex){
            throw new TravelAgencyPersistenceException("Failed to update trip"  + ex.getMessage(), ex.getCause());
        }   
    }

    @Override
    public Trip findTripById(Long id) {
        try{
            return tripDao.findById(id);
        }
        catch(Exception ex){
            throw new TravelAgencyPersistenceException("Failed to find trip by id"  + ex.getMessage(), ex.getCause());
        }   
    }

    @Override
    public List<Trip> findAllTrips() {
        try{
            return tripDao.findAllTrips();
        }
        catch(Exception ex){
            throw new TravelAgencyPersistenceException("Failed to find all trips" + ex.getMessage(), ex.getCause());
        }  
    }

    @Override
    public List<Trip> findTripsByCountry(String countryName) {
        try{
            return tripDao.findTripsByCountry(countryName);
        }
        catch(Exception ex){
            throw new TravelAgencyPersistenceException("Failed to find trip by country name " + countryName + ex.getMessage(), ex.getCause());
        }  
    }

    @Override
    public void changePrice(Trip trip, BigDecimal price) {
        try{
             trip.setPrice(price);
             tripDao.update(trip);
        }
        catch(Exception ex){
            throw new TravelAgencyPersistenceException("Failed to change price at trip " + trip.toString() + ex.getMessage(), ex.getCause());
        }  
    }

    @Override
    public void addExcursionToTrip(Trip trip,Excursion excursion){
        try{
            trip.addPossibleExcursion(excursion);
            //excursion.setTrip(trip);
            tripDao.update(trip);
            excursionDao.update(excursion);
        }
        catch(Exception ex){
            throw new TravelAgencyPersistenceException("Failed to add possible excursion " + excursion.toString() + "to trip " + trip.toString() + ex.getMessage(), ex.getCause());
        }
    }
}
