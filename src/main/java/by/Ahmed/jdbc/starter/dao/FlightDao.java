package by.Ahmed.jdbc.starter.dao;

import by.Ahmed.jdbc.starter.DaoException;
import by.Ahmed.jdbc.starter.entity.Flight;
import by.Ahmed.jdbc.starter.entity.FlightStatus;
import by.Ahmed.jdbc.starter.entity.Ticket;
import by.Ahmed.jdbc.starter.utils.ConnectionManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FlightDao implements Dao<Long, Flight>{
    private static final FlightDao INSTANCE = new FlightDao();

    private static String FIND_BY_ID_SQL = """
            SELECT id, flight_no, departure_date, departure_airport_code, arrival_date, arrival_airport_code, aircraft_id, status
            FROM flight
            WHERE id = ?
            """;

    @Override
    public boolean update(Flight flight) {
        return false;
    }


    public Optional<Flight> findById(Long id, Connection connection) {
        try (var statement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            Flight flight = null;
            statement.setLong(1, id);
            var result = statement.executeQuery();
            if (result.next())
                flight = buildFlight(result);
            return Optional.ofNullable(flight);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private Flight buildFlight(ResultSet result) throws SQLException {
        return new Flight(
                result.getLong("id"),
                result.getString("flight_no"),
                result.getTimestamp("departure_date").toLocalDateTime(),
                result.getString("departure_airport_code"),
                result.getTimestamp("arrival_date").toLocalDateTime(),
                result.getString("arrival_airport_code"),
                result.getInt("aircraft_id"),
                FlightStatus.valueOf(result.getString("status"))
        );
    }

    @Override
    public List<Flight> findAll() {
        String FIND_ALL = """
            SELECT id, flight_no, departure_date, departure_airport_code, arrival_date, arrival_airport_code, aircraft_id, status FROM flight;
            """;
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_ALL)) {
            List<Flight> flights = new ArrayList<>();
            var result = statement.executeQuery();
            while (result.next()) {
                flights.add(buildFlight(result));
            }
            return flights;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public Flight save(Flight flight) {
        return null;
    }

    public static FlightDao getInstance() {
        return INSTANCE;
    }

    private FlightDao() {
    }

    @Override
    public Optional<Flight> findById(Long flightId) {
        try (var connection = ConnectionManager.get()){
            return findById(flightId, connection);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
