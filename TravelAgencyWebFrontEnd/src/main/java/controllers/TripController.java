/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import cz.muni.fi.pa165.travelagency.api.dto.TripDTO;
import cz.muni.fi.pa165.travelagency.api.facade.TripFacade;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        } catch (Exception e){
            redirectAttributes.addFlashAttribute("alert_danger", "Deletion of trip failed");
        }
        
        return "redirect:/admin/trip/list";
    }
           
}