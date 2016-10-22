/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.travelAgency.persistence.dao;

import cz.muni.fi.pa165.travelAgency.persistence.entity.User;
import enums.UserRole;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Martin
 */
@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager em;

    public void create(User user) {
        em.persist(user);
    }

    public void remove(User user) {
        em.remove(user);
    }

    public User update(User user) {
        return em.merge(user);
    }

    public List<User> findAllCustomers() {
        final Query query = em.createQuery("SELECT u FROM User as u WHERE u.userRole = :userRole");
        query.setParameter("userRole", UserRole.CUSTOMER);
        return query.getResultList();
    }

    public User findByEmail(String email) {
        final Query query = em.createQuery("SELECT u FROM User as u WHERE u.email = :email");
        query.setParameter("email", email);
        return (User) query.getSingleResult();
    }
}
