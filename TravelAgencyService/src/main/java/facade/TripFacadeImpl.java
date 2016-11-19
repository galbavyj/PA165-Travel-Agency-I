package facade;

import cz.muni.fi.pa165.travelAgency.persistence.entity.Trip;
import cz.muni.fi.pa165.travelagency.api.dto.TripDTO;
import cz.muni.fi.pa165.travelagency.api.facade.TripFacade;
import cz.muni.fi.pa165.travelagency.travelagencyservice.MappingService;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.TripService;

/**
 *
 * @author Patrik Behrami
 */
@Service
@Transactional
public class TripFacadeImpl implements TripFacade {
    
    @Autowired
    private MappingService mappingService;
    
    @Autowired
    private TripService tripService;
    
    @Override
    public void createTrip(TripDTO trip) {
        tripService.createTrip(mappingService.mapTo(trip, Trip.class));
    }

    @Override
    public void removeTrip(TripDTO trip) {
        tripService.removeTrip(mappingService.mapTo(trip, Trip.class));
    }

    @Override
    public void updateTrip(TripDTO trip) {
        tripService.updateTrip(mappingService.mapTo(trip, Trip.class));
    }

    @Override
    public TripDTO findTripById(Long id) {
        Trip trip = tripService.findTripById(id);
        if (trip == null)
            return null;
        else 
            return mappingService.mapTo(trip, TripDTO.class);
    }

    @Override
    public List<TripDTO> findAllTrips() {
        return mappingService.mapTo(tripService.findAllTrips(), TripDTO.class);
    }

    @Override
    public List<TripDTO> findTripsByCountry(String country) {
        return mappingService.mapTo(tripService.findTripsByCountry(country), TripDTO.class);
    }

    @Override
    public void changePrice(TripDTO trip, BigDecimal price) {
        tripService.changePrice(mappingService.mapTo(trip, Trip.class), price);
    }
    
}
