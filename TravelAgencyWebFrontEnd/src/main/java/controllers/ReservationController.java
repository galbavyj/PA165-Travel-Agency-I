package controllers;

import cz.muni.fi.pa165.travelagency.api.dto.ReservationDTO;
import cz.muni.fi.pa165.travelagency.api.facade.CustomerFacade;
import cz.muni.fi.pa165.travelagency.api.facade.ReservationFacade;
import cz.muni.fi.pa165.travelagency.travelagencysampledata.SampleDataLoadingFacadeImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Juraj
 */
@Controller
@RequestMapping("/admin/reservation")
public class ReservationController {

    final static Logger log = LoggerFactory.getLogger(SampleDataLoadingFacadeImpl.class);

    @Autowired
    private ReservationFacade reservationFacade;

    @Autowired
    private CustomerFacade userFacade;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String listReservations(Model model, HttpServletRequest req) {
        log.error("request: GET /admin/reservation/list");
        model.addAttribute("reservations", reservationFacade.findAllReservations());
        return "/admin/reservation/list";
    }

    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public String showReservation(
            @PathVariable("id") long id,
            HttpServletRequest req,
            RedirectAttributes redirectAttributes,
            Model model) {

        log.error("request: GET /admin/reservation/view/" + id);
        ReservationDTO reservationDTO = reservationFacade.findReservationsById(id);
        if (reservationDTO == null) {
            redirectAttributes.addFlashAttribute("alert_warning", "Unknown reservation");
            return "redirect:/admin/reservation/list";
        }
        model.addAttribute("reservation", reservationDTO);
        return "/admin/reservation/view";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public String deleteReservation(
            @PathVariable("id") long id,
            HttpServletRequest req,
            RedirectAttributes redirectAttributes,
            Model model) {

        log.error("request: GET /admin/reservation/delete/" + id);
        ReservationDTO reservationDTO = reservationFacade.findReservationsById(id);
        if (reservationDTO == null) {
            redirectAttributes.addFlashAttribute("alert_warning", "Unknown reservation.");
            return "redirect:/admin/reservation/list";
        }
        try {
            reservationFacade.removeReservation(reservationDTO);
            redirectAttributes.addFlashAttribute("alert_success", "Reservation deleted.");
        } catch(Exception e) {
            redirectAttributes.addFlashAttribute("alert_danger", "Deletion of specified reservation failed.");
        }
        return "redirect:/admin/reservation/list";
    }
}

