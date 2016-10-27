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
import javax.persistence.Query;

/**
 *
 * @author Patrik Behrami
 */
public class TripDaoImpl implements TripDao {

    @PersistenceContext
    private EntityManager em;
    
    @Override
    public void create(Trip trip) {
        em.persist(trip);
    }

    @Override
    public void remove(Trip trip) {
        em.remove(trip);
    }

    @Override
    public Trip update(Trip trip) {
        return em.merge(trip);
    }

    @Override
    public List<Trip> findAllTrips() {
        return em.createQuery("SELECT t FROM Trip t",Trip.class).getResultList();
    }

    @Override
    public List<Trip> findTripsByCountry(String countryName) {
        return em.createQuery("SELECT t FROM Trip as t WHERE t.country = :country").setParameter("country", countryName).getResultList();
    }
    
}
