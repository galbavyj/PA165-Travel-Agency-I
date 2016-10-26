/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.travelAgency.persistence.entity;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Patrik Behrami
 */
@Entity
public class Trip {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;   
    
    @NotNull
    @Column(nullable = false)
    private Date from;
    
    @NotNull
    @Column(nullable = false)
    private Date to;
    
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    
    @OneToMany
    private Set<Excursion> possibleExcursions;
    
    @OneToOne
    private Address addressOfHotel;
    
    @NotNull
    @Column(nullable = false)
    private BigDecimal price;

    public Trip(){
        
    }
    
    public Trip(Date from, Date to, Date created, Set<Excursion> possibleExcursions, Address addressOfHotel, BigDecimal price) {
        this.from = from;
        this.to = to;
        this.created = created;
        this.possibleExcursions = possibleExcursions;
        this.addressOfHotel = addressOfHotel;
        this.price = price;
    }

    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Set<Excursion> getPossibleExcursions() {
        return Collections.unmodifiableSet(possibleExcursions);
    }

    public void setPossibleExcursions(Set<Excursion> possibleExcursions) {
        this.possibleExcursions = possibleExcursions;
    }

    public Address getAddressOfHotel() {
        return addressOfHotel;
    }

    public void setAddressOfHotel(Address addressOfHotel) {
        this.addressOfHotel = addressOfHotel;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    
    
    
    
}
