package service;

import cz.muni.fi.pa165.travelAgency.persistence.entity.Trip;

/**
 *  @author
 */
public interface TripService {

    Trip findTripById(Long id);

}
