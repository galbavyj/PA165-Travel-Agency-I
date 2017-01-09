package service;

import cz.muni.fi.pa165.travelAgency.persistence.dao.ExcursionDao;
import cz.muni.fi.pa165.travelAgency.persistence.dao.ReservationDao;
import cz.muni.fi.pa165.travelAgency.persistence.dao.TripDao;
import cz.muni.fi.pa165.travelAgency.persistence.entity.Excursion;
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
    public Trip createTrip(Trip trip) {
        tripDao.create(trip);
        return trip;

    }

    @Override
    public void removeTrip(Trip trip) {
        if (reservationDao.findReservationsByTrip(trip).size() > 0){
            throw new IllegalStateException("Can't delete trips which are in active reservations");
        }

        trip.deleteAllPossibleExcursions();
        tripDao.update(trip);

        tripDao.remove(trip);
    }

    @Override
    public Trip updateTrip(Trip trip) {
        return tripDao.update(trip);  
    }

    @Override
    public Trip findTripById(Long id) {
        return tripDao.findById(id);
    }

    @Override
    public List<Trip> findAllTrips() {
        return tripDao.findAllTrips();
    }

    @Override
    public List<Trip> findTripsByCountry(String countryName) {
        return tripDao.findTripsByCountry(countryName);
    }

    @Override
    public void changePrice(Trip trip, BigDecimal price) {
            trip.setPrice(price);
            tripDao.update(trip);
    }

    @Override
    public void addExcursionToTrip(Trip trip,Excursion excursion){
            trip.addPossibleExcursion(excursion);
            tripDao.update(trip);
            excursionDao.update(excursion);
    }
}
