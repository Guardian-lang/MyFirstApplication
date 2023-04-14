package by.Ahmed.jdbc.starter.service;

import by.Ahmed.jdbc.starter.dao.UserDao;
import by.Ahmed.jdbc.starter.dto.CreateUserDto;
import by.Ahmed.jdbc.starter.mapper.CreateUserMapper;
import by.Ahmed.jdbc.starter.validator.CreateUserValidator;
import by.Ahmed.jdbc.starter.validator.ValidationException;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class UserService {

    private static final UserService INSTANCE = new UserService();

    private final CreateUserValidator createUserValidator = CreateUserValidator.getInstance();
    private final UserDao userDao = UserDao.getInstance();
    private final CreateUserMapper createUserMapper = CreateUserMapper.getInstance();

    public Integer create(CreateUserDto userDto) {
        var validationResult = createUserValidator.isValid(userDto);
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }
        var userEntity = createUserMapper.mapFrom(userDto);
        userDao.save(userEntity);
        return userEntity.getId();
    }

    public static UserService getInstance() {
        return INSTANCE;
    }
}