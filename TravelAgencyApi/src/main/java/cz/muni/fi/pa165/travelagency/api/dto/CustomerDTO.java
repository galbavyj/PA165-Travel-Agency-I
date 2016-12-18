/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.travelagency.api.dto;

import cz.muni.fi.pa165.travelagency.api.enums.CustomerRole;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Martin
 */
public class CustomerDTO {
    
    private Long id;
    private String firstName;
    private String lastName;
    private String passwordHash;
    private String email;
    private AddressDTO address;
    private String phoneNumber;
    private Date created;
    private CustomerRole customerRole;
    private Set<ReservationDTO> reservations;

    public CustomerDTO() {
        reservations = new HashSet<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
    
    

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public CustomerRole getCustomerRole() {
        return customerRole;
    }

    public void setCustomerRole(CustomerRole customerRole) {
        this.customerRole = customerRole;
    }

    public boolean isAdmin() {
        return customerRole == CustomerRole.ADMIN;
    }

    public Set<ReservationDTO> getReservations() {
        return reservations;
    }

    public void setReservations(Set<ReservationDTO> reservations) {
        this.reservations = reservations;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + (this.firstName != null ? this.firstName.hashCode() : 0);
        hash = 59 * hash + (this.lastName != null ? this.lastName.hashCode() : 0);
        hash = 59 * hash + (this.email != null ? this.email.hashCode() : 0);
        hash = 59 * hash + (this.address != null ? this.address.hashCode() : 0);
        hash = 59 * hash + (this.phoneNumber != null ? this.phoneNumber.hashCode() : 0);
        hash = 59 * hash + (this.customerRole != null ? this.customerRole.hashCode() : 0);
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
        if (!(obj instanceof CustomerDTO)) {
            return false;
        }
        final CustomerDTO other = (CustomerDTO) obj;
        if ((this.firstName == null) ? (other.firstName != null) : !this.firstName.equals(other.firstName)) {
            return false;
        }
        if ((this.lastName == null) ? (other.lastName != null) : !this.lastName.equals(other.lastName)) {
            return false;
        }
        if ((this.email == null) ? (other.email != null) : !this.email.equals(other.email)) {
            return false;
        }
        if ((this.phoneNumber == null) ? (other.phoneNumber != null) : !this.phoneNumber.equals(other.phoneNumber)) {
            return false;
        }
        if (this.address != other.address && (this.address == null || !this.address.equals(other.address))) {
            return false;
        }
        if (this.customerRole != other.customerRole) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "customer firstName=";
    }
}
