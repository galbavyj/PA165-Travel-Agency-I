/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import cz.muni.fi.pa165.travelagency.api.dto.CustomerDTO;
import cz.muni.fi.pa165.travelagency.api.dto.ExcursionDTO;
import cz.muni.fi.pa165.travelagency.api.dto.TripDTO;
import cz.muni.fi.pa165.travelagency.api.enums.ExcursionType;
import cz.muni.fi.pa165.travelagency.api.facade.ExcursionFacade;
import cz.muni.fi.pa165.travelagency.api.facade.TripFacade;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
    
    private ExcursionDTO excursionToUpdate;
    

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model, RedirectAttributes redirectAttributes, HttpServletRequest req) {
        HttpSession session = req.getSession(true);
        CustomerDTO customer = (CustomerDTO) session.getAttribute("authUser");
        if (customer != null && customer.isAdmin()) {
            model.addAttribute("excursions", excursionFacade.findAllExcursions());
            return "/admin/excursion/list";
        } else {
            redirectAttributes.addFlashAttribute("alert_danger", "You do not have authentication to visit this page.");
            return "redirect:/";
        }
    }
    
    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newExcursion(Model model, RedirectAttributes redirectAttributes, HttpServletRequest req) {
        log.debug("new()");
        HttpSession session = req.getSession(true);
        CustomerDTO customer = (CustomerDTO) session.getAttribute("authUser");
        if (customer != null && customer.isAdmin()) {
            model.addAttribute("excursionCreate", new ExcursionDTO());
            return "admin/excursion/new";
        } else {
            redirectAttributes.addFlashAttribute("alert_danger", "You do not have authentication to visit this page.");
            return "redirect:/";
        }
    }
    
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public String editExcursion(@PathVariable("id") long id, Model model) {
        log.debug("edit()");
        excursionToUpdate = excursionFacade.findExcursionById(id);
        model.addAttribute("excursionEdit", excursionToUpdate);
        return "admin/excursion/edit";
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
        if (bindingResult.hasErrors()) {
            return "admin/excursion/new";
        }
        
        Date created = new Date();
        formBean.setCreated(created);
      
        Long id = excursionFacade.createExcursion(formBean);
        
        redirectAttributes.addFlashAttribute("alert_success", "Excursion " + id + " was created");
        return "redirect:" + uriBuilder.path("/admin/excursion/list").buildAndExpand(id).encode().toUriString();
    }
    
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public String delete(@PathVariable long id, Model model, UriComponentsBuilder uriBuilder, RedirectAttributes redirectAttributes) {
        excursionFacade.removeExcursion(excursionFacade.findExcursionById(id));
        log.debug("delete({})", id);
        redirectAttributes.addFlashAttribute("alert_success", "Excursion was deleted.");
        return "redirect:" + uriBuilder.path("/admin/excursion/list").buildAndExpand(id).encode().toUriString();
    }
    
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public String update(@Valid @ModelAttribute("excursionEdit") ExcursionDTO formBean, BindingResult bindingResult,
                         Model model, RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder) {
        log.debug("update(excursionCreate={})", formBean);
        if (bindingResult.hasErrors()) {
            return "admin/excursion/edit";
        }
        
        prepareUpdate(formBean);
        excursionFacade.updateExcursion(excursionToUpdate);

        redirectAttributes.addFlashAttribute("alert_success", "Excursion was edited");
        return "redirect:" + uriBuilder.path("/admin/excursion/list").buildAndExpand().encode().toUriString();
    }
    
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
    }
    
    private void prepareUpdate(ExcursionDTO excursion) {
        excursionToUpdate.setPlace(excursion.getPlace());
        excursionToUpdate.setDescription(excursion.getDescription());
        excursionToUpdate.setPrice(excursion.getPrice());
        excursionToUpdate.setDurationInHours(excursion.getDurationInHours());
        excursionToUpdate.setFromDate(excursion.getFromDate());
        excursionToUpdate.setExcursionType(excursion.getExcursionType());
    }
    
}
