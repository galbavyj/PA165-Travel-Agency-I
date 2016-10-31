package cz.muni.fi.pa165.travelAgency.persistence.dao;

import cz.muni.fi.pa165.travelAgency.persistence.entity.Trip;
import java.util.List;

/**
 *
 * @author Patrik Behrami
 */
public interface TripDao {
    /**
     * Add new trip into database
     *
     * @param trip trip to be added into database
     */
    public void create(Trip trip);

    /**
     * Removes trip from database
     *
     * @param trip trip to be removed from database
     * @throws IllegalArgumentException in case trip is not found
     */
    public void remove(Trip trip);

    /**
     * Add new trip into database
     *
     * @param trip trip to be added into database
     * @return updated Trip
     * @throws IllegalArgumentException in case trip is not found
     */
    public Trip update(Trip trip);

    
    /**
     * Finds Trip by id
     * 
     * @param id of a Trip we are looking for
     * @return Trip with specified id
     */
    public Trip findById(Long id);
    
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

    
}
