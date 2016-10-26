/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.travelAgency.persistence.dao;

import cz.muni.fi.pa165.travelAgency.persistence.entity.Trip;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Patrik Behrami
 */
public class TripDaoImpl implements TripDao {

    @PersistenceContext
    private EntityManager em;
    
    public void create(Trip trip) {
        em.persist(trip);
    }

    public void remove(Trip trip) {
        em.remove(trip);
    }

    public Trip update(Trip trip) {
        return em.merge(trip);
    }

    public List<Trip> findAllTrips() {
        return em.createQuery("SELECT t FROM Trip t",Trip.class).getResultList();
    }

    @Override
    public List<Trip> findTripsByCountry(String countryName) {
        //TODO
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
