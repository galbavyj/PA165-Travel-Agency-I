/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.travelAgency.persistence.dao;

import cz.muni.fi.pa165.travelAgency.persistence.entity.Customer;
import cz.muni.fi.pa165.travelAgency.persistence.entity.Reservation;
import cz.muni.fi.pa165.travelagency.api.enums.CustomerRole;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Martin
 */
@Repository
@Transactional
public class CustomerDaoImpl implements CustomerDao {

    @PersistenceContext
    private EntityManager em;

    public void create(Customer customer) {
        em.persist(customer);
    }

    public void remove(Customer customer) {
        em.remove(customer);
    }

    public Customer update(Customer customer) {
        return em.merge(customer);
    }

    public List<Customer> findAllCustomers() {
        final Query query = em.createQuery("SELECT u FROM Customer as u WHERE u.customerRole = :customerRole");
        query.setParameter("customerRole", CustomerRole.CUSTOMER);
        return query.getResultList();
    }

    public Customer findByEmail(String email) {
        return em.createQuery("SELECT u FROM Customer as u WHERE u.email = :email",Customer.class)
                .setParameter("email", email).getSingleResult();
    }

    public Customer findById(Long id) {
        return em.createQuery("SELECT u FROM Customer as u WHERE u.id = :id",Customer.class)
                .setParameter("id", id).getSingleResult();
    }

    @Override
    public void addReservation(Customer customer, Reservation reservation) {
        customer.addReservation(reservation);
        em.merge(customer);
    }
}
