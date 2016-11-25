package facade;

import cz.muni.fi.pa165.travelAgency.persistence.entity.Customer;
import cz.muni.fi.pa165.travelAgency.persistence.entity.Excursion;
import cz.muni.fi.pa165.travelAgency.persistence.entity.Reservation;
import cz.muni.fi.pa165.travelAgency.persistence.entity.Trip;
import cz.muni.fi.pa165.travelagency.api.dto.ExcursionDTO;
import cz.muni.fi.pa165.travelagency.api.dto.ReservationCreateDTO;
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
import java.util.Date;
import java.util.HashSet;
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
    public Long createReservation(ReservationCreateDTO r) {

        Customer customer = customerService.findById(r.getCustomerId());
        if (customer == null){
            throw new IllegalArgumentException("customer does not exist");
        }

        Trip trip = tripService.findTripById(r.getTripId());
        if (trip == null){
            throw new IllegalArgumentException("trip does not exist");
        }

        Reservation reservation = new Reservation(customer, new Date(), trip, new HashSet<>());

        if (r.getExcursionIDs() != null) {
            for (Long excursionId : r.getExcursionIDs()) {
                Excursion ex = excursionService.findExcursionById(excursionId);
                reservation.addExcursion(ex);
            }
        }
        reservationService.createReservation(reservation);

        return reservation.getId();
    }


    @Override
    public void updateReservation(ReservationDTO reservationDTO) {

        Reservation reservation = beanMappingService.mapTo(reservationDTO, Reservation.class);

        reservation.setCustomer(beanMappingService.mapTo(reservationDTO.getCustomer(), Customer.class));
        reservation.setTrip(beanMappingService.mapTo(reservationDTO.getTrip(), Trip.class));
        reservation.setCreated(new Date());

        Set<ExcursionDTO> excursions = reservationDTO.getExcursions();
        for (ExcursionDTO excursionDTO : excursions){
            Excursion ex = beanMappingService.mapTo(excursionDTO, Excursion.class);
            if(!reservation.getExcursions().contains(ex)){
                reservation.addExcursion(ex);
            }
        }

        reservationService.updateReservation(reservation);
    }

    @Override
    public void removeReservation(Long reservationId) {
        Reservation reservation = reservationService.findReservationById(reservationId);

        Set<Excursion> excursions = reservation.getExcursions();
        for(Excursion excursion : excursions){
            excursion.removeReservation(reservation);
            excursionService.updateExcursion(excursion);
        }

        reservation.getCustomer().removeReservation(reservation);
        customerService.updateCustomer(reservation.getCustomer());

        reservationService.removeReservation(reservation);
    }

    @Override
    public void addExcursion(Long reservationId, Long excursionId) {
        Reservation reservation = reservationService.findReservationById(reservationId);
        Excursion excursion = excursionService.findExcursionById(excursionId);
        reservationService.addExcursionToReservation(reservation, excursion);
    }

    @Override
    public List<ReservationDTO> getAllReservations() {
        return beanMappingService.mapTo(
                reservationService.findAllReservations(), ReservationDTO.class);
    }

    @Override
    public ReservationDTO getReservationsById(Long reservationId) {
        return beanMappingService.mapTo(
                reservationService.findReservationById(reservationId), ReservationDTO.class);
    }

    @Override
    public List<ReservationDTO> getReservationsByTrip(Long tripId) {
        return beanMappingService.mapTo(
                reservationService.findReservationsByTrip(
                        tripService.findTripById(tripId)), ReservationDTO.class);
    }

    @Override
    public List<ReservationDTO> getReservationsByCustomer(Long customerId) {
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
