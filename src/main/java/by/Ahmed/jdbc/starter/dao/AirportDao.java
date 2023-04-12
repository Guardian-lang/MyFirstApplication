package by.Ahmed.jdbc.starter.dao;

import by.Ahmed.jdbc.starter.entity.Airport;

import java.util.List;
import java.util.Optional;

public class AirportDao implements Dao <String, Airport> {

    @Override
    public boolean update(Airport airport) {
        return false;
    }

    @Override
    public Optional<Airport> findById(String code) {
        return Optional.empty();
    }

    @Override
    public List<Airport> findAll() {
        return null;
    }

    @Override
    public boolean delete(String code) {
        return false;
    }

    @Override
    public Airport save(Airport airport) {
        return null;
    }
}
