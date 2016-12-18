package facade;

import cz.muni.fi.pa165.travelAgency.persistence.entity.Address;
import cz.muni.fi.pa165.travelAgency.persistence.entity.Excursion;
import cz.muni.fi.pa165.travelAgency.persistence.entity.Trip;
import cz.muni.fi.pa165.travelagency.api.dto.AddressDTO;
import cz.muni.fi.pa165.travelagency.api.dto.ExcursionDTO;
import cz.muni.fi.pa165.travelagency.api.dto.TripCreateDTO;
import cz.muni.fi.pa165.travelagency.api.dto.TripDTO;
import cz.muni.fi.pa165.travelagency.api.facade.TripFacade;
import cz.muni.fi.pa165.travelagency.travelagencyservice.MappingService;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
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
    
    @Inject
    private MappingService mappingService;
    
    @Inject
    private TripService tripService;
    
    @Override
    public void createTrip(TripCreateDTO tripCreateDTO) {
        Trip trip = mappingService.mapTo(tripCreateDTO, Trip.class);
        Address address = new Address();
        address.setCountry(tripCreateDTO.getCountry());
        address.setCity(tripCreateDTO.getCity());
        address.setStreet(tripCreateDTO.getStreet());
        address.setNumberOfHouse(tripCreateDTO.getNumberOfHouse());
        
        trip.setAddressOfHotel(address);
        trip.setCreatedDate(new Date());
        trip.setFromDate(trip.getFromDate());
        trip.setToDate(trip.getToDate());
        
        tripService.createTrip(trip);
    }

    @Override
    public void removeTrip(TripDTO trip) {
        Trip tr = new Trip();
        tr = mappingService.mapTo(trip, Trip.class);
        
        tripService.removeTrip(tr);
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
    
    public void addExcursionToTrip(TripDTO trip, ExcursionDTO excursion){
        tripService.addExcursionToTrip(mappingService.mapTo(trip, Trip.class), mappingService.mapTo(excursion, Excursion.class));
    }

    
}
