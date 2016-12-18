package controllers;

import cz.muni.fi.pa165.travelagency.api.dto.*;
import cz.muni.fi.pa165.travelagency.api.facade.ExcursionFacade;
import cz.muni.fi.pa165.travelagency.api.facade.ReservationFacade;
import cz.muni.fi.pa165.travelagency.api.facade.TripFacade;
import cz.muni.fi.pa165.travelagency.travelagencyservice.TravelAgencyPersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;


/**
 * @author Juraj
 */
@Controller
@RequestMapping("/shopping")
public class ShoppingController {

    final static Logger log = LoggerFactory.getLogger(ShoppingController.class);

    @Inject
    TripFacade tripFacade;

    @Inject
    ExcursionFacade excursionFacade;

    @Inject
    ReservationFacade reservationFacade;

    @RequestMapping(method = RequestMethod.GET)
    public String showAvailableTrips(Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        log.error("request: GET /shopping");

        HttpSession session = request.getSession(true);
        if (session.getAttribute("authUser") == null) {
            redirectAttributes.addFlashAttribute("alert_danger", "You do not have authentication to visit this page.");
            return "redirect:/";
        }
        model.addAttribute("trips", tripFacade.findAllTrips());
        return "shopping/shopping_list";
    }

    @RequestMapping(value = "/trip/{id}", method = RequestMethod.GET)
    public String showTrip(@PathVariable("id") long id, Model model, HttpServletRequest request,
                           RedirectAttributes redirectAttributes) {
        log.error("request: GET /shopping/trip/" + id);

        HttpSession session = request.getSession(true);
        if (session.getAttribute("authUser") == null) {
            redirectAttributes.addFlashAttribute("alert_danger", "You do not have authentication to visit this page.");
            return "redirect:/";
        }

        TripDTO tripDTO = tripFacade.findTripById(id);
        if (tripDTO == null) {
            return "redirect:/shopping";
        }
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setTripID(tripDTO.getId());
        model.addAttribute("createReservation", reservationDTO);
        model.addAttribute("trip", tripDTO);
        model.addAttribute("countExcursion", tripDTO.getPossibleExcursions().size());
        return "shopping/trip_reservation";
    }

    @RequestMapping(value = "/makeReservation", method = RequestMethod.POST)
    public String reserve(@ModelAttribute("createReservation") ReservationDTO formBean, Model model,
                          RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder,
                          HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession(true);
        CustomerDTO customerDTO = (CustomerDTO) session.getAttribute("authUser");
        if (customerDTO == null) {
            redirectAttributes.addFlashAttribute("alert_danger", "You do not have authentication to visit this page.");
            return "redirect:/";
        }

        formBean.setTrip(tripFacade.findTripById(formBean.getTripID()));

        if(formBean.getExcursionsID() != null){
            for(Long id : formBean.getExcursionsID()) {
                formBean.getExcursions().add(excursionFacade.findExcursionById(id));
            }
        }

        formBean.setCustomer(customerDTO);
        formBean.setCreated(new Date());
        try {
            Long id = reservationFacade.createReservation(formBean);
            redirectAttributes.addFlashAttribute("alert_success", "Reservation " + id + " was created");
            return "redirect:" + uriBuilder.path("/shopping/reservation/{id}").buildAndExpand(id).encode().toUriString();
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("alert_danger", "Unable to create reservation: " + e.getMessage());
        }
        return "redirect:" + uriBuilder.path("/shopping").build().encode().toUriString();
    }

    @RequestMapping(value = "/excursion/{id}", method = RequestMethod.GET)
    public String showExcursion(@PathVariable("id") long id, Model model, HttpServletRequest request,
                                RedirectAttributes redirectAttributes) {
        log.error("request: GET /shopping/excursion/" + id);

        HttpSession session = request.getSession(true);
        if (session.getAttribute("authUser") == null) {
            redirectAttributes.addFlashAttribute("alert_danger", "You do not have authentication to visit this page.");
            return "redirect:/";
        }

        ExcursionDTO t = excursionFacade.findExcursionById(id);
        if (t == null) {
            return "redirect:/shopping";
        }
        model.addAttribute("excursion", t);
        return "shopping/excursion_view";
    }



    @RequestMapping(value = "/reservations", method = RequestMethod.GET)
    public String listReservations(Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        log.error("request: GET /shopping/reservations");

        HttpSession session = request.getSession(true);
        CustomerDTO customerDTO = (CustomerDTO) session.getAttribute("authUser");
        if (customerDTO == null) {
            redirectAttributes.addFlashAttribute("alert_danger", "You do not have authentication to visit this page.");
            return "redirect:/";
        }

        List<ReservationDTO>  reservations = reservationFacade.findReservationsByCustomer(customerDTO.getId());
        for(ReservationDTO res : reservations) {
            res.setTotalPrice(reservationFacade.getTotalPriceOfReservation(res.getId()));
        }
        model.addAttribute("reservations", reservations);
        return "/shopping/reservations_list";
    }


    @RequestMapping(value = "/reservation/{id}", method = RequestMethod.GET)
    public String showReservation(@PathVariable("id") long id, Model model,
            RedirectAttributes redirectAttributes, HttpServletRequest request) {
        log.error("request: GET /shopping/reservation/view/" + id);
        ReservationDTO reservationDTO = reservationFacade.findReservationsById(id);
        if (reservationDTO == null) {
            return "redirect:/shopping/reservations";
        }
        HttpSession session = request.getSession(true);
        if (!reservationDTO.getCustomer().getId().equals(((CustomerDTO) session.getAttribute("authUser")).getId())) {
            redirectAttributes.addFlashAttribute("alert_danger", "You do not have authentication to visit this page.");
            return "redirect:/";
        }
        reservationDTO.setTotalPrice(reservationFacade.getTotalPriceOfReservation(reservationDTO.getId()));
        model.addAttribute("countExcursion", reservationDTO.getExcursions().size());
        model.addAttribute("reservation", reservationDTO);
        return "/shopping/reservation_view";
    }



    @RequestMapping(value = "/reservation/delete/{id}", method = RequestMethod.POST)
    public String deleteReservation(@PathVariable("id") long id, Model model,
            RedirectAttributes redirectAttributes, HttpServletRequest request) {

        HttpSession session = request.getSession(true);
        log.error("request: POST /shopping/reservation/delete/" + id);
        try {
            ReservationDTO reservationDTO = reservationFacade.findReservationsById(id);
            if (reservationDTO == null || !reservationDTO.getCustomer().getId().equals(((CustomerDTO) session.getAttribute("authUser")).getId())) {
                redirectAttributes.addFlashAttribute("alert_danger", "You do not have authentication to visit this page.");
                return "redirect:/";
            }
            reservationFacade.removeReservation(reservationDTO);
            redirectAttributes.addFlashAttribute("alert_success", "Reservation " + id + " has been deleted.");
        } catch(TravelAgencyPersistenceException e) {
            redirectAttributes.addFlashAttribute("alert_danger", "Deletion of reservation " + id + " has failed.");
        }
        return "redirect:/shopping/reservations";
    }
}

