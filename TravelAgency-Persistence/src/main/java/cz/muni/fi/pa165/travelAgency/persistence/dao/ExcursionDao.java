/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.travelAgency.persistence.dao;

import cz.muni.fi.pa165.travelAgency.persistence.entity.Excursion;
import java.util.List;

/**
 *
 * @author Lucia
 */
public interface ExcursionDao {
    /**
     * Adds new excursion into database
     *
     * @param excursion new excursion to database
     */
    public void create(Excursion excursion);

    /**
     * Deletes excursion from database
     *
     * @param ex excursion, which will be removed from DB
     * @throws IllegalArgumentException in case ID is not found
     */
    public void remove(Excursion ex);

    /**
     * Edit the excursion in the database
     *
     * @param excursion excursion which will be changed
     * @return updated Excursion
     * @throws IllegalArgumentException in case Excursion is not found
     */
    public Excursion update(Excursion excursion);

    
    /**
     * Find all excursions
     *
     * @return all excursions
     */
    public List<Excursion> findAllExcursions();
    
    /**
     * Find some excursion by its ID
     * @param exId ID of excursion
     * @return found excursion
     */
    public Excursion findExcursionById(Long exId);    
}
