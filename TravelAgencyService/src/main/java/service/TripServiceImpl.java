package service;

import cz.muni.fi.pa165.travelAgency.persistence.dao.TripDao;
import cz.muni.fi.pa165.travelAgency.persistence.entity.Trip;
import java.math.BigDecimal;
import java.util.List;
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
    
    @Override
    public void createTrip(Trip trip) {
        tripDao.create(trip);
    }

    @Override
    public void removeTrip(Trip trip) {
        tripDao.remove(trip);
    }

    @Override
    public Trip updateTrip(Trip trip) {
        Trip updatedTrip = tripDao.update(trip);
        return updatedTrip;
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

   
}
