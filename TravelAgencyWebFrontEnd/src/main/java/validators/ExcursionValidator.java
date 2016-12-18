/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package validators;

import cz.muni.fi.pa165.travelagency.api.dto.ExcursionDTO;
import java.util.Date;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 *
 * @author Lucia
 */
public class ExcursionValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return ExcursionDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ExcursionDTO excursionDTO = (ExcursionDTO) target;
        if (excursionDTO.getDescription() == null) errors.reject("Can't be null");
        if (excursionDTO.getFromDate() == null) errors.reject("Can't be null");
        if (excursionDTO.getPlace() == null) errors.reject("Can't be null");
        if (excursionDTO.getDescription() == null) errors.reject("Can't be null");
        if (excursionDTO.getPrice() == null) errors.reject("Can't be null");
        Date today = new Date();
        if (excursionDTO.getFromDate().before(today)){
            errors.reject("Date from can't be in the past.");
        }
        
    }
    
}
