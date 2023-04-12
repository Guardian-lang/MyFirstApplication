package by.Ahmed.jdbc.starter.entity;

import java.math.BigDecimal;

public class Ticket {
    private Long id;
    private String passportNo;
    private String passengerName;
    private Flight flight;
    private String seatNo;
    private BigDecimal cost;

    public Ticket() {

    }

    public Ticket(Long id, String passportNo, String passengerName, Flight flightId, String seatNo, BigDecimal cost) {
        this.id = id;
        this.passportNo = passportNo;
        this.passengerName = passengerName;
        this.flight = flightId;
        this.seatNo = seatNo;
        this.cost = cost;
    }

    public Long getId() {
        return id;
    }

    public String getPassportNo() {
        return passportNo;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public Flight getFlight() {
        return flight;
    }

    public String getSeatNo() {
        return seatNo;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPassportNo(String passportNo) {
        this.passportNo = passportNo;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public void setFlightId(Flight flightId) {
        this.flight = flightId;
    }

    public void setSeatNo(String seatNo) {
        this.seatNo = seatNo;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", passportNo='" + passportNo + '\'' +
                ", passengerName='" + passengerName + '\'' +
                ", flightId=" + flight +
                ", seatNo='" + seatNo + '\'' +
                ", cost=" + cost +
                '}';
    }
}
