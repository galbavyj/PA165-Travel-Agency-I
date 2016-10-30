/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.travelAgency.persistence.dao;

import cz.muni.fi.pa165.travelAgency.persistence.entity.Excursion;
import cz.muni.fi.pa165.travelAgency.persistence.entity.Reservation;
import java.util.List;
import java.util.Set;
<<<<<<< HEAD
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
=======
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
>>>>>>> refs/remotes/origin/master

/**
 *
 * @author Lucia
 */
@Repository
@Transactional
public class ExcursionDaoImpl implements ExcursionDao {

    @PersistenceContext
    private EntityManager em;

    public void create(Excursion ex) {
            em.persist(ex);
    }

    public void remove(Excursion ex) {
        em.remove(ex);
    }

    public Excursion update(Excursion ex) {
        return em.merge(ex);
    }

    public List<Excursion> findAllExcursions() {
        TypedQuery<Excursion> query = em.createQuery("SELECT e FROM Excursion e",
				Excursion.class);
		return (List<Excursion>) query.getResultList();
    }
    
}
