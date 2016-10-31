package cz.muni.fi.pa165.travelAgency.persistence.entity;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Patrik Behrami
 */
@Entity
public class Trip {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;   
    
    @NotNull
    @Column(nullable = false)
    private Date fromDate;
    
    @NotNull
    @Column(nullable = false)
    private Date toDate;
    
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    
    @OneToMany
    private Set<Excursion> possibleExcursions;
    
    @Embedded
    private Address addressOfHotel;
    
    @NotNull
    @Column(nullable = false)
    private BigDecimal price;
    
    private String filePathToPicture;
     
    public Trip(){
        possibleExcursions = new HashSet<>();
    }
    
    public Trip(Date from, Date to, Date created, Set<Excursion> possibleExcursions, Address addressOfHotel, BigDecimal price) {
        this.fromDate = from;
        this.toDate = to;
        this.createdDate = created;
        this.possibleExcursions = possibleExcursions;
        this.addressOfHotel = addressOfHotel;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFrom() {
        return fromDate;
    }

    public void setFrom(Date from) {
        this.fromDate = from;
    }

    public Date getTo() {
        return toDate;
    }

    public void setTo(Date to) {
        this.toDate = to;
    }

    public Date getCreated() {
        return createdDate;
    }

    public void setCreated(Date created) {
        this.createdDate = created;
    }

    public Set<Excursion> getPossibleExcursions() {
        return Collections.unmodifiableSet(possibleExcursions);
    }

    public void setPossibleExcursions(Set<Excursion> possibleExcursions) {
        this.possibleExcursions = possibleExcursions;
    }

    public Address getAddressOfHotel() {
        return addressOfHotel;
    }

    public void setAddressOfHotel(Address addressOfHotel) {
        this.addressOfHotel = addressOfHotel;
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
        hash = 37 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 37 * hash + (this.toDate != null ? this.toDate.hashCode() : 0);
        hash = 37 * hash + (this.fromDate != null ? this.fromDate.hashCode() : 0);
        hash = 37 * hash + (this.possibleExcursions != null ? this.possibleExcursions.hashCode() : 0);
        hash = 37 * hash + (this.addressOfHotel != null ? this.addressOfHotel.hashCode() : 0);
        hash = 37 * hash + (this.price != null ? this.price.hashCode() : 0);
        hash = 37 * hash + (this.createdDate != null ? this.createdDate.hashCode() : 0);
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
        final Trip other = (Trip) obj;
        if ((this.fromDate == null) ? (other.fromDate != null) : !this.fromDate.equals(other.fromDate)) {
            return false;
        }
        if ((this.toDate == null) ? (other.toDate != null) : !this.toDate.equals(other.toDate)) {
            return false;
        }
        if ((this.addressOfHotel == null) ? (other.addressOfHotel != null) : !this.addressOfHotel.equals(other.addressOfHotel)) {
            return false;
        }
        if ((this.price == null) ? (other.price != null) : !this.price.equals(other.price)) {
            return false;
        }
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        if (this.filePathToPicture != other.filePathToPicture && (this.filePathToPicture == null
                || !this.filePathToPicture.equals(other.filePathToPicture))) {
            return false;
        }
        if (this.createdDate != other.createdDate && (this.createdDate == null || !this.createdDate.equals(other.createdDate))) {
            return false;
        }
        
        if (this.possibleExcursions != other.possibleExcursions && (this.possibleExcursions == null ||
                !this.possibleExcursions.equals(other.possibleExcursions))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Trip{" + "id=" + id.toString() + ", from=" + fromDate.toString() + ", to=" + toDate.toString() + ", addressOfHotel=" + 
                addressOfHotel.toString() + ", price=" + price.toString() +  ", created=" + createdDate.toString() + ", filePathToPicture=" +
                filePathToPicture + ", possibleExcursions=" + possibleExcursions.toString() + '}';
    }
    
    
    
}