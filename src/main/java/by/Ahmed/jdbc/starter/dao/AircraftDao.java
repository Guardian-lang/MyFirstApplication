package by.Ahmed.jdbc.starter.dao;

import by.Ahmed.jdbc.starter.DaoException;
import by.Ahmed.jdbc.starter.entity.Aircraft;
import by.Ahmed.jdbc.starter.utils.ConnectionManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AircraftDao implements Dao <Long, Aircraft>{

    private static final AircraftDao INSTANCE = new AircraftDao();

    public static AircraftDao getInstance() {
        return INSTANCE;
    }

    private static final String SAVE_SQL = """
            INSERT INTO aircraft (id, model)
            VALUES (?, ?)
            """;

    private static final String DELETE_SQL = """
            DELETE FROM aircraft
            WHERE id = ?
            """;

    private static final String FIND_ALL = """
            SELECT s.aircraft_id, s.seat_no
            FROM seat s
            LEFT JOIN aircraft a on s.aircraft_id = a.id
            """;

    private static final String FIND_BY_ID = FIND_ALL + """
            WHERE a.id = ?
            """;

    private static final String UPDATE = """
            UPDATE aircraft SET
            model = ?
            WHERE id = ?""";

    @Override
    public boolean update(Aircraft aircraft) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(UPDATE)) {
            statement.setLong(1, aircraft.getId());
            statement.setString(2, aircraft.getModel());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Aircraft> findById(Long id) {
        Connection connection = ConnectionManager.get();
        try (var statement = connection.prepareStatement(FIND_BY_ID)) {
            Aircraft aircraft = null;
            statement.setLong(1, id);
            var result = statement.executeQuery();
            if (result.next())
                aircraft = buildAircraft(result);
            return Optional.ofNullable(aircraft);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private Aircraft buildAircraft(ResultSet result) throws SQLException {
        return new Aircraft(
                result.getLong("id"),
                result.getString("model")
        );
    }

    @Override
    public List<Aircraft> findAll() {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_ALL)) {
            List<Aircraft> aircrafts = new ArrayList<>();
            var result = statement.executeQuery();
            while (result.next()) {
                aircrafts.add(buildAircraft(result));
            }
            return aircrafts;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean delete(Long id) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(DELETE_SQL)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Aircraft save(Aircraft aircraft) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, aircraft.getId());
            statement.setString(2, aircraft.getModel());

            statement.executeUpdate();
            var generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                aircraft.setId(generatedKeys.getLong("id"));
            }
            return aircraft;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
