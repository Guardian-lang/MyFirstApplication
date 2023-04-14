package by.Ahmed.jdbc.starter.mapper;

import by.Ahmed.jdbc.starter.dto.CreateUserDto;
import by.Ahmed.jdbc.starter.entity.Gender;
import by.Ahmed.jdbc.starter.entity.Role;
import by.Ahmed.jdbc.starter.entity.User;
import by.Ahmed.jdbc.starter.utils.LocalDateFormatter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class CreateUserMapper implements Mapper<CreateUserDto, User> {

    private static final CreateUserMapper INSTANCE = new CreateUserMapper();

    @Override
    public User mapFrom(CreateUserDto object) {
        return User.builder()
                .name(object.getName())
                .birthday(LocalDateFormatter.format(object.getBirthday()))
                .email(object.getEmail())
                .password(object.getPassword())
                .gender(Gender.valueOf(object.getGender()))
                .role(Role.valueOf(object.getRole()))
                .build();
    }

    public static CreateUserMapper getInstance() {
        return INSTANCE;
    }
}
