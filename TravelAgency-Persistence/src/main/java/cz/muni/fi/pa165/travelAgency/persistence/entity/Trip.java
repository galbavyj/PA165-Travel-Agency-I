package cz.muni.fi.pa165.travelAgency.persistence.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Future;
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
    @Future
    private Date fromDate;
    
    @NotNull
    @Column(nullable = false)
    @Future
    private Date toDate;
    
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    
    @ManyToMany()
    private Set<Excursion> possibleExcursions;
    
    @Embedded
    private Address addressOfHotel;
    
    @NotNull
    @Column(nullable = false)
    @DecimalMin("1.0")
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

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Set<Excursion> getPossibleExcursions() {
        return possibleExcursions;
    }

    public void setPossibleExcursions(Set<Excursion> possibleExcursions) {
        this.possibleExcursions = possibleExcursions;
    }

    public void addPossibleExcursion(Excursion possibleExcursion){
        this.possibleExcursions.add(possibleExcursion);
    }
    
    public void removePossibleExcursion(Excursion possibleExcursion){
        this.possibleExcursions.remove(possibleExcursion);
    }
    
    public void deleteAllPossibleExcursions(){
        this.possibleExcursions.clear();
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

    public String getFilePathToPicture() {
        return filePathToPicture;
    }

    public void setFilePathToPicture(String filePathToPicture) {
        this.filePathToPicture = filePathToPicture;
    }
    
    @Override
    public int hashCode() {
        int hash = 11;
        hash = 37 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 37 * hash + (this.toDate != null ? this.toDate.hashCode() : 0);
        hash = 37 * hash + (this.fromDate != null ? this.fromDate.hashCode() : 0);
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
        return true;
    }

    @Override
    public String toString() {
        return "Trip{" + "id=" + id.toString() + ", from=" + fromDate.toString() + ", to=" + toDate.toString() + ", addressOfHotel=" + 
                addressOfHotel.toString() + ", price=" + price.toString() +  ", created=" + createdDate.toString() + ", filePathToPicture=" +
                filePathToPicture + ", possibleExcursions=" + possibleExcursions.toString() + '}';
    }
    
    
    
}