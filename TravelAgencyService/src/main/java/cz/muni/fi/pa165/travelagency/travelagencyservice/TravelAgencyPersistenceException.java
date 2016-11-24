/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.travelagency.travelagencyservice;

import org.springframework.dao.DataAccessException;

/**
 *
 * @author Martin
 */
public class TravelAgencyPersistenceException extends DataAccessException {
    
    public TravelAgencyPersistenceException(String msg) {
        super(msg + "Exception thrown at persistance layer");
    }
    
    public TravelAgencyPersistenceException(String msg, Throwable cause) {
        super(msg + "Exception thrown at persistance layer", cause);
    }
    
}
