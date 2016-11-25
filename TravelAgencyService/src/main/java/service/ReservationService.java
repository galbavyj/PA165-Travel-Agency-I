package service;


import cz.muni.fi.pa165.travelAgency.persistence.entity.Customer;
import cz.muni.fi.pa165.travelAgency.persistence.entity.Excursion;
import cz.muni.fi.pa165.travelAgency.persistence.entity.Reservation;
import cz.muni.fi.pa165.travelAgency.persistence.entity.Trip;

import java.math.BigDecimal;
import java.util.List;

/**
 * Service for reservation
 *
 * @author Juraj
 */
public interface ReservationService {

    /**
     * Create new reservation
     * @param reservation reservation to be created
     * @return created reservation
     */
    Reservation createReservation(Reservation reservation);

    /**
     * Update new reservation
     * @param reservation reservation to be updated
     * @return updated reservation
     */
    Reservation updateReservation(Reservation reservation);

    /**
     * Delete reservation
     * @param reservation reservation to be delete
     */
    void removeReservation(Reservation reservation);

    /**
     * Add excursion to reservation
     * @param reservation reservation
     * @param excursion excursion to be added to reservation
     */
    void addExcursionToReservation(Reservation reservation, Excursion excursion);

    /**
     * Find all reservation
     * @return List of all reservations
     */
    List<Reservation> findAllReservations();

    /**
     * Find reservation by Id
     * @param id id of reservation
     * @return reservation with wanted ID
     */
    Reservation findReservationById(Long id);

    /**
     * Find Reservation by {@link Customer}
     * @param customer customer
     * @return List of Reservations
     */
    List<Reservation> findReservationsByCustomer(Customer customer);

    /**
     * Find Reservation by {@link Trip}
     * @param trip trip
     * @return List of Reservations
     */
    List<Reservation> findReservationsByTrip(Trip trip);


    /**
     * Get price of whole reservation
     * @param reservation
     * @return total price of reservation
     */
    BigDecimal getTotalPrice(Reservation reservation);
}
