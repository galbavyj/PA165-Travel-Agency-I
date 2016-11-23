/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import cz.muni.fi.pa165.travelAgency.persistence.entity.Excursion;
import cz.muni.fi.pa165.travelagency.api.dto.ExcursionDTO;
import cz.muni.fi.pa165.travelagency.api.facade.ExcursionFacade;
import cz.muni.fi.pa165.travelagency.travelagencyservice.MappingService;
import java.math.BigDecimal;
import java.util.List;
import javax.inject.Inject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.ExcursionService;

/**
 *
 * @author Lucia
 */
@Service
@Transactional
public class ExcursionFacadeImpl implements ExcursionFacade {
    
    @Inject
    private MappingService mappingService;
    
    @Inject
    private ExcursionService excursionService;

    @Override
    public Long createExcursion(ExcursionDTO excursion) {
        Excursion mappedExcursion = mappingService.mapTo(excursion, Excursion.class);
        Excursion newExcursion = excursionService.createExcursion(mappedExcursion);
        return newExcursion.getId();
    }

    @Override
    public void removeExcursion(ExcursionDTO ex) {
        Excursion mappedExcursion = mappingService.mapTo(ex, Excursion.class);
        excursionService.removeExcursion(mappedExcursion);
    }

    @Override
    public void changeDescription(ExcursionDTO excursion, String description) {
        Excursion mappedExcursion = mappingService.mapTo(excursion, Excursion.class);
        excursion.setDescription(description);
        excursionService.updateExcursion(mappedExcursion);
    }

    @Override
    public void changePrice(ExcursionDTO excursion, BigDecimal price) {
        Excursion mappedExcursion = mappingService.mapTo(excursion, Excursion.class);
        excursion.setPrice(price);
        excursionService.updateExcursion(mappedExcursion);
    }

    @Override
    public List<ExcursionDTO> findAllExcursions() {
        return mappingService.mapTo(excursionService.findAllExcursions(), ExcursionDTO.class);
    }

    @Override
    public ExcursionDTO findExcursionById(Long exId) {
        return mappingService.mapTo(excursionService.findExcursionById(exId), ExcursionDTO.class);

    }

    @Override
    public void updateExcursion(ExcursionDTO ex) {
        Excursion mappedExcursion = mappingService.mapTo(ex, Excursion.class);
        excursionService.updateExcursion(mappedExcursion);
    }
    
}
