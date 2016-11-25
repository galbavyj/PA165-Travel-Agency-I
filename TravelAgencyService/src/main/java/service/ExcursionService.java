package service;

import cz.muni.fi.pa165.travelAgency.persistence.entity.Excursion;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author Lucia
 */
@Service
public interface ExcursionService {
    /**
     * Create new excursion
     *
     * @param excursion excursion, which you want to create
     */
    public Excursion createExcursion(Excursion excursion);

    /**
     * Delete the excursion
     *
     * @param ex excursion, which will be deleted
     */
    public void removeExcursion(Excursion ex);

    /**
     * Change the description of the excursion
     *
     * @param excursion excursion which will be changed
     * @param description new description of excursion
     */
    public void changeDescription(Excursion excursion, String description);
    
    /**
     * Change the description of the excursion
     *
     * @param excursion excursion which will be changed
     * @param price new price of excursion
     */
    public void changePrice(Excursion excursion, BigDecimal price);

    
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
    
    /**
     * Update excursion
     * 
     * @param ex Excursion to be updated
     * @return updated id of excursion
     */
    public Long updateExcursion(Excursion ex);
    
}
