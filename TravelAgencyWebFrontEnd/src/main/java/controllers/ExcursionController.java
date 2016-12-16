/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import cz.muni.fi.pa165.travelagency.api.dto.ExcursionDTO;
import cz.muni.fi.pa165.travelagency.api.dto.TripDTO;
import cz.muni.fi.pa165.travelagency.api.enums.ExcursionType;
import cz.muni.fi.pa165.travelagency.api.facade.ExcursionFacade;
import cz.muni.fi.pa165.travelagency.api.facade.TripFacade;
import java.util.List;
import javax.inject.Inject;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

/**
 *
 * @author Lucia
 */
@Controller
@RequestMapping("/admin/excursion")
public class ExcursionController {
    
    final static Logger log = LoggerFactory.getLogger(ExcursionController.class);

    @Inject
    private ExcursionFacade excursionFacade;
    
    @Inject
    private TripFacade tripFacade;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("excursions", excursionFacade.findAllExcursions());
        return "/admin/excursion/list";
    }
    
    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newExcursion(Model model) {
        log.debug("new()");
        model.addAttribute("excursionCreate", new ExcursionDTO());
        return "admin/excursion/new";
    }
    
    @ModelAttribute("trips")
    public List<TripDTO> trips() {
        log.debug("trips()");
        return tripFacade.findAllTrips();
    }
    
    @ModelAttribute("types")
    public ExcursionType[] types() {
        log.debug("types()");
        return ExcursionType.values();
    }
    
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@Valid @ModelAttribute("excursionCreate") ExcursionDTO formBean, BindingResult bindingResult,
                         Model model, RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder) {
        log.debug("create(excursionCreate={})", formBean);
        //in case of validation error forward back to the the form
        if (bindingResult.hasErrors()) {
            for (ObjectError ge : bindingResult.getGlobalErrors()) {
                log.trace("ObjectError: {}", ge);
            }
            for (FieldError fe : bindingResult.getFieldErrors()) {
                model.addAttribute(fe.getField() + "_error", true);
                log.trace("FieldError: {}", fe);
            }
            return "admin/excursion/new";
        }
       
        Long id = excursionFacade.createExcursion(formBean);
        //report success
        redirectAttributes.addFlashAttribute("alert_success", "Excursion " + id + " was created");
        return "redirect:" + uriBuilder.path("/admin/excursion/list").buildAndExpand(id).encode().toUriString();
    }
    
}
