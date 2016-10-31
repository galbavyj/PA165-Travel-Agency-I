package cz.muni.fi.pa165.travelAgency.persistence.entity;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

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

    public Reservation(Customer customer,Date created, Trip trip, Set<Excursion> excursions){
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
        if (getCustomer() != null ? !getCustomer().equals(that.getCustomer()) : that.getCustomer() != null)
            return false;
        if (getCreated() != null ? !getCreated().equals(that.getCreated()) : that.getCreated() != null) return false;
        if (getTrip() != null ? !getTrip().equals(that.getTrip()) : that.getTrip() != null) return false;
        return getExcursions() != null ? getExcursions().equals(that.getExcursions()) : that.getExcursions() == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + (getCustomer() != null ? getCustomer().hashCode() : 0);
        result = 31 * result + (getCreated() != null ? getCreated().hashCode() : 0);
        result = 31 * result + (getTrip() != null ? getTrip().hashCode() : 0);
        result = 31 * result + (getExcursions() != null ? getExcursions().hashCode() : 0);
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
