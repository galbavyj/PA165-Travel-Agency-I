package service;

import cz.muni.fi.pa165.travelAgency.persistence.dao.ExcursionDao;
import cz.muni.fi.pa165.travelAgency.persistence.entity.Excursion;
import cz.muni.fi.pa165.travelagency.travelagencyservice.TravelAgencyPersistenceException;
import java.math.BigDecimal;
import java.util.List;
import javax.inject.Inject;
import org.springframework.stereotype.Service;

/**
 *
 * @author Lucia
 */
@Service
public class ExcursionServiceImpl implements ExcursionService {
    
    @Inject
	private ExcursionDao excursionDao;

    @Override
    public Excursion createExcursion(Excursion excursion) {
        try {
             excursionDao.create(excursion);
        }
        catch (Exception e) {
            throw new TravelAgencyPersistenceException("Failed to create customer" + e.toString() + e.getMessage(), e.getCause());
        }
        
        return excursion;
    }

    @Override
    public void deleteExcursion(Excursion ex) {
        try {
             excursionDao.remove(ex);
        }
        catch (Exception e) {
            throw new TravelAgencyPersistenceException("Failed to create customer" + e.toString() + e.getMessage(), e.getCause());
        }
    }

    @Override
    public void changeDescription(Excursion excursion, String description) {
        excursion.setDescription(description);
        try {
             excursionDao.update(excursion);
        }
        catch (Exception e) {
            throw new TravelAgencyPersistenceException("Failed to create customer" + e.toString() + e.getMessage(), e.getCause());
        }
    }

    @Override
    public void changePrice(Excursion excursion, BigDecimal price) {
        excursion.setPrice(price);
        try {
             excursionDao.update(excursion);
        }
        catch (Exception e) {
            throw new TravelAgencyPersistenceException("Failed to create customer" + e.toString() + e.getMessage(), e.getCause());
        }
    }

    @Override
    public List<Excursion> findAllExcursions() {
        try {
             return excursionDao.findAllExcursions();
        }
        catch (Exception e) {
            throw new TravelAgencyPersistenceException("Failed to create customer" + e.toString() + e.getMessage(), e.getCause());
        }
    }

    @Override
    public Excursion findExcursionById(Long exId) {
        try {
             return excursionDao.findExcursionById(exId);
        }
        catch (Exception e) {
            throw new TravelAgencyPersistenceException("Failed to create customer" + e.toString() + e.getMessage(), e.getCause());
        }
    }

    @Override
    public Long updateExcursion(Excursion ex) {
        try {
             excursionDao.update(ex);
        }
        catch (Exception e) {
            throw new TravelAgencyPersistenceException("Failed to create customer" + e.toString() + e.getMessage(), e.getCause());
        }
        return ex.getId();
    }
    
}
