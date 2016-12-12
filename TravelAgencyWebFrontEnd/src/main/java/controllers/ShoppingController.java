package controllers;

import cz.muni.fi.pa165.travelagency.api.dto.CustomerDTO;
import cz.muni.fi.pa165.travelagency.api.dto.ExcursionDTO;
import cz.muni.fi.pa165.travelagency.api.dto.ReservationDTO;
import cz.muni.fi.pa165.travelagency.api.dto.TripDTO;
import cz.muni.fi.pa165.travelagency.api.facade.ExcursionFacade;
import cz.muni.fi.pa165.travelagency.api.facade.ReservationFacade;
import cz.muni.fi.pa165.travelagency.api.facade.TripFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Juraj
 */
@Controller
@RequestMapping("/shopping")
public class ShoppingController {

    final static Logger log = LoggerFactory.getLogger(ShoppingController.class);

    @Autowired
    TripFacade tripFacade;

    @Autowired
    ExcursionFacade excursionFacade;

    @Autowired
    ReservationFacade reservationFacade;

    @RequestMapping(method = RequestMethod.GET)
    public String show(Model model) {
        log.error("request: GET /shopping");
        model.addAttribute("trips", tripFacade.findAllTrips());
        return "shopping/shopping_list";
    }

    @RequestMapping(value = "/trip/{id}", method = RequestMethod.GET)
    public String showTrip(@PathVariable("id") long id, Model model) {
        log.error("request: GET /shopping/trip/" + id);
        TripDTO tripDTO = tripFacade.findTripById(id);
        if (tripDTO == null) {
            return "redirect:/shopping";
        }
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setId(id);
        model.addAttribute("createReservation", reservationDTO);
        model.addAttribute("trip", tripDTO);
        return "shopping/trip_view";
    }

    @RequestMapping(value = "/excursion/{id}", method = RequestMethod.GET)
    public String showExcursion(@PathVariable("id") long id, Model model) {
        log.error("request: GET /shopping/excursion/" + id);
        ExcursionDTO t = excursionFacade.findExcursionById(id);
        if (t == null) {
            return "redirect:/shopping";
        }
        model.addAttribute("excursion", t);
        return "shopping/excursion_view";
    }

    @RequestMapping(value = "/reserve", method = RequestMethod.POST)
    public String reserve(
            @ModelAttribute("createReservation") ReservationDTO formBean,
            Model model,
            RedirectAttributes redirectAttributes,
            UriComponentsBuilder uriBuilder,
            HttpServletRequest req,
            HttpServletResponse res
    ) {
        log.debug("create(productCreate={})", formBean);

        HttpSession session = req.getSession(true);

        try {
            Long id = reservationFacade.createReservation(formBean);
            redirectAttributes.addFlashAttribute("alert_success", "Reservation " + id + " was created");
            return "redirect:" + uriBuilder.path("/shopping/reservations/{id}").buildAndExpand(id).encode().toUriString();
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("alert_danger", "Unable to create reservation: " + e.getMessage());
        }
        return "redirect:" + uriBuilder.path("/shopping").build().encode().toUriString();
    }

    @RequestMapping(value = "/reservations", method = RequestMethod.GET)
    public String showCustomersReservations(
            HttpServletRequest req,
            Model model) {
        log.error("request: GET /shopping/reservations");
        CustomerDTO customerDTO = (CustomerDTO) req.getAttribute("authCustomer");
        model.addAttribute("reservations", reservationFacade.findReservationsByCustomer(customerDTO.getId()));
        return "/shopping/reservations_list";
    }

    @RequestMapping(value = "/reservations/{id}", method = RequestMethod.GET)
    public String showCustomersReservation(
            @PathVariable("id") long id,
            HttpServletRequest req,
            Model model) {
        log.error("request: GET /shopping/reservation/view/" + id);
        ReservationDTO reservationDTO = reservationFacade.findReservationsById(id);
        if (reservationDTO == null) {
            return "redirect:/shopping/reservations";
        }
        if (!reservationDTO.getCustomer().equals((CustomerDTO) req.getAttribute("authUser"))) {
            return "redirect:/shopping/reservations";
        }
        model.addAttribute("reservation", reservationDTO);
        return "/shopping/reservation_view";
    }

    @RequestMapping(value = "/reservations/{id}/delete", method = RequestMethod.POST)
    public String deleteCustomersReservation(
            @PathVariable("id") long id,
            RedirectAttributes redirectAttributes,
            HttpServletRequest req,
            Model model) {
        log.error("request: POST /shopping/reservations/" + id + "/delete");
        try {
            ReservationDTO reservationDTO = reservationFacade.findReservationsById(id);
            if (reservationDTO != null && reservationDTO.getCustomer().equals((CustomerDTO) req.getAttribute("authUser"))) {
                reservationFacade.removeReservation(reservationDTO);
                redirectAttributes.addFlashAttribute("alert_success", "Reservation deleted.");
            }
        } catch(Exception e) {
            redirectAttributes.addFlashAttribute("alert_danger", "Deletion of specified reservation failed.");
        }
        return "redirect:/shopping/reservations";
    }
}

