package by.Ahmed.jdbc.starter.entity;

import lombok.*;

import javax.persistence.Embeddable;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Embeddable
@Table(name = "seat")
public class Seat {
    private Long aircraft_id;
    private String seat_no;

    public Long getAircraftId() {
        return aircraft_id;
    }

    public void setAircraftId(Long aircraft_id) {
        this.aircraft_id = aircraft_id;
    }

    public String getSeatNo() {
        return seat_no;
    }

    public void setSeatNo(String seat_no) {
        this.seat_no = seat_no;
    }
}
