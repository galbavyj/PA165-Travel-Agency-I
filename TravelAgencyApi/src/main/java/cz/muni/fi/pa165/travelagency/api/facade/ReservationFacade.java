package cz.muni.fi.pa165.travelagency.api.facade;

import cz.muni.fi.pa165.travelagency.api.dto.ReservationDTO;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author Juraj
 */
public interface ReservationFacade {


    /**
     * Create reservation
     * @param reservation DTO of reservation to be created
     * @return id of created reservation
     */
    Long createReservation(ReservationDTO reservation);

    /**
     * Update reservation
     * @param reservation DTO of reservation to be updated
     */
    void updateReservation(ReservationDTO reservation);

    /**
     * Delete reservation
     * @param reservation DTO of reservation to be removed
     */
    void removeReservation(ReservationDTO reservation);

    /**
     * Add excursion to reservation
     * @param reservationId Id of reservation
     * @param excursionId Id of excursion to be added to reservation
     */
    void addExcursion(Long reservationId, Long excursionId);

    /**
     * Get All reservations
     * @return List of reservations
     */
    List<ReservationDTO> findAllReservations();

    /**
     * Get DTO of reservation by Id
     * @param reservationId
     * @return DTO of reservation
     */
    ReservationDTO findReservationsById(Long reservationId);

    /**
     * Get reservations by trip
     * @param tripId Id of trip
     * @return  List of reservations
     */
    List<ReservationDTO> findReservationsByTrip(Long tripId);

    /**
     * Get reservations by Customer
     * @param customerId customer Id
     * @return list of reservations
     */
    List<ReservationDTO> findReservationsByCustomer(Long customerId);

    /**
     * Total price of reservation
     *
     * @param Id Id of reservation
     * @return total price of specified reservation
     */
    BigDecimal getTotalPriceOfReservation(Long Id);
}
