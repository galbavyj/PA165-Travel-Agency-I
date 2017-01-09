package cz.muni.fi.pa165.travelagency.api.dto;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.awt.Image;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;

/**
 * @Patrik Behrami
 */
public class TripCreateDTO {
  
    @NotNull(message = "Please enter a date")
    @Future(message = "Date must be in the future")
    private Date fromDate;
    
    @NotNull(message = "Please enter a date")
    @Future(message = "Date must be in the future")
    private Date toDate;
    
    private Set<Long> possibleExcursionId;
    
    @NotBlank(message = "Please enter a value.")
    @Size(max = 30, message = "Please don't enter more than 30 characters.")
    private String country;
    
    @NotBlank(message = "Please enter a value.")
    @Size(max = 30, message = "Please don't enter more than 30 characters.")
    private String city;
    
    @NotBlank(message = "Please enter a value.")
    @Size(max = 30, message = "Please don't enter more than 30 characters.")
    private String street;
    
    @NotNull(message = "Please enter a value")
    @Min(value = 1, message = "Number of house can't be negative or zero")
    private int numberOfHouse;
    
    @NotNull(message = "Please enter a value")
    @DecimalMin(value = "1", message = "Price can't be negative or zero")
    private BigDecimal price;
    
    private Image image;

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public Set<Long> getPossibleExcursionId() {
        return possibleExcursionId;
    }

    public void setPossibleExcursionId(Set<Long> possibleExcursionId) {
        this.possibleExcursionId = possibleExcursionId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getNumberOfHouse() {
        return numberOfHouse;
    }

    public void setNumberOfHouse(int numberOfHouse) {
        this.numberOfHouse = numberOfHouse;
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
        hash = 37 * hash + (this.toDate != null ? this.toDate.hashCode() : 0);
        hash = 37 * hash + (this.fromDate != null ? this.fromDate.hashCode() : 0);
        hash = 37 * hash + (this.price != null ? this.price.hashCode() : 0);

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
        final TripCreateDTO other = (TripCreateDTO) obj;
        if ((this.fromDate == null) ? (other.fromDate != null) : !this.fromDate.equals(other.fromDate)) {
            return false;
        }
        if ((this.toDate == null) ? (other.toDate != null) : !this.toDate.equals(other.toDate)) {
            return false;
        }
        if ((this.price == null) ? (other.price != null) : !this.price.equals(other.price)) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "Trip{--";
    }
    
    
}
