package by.Ahmed.jdbc.starter.dao;

import by.Ahmed.jdbc.starter.DaoException;
import by.Ahmed.jdbc.starter.entity.Aircraft;
import by.Ahmed.jdbc.starter.entity.Airport;
import by.Ahmed.jdbc.starter.entity.Seat;
import by.Ahmed.jdbc.starter.utils.ConnectionManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AirportDao implements Dao <String, Airport> {

    private static final AirportDao INSTANCE = new AirportDao();

    public static AirportDao getInstance() {
        return INSTANCE;
    }

    private static final String SAVE_SQL = """
            INSERT INTO airport (code, country, city)
            VALUES (?, ?, ?)
            """;

    private static final String DELETE_SQL = """
            DELETE FROM airport
            WHERE code = ?
            """;

    private static final String FIND_ALL = """
            SELECT code, country, city 
            FROM airport
            """;

    private static final String FIND_BY_CODE = FIND_ALL + """
            WHERE code = ?
            """;

    private static final String UPDATE = """
            UPDATE airport SET
            country = ?,
            city = ?
            WHERE code = ?""";

    @Override
    public boolean update(Airport airport) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(UPDATE)) {
            statement.setString(1, airport.getCode());
            statement.setString(1, airport.getCountry());
            statement.setString(1, airport.getCity());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Airport> findById(String code) {
        Connection connection = ConnectionManager.get();
        try (var statement = connection.prepareStatement(FIND_BY_CODE)) {
            Airport airport = null;
            statement.setString(1, code);
            var result = statement.executeQuery();
            if (result.next())
                airport = buildAirport(result);
            return Optional.ofNullable(airport);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private Airport buildAirport(ResultSet result) throws SQLException {
        return new Airport(
                result.getString("code"),
                result.getString("country"),
                result.getString("city")
        );
    }

    @Override
    public List<Airport> findAll() {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_ALL)) {
            List<Airport> airports = new ArrayList<>();
            var result = statement.executeQuery();
            while (result.next()) {
                airports.add(buildAirport(result));
            }
            return airports;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean delete(String code) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(DELETE_SQL)) {
            statement.setString(1, code);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Airport save(Airport airport) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, airport.getCode());
            statement.setString(2, airport.getCountry());
            statement.setString(2, airport.getCity());

            statement.executeUpdate();
            var generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                airport.setCode(generatedKeys.getString("code"));
            }
            return airport;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
