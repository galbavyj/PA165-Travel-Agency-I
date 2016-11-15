package service;

import cz.muni.fi.pa165.travelAgency.persistence.dao.ReservationDao;
import cz.muni.fi.pa165.travelAgency.persistence.entity.Customer;
import cz.muni.fi.pa165.travelAgency.persistence.entity.Excursion;
import cz.muni.fi.pa165.travelAgency.persistence.entity.Reservation;
import cz.muni.fi.pa165.travelAgency.persistence.entity.Trip;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author Juraj
 */
@Service
public class ReservationServiceImpl implements ReservationService {

    @Inject
    private ReservationDao reservationDao;

    @Override
    public Reservation createReservation(Reservation reservation) {
        reservationDao.create(reservation);
        return reservation;
    }

    @Override
    public Reservation updateReservation(Reservation reservation) {
        return reservationDao.update(reservation);
    }

    @Override
    public void removeReservation(Reservation reservation) {
        reservationDao.remove(reservation);
    }

    @Override
    public void addExcursionToReservation(Reservation reservation, Excursion excursion) {
        reservation.addExcursion(excursion);
        reservationDao.update(reservation);
    }

    @Override
    public List<Reservation> findAllReservations() {
        return reservationDao.findAllReservations();
    }

    @Override
    public Reservation findReservationById(Long id) {
        return reservationDao.findById(id);
    }

    @Override
    public List<Reservation> findReservationsByCustomer(Customer customer) {
        return reservationDao.findReservationsByCustomer(customer);
    }

    @Override
    public List<Reservation> findReservationsByTrip(Trip trip) {
        return reservationDao.findReservationsByTrip(trip);
    }

    @Override
    public BigDecimal getTotalPrice(Reservation reservation) {
        if(reservation == null){
            throw new IllegalArgumentException();
        }
        reservation = reservationDao.findById(reservation.getId());
        
        BigDecimal totalPrice = reservation.getTrip().getPrice();
        for(Excursion excursion : reservation.getExcursions()){
            totalPrice = totalPrice.add(excursion.getPrice());
        }

        return totalPrice;
    }
}
