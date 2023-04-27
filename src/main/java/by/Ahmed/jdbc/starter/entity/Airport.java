package by.Ahmed.jdbc.starter.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "airport")
public class Airport {
    @Id
    private String code;
    @Column(name = "country")
    private String country;
    @Column(name = "city")
    private String city;
}
