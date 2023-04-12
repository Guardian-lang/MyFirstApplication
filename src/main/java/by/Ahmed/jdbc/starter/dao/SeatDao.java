package by.Ahmed.jdbc.starter.dao;

import by.Ahmed.jdbc.starter.DaoException;
import by.Ahmed.jdbc.starter.entity.Aircraft;
import by.Ahmed.jdbc.starter.entity.Seat;
import by.Ahmed.jdbc.starter.utils.ConnectionManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SeatDao implements Dao <Long, Seat>{

    private static final SeatDao INSTANCE = new SeatDao();

    private static final AircraftDao aircraftDao = AircraftDao.getInstance();

    private static final String SAVE_SQL = """
            INSERT INTO seat (aircraft_id, seat_no)
            VALUES (?, ?)
            """;

    private static final String DELETE_SQL = """
            DELETE FROM seat
            WHERE aircraft_id = ?
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
            UPDATE seat SET
            seat_no = ?
            WHERE aircraft_id = ?""";

    @Override
    public boolean update(Seat seat) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(UPDATE)) {
            statement.setString(1, seat.getSeatNo());
            statement.setLong(2, seat.getAircraftId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Seat> findById(Long aircraftId) {
        Connection connection = ConnectionManager.get();
        try (var statement = connection.prepareStatement(FIND_BY_ID)) {
            Seat seat = null;
            statement.setLong(1, aircraftId);
            var result = statement.executeQuery();
            if (result.next())
                seat = buildSeat(result);
            return Optional.ofNullable(seat);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private Seat buildSeat(ResultSet result) throws SQLException {
        var aircraft = new Aircraft(
                result.getLong("id"),
                result.getString("model")
        );
        return new Seat(
                result.getLong("aircraft_id"),
                result.getString("seat_no")
        );
    }

    @Override
    public List<Seat> findAll() {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_ALL)) {
            List<Seat> seats = new ArrayList<>();
            var result = statement.executeQuery();
            while (result.next()) {
                seats.add(buildSeat(result));
            }
            return seats;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean delete(Long aircraftId) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(DELETE_SQL)) {
            statement.setLong(1, aircraftId);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Seat save(Seat seat) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, seat.getSeatNo());
            statement.setLong(2, seat.getAircraftId());

            statement.executeUpdate();
            var generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                seat.setAircraftId(generatedKeys.getLong("aircraft_id"));
            }
            return seat;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
