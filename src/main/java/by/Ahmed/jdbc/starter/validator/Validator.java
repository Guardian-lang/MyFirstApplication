package by.Ahmed.jdbc.starter.validator;

public interface Validator<T> {
    ValidationResult isValid(T object);
}
