/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.travelagency.travelagencyservice;

import cz.muni.fi.pa165.travelAgency.persistence.entity.Address;
import cz.muni.fi.pa165.travelAgency.persistence.entity.Customer;
import cz.muni.fi.pa165.travelagency.api.dto.AddressDTO;
import cz.muni.fi.pa165.travelagency.api.dto.CustomerDTO;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import service.CustomerServiceImpl;
import service.ExcursionServiceImpl;
import travelAgency.TravelAgencyPersistenceContext;

@Configuration
@Import(TravelAgencyPersistenceContext.class)
@ComponentScan(basePackageClasses={CustomerServiceImpl.class})
public class ServiceConfiguration {

	@Bean
	public Mapper dozer(){
		return new DozerBeanMapper();	
	}
	public class DozerCustomConfig extends BeanMappingBuilder {
	    @Override
	    protected void configure() {
	        mapping(Customer.class, CustomerDTO.class);
	        mapping(Address.class, AddressDTO.class);
	    }
	}
}

