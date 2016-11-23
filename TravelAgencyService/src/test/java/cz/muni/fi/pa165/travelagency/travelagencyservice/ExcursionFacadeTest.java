/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.travelagency.travelagencyservice;

import cz.muni.fi.pa165.travelAgency.persistence.entity.Excursion;
import cz.muni.fi.pa165.travelagency.api.dto.ExcursionDTO;
import cz.muni.fi.pa165.travelagency.api.enums.ExcursionType;
import cz.muni.fi.pa165.travelagency.api.facade.ExcursionFacade;
import facade.ExcursionFacadeImpl;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import org.hibernate.service.spi.ServiceException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import service.ExcursionService;

/**
 *
 * @author Patrik Behrami
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class ExcursionFacadeTest extends AbstractTestNGSpringContextTests {
    @Mock
    private ExcursionService excursionService;
    
    @Mock
    private MappingService mappingService;
    
    @InjectMocks
    private ExcursionFacade excursionFacade = new ExcursionFacadeImpl();
    
    private Excursion excursion1;
    private Excursion excursion2;
        
    private ExcursionDTO excursionDTO1;
    private ExcursionDTO excursionDTO2;
    
    @BeforeClass
    void setUpClass() throws ServiceException{
        MockitoAnnotations.initMocks(this);
    }
    
    @BeforeMethod
    public void setUpMethod() throws Exception{
        Calendar calendar = Calendar.getInstance();
        calendar.set(2016, 11,21,10,0);
        Date created1 = calendar.getTime();
        
        calendar.set(2016, 12,20,11,0);
        Date fromDate1 = calendar.getTime();
       
        calendar.set(2016, 11,21,11,0);
        Date created2 = calendar.getTime();
        
        calendar.set(2016, 12,15,9,0);
        Date fromDate2 = calendar.getTime();
        
        excursion1 = new Excursion();
        excursion1.setCreated(created1);
        excursion1.setDescription("Basic tour around one of the biggest museums in Poland");
        excursion1.setDurationInHours(3);
        excursion1.setFromDate(fromDate1);
        excursion1.setExcursionType(ExcursionType.EDUCATIONAL);
        excursion1.setPlace("Technical Museum of Warsaw");
        excursion1.setPrice(BigDecimal.valueOf(20));
     
        excursion2 = new Excursion();
        excursion2.setCreated(created2);
        excursion2.setDescription("One of the most viewed football matches in the Europe - El Clasico");
        excursion1.setDurationInHours(2);
        excursion1.setFromDate(fromDate2);
        excursion1.setExcursionType(ExcursionType.ENTERTAINMENT);
        excursion1.setPlace("Nou Camp stadion in Barcelona");
        excursion1.setPrice(BigDecimal.valueOf(50));
        
       /* excursionDTO1 = new ExcursionDTO();
        excursionDTO1.setCreated(created2);
        excursionDTO1.setDescription("One of the most viewed football matches in the Europe - El Clasico");
        excursionDTO1.setDurationInHours(2);
        excursionDTO1.setFromDate(fromDate2);
        excursionDTO1.setExcursionType(ExcursionType.ENTERTAINMENT);
        excursionDTO1.setPlace("Nou Camp stadion in Barcelona");
        excursionDTO1.setPrice(BigDecimal.valueOf(50));*/
        
        excursionDTO1 = mappingService.mapTo(excursion1, ExcursionDTO.class);
        excursionDTO2 = mappingService.mapTo(excursion2, ExcursionDTO.class);
        
        when(mappingService.mapTo(excursionDTO1, Excursion.class)).thenReturn(excursion1);
    }
    
    @Test
    public void testCreateExcursion(){
        excursion1.setId(new Long(1));
        when(excursionService.createExcursion(excursion1)).thenReturn(excursion1);
        excursionFacade.createExcursion(excursionDTO1);
        verify(excursionService).createExcursion(excursion1);
    }
    
    @Test
    public void testRemoveExcursion(){
        excursionFacade.removeExcursion(excursionDTO1);
        verify(excursionService).removeExcursion(excursion1);
        
    }
    
    @Test
    public void testUpdateExcursion(){
        excursionFacade.updateExcursion(excursionDTO1);
        verify(excursionService).updateExcursion(excursion1);
    }
    
    @Test
    public void testFindById(){
        excursion1.setId(new Long(1));
        when(excursionService.findExcursionById(excursion1.getId())).thenReturn(excursion1);
        ExcursionDTO excursionReturned = excursionFacade.findExcursionById(excursion1.getId());
        Assert.assertEquals(mappingService.mapTo(excursionReturned, Excursion.class),excursion1);
    }
    
    @Test
    public void testFindAll(){
        List<Excursion> excursionList = Arrays.asList(excursion1,excursion2);
        when(excursionService.findAllExcursions()).thenReturn(Arrays.asList(excursion1,excursion2));
        when(mappingService.mapTo(Arrays.asList(excursion1,excursion2), ExcursionDTO.class)).thenReturn(Arrays.asList(excursionDTO1,excursionDTO2));
        when(mappingService.mapTo(Arrays.asList(excursionDTO1,excursionDTO2), Excursion.class)).thenReturn(Arrays.asList(excursion1,excursion2));
        List<ExcursionDTO> excursionListReturned = excursionFacade.findAllExcursions();
        Assert.assertEquals(mappingService.mapTo(excursionListReturned, Excursion.class),excursionList);
    }
    
    @Test
    public void testChangeDescription(){
        String newDescription = "Tour around polish museum";
        excursionFacade.changeDescription(excursionDTO1, newDescription);
        verify(excursionService).updateExcursion(excursion1);
        Assert.assertEquals(newDescription,excursion1.getDescription());
    }
    
    @Test
    public void testChangePrice(){
        BigDecimal newPrice = BigDecimal.valueOf(500);
        excursionFacade.changePrice(excursionDTO1, newPrice);
        verify(excursionService).updateExcursion(excursion1);
        Assert.assertEquals(newPrice,excursion1.getPrice());
    }
}
