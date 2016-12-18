/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.travelagency.travelagencyservice;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import service.CustomerServiceImpl;
import travelAgency.TravelAgencyPersistenceContext;

@Configuration
@Import(TravelAgencyPersistenceContext.class)
@ComponentScan(basePackageClasses={CustomerServiceImpl.class})
public class ServiceConfiguration {

	@Bean
	public Mapper dozer(){
		return new DozerBeanMapper();	
	}
}

