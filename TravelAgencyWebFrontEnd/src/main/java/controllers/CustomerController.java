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

import cz.muni.fi.pa165.travelagency.api.facade.CustomerFacade;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Martin
 */
@Controller
@RequestMapping("/admin/customer")
public class CustomerController {

    final static Logger log = LoggerFactory.getLogger(CustomerController.class);

    @Inject
    private CustomerFacade userFacade;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("customers", userFacade.findAllCustomers());
        return "/admincustomer/list";
    }
}