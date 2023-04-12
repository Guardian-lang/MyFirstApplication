package by.Ahmed.jdbc.starter.dto;

import lombok.*;

@Getter @Setter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class TicketDto {
    private Long id;
    private Long flightId;
    private String seatNo;
}
