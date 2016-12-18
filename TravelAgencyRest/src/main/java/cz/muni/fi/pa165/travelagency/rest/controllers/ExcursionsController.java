package cz.muni.fi.pa165.travelagency.rest.controllers;

import cz.muni.fi.pa165.travelagency.api.dto.ExcursionDTO;
import cz.muni.fi.pa165.travelagency.api.facade.ExcursionFacade;
import cz.muni.fi.pa165.travelagency.rest.exceptions.NotFoundException;
import java.util.List;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Lucia
 */

@RestController
@RequestMapping("/excursions")
public class ExcursionsController {
    
    final static Logger logger = LoggerFactory.getLogger(CustomersController.class);
 
    @Inject
    private ExcursionFacade excursionFacade;
    
    /**
     * Get list of Excursions
     *
     * @return List of excursions
     */
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<ExcursionDTO> findExcursions() {
        logger.debug("rest findExcursions()");
        return excursionFacade.findAllExcursions();
    }
    
    /**
     *
     * Get Excursion by id
     * 
     * @param id identifier for a customer
     * @return ExcursionDTO
     * @throws NotFoundException
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final ExcursionDTO getExcursions(@PathVariable("id") long id) throws Exception {
        logger.debug("rest getExcursion({})", id);
        ExcursionDTO excursionDTO = excursionFacade.findExcursionById(id);
        if (excursionDTO != null) {
            return excursionDTO;
        } else {
            throw new NotFoundException();
        }
    }
    
    /**
     * Delete one Excursions by its id 
     * 
     * @param id identifier for Excursion
     * @throws NotFoundException
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public final void deleteExcursion(@PathVariable("id") long id) throws Exception {
        logger.debug("rest deleteExcursion({})", id);
        try {
            excursionFacade.removeExcursion(excursionFacade.findExcursionById(id));
        } catch (Exception ex) {
            throw new NotFoundException();
        }
    }
    
    
    /**
     * Create a new Excursion
     * 
     * @param excursion ExcursionDTO with required fields for creation
     * @throws NotFoundException()
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final String createCustomer(@RequestBody ExcursionDTO excursion) throws Exception {

        logger.debug("rest createExcursion()");
           
        try {
            excursionFacade.createExcursion(excursion);
            return "success";
        } catch (Exception ex) {
            throw new NotFoundException();
        }
    }
    
    /**
     * Update the sent excursion
     * @param excursionDto DTO of the excursion
     * @throws NotFoundException When excursion is not available
     */
    @RequestMapping(value = "/update", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final void updateExcursion(@RequestBody ExcursionDTO excursionDto) throws Exception {
        try {
            excursionFacade.updateExcursion(excursionDto);            
        } catch (Exception ex) {
            throw new NotFoundException();
        }        
    }
    
    
}
