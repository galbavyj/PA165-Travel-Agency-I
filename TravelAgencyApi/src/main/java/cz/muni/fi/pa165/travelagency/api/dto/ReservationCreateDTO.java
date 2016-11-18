package cz.muni.fi.pa165.travelagency.api.dto;

import javax.validation.constraints.NotNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Juraj
 */
public class ReservationCreateDTO {

    @NotNull
    private Long customerId;

    @NotNull
    private Long tripId;

    private Set<Long> excursionIDs = new HashSet<>();


    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Set<Long> getExcursionIDs() {
        return Collections.unmodifiableSet(excursionIDs);
    }

    public void setExcursionIDs(Set<Long> excursionIDs) {
        this.excursionIDs = excursionIDs;
    }

    public Long getTripId() {
        return tripId;
    }

    public void setTripId(Long tripId) {
        this.tripId = tripId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReservationCreateDTO)) return false;

        ReservationCreateDTO that = (ReservationCreateDTO) o;

        if (getCustomerId() != that.getCustomerId()) return false;
        if (getTripId() != that.getTripId()) return false;
        return getExcursionIDs() != null ? getExcursionIDs().equals(that.getExcursionIDs()) : that.getExcursionIDs() == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (getCustomerId() ^ (getCustomerId() >>> 32));
        result = 31 * result + (int) (getTripId() ^ (getTripId() >>> 32));
        result = 31 * result + (getExcursionIDs() != null ? getExcursionIDs().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ReservationCreateDTO{" +
                "customerId=" + customerId +
                ", tripId=" + tripId +
                ", excursionIDs=" + excursionIDs +
                '}';
    }
}
