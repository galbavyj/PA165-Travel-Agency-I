package cz.muni.fi.pa165.travelagency.api.facade;

import cz.muni.fi.pa165.travelagency.api.dto.ExcursionDTO;
import cz.muni.fi.pa165.travelagency.api.dto.TripCreateDTO;
import cz.muni.fi.pa165.travelagency.api.dto.TripDTO;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Patrik Behrami
 */
public interface TripFacade {
    /**
     * Create new Trip.
     * 
     * @param trip DTO of Trip to be created
     */
    public void createTrip(TripCreateDTO trip);
    
    /**
     * Remove Trip.
     * 
     * @param trip DTO of Trip to be removed
     */
    public void removeTrip(TripDTO trip);
    
    /**
     * Update Trip.
     * 
     * @param trip DTO of Trip to be updated
     */
    public void updateTrip(TripDTO trip);
    
    /**
     * Find Trip with certain id.
     * 
     * @param id Id of a Trip to be found.
     * @return DTO of Trip with the id.
     */
    public TripDTO findTripById(Long id);
    
    /**
     * Find all Trips.
     * 
     * @return List of all Trips.
     */
    public List<TripDTO> findAllTrips();
    
    /**
     * Find all Trips to a certain country
     * 
     * @param country Name of a country 
     * @return List of Trips to a certain country
     */
    public List<TripDTO> findTripsByCountry(String country);
    
    /**
     * Change price of a Trip
     * 
     * @param trip DTO of a Trip whose price is going to get changed
     * @param price New price
     */
    public void changePrice(TripDTO trip, BigDecimal price);
    
    /**
     * Add possible Excursion to a list of possible Excursion no a Trip
     * 
     * @param trip DTO of a Trip whose list of possible Excursions is to be updated
     * @param excursion DTO of and Excursion to be added to list of possible Excursions on trip
     */
    public void addExcursionToTrip(TripDTO trip, ExcursionDTO excursion);
    
}
