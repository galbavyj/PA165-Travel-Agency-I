package controllers;

import cz.muni.fi.pa165.travelagency.api.dto.CustomerDTO;
import cz.muni.fi.pa165.travelagency.api.dto.ReservationDTO;
import cz.muni.fi.pa165.travelagency.api.facade.CustomerFacade;
import cz.muni.fi.pa165.travelagency.api.facade.ReservationFacade;
import cz.muni.fi.pa165.travelagency.travelagencysampledata.SampleDataLoadingFacadeImpl;
import cz.muni.fi.pa165.travelagency.travelagencyservice.TravelAgencyPersistenceException;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @author Juraj
 */
@Controller
@RequestMapping("/admin/reservation")
public class ReservationController {

    final static Logger log = LoggerFactory.getLogger(SampleDataLoadingFacadeImpl.class);

    @Inject
    private ReservationFacade reservationFacade;

    @Inject
    private CustomerFacade customerFacade;

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public String deleteReservation(@PathVariable("id") long id, HttpServletRequest request,
                                    RedirectAttributes redirectAttributes, Model model) {
        log.error("request: POST /admin/reservation/delete/" + id);
        HttpSession session = request.getSession(true);
        CustomerDTO customer = (CustomerDTO) session.getAttribute("authUser");
        if (customer == null || !customer.isAdmin()) {
            redirectAttributes.addFlashAttribute("alert_danger", "You do not have authentication to visit this page.");
            return "redirect:/";
        }

        ReservationDTO reservationDTO = reservationFacade.findReservationsById(id);
        if (reservationDTO == null) {
            redirectAttributes.addFlashAttribute("alert_warning", "Unknown reservation.");
            return "redirect:/admin/reservation/list";
        }
        try {
            reservationFacade.removeReservation(reservationDTO);
            redirectAttributes.addFlashAttribute("alert_success", "Reservation " + id + " has been deleted.");
        } catch(TravelAgencyPersistenceException e) {
            redirectAttributes.addFlashAttribute("alert_danger", "Deletion of reservation " + id + " has failed.");
        }
        return "redirect:/admin/reservation/list";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String listReservations(Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        log.error("request: GET /admin/reservation/list");

        HttpSession session = request.getSession(true);
        CustomerDTO customer = (CustomerDTO) session.getAttribute("authUser");
        if (customer == null || !customer.isAdmin()) {
            redirectAttributes.addFlashAttribute("alert_danger", "You do not have authentication to visit this page.");
            return "redirect:/";
        }
        List<ReservationDTO>  reservations = reservationFacade.findAllReservations();
        for(ReservationDTO res : reservations){
            res.setTotalPrice(reservationFacade.getTotalPriceOfReservation(res.getId()));
        }
        model.addAttribute("reservations", reservations);

        return "/admin/reservation/list";
    }

    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public String showReservation(@PathVariable("id") long id, HttpServletRequest request,
            RedirectAttributes redirectAttributes, Model model) {

        log.error("request: GET /admin/reservation/view/" + id);

        HttpSession session = request.getSession(true);
        CustomerDTO customer = (CustomerDTO) session.getAttribute("authUser");
        if (customer == null || !customer.isAdmin()) {
            redirectAttributes.addFlashAttribute("alert_danger", "You do not have authentication to visit this page.");
            return "redirect:/";
        }

        ReservationDTO reservationDTO = reservationFacade.findReservationsById(id);
        if (reservationDTO == null) {
            redirectAttributes.addFlashAttribute("warning", "Unknown reservation");
            return "redirect:/admin/reservation/list";
        }
        reservationDTO.setTotalPrice(reservationFacade.getTotalPriceOfReservation(id));
        model.addAttribute("reservation", reservationDTO);
        model.addAttribute("countExcursion", reservationDTO.getExcursions().size());
        return "/admin/reservation/view";
    }
}

