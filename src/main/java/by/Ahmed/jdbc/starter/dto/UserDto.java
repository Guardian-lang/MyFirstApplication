package by.Ahmed.jdbc.starter.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserDto {
    Long id;
    String mail;
}
