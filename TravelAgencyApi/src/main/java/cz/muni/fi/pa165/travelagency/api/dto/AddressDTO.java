/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.travelagency.api.dto;

/**
 *
<<<<<<< HEAD
 * @author behra
 */
class AddressDTO {
    
=======
 * @author Martin
 */
public class AddressDTO {
    
    private String country;
    private String city;
    private String street;
    private Integer numberOfHouse;

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

    public Integer getNumberOfHouse() {
        return numberOfHouse;
    }

    public void setNumberOfHouse(Integer numberOfHouse) {
        this.numberOfHouse = numberOfHouse;
    }
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + (this.country != null ? this.country.hashCode() : 0);
        hash = 23 * hash + (this.city != null ? this.city.hashCode() : 0);
        hash = 23 * hash + (this.street != null ? this.street.hashCode() : 0);
        hash = 23 * hash + (this.numberOfHouse != null ? this.numberOfHouse.hashCode() : 0);
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
        if (!(obj instanceof AddressDTO)) {
            return false;
        }
        final AddressDTO other = (AddressDTO) obj;
        if ((this.country == null) ? (other.country != null) : !this.country.equals(other.country)) {
            return false;
        }
        if ((this.city == null) ? (other.city != null) : !this.city.equals(other.city)) {
            return false;
        }
        if ((this.street == null) ? (other.street != null) : !this.street.equals(other.street)) {
            return false;
        }
        if (this.numberOfHouse != other.numberOfHouse && (this.numberOfHouse == null || !this.numberOfHouse.equals(other.numberOfHouse))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Address{" + " country=" + country + ", city=" + city + ", street=" + street + ", numberOfHouse=" + numberOfHouse.toString() + '}';
    }
>>>>>>> refs/remotes/origin/master
}
