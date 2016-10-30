/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.travelAgency.persistence.dao;

import cz.muni.fi.pa165.travelAgency.persistence.entity.Customer;
import enums.UserRole;
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
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager em;

    public void create(Customer user) {
        em.persist(user);
    }

    public void remove(Customer user) {
        em.remove(user);
    }

    public Customer update(Customer user) {
        return em.merge(user);
    }

    public List<Customer> findAllCustomers() {
        final Query query = em.createQuery("SELECT u FROM User as u WHERE u.userRole = :userRole");
        query.setParameter("userRole", UserRole.CUSTOMER);
        return query.getResultList();
    }

    public Customer findByEmail(String email) {
        final Query query = em.createQuery("SELECT u FROM User as u WHERE u.email = :email");
        query.setParameter("email", email);
        return (Customer) query.getSingleResult();
    }
}
