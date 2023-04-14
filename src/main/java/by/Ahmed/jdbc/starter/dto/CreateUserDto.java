package by.Ahmed.jdbc.starter.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateUserDto {
    String name;
    String birthday;
    String email;
    String password;
    String role;
    String gender;
}
