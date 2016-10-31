/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.travelAgency.persistence.entity;

    
import enums.ExcursionType;
import enums.UserRole;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Lucia
 */
@Entity
public class Excursion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date from;

    @NotNull
    @Column(nullable = false)
    private int durationInHours;

    @Column(nullable = false)
    private String description;
    
    @NotNull
    @Column(unique = true)
    private String place;

    @NotNull
    @Column(nullable = false)
    private BigDecimal price;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    @ManyToOne()
    private Trip trip;
    
    @ManyToMany()
    private Set<Reservation> reservations;
    
    @NotNull
    @Column
    private ExcursionType excursionType;

    public ExcursionType getExcursionType() {
        return excursionType;
    }

    public void setExcursionType(ExcursionType excursionType) {
        this.excursionType = excursionType;
    }

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public int getDurationInHours() {
        return durationInHours;
    }

    public void setDurationInHours(int durationInHours) {
        this.durationInHours = durationInHours;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }   

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    
    public Set<Reservation> getReservations() {
        return Collections.unmodifiableSet(reservations);
    }

    public void setReservations(Set<Reservation> reservations) {
        this.reservations = reservations;
    }
    
    public void addReservation(Reservation reservation){
        this.reservations.add(reservation);
    }
    
    public Long getId() {
        return id;
    }
    
    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }
    
    public Excursion(){
        
    }
    
    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((place == null) ? 0 : place.hashCode());
                result = prime * result + ((description == null) ? 0 : description.hashCode());
                result = prime * result + ((price == null) ? 0 : price.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (! (obj instanceof Excursion))
			return false;
		Excursion other = (Excursion) obj;
		if (place == null) {
			if (other.getPlace() != null)
				return false;
		} else if (!place.equals(other.getPlace()))
			return false;
                if (description == null) {
			if (other.getDescription() != null)
				return false;
		} else if (!description.equals(other.getDescription()))
			return false;
		return true;
	}
        
    @Override
    public String toString() {
        return "Excursion{" + "id=" + id + ", place=" + place + ", description=" + description + ", from=" + from + ", duration in hours=" + durationInHours + ", price= " + price;
    }
}
