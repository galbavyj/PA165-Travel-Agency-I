package cz.muni.fi.pa165.travelagency.api.dto;

import cz.muni.fi.pa165.travelagency.api.enums.ExcursionType;
import java.math.BigDecimal;
import java.util.Date;
import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author Lucia
 */
public class ExcursionDTO {
    private Long id;
    
    @NotNull(message = "Please, choose a date from")
    @Future(message = "Date must be in the future")
    private Date fromDate;
    
    @NotNull(message = "Please, choose a duration in hours")
    @Min(value = 0, message = "Duration of excursion can't be negative")
    private int durationInHours;
    
    @NotBlank(message = "Please, choose a description")
    private String description;
    
    @NotBlank(message = "Please, choose a place of the excursion")
    private String place;
    
    @NotNull(message = "Please, choose a price")
    @Min(value = 0, message = "Price of excursion can't be negative")
    private BigDecimal price;
    
    private Date created;
    
    @NotNull(message = "Please, choose a type of the excursion")
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
