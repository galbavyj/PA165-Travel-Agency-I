package cz.muni.fi.pa165.travelAgency.persistence.dao;

import cz.muni.fi.pa165.travelAgency.persistence.entity.Trip;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Patrik Behrami
 */
@Repository
@Transactional
public class TripDaoImpl implements TripDao {

    @PersistenceContext
    private EntityManager em;
    
    @Override
    public void create(Trip trip) {
        em.persist(trip);
    }

    @Override
    public void remove(Trip trip) {
        //em.remove(trip);
        em.remove(em.contains(trip) ? trip : em.merge(trip));
    }

    @Override
    public Trip update(Trip trip) {
        return em.merge(trip);
    }

    @Override
    public Trip findById(Long id) {
        return em.find(Trip.class, id);
    }

    
    @Override
    public List<Trip> findAllTrips() {
        return em.createQuery("SELECT t FROM Trip t",Trip.class).getResultList();
    }

    @Override
    public List<Trip> findTripsByCountry(String countryName) {
        return em.createQuery("SELECT t FROM Trip as t WHERE t.addressOfHotel.country = :country",Trip.class).setParameter("country", countryName).getResultList();
    }
    
}
