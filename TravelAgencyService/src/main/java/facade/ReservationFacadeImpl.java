package facade;

import cz.muni.fi.pa165.travelAgency.persistence.entity.Excursion;
import cz.muni.fi.pa165.travelAgency.persistence.entity.Reservation;
import cz.muni.fi.pa165.travelagency.api.dto.ReservationDTO;
import cz.muni.fi.pa165.travelagency.api.facade.ReservationFacade;
import cz.muni.fi.pa165.travelagency.travelagencyservice.MappingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.CustomerService;
import service.ExcursionService;
import service.ReservationService;
import service.TripService;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

/**
 * @Author Juraj
 */
@Service
@Transactional
public class ReservationFacadeImpl implements ReservationFacade {

    @Inject
    private MappingService beanMappingService;

    @Inject
    private ReservationService reservationService;

    @Inject
    private CustomerService customerService;

    @Inject
    private TripService tripService;

    @Inject
    private ExcursionService excursionService;


    @Override
    public void createReservation(ReservationDTO reservationDTO) {
        reservationService.createReservation(beanMappingService.mapTo(reservationDTO, Reservation.class));
    }

    @Override
    public void updateReservation(ReservationDTO reservationDTO) {
        reservationService.updateReservation(beanMappingService.mapTo(reservationDTO, Reservation.class));
    }

    @Override
    public void removeReservation(ReservationDTO reservationDTO) {
        reservationService.removeReservation(beanMappingService.mapTo(reservationDTO, Reservation.class));
    }

    @Override
    public void addExcursion(Long reservationId, Long excursionId) {
        Reservation reservation = reservationService.findReservationById(reservationId);
        Excursion excursion = excursionService.findExcursionById(excursionId);
        reservationService.addExcursionToReservation(reservation, excursion);
    }

    @Override
    public List<ReservationDTO> findAllReservations() {
        return beanMappingService.mapTo(
                reservationService.findAllReservations(), ReservationDTO.class);
    }

    @Override
    public ReservationDTO findReservationsById(Long reservationId) {
        return beanMappingService.mapTo(
                reservationService.findReservationById(reservationId), ReservationDTO.class);
    }

    @Override
    public List<ReservationDTO> findReservationsByTrip(Long tripId) {
        return beanMappingService.mapTo(
                reservationService.findReservationsByTrip(
                        tripService.findTripById(tripId)), ReservationDTO.class);
    }

    @Override
    public List<ReservationDTO> findReservationsByCustomer(Long customerId) {
        return beanMappingService.mapTo(
                reservationService.findReservationsByCustomer(
                        customerService.findById(customerId)), ReservationDTO.class);
    }

    @Override
    public BigDecimal getTotalPriceOfReservation(Long reservationId) {
        Reservation reservation = reservationService.findReservationById(reservationId);

        return reservationService.getTotalPrice(reservation);
    }
}
