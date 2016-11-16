package cz.muni.fi.pa165.travelagency.api.facade;

import cz.muni.fi.pa165.travelagency.api.dto.CustomerDTO;
import cz.muni.fi.pa165.travelagency.api.dto.ReservationCreateDTO;
import cz.muni.fi.pa165.travelagency.api.dto.ReservationDTO;
import cz.muni.fi.pa165.travelagency.api.dto.ReservationTotalPriceDTO;

import java.util.List;

/**
 * @author Juraj
 */
public interface ReservationFacade {


    /**
     * Create reservation
     * @param reservation reservation to be created
     * @return
     */
    Long createReservation(ReservationCreateDTO reservation);

    void updateReservation(ReservationDTO reservation);

    void removeReservation(Long reservationId);

    void addExcursion(Long reservationId, Long excursionId);

    List<ReservationDTO> getAllReservations();

    ReservationDTO getReservationsById(Long reservationId);

    List<ReservationDTO> getReservationsByTrip(Long tripId);

    List<ReservationDTO> getReservationsByCustomer(Long customerId);


    /**
     * Total price of reservation
     *
     * @param Id Id of reservation
     * @return total price of specified reservation
     */
    ReservationTotalPriceDTO getTotalPriceOfReservation(Long Id);
}
