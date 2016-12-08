/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import cz.muni.fi.pa165.travelagency.api.dto.CustomerAuthenticateDTO;
import cz.muni.fi.pa165.travelagency.api.dto.CustomerDTO;
import cz.muni.fi.pa165.travelagency.api.facade.CustomerFacade;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Martin
 */
@Controller
@RequestMapping("/authentication")
public class AuthenticationController {

    final static Logger log = LoggerFactory.getLogger(AuthenticationController.class);

    @Inject
    CustomerFacade userFacade;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String authForm(
            Model model,
            HttpServletRequest req,
            HttpServletResponse res) {
        log.error("request: GET /authentication/login");
        HttpSession session = req.getSession(true);
        if (session.getAttribute("authUser") != null) {
            return "redirect:/shopping";
        }
        return "authentication/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String authenticate(
            @RequestParam String email,
            @RequestParam String password,
            Model model,
            RedirectAttributes redirectAttributes,
            HttpServletRequest req,
            HttpServletResponse res) {
        log.error("request: POST /authentication/login");
        CustomerAuthenticateDTO authDTO = new CustomerAuthenticateDTO();
        authDTO.setEmail(email);
        authDTO.setPassword(password);
        boolean authenticated = userFacade.authenticateCustomer(authDTO);
        if (!authenticated) {
            redirectAttributes.addFlashAttribute("alert_info", "Wrong email or password");
            return "redirect:/admin/customer/list";
        }
        HttpSession session = req.getSession(true);
        CustomerDTO customer = userFacade.findCustomerByEmail(email);
        session.setAttribute("authUser", customer);
        redirectAttributes.addFlashAttribute("alert_info", "You have been logged in.");
        return "redirect:/shopping";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(Model model,
            RedirectAttributes redirectAttributes,
            HttpServletRequest req) {
        log.error("request: /authentication/logout");
        HttpSession session = req.getSession(true);
        session.removeAttribute("authUser");
        redirectAttributes.addFlashAttribute("alert_info", "You have been successfully logged out.");
        return "redirect:/";
    }
}
