package by.Ahmed.jdbc.starter.mapper;

public interface Mapper<F, T> {

    T mapFrom(F object);
}
