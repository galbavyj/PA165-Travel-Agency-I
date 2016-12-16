/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

/**
 *
 * @author Martin
 */
import cz.muni.fi.pa165.travelagency.api.dto.CustomerDTO;
import cz.muni.fi.pa165.travelagency.api.facade.CustomerFacade;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Martin
 */
@Controller
@RequestMapping("/admin/customer")
public class CustomerController {

    final static Logger log = LoggerFactory.getLogger(CustomerController.class);

    @Inject
    private CustomerFacade customerFacade;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model, RedirectAttributes redirectAttributes, HttpServletRequest req) {
        HttpSession session = req.getSession(true);
        CustomerDTO customer = (CustomerDTO) session.getAttribute("authUser");
        if (customer != null && customer.isAdmin()) {
            model.addAttribute("customers", customerFacade.findAllCustomers());
            return "/admin/customer/list";
        } else {
            redirectAttributes.addFlashAttribute("alert_danger", "You do not have authentication to visit this page.");
            return "redirect:/";
        }
    }
}
