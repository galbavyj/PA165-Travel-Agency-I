/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.travelagency.api.dto;

import java.util.Date;

/**
 *
 * @author behra
 */
public class TripUpdateDTO extends TripCreateDTO{
    
    private Long id;
    private Date createdDate;
    private String filePathToPicture;

    public String getFilePathToPicture() {
        return filePathToPicture;
    }

    public void setFilePathToPicture(String filePathToPicture) {
        this.filePathToPicture = filePathToPicture;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    
}
