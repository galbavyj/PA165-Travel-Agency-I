/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.travelAgency.persistence.dao;

import cz.muni.fi.pa165.travelAgency.persistence.entity.Excursion;
import cz.muni.fi.pa165.travelAgency.persistence.entity.Reservation;
import java.util.Set;

/**
 *
 * @author Lucia
 */
public interface ExcursionDao {
    /**
     * Adds new excursion into database
     *
     * @param excursion new excursion to database
     */
    public void create(Excursion excursion);

    /**
     * Deletes excursion from database
     *
     * @param excursionId ID of excursion, which will be removed from DB
     * @throws IllegalArgumentException in case ID is not found
     */
    public void remove(Long excursionId);

    /**
     * Edit the excursion in the database
     *
     * @param excursion excursion which will be changed
     * @return updated Excursion
     * @throws IllegalArgumentException in case Excursion is not found
     */
    public Excursion update(Excursion excursion);

    
    /**
     * Find all excursions
     *
     * @return all excursions
     */
    public Set<Excursion> findAllExcursions();
    
    /**
     * List all excursions in given Trip
     *
     * @param trip trip for which we want to find excursions
     * @return all Excursions in Trip
     * @throws IllegalArgumentException in case trip is not found
     */
    //public Set<Excursion> findAllExcursionsInTrip(Trip trip);
    
    /**
     * Find all excursions in a reservation
     *
     * @param reservation Reservation for which we want to find the excursions
     * @return all excursions in reservation
     */
    public Set<Excursion> findAllExcursionsInReservation(Reservation reservation);

    
}
