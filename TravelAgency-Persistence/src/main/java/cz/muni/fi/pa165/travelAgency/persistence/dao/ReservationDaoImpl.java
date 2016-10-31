package cz.muni.fi.pa165.travelAgency.persistence.dao;

import cz.muni.fi.pa165.travelAgency.persistence.entity.Customer;
import cz.muni.fi.pa165.travelAgency.persistence.entity.Reservation;
import cz.muni.fi.pa165.travelAgency.persistence.entity.Trip;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Juraj Galbavý
 */
@Repository
@Transactional
public class ReservationDaoImpl implements ReservationDao{

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(Reservation reservation) {
        em.persist(reservation);
    }

    @Override
    public Reservation update(Reservation reservation) {
        return em.merge(reservation);
    }


    @Override
    public void remove(Reservation reservation) {
        em.remove(findById(reservation.getId()));
    }

    @Override
    public List<Reservation> findAllReservations() {
        return em.createQuery("SELECT r FROM Reservation r", Reservation.class)
                .getResultList();
    }

    @Override
    public List<Reservation> findReservationsByCustomer(Customer customer) {
        return em.createQuery("SELECT r FROM Reservation r WHERE r.customer=:customer", Reservation.class)
                .setParameter("customer", customer).getResultList();

    }

    @Override
    public List<Reservation> findReservationsByTrip(Trip trip) {
        return em.createQuery("SELECT r FROM Reservation r WHERE r.trip=:trip", Reservation.class)
                .setParameter("trip", trip).getResultList();

    }

    @Override
    public Reservation findById(Long id) {
        return em.find(Reservation.class, id);
    }


}
