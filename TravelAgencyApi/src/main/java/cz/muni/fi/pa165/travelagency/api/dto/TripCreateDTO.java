package cz.muni.fi.pa165.travelagency.api.dto;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @Patrik Behrami
 */
public class TripCreateDTO {
  
    private Date fromDate;
    private Date toDate;
    private Set<Long> possibleExcursionId;
    private String country;
    private String city;
    private String street;
    private int numberOfHouse;
    private BigDecimal price;
    private String filePathToPicture;

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

    public String getFilePathToPicture() {
        return filePathToPicture;
    }

    public void setFilePathToPicture(String filePathToPicture) {
        this.filePathToPicture = filePathToPicture;
    }
    
    
    
    @Override
    public int hashCode() {
        int hash = 11;
        hash = 37 * hash + (this.toDate != null ? this.toDate.hashCode() : 0);
        hash = 37 * hash + (this.fromDate != null ? this.fromDate.hashCode() : 0);
        hash = 37 * hash + (this.price != null ? this.price.hashCode() : 0);
        hash = 37 * hash + (this.filePathToPicture != null ? this.filePathToPicture.hashCode() : 0);

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

        if (this.filePathToPicture != other.filePathToPicture && (this.filePathToPicture == null
                || !this.filePathToPicture.equals(other.filePathToPicture))) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "Trip{--";
    }
    
    
}
