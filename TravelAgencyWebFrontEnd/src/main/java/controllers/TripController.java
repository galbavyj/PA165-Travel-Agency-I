/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import cz.muni.fi.pa165.travelAgency.persistence.entity.Excursion;
import cz.muni.fi.pa165.travelagency.api.dto.AddressDTO;
import cz.muni.fi.pa165.travelagency.api.dto.CustomerDTO;
import cz.muni.fi.pa165.travelagency.api.dto.ExcursionDTO;
import cz.muni.fi.pa165.travelagency.api.dto.ReservationDTO;
import cz.muni.fi.pa165.travelagency.api.dto.TripCreateDTO;
import cz.muni.fi.pa165.travelagency.api.dto.TripDTO;
import cz.muni.fi.pa165.travelagency.api.dto.TripUpdateDTO;
import cz.muni.fi.pa165.travelagency.api.facade.ExcursionFacade;
import cz.muni.fi.pa165.travelagency.api.facade.ReservationFacade;
import cz.muni.fi.pa165.travelagency.api.facade.TripFacade;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import javax.validation.Valid;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

/**
 *
 * @author behra
 */
@Controller
@MultipartConfig(location="WEB-INF/resources/",
                 fileSizeThreshold=0,    
                 maxFileSize=5242880,       
                 maxRequestSize=20971520)
@RequestMapping("/admin/trip")
public class TripController {

    public TripController() {

    }

    final static Logger log = LoggerFactory.getLogger(CustomerController.class);

    @Inject
    private TripFacade tripFacade;
    
    @Inject
    private ExcursionFacade excursionFacade;
    
    @Inject
    private ReservationFacade reservationFacade;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {  
        HttpSession session = request.getSession(true);
        CustomerDTO customer = (CustomerDTO) session.getAttribute("authUser");
        if (customer == null || !customer.isAdmin()) {
            redirectAttributes.addFlashAttribute("alert_danger", "You do not have authentication to visit this page.");
            return "redirect:/";
        }     
        
        model.addAttribute("trips", tripFacade.findAllTrips());
        return "/admin/trip/list";
    }
    
  
    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public String view(@PathVariable("id") Long id,RedirectAttributes redirectAttributes, Model model, HttpServletRequest request){
        HttpSession session = request.getSession(true);
        CustomerDTO customer = (CustomerDTO) session.getAttribute("authUser");
        if (customer == null || !customer.isAdmin()) {
            redirectAttributes.addFlashAttribute("alert_danger", "You do not have authentication to visit this page.");
            return "redirect:/";
        }
        
        
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
    public String deleteReservation(@PathVariable("id") Long id,RedirectAttributes redirectAttributes, Model model, HttpServletRequest request){
        HttpSession session = request.getSession(true);
        CustomerDTO customer = (CustomerDTO) session.getAttribute("authUser");
        if (customer == null || !customer.isAdmin()) {
            redirectAttributes.addFlashAttribute("alert_danger", "You do not have authentication to visit this page.");
            return "redirect:/";
        }
        
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
    
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable("id") long id, Model model,RedirectAttributes redirectAttributes){ 
        TripDTO tripEdit = tripFacade.findTripById(id);
        List<ReservationDTO> reservationsWithTrip = reservationFacade.findReservationsByTrip(tripEdit.getId());
        
        if (reservationsWithTrip.size() > 0){
            redirectAttributes.addFlashAttribute("alert_danger", "You can't edit trip that is already in a reservation.");
            return "redirect:/admin/trip/list";
        }

        model.addAttribute("tripEdit", mapTripDTOtoTripUpdateDTO(tripEdit));
        return "admin/trip/edit"; 
    }
    
    @RequestMapping(value = "/uploadForm/{id}", method = RequestMethod.GET)
    public String uploadForm(@PathVariable("id") long id, Model model,RedirectAttributes redirectAttributes){ 
        TripDTO trip = tripFacade.findTripById(id);
        
        model.addAttribute("tripId", trip.getId());

        return "admin/trip/uploadForm"; 
    }
    
    @RequestMapping(value = "/upload/{id}",method = RequestMethod.POST)
    public String upload(@PathVariable("id") long id,@RequestParam("file") MultipartFile file, Model model,
            RedirectAttributes redirectAttributes, HttpServletRequest request,UriComponentsBuilder uriBuilder)throws ServletException, IOException{ 
        TripDTO trip = tripFacade.findTripById(id);
       
        if (file.isEmpty()){
            redirectAttributes.addFlashAttribute("alert_danger", "No file was uploaded. Please upload a file.");
            return "redirect:" + uriBuilder.path("/admin/trip/uploadForm/" + trip.getId().intValue()).buildAndExpand().encode().toUriString();
        }
        
        if (!file.getContentType().toLowerCase().contains("image")){
            redirectAttributes.addFlashAttribute("alert_danger", "Uploaded file was not image. Please upload a valid image type.");
            return "redirect:" + uriBuilder.path("/admin/trip/uploadForm/" + trip.getId().intValue()).buildAndExpand().encode().toUriString();
        }
        
        InputStream fileContent = file.getInputStream();
        OutputStream outputStream = null;
        outputStream = new FileOutputStream(request.getSession().getServletContext().getRealPath("/") + "\\WEB-INF\\resources\\" +  trip.getId().intValue() + ".jpg");
        
        int readBytes = 0;
        byte[] buffer = new byte[8192];
        while ((readBytes = fileContent.read(buffer, 0, 8192)) != -1) {
        outputStream.write(buffer, 0, readBytes);
        }
        outputStream.close();
        fileContent.close();

        trip.setFilePathToPicture("/resources/" + trip.getId().intValue() + ".jpg");
      
        tripFacade.updateTrip(trip);
 
        redirectAttributes.addFlashAttribute("alert_success", "Image was uploaded");
        return "redirect:" + uriBuilder.path("/admin/trip/list").buildAndExpand().encode().toUriString();
    }
           
    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newProduct(Model model,HttpServletRequest request, RedirectAttributes redirectAttributes) {
        HttpSession session = request.getSession(true);
        CustomerDTO customer = (CustomerDTO) session.getAttribute("authUser");
        if (customer == null || !customer.isAdmin()) {
            redirectAttributes.addFlashAttribute("alert_danger", "You do not have authentication to visit this page.");
            return "redirect:/";
        }
        
        log.debug("trip() new");
        model.addAttribute("tripCreate", new TripCreateDTO());
        return "admin/trip/new";
    }

    @ModelAttribute("excursions")
    public List<ExcursionDTO> excursions() {
        log.debug("excursions()");
        return excursionFacade.findAllExcursions();
    }

    
    
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
    }
    
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public String update(@Valid @ModelAttribute("tripEdit") TripUpdateDTO updatedTrip, BindingResult bindingResult,
                         Model model, RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder) {
        
        if (bindingResult.hasErrors()) {
            for (ObjectError ge : bindingResult.getGlobalErrors()) {
                log.trace("ObjectError: {}", ge);
            }
            for (FieldError fe : bindingResult.getFieldErrors()) {
                model.addAttribute(fe.getField() + "_error", true);
                log.trace("FieldError: {}", fe);
            }
            return "admin/trip/edit";
        }
        
        if (updatedTrip.getFromDate().after(updatedTrip.getToDate())){
            model.addAttribute("fromDate_error", true);
            model.addAttribute("toDate_error", true);
            model.addAttribute("dateFail", true);

            return "admin/trip/edit";
        }
        
        if (updatedTrip.getPossibleExcursionId() != null){
            for (Long excursionId : updatedTrip.getPossibleExcursionId()){
                Date excursionDate = excursionFacade.findExcursionById(excursionId).getFromDate(); 
                if (!((excursionDate.after(updatedTrip.getFromDate()) || excursionDate.equals(updatedTrip.getFromDate())) &&
                        (excursionDate.before(updatedTrip.getToDate()) || excursionDate.equals(updatedTrip.getToDate())))){
                    model.addAttribute("excursionDateFail", true);
                    
                    return "admin/trip/edit";
                }
            }
        }
        
 
        tripFacade.updateTrip(mapTripUpdateDTOtoTripDTO(updatedTrip));

        
        redirectAttributes.addFlashAttribute("alert_success", "Trip was edited");
        return "redirect:" + uriBuilder.path("/admin/trip/list").buildAndExpand().encode().toUriString();
    }
    
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@Valid @ModelAttribute("tripCreate") TripCreateDTO createdTrip, BindingResult bindingResult,
                         Model model, RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder,HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        CustomerDTO customer = (CustomerDTO) session.getAttribute("authUser");
        if (customer == null || !customer.isAdmin()) {
            redirectAttributes.addFlashAttribute("alert_danger", "You do not have authentication to visit this page.");
            return "redirect:/";
        }
        
        log.debug("create(tripCreate={})", createdTrip);
        
        if (bindingResult.hasErrors()) {
            for (ObjectError ge : bindingResult.getGlobalErrors()) {
                log.trace("ObjectError: {}", ge);
            }
            for (FieldError fe : bindingResult.getFieldErrors()) {
                model.addAttribute(fe.getField() + "_error", true);
                log.trace("FieldError: {}", fe);
            }
            return "admin/trip/new";
        }

        if (createdTrip.getFromDate().after(createdTrip.getToDate())){
            model.addAttribute("fromDate_error", true);
            model.addAttribute("toDate_error", true);
            model.addAttribute("dateFail", true);

            return "admin/trip/new";
        }
        
        if (createdTrip.getPossibleExcursionId() != null){
            for (Long excursionId : createdTrip.getPossibleExcursionId()){
                Date excursionDate = excursionFacade.findExcursionById(excursionId).getFromDate(); 
                if (!((excursionDate.after(createdTrip.getFromDate()) || excursionDate.equals(createdTrip.getFromDate())) &&
                        (excursionDate.before(createdTrip.getToDate()) || excursionDate.equals(createdTrip.getToDate())))){
                    model.addAttribute("excursionDateFail", true);
                    
                    return "admin/trip/new";
                }
            }
        }
        
        tripFacade.createTrip(createdTrip);

        redirectAttributes.addFlashAttribute("alert_success", "Trip was created");
        return "redirect:list";
    }
    
    public TripUpdateDTO mapTripDTOtoTripUpdateDTO(TripDTO tripDTO){
        TripUpdateDTO tripUpdateDTO = new TripUpdateDTO();
        tripUpdateDTO.setFromDate(tripDTO.getFromDate());
        tripUpdateDTO.setToDate(tripDTO.getToDate());
        tripUpdateDTO.setCreatedDate(tripDTO.getCreatedDate());
        tripUpdateDTO.setCity(tripDTO.getAddressOfHotel().getCity());
        tripUpdateDTO.setStreet(tripDTO.getAddressOfHotel().getStreet());
        tripUpdateDTO.setCountry(tripDTO.getAddressOfHotel().getCountry());
        tripUpdateDTO.setNumberOfHouse(tripDTO.getAddressOfHotel().getNumberOfHouse());
        tripUpdateDTO.setId(tripDTO.getId());
        tripUpdateDTO.setPrice(tripDTO.getPrice());
        tripUpdateDTO.setFilePathToPicture(tripDTO.getFilePathToPicture());
        
        Set<Long> possibleExcursionsId = new HashSet<>();
        
        for (ExcursionDTO ex : tripDTO.getPossibleExcursions()){
            possibleExcursionsId.add(ex.getId());
        }
        
        tripUpdateDTO.setPossibleExcursionId(possibleExcursionsId);
        
        return tripUpdateDTO;
    }
    
    public TripDTO mapTripUpdateDTOtoTripDTO(TripUpdateDTO tripUpdateDTO){
        TripDTO tripDTO = new TripDTO();

        tripDTO.setFromDate(tripUpdateDTO.getFromDate());
        tripDTO.setToDate(tripUpdateDTO.getToDate());
        tripDTO.setCreatedDate(tripUpdateDTO.getCreatedDate());
        
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setCountry(tripUpdateDTO.getCountry());
        addressDTO.setCity(tripUpdateDTO.getCity());
        addressDTO.setStreet(tripUpdateDTO.getStreet());
        addressDTO.setNumberOfHouse(tripUpdateDTO.getNumberOfHouse());
        
        tripDTO.setAddressOfHotel(addressDTO);
        
        tripDTO.setId(tripUpdateDTO.getId());
        tripDTO.setPrice(tripUpdateDTO.getPrice());
        tripDTO.setFilePathToPicture(tripUpdateDTO.getFilePathToPicture());
        
        if (tripUpdateDTO.getPossibleExcursionId() != null){
            for (Long excursionID : tripUpdateDTO.getPossibleExcursionId()){
                tripDTO.addPossibleExcursion(excursionFacade.findExcursionById(excursionID));
            }
        }
        else
        {
            tripDTO.deleteAllPossibleExcursions();
        }
        
        return tripDTO;
    }
}