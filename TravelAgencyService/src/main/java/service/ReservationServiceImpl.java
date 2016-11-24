package service;

import cz.muni.fi.pa165.travelAgency.persistence.dao.ReservationDao;
import cz.muni.fi.pa165.travelAgency.persistence.entity.Customer;
import cz.muni.fi.pa165.travelAgency.persistence.entity.Excursion;
import cz.muni.fi.pa165.travelAgency.persistence.entity.Reservation;
import cz.muni.fi.pa165.travelAgency.persistence.entity.Trip;
import cz.muni.fi.pa165.travelagency.travelagencyservice.TravelAgencyPersistenceException;
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
        try {
            reservationDao.create(reservation);
            return reservation;
        } catch(Exception e){
            throw new TravelAgencyPersistenceException("Failed to create reservation" + e);
        }

    }

    @Override
    public Reservation updateReservation(Reservation reservation) {
        try {
            return reservationDao.update(reservation);
        } catch(Exception e){
            throw new TravelAgencyPersistenceException("Failed to update reservation" + e);
        }
    }

    @Override
    public void removeReservation(Reservation reservation) {
        try {
            reservationDao.remove(reservation);
        } catch(Exception e){
            throw new TravelAgencyPersistenceException("Failed to remove reservation" + e);
        }


    }

    @Override
    public void addExcursionToReservation(Reservation reservation, Excursion excursion) {

        try {
            reservation.addExcursion(excursion);
            reservationDao.update(reservation);
        } catch(Exception e){
            throw new TravelAgencyPersistenceException("Failed to add excursion to reservation" + e);
        }
    }

    @Override
    public List<Reservation> findAllReservations() {
        try {
            return reservationDao.findAllReservations();
        } catch(Exception e) {
            throw new TravelAgencyPersistenceException("Failed to find all reservations" + e);
        }
    }

    @Override
    public Reservation findReservationById(Long id) {
        try {
            return reservationDao.findById(id);
        } catch(Exception e){
            throw new TravelAgencyPersistenceException("Failed to find reservation by id" + e);
        }
    }

    @Override
    public List<Reservation> findReservationsByCustomer(Customer customer) {
        try {
            return reservationDao.findReservationsByCustomer(customer);
        } catch(Exception e){
            throw new TravelAgencyPersistenceException("Failed to find reservation by customer" + e);
        }
    }

    @Override
    public List<Reservation> findReservationsByTrip(Trip trip) {
        try {
            return reservationDao.findReservationsByTrip(trip);
        } catch(Exception e){
            throw new TravelAgencyPersistenceException("Failed to find reservation by trip" + e);
        }
    }

    @Override
    public BigDecimal getTotalPrice(Reservation reservation) {
        try{
            if(reservation == null){
                throw new IllegalArgumentException();
            }
            reservation = reservationDao.findById(reservation.getId());

            BigDecimal totalPrice = reservation.getTrip().getPrice();
            for(Excursion excursion : reservation.getExcursions()){
                totalPrice = totalPrice.add(excursion.getPrice());
            }

            return totalPrice;
        }catch(Exception e){
            throw new TravelAgencyPersistenceException("Failed to get total price" + e);
        }

    }
}
