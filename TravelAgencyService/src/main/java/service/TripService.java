package service;

import cz.muni.fi.pa165.travelAgency.persistence.entity.Excursion;
import cz.muni.fi.pa165.travelAgency.persistence.entity.Trip;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Patrik Behrami
 */
public interface TripService {
    /**
     * Create new Trip
     *
     * @param trip Trip to be created
     */
    void createTrip(Trip trip);

    /**
     * Removes Trip
     *
     * @param trip Trip to be removed
     */
    void removeTrip(Trip trip);

    /**
     * Updates Trip
     *
     * @param trip trip to be updated
     * @return updated Trip
     */
    Trip updateTrip(Trip trip);

    
    /**
     * Finds Trip by specified id
     * 
     * @param id of a Trip we are looking for
     * @return Trip with specified id
     */
    public Trip findTripById(Long id);
    
    /**
     * Find all trips
     *
     * @return All trips in a List
     */
    public List<Trip> findAllTrips();
   
    /**
     * Find all trips that are taking place in a certain country
     * 
     * @param countryName Name of the country we want to find Trips to.
     * @return List of Trips available in demanded country
     */
    public List<Trip> findTripsByCountry(String countryName);
    
    /**
     * Changes price of specified Trip
     * 
     * @param trip Trip to have its price changed
     * @param price New price
     */
    public void changePrice(Trip trip, BigDecimal price);

    /**
     * Adds possible Excursion to a list of possible Excursions on a Trip.
     * 
     * @param trip Trip, whose list of possible Excursions should be updated
     * @param excursion Excursion to add to list
     */
    public void addExcursionToTrip(Trip trip,Excursion excursion);
}
