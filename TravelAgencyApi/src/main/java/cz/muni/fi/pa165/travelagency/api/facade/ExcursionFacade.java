package cz.muni.fi.pa165.travelagency.api.facade;

import cz.muni.fi.pa165.travelagency.api.dto.ExcursionDTO;
import cz.muni.fi.pa165.travelagency.api.enums.ExcursionType;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Lucia
 */
public interface ExcursionFacade {
    /**
     * Create new excursion
     *
     * @param excursion DTO of excursion, which you want to create
     * @return ID of new excursion
     */
    public Long createExcursion(ExcursionDTO excursion);

    /**
     * Delete the excursion
     *
     * @param ex DTO of excursion, which will be deleted
     */
    public void removeExcursion(ExcursionDTO ex);

    /**
     * Change the description of the excursion
     *
     * @param excursion excursion which will be changed
     * @param description new description of excursion
     */
    public void changeDescription(ExcursionDTO excursion, String description);
    
    /**
     * Change the description of the excursion
     *
     * @param excursion DTO of excursion which will be changed
     * @param price new price of excursion
     */
    public void changePrice(ExcursionDTO excursion, BigDecimal price);

    
    /**
     * Find all excursions
     *
     * @return all excursions
     */
    public List<ExcursionDTO> findAllExcursions();
    
    /**
     * Find some excursion by its ID
     * @param exId ID of excursion
     * @return found excursion
     */
    public ExcursionDTO findExcursionById(Long exId); 
    
    /**
     * Update excursion
     * 
     * @param ex DTO of excursion to be updated
     */
    public void updateExcursion(ExcursionDTO ex);
   
}
