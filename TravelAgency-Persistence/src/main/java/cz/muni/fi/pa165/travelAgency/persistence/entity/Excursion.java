/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.travelAgency.persistence.entity;

    
import enums.UserRole;
import java.util.Date;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
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
    private int duration;

    @Column(nullable = false)
    private String description;
    
    @NotNull
    @Column(unique = true)
    private String place;

    @NotNull
    @Column(nullable = false)
    private int price;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    //@ManyToMany()
    //private Set<Trip> trips;
    
    @ManyToMany()
    private Set<Reservation> reservations;

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    
    public Set<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(Set<Reservation> reservations) {
        this.reservations = reservations;
    }
    
    public Long getId() {
        return id;
    }
    
    public Excursion(){
        
    }
}
