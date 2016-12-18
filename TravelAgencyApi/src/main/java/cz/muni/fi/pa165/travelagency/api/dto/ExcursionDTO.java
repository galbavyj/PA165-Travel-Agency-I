package cz.muni.fi.pa165.travelagency.api.dto;

import cz.muni.fi.pa165.travelagency.api.enums.ExcursionType;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

/**
 *
 * @author Lucia
 */
public class ExcursionDTO {
    private Long id;
    private Date fromDate;
    private int durationInHours;
    private String description;
    private String place;
    private BigDecimal price;
    private Date created;
    //private TripDTO trip;
    //private Set<ReservationDTO> reservations;
    private ExcursionType excursionType;

    public ExcursionDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
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

    /*public TripDTO getTrip() {
        return trip;
    }

    public void setTrip(TripDTO trip) {
        this.trip = trip;
    }

    public Set<ReservationDTO> getReservations() {
        return reservations;
    }

    public void setReservations(Set<ReservationDTO> reservations) {
        this.reservations = reservations;
    }*/

    public ExcursionType getExcursionType() {
        return excursionType;
    }

    public void setExcursionType(ExcursionType excursionType) {
        this.excursionType = excursionType;
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
		if (! (obj instanceof ExcursionDTO))
			return false;
		ExcursionDTO other = (ExcursionDTO) obj;
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
                if (price == null) {
			if (other.getPrice() != null)
				return false;
		} else if (!price.equals(other.getPrice()))
			return false;
		return true;
	}
}
