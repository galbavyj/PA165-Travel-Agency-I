/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.travelagency.api.dto;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Juraj
 */
public class ReservationDTO {

    private Long id;
    private CustomerDTO customer;
    private Date created;
    private TripDTO trip;
    private Set<ExcursionDTO> excursions = new HashSet<>();
    private BigDecimal totalPrice;

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }


    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CustomerDTO getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDTO customer) {
        this.customer = customer;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public TripDTO getTrip() {
        return trip;
    }

    public void setTrip(TripDTO trip) {
        this.trip = trip;
    }

    public Set<ExcursionDTO> getExcursions() {
        return excursions;
    }

    public void setExcursions(Set<ExcursionDTO> excursions) {
        this.excursions = excursions;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReservationDTO)) return false;

        ReservationDTO that = (ReservationDTO) o;

        if (getCreated() != null ? !getCreated().equals(that.getCreated()) : that.getCreated() != null) return false;
        if (getTrip() != null ? !getTrip().equals(that.getTrip()) : that.getTrip() != null) return false;
        return getExcursions() != null ? getExcursions().equals(that.getExcursions()) : that.getExcursions() == null;

    }

    @Override
    public int hashCode() {
        int result = getCreated() != null ? getCreated().hashCode() : 0;
        result = 31 * result + (getTrip() != null ? getTrip().hashCode() : 0);
        result = 31 * result + (getExcursions() != null ? getExcursions().hashCode() : 0);
        return result;
    }
}
