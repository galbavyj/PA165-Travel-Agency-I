/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.travelAgency.persistence.entity;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Embedded;
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
    
    @Embedded
    private Address addressOfHotel;
    
    @NotNull
    @Column(nullable = false)
    private BigDecimal price;
    
    private String filePathToPicture;
     
    public Trip(){
        possibleExcursions = new HashSet<>();
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
    
    @Override
    public int hashCode() {
        int hash = 11;
        hash = 37 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 37 * hash + (this.to != null ? this.to.hashCode() : 0);
        hash = 37 * hash + (this.from != null ? this.from.hashCode() : 0);
        hash = 37 * hash + (this.possibleExcursions != null ? this.possibleExcursions.hashCode() : 0);
        hash = 37 * hash + (this.addressOfHotel != null ? this.addressOfHotel.hashCode() : 0);
        hash = 37 * hash + (this.price != null ? this.price.hashCode() : 0);
        hash = 37 * hash + (this.created != null ? this.created.hashCode() : 0);
        hash = 37 * hash + (this.filePathToPicture != null ? this.filePathToPicture.hashCode() : 0);

        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Trip other = (Trip) obj;
        if ((this.from == null) ? (other.from != null) : !this.from.equals(other.from)) {
            return false;
        }
        if ((this.to == null) ? (other.to != null) : !this.to.equals(other.to)) {
            return false;
        }
        if ((this.addressOfHotel == null) ? (other.addressOfHotel != null) : !this.addressOfHotel.equals(other.addressOfHotel)) {
            return false;
        }
        if ((this.price == null) ? (other.price != null) : !this.price.equals(other.price)) {
            return false;
        }
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        if (this.filePathToPicture != other.filePathToPicture && (this.filePathToPicture == null
                || !this.filePathToPicture.equals(other.filePathToPicture))) {
            return false;
        }
        if (this.created != other.created && (this.created == null || !this.created.equals(other.created))) {
            return false;
        }
        
        if (this.possibleExcursions != other.possibleExcursions && (this.possibleExcursions == null ||
                !this.possibleExcursions.equals(other.possibleExcursions))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Trip{" + "id=" + id.toString() + ", from=" + from.toString() + ", to=" + to.toString() + ", addressOfHotel=" + 
                addressOfHotel.toString() + ", price=" + price.toString() +  ", created=" + created.toString() + ", filePathToPicture=" +
                filePathToPicture + ", possibleExcursions=" + possibleExcursions.toString() + '}';
    }
    
    
    
}