/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.travelAgency.persistence.dao;

import cz.muni.fi.pa165.travelAgency.persistence.entity.User;
import java.util.Set;

/**
 *
 * @author Martin
 */
public interface UserDao {

    /**
     * Add new user into database
     *
     * @param user user to be added into database
     */
    public void create(User user);

    /**
     * Removes user from database
     *
     * @param user user to be removed from database
     * @throws IllegalArgumentException in case user is not found
     */
    public void remove(User user);

    /**
     * Add new user into database
     *
     * @param user user to be added into database
     * @return updated User
     * @throws IllegalArgumentException in case user is not found
     */
    public User update(User user);

    
    /**
     * find all customers
     *
     * @return all customers
     */
    public Set<User> findAllCustomers();
    
    /**
     * find user by email in database
     *
     * @param email email of user
     * @return user with given email
     * @throws IllegalArgumentException in case user is not found
     */
    public User findByEmail(String email);

}
