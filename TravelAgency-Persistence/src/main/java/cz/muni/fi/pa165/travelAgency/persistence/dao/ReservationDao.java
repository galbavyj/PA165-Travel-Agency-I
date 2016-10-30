package cz.muni.fi.pa165.travelAgency.persistence.dao;

import cz.muni.fi.pa165.travelAgency.persistence.entity.Reservation;
import cz.muni.fi.pa165.travelAgency.persistence.entity.Trip;
import cz.muni.fi.pa165.travelAgency.persistence.entity.Customer;

import java.util.List;

/**
 * @author Juraj Galbavý
 */
public interface ReservationDao {

    /**
     * Adds new Reservation to database
     *
     * @param reservation reservation to be added into database
     */
    public void create(Reservation reservation);

    /**
     * Update Reservation in database
     *
     * @param reservation reservation to be updated
     * @return updated Reservation
     */
    public Reservation update(Reservation reservation);

    /**
     * Removes Reservation from database
     *
     * @param reservation reservation to be removed from database
     */
    public void remove(Reservation reservation);

    /**
     * Find all Reservations
     *
     * @return List of Reservations
     */
    public List<Reservation> findAllReservations();

    /**
     * Find Reservation by {@link Customer}
     * @param user user
     * @return List of Reservations
     */
    public List<Reservation> findReservationsByUser(Customer user);

    /**
     * Find Reservation by {@link Trip}
     * @param trip trip
     * @return List of Reservations
     */
    public List<Reservation> findReservationsByTrip(Trip trip);

    /**
     * Find Reservation by id
     * @param id id of reservation
     * @return reservation
     */
    public Reservation findById(Long id);



}
