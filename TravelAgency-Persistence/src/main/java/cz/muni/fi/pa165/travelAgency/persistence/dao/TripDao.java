/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.travelAgency.persistence.dao;

import cz.muni.fi.pa165.travelAgency.persistence.entity.Trip;
import java.util.List;
import java.util.Set;

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
    public void delete(Trip trip);

    /**
     * Add new trip into database
     *
     * @param trip trip to be added into database
     * @return updated Trip
     * @throws IllegalArgumentException in case trip is not found
     */
    public Trip update(Trip trip);

    
    /**
     * Find all trips
     *
     * @return All trips in a List
     */
    public List<Trip> findAllTrips();
   
    /**
     * Finds Trip by id
     * 
     * @param id of a trip we are looking for
     * @return founded Trip
     */
    public Trip findById (Long id);
    
    
    /**
     * Find all trips that are taking place in a certain country
     * 
     * @param countryName Name of the country we want to find Trips to.
     * @return List of Trips available in demanded country
     */
    public List<Trip> findTripsByCountry(String countryName);

    
}
