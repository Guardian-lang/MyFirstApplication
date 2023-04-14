package by.Ahmed.jdbc.starter.validator;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

import by.Ahmed.jdbc.starter.validator.Error;

public class ValidationResult {

    @Getter
    private final List<Error> errors = new ArrayList<>();

    public void add(Error error) {
        this.errors.add(error);
    }

    public boolean isValid() {
        return errors.isEmpty();
    }
}
