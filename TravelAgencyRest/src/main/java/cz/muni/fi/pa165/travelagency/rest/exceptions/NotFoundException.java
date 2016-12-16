/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.travelagency.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author Lucia
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason="We couldn't find what you were looking for :(")
public class NotFoundException extends RuntimeException{
    
}
