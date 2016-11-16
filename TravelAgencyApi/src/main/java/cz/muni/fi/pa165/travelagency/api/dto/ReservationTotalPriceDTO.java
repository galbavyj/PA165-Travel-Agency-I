package cz.muni.fi.pa165.travelagency.api.dto;

import java.math.BigDecimal;

/**
 * @author Juraj
 */
public class ReservationTotalPriceDTO {

    private ReservationDTO reservation;
    private BigDecimal price;

    public ReservationDTO getReservation() {
        return reservation;
    }

    public void setReservation(ReservationDTO reservation) {
        this.reservation = reservation;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReservationTotalPriceDTO)) return false;

        ReservationTotalPriceDTO that = (ReservationTotalPriceDTO) o;

        if (reservation != null ? !reservation.equals(that.reservation) : that.reservation != null) return false;
        return price != null ? price.equals(that.price) : that.price == null;

    }

    @Override
    public int hashCode() {
        int result = reservation != null ? reservation.hashCode() : 0;
        result = 31 * result + (price != null ? price.hashCode() : 0);
        return result;
    }
}
