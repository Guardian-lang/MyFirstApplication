package by.Ahmed.jdbc.starter.dao;

import by.Ahmed.jdbc.starter.DaoException;
import by.Ahmed.jdbc.starter.dto.*;
import by.Ahmed.jdbc.starter.entity.Flight;
import by.Ahmed.jdbc.starter.entity.FlightStatus;
import by.Ahmed.jdbc.starter.entity.Ticket;
import by.Ahmed.jdbc.starter.utils.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TicketDao implements Dao <Long, Ticket> {
    private static final TicketDao INSTANCE = new TicketDao();

    private static final FlightDao flightDao = FlightDao.getInstance();

    private static final String SAVE_SQL = """
            INSERT INTO ticket (passport_no, passenger_name, flight_id, seat_no, cost)
            VALUES (?, ?, ?, ?, ?)
            """;

    private static final String DELETE_SQL = """
            DELETE FROM ticket
            WHERE id = ?
            """;

    private static final String FIND_ALL = """
            SELECT t.id, t.passport_no, t.passenger_name, t.flight_id, t.seat_no, t.cost,
            f.id, f.flight_no, f.departure_date, f.departure_airport_code, f.arrival_date, f.arrival_airport_code, f.aircraft_id, f.status
            FROM ticket t
            LEFT JOIN flight f on t.flight_id = f.id
            """;

    private static final String FIND_BY_ID = FIND_ALL + """
            WHERE t.id = ?
            """;

    private static final String UPDATE = """
            UPDATE ticket SET
            passport_no = ?,
            passenger_name = ?,
            flight_id = ?,
            seat_no = ?,
            cost = ?
            WHERE id = ?""";

    public boolean update(Ticket ticket) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(UPDATE)) {
            statement.setString(1, ticket.getPassportNo());
            statement.setString(2, ticket.getPassengerName());
            statement.setLong(3, ticket.getFlight().getId());
            statement.setString(4, ticket.getSeatNo());
            statement.setBigDecimal(5, ticket.getCost());
            statement.setLong(6, ticket.getId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Ticket> findById(Long id) {
        return Optional.empty();
    }

    public Optional<Ticket> findById() {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_BY_ID)) {
            Ticket ticket = null;
            var result = statement.executeQuery();
            if (result.next())
                ticket = buildTicket(result);
            return Optional.ofNullable(ticket);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private Ticket buildTicket(ResultSet result) throws SQLException {
        var flight = new Flight(
                result.getLong("flight_id"),
                result.getString("flight_no"),
                result.getTimestamp("departure_date").toLocalDateTime(),
                result.getString("departure_airport_code"),
                result.getTimestamp("arrival_date").toLocalDateTime(),
                result.getString("arrival_airport_code"),
                result.getInt("aircraft_id"),
                FlightStatus.valueOf(result.getString("status"))
        );
        return new Ticket(
                result.getLong("id"),
                result.getString("passport_no"),
                result.getString("passenger_name"),
                flightDao.findById(
                        result.getLong("flight_id"),
                        result.getStatement().getConnection()
                        ).orElse(null),
                result.getString("seat_no"),
                result.getBigDecimal("cost")
        );
    }

    public List<Ticket> findAll() {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_ALL)) {
            List<Ticket> tickets = new ArrayList<>();
            var result = statement.executeQuery();
            while (result.next()) {
                tickets.add(buildTicket(result));
            }
            return tickets;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public List<Ticket> findAll(TicketFilter filter) {
        List<Object> parameters = new ArrayList<>();
        List<String> whereSql = new ArrayList<>();
        if (filter.seatNo() != null) {
            whereSql.add("seat_no like ?");
            parameters.add("%" + filter.seatNo() + "%");
        }
        if (filter.passengerName() != null) {
            whereSql.add("passenger_name = ?");
            parameters.add(filter.passengerName());
        }
        parameters.add(filter.limit());
        parameters.add(filter.offset());

        var where = whereSql.stream()
                .collect(Collectors.joining(
                        " AND ",
                        parameters.size() > 2 ? " WHERE " : " ",
                        " LIMIT ? OFFSET ? "
                ));
        String sql = FIND_ALL + where;

        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(sql)) {
            List<Ticket> tickets = new ArrayList<>();
            for (int i = 0; i < parameters.size(); i++) {
                statement.setObject(i + 1, parameters.get(i));
            }
            System.out.println(statement);
            var result = statement.executeQuery();
            while (result.next()) {
                tickets.add(buildTicket(result));
            }
            return tickets;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public Ticket save(Ticket ticket) {
        try (var connection = ConnectionManager.get();
        var statement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, ticket.getPassportNo());
            statement.setString(2, ticket.getPassengerName());
            statement.setLong(3, ticket.getFlight().getId());
            statement.setString(2, ticket.getSeatNo());
            statement.setBigDecimal(2, ticket.getCost());

            statement.executeUpdate();
            var generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                ticket.setId(generatedKeys.getLong("id"));
            }
            return ticket;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public boolean delete(Long id) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(DELETE_SQL)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public static TicketDao getInstance() {
        return INSTANCE;
    }
    public TicketDao() {
    }
}
