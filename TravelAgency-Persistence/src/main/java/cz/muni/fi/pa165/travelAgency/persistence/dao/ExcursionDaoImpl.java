/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.travelAgency.persistence.dao;

import cz.muni.fi.pa165.travelAgency.persistence.entity.Excursion;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Lucia
 */
@Repository
@Transactional
public class ExcursionDaoImpl implements ExcursionDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(Excursion ex) {
            em.persist(ex);
    }

    @Override
    public void remove(Excursion ex) {
        em.remove(ex);
    }

    @Override
    public Excursion update(Excursion ex) {
        return em.merge(ex);
    }

    @Override
    public List<Excursion> findAllExcursions() {
        TypedQuery<Excursion> query = em.createQuery("SELECT e FROM Excursion e",
				Excursion.class);
		return (List<Excursion>) query.getResultList();
    }
    

    @Override
    public Excursion findExcursionById(Long exId) {
        return em
				.createQuery("select e from Excursion e WHERE e.id = :exId",
						Excursion.class).setParameter("exId", exId)
				.getSingleResult();
    }

}
