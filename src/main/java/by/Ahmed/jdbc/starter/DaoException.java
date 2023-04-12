package by.Ahmed.jdbc.starter;

import java.sql.SQLException;

public class DaoException extends RuntimeException {
    public DaoException(Throwable e) {
        super(e);
    }
}
