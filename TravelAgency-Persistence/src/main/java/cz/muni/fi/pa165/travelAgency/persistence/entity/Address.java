/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.travelAgency.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Martin
 */
@Embeddable
public class Address {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private String country;
    
    @NotNull
    @Column(nullable = false)
    private String city;
    
    @NotNull
    @Column(nullable = false)
    private String street;
    
    @NotNull
    @Column(nullable = false)
    private Integer numberOfHouse;

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 23 * hash + (this.country != null ? this.country.hashCode() : 0);
        hash = 23 * hash + (this.city != null ? this.city.hashCode() : 0);
        hash = 23 * hash + (this.street != null ? this.street.hashCode() : 0);
        hash = 23 * hash + (this.numberOfHouse != null ? this.numberOfHouse.hashCode() : 0);
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
        final Address other = (Address) obj;
        if ((this.country == null) ? (other.country != null) : !this.country.equals(other.country)) {
            return false;
        }
        if ((this.city == null) ? (other.city != null) : !this.city.equals(other.city)) {
            return false;
        }
        if ((this.street == null) ? (other.street != null) : !this.street.equals(other.street)) {
            return false;
        }
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        if (this.numberOfHouse != other.numberOfHouse && (this.numberOfHouse == null || !this.numberOfHouse.equals(other.numberOfHouse))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Address{" + "id=" + id + ", country=" + country + ", city=" + city + ", street=" + street + ", numberOfHouse=" + numberOfHouse.toString() + '}';
    }
}
