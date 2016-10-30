package cz.muni.fi.pa165.travelAgency.persistence.entity;

import javax.validation.constraints.NotNull;

import javax.persistence.*;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Class of Reservation
 *
 * @author Juraj Galbavý
 */

@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @ManyToOne
    private Customer customer;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    @NotNull
    @ManyToOne
    private Trip trip;

    @NotNull
    @ManyToMany
    private Set<Excursion> excursions = new HashSet<>();


    public Reservation(){}

    public Reservation(long id, Customer customer,Date created, Trip trip, Set<Excursion> excursions){
        this.id = id;
        this.customer = customer;
        this.trip = trip;
        this.excursions = excursions;
        this.created = created;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public Set<Excursion> getExcursions() {
        return Collections.unmodifiableSet(excursions);
    }

    public void setExcursions(Set<Excursion> excursions) {
        this.excursions = excursions;
    }

    public void addExcursions(Excursion excursion) {
        this.excursions.add(excursion);
    }

    public void deleteExcursion(Excursion excursion){
        this.excursions.remove(excursion);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Reservation)) return false;

        Reservation that = (Reservation) o;

        if (getId() != that.getId()) return false;
        if (!getCustomer().equals(that.getCustomer())) return false;
        if (!getCreated().equals(that.getCreated())) return false;
        if (!getTrip().equals(that.getTrip())) return false;
        return getExcursions().equals(that.getExcursions());

    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + getCustomer().hashCode();
        result = 31 * result + getCreated().hashCode();
        result = 31 * result + getTrip().hashCode();
        result = 31 * result + getExcursions().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", customer=" + customer +
                ", created=" + created +
                ", trip=" + trip +
                ", excursions=" + excursions +
                '}';
    }
}
