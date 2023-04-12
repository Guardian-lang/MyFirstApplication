package by.Ahmed.jdbc.starter.entity;

public class Seat {
    private Long aircraftId;
    private String seatNo;

    public Seat(Long aircraftId, String seatNo) {
        this.aircraftId = aircraftId;
        this.seatNo = seatNo;
    }

    public Seat() {

    }

    public Long getAircraftId() {
        return aircraftId;
    }

    public String getSeatNo() {
        return seatNo;
    }

    public void setAircraftId(Long aircraftId) {
        this.aircraftId = aircraftId;
    }

    public void setSeatNo(String seatNo) {
        this.seatNo = seatNo;
    }

    @Override
    public String toString() {
        return "Seat{" +
                "aircraftId=" + aircraftId +
                ", seatNo='" + seatNo + '\'' +
                '}';
    }
}
