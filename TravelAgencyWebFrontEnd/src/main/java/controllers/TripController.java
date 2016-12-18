/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import cz.muni.fi.pa165.travelagency.api.dto.ExcursionDTO;
import cz.muni.fi.pa165.travelagency.api.dto.TripCreateDTO;
import cz.muni.fi.pa165.travelagency.api.dto.TripDTO;
import cz.muni.fi.pa165.travelagency.api.facade.ExcursionFacade;
import cz.muni.fi.pa165.travelagency.api.facade.TripFacade;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

/**
 *
 * @author behra
 */
@Controller
@RequestMapping("/admin/trip")
public class TripController {

    public TripController() {

    }

    final static Logger log = LoggerFactory.getLogger(CustomerController.class);

    @Inject
    private TripFacade tripFacade;
    
    @Inject
    private ExcursionFacade excursionFacade;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("trips", tripFacade.findAllTrips());
        return "/admin/trip/list";
    }
    
  
    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public String view(@PathVariable("id") Long id,RedirectAttributes redirectAttributes, Model model){
        TripDTO tripDTO = tripFacade.findTripById(id);
        if (tripDTO == null){
            redirectAttributes.addFlashAttribute("alert_warning", "Null trip");
            return "redirect:/admin/trip/list";
        }
        model.addAttribute("trip",tripDTO);
        model.addAttribute("possibleExcursions",tripDTO.getPossibleExcursions());

        return "/admin/trip/view";
    }
    
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public String deleteReservation(@PathVariable("id") Long id,RedirectAttributes redirectAttributes, Model model){
        TripDTO tripDTO = tripFacade.findTripById(id);
        if (tripDTO == null){
            redirectAttributes.addFlashAttribute("alert_warning", "Null trip");
            return "redirect:/admin/trip/list";
        }
        
        try {
            tripFacade.removeTrip(tripDTO);
            redirectAttributes.addFlashAttribute("alert_success", "Trip deleted.");
        } catch (IllegalStateException e){
            redirectAttributes.addFlashAttribute("alert_danger", "Can't delete trip which is already booked in a reservation");
        } catch (Exception e){
            redirectAttributes.addFlashAttribute("alert_danger", "Deletion of trip failed");
        }
            
        return "redirect:/admin/trip/list";
    }
           
    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newProduct(Model model) {
        log.debug("trip() new");
        model.addAttribute("tripCreate", new TripCreateDTO());
        return "admin/trip/new";
    }

    @ModelAttribute("excursions")
    public List<ExcursionDTO> excursions() {
        log.debug("excursions()");
        return excursionFacade.findAllExcursions();
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@ModelAttribute("tripCreate") TripCreateDTO formBean, BindingResult bindingResult,
                         Model model, RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder) {
        log.debug("create(tripCreate={})", formBean);
        //in case of validation error forward back to the the form
        /*if (bindingResult.hasErrors()) {
            for (ObjectError ge : bindingResult.getGlobalErrors()) {
                log.trace("ObjectError: {}", ge);
            }
            for (FieldError fe : bindingResult.getFieldErrors()) {
                model.addAttribute(fe.getField() + "_error", true);
                log.trace("FieldError: {}", fe);
            }
            return "admin/trip/new";
        }*/
        //create product
        
        Calendar calendar = Calendar.getInstance();
        calendar.set(2017, Calendar.JANUARY, 17);
        Date fromDate = calendar.getTime();
        
        calendar.set(2017, Calendar.JANUARY, 19);
        Date toDate = calendar.getTime();
 
        formBean.setFromDate(fromDate);
        formBean.setToDate(toDate);
        
        tripFacade.createTrip(formBean);

        redirectAttributes.addFlashAttribute("alert_success", "Product " + " was created");
        return "redirect:list";
    }
    
}