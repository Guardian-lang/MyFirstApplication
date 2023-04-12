package by.Ahmed;

import by.Ahmed.jdbc.starter.DaoException;
import by.Ahmed.jdbc.starter.entity.Ticket;
import by.Ahmed.jdbc.starter.utils.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class App 
{
    public static void main(String[] args) throws SQLException {
        Ticket ticket = null;
        System.out.println("Задание 1:\n");
        mostPopularPassengers();
        System.out.println("\nЗадание 2:\n");
        passengerTickets();
        System.out.println("\nЗадание 3:\n");
        updatePassenger(ticket);
        System.out.println("\nЗадание 4:\n");
        updateDataByFlightId(7L);
    }

    public static void mostPopularPassengers() throws SQLException {
        String sql = """
                select * from (select passenger_name, count(passenger_name) from ticket
                                                                                              group by passenger_name) c
                                                                               order by c.count desc limit 5;
                """;


        try (var connection = ConnectionManager.get();
             var statement = connection.createStatement()) {

            var result = statement.executeQuery(sql);

            while (result.next()) {
                System.out.println(result.getString("passenger_name"));
                System.out.println(result.getInt("count"));
                System.out.println("------------------");
            }
        }
    }

    public static void passengerTickets() throws SQLException{
        String sql = """
                select * from (select passenger_name, count(passenger_name) from ticket
                                                                                              group by passenger_name) c
                                                                               order by c.count desc;
                """;


        try (var connection = ConnectionManager.get();
             var statement = connection.createStatement()) {

            var result = statement.executeQuery(sql);

            while (result.next()) {
                System.out.println(result.getString("passenger_name"));
                System.out.println(result.getInt("count"));
                System.out.println("------------------");
            }
        }
    }

    public static boolean updatePassenger(Ticket ticket) {
        String UPDATE = """
            UPDATE ticket SET
            passport_no = ?,
            passenger_name = ?,
            flight_id = ?,
            seat_no = ?,
            cost = ?
            WHERE id = ?""";
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

    public static void updateDataByFlightId (Long flightId) throws SQLException {
        String updateFlightSQL = """
                UPDATE flight SET
            flight_no = ?,
            departure_date = ?,
            departure_airport_code = ?,
            arrival_date = ?,
            arrival_airport_code = ?,
            aircraft_id = ?,
            status = ?
            WHERE id = ?
                """;
        String updateTicketSQL = """
                UPDATE ticket SET
            passport_no = ?,
            passenger_name = ?,
            flight_id = ?,
            seat_no = ?,
            cost = ?
            WHERE id = ?
                """;

        Connection connection = null;
        PreparedStatement updateFlightStatement = null;
        PreparedStatement updateTicketStatement = null;

        try {
            connection = ConnectionManager.get();
            updateFlightStatement =
                    connection.prepareStatement(updateFlightSQL);
            updateTicketStatement =
                    connection.prepareStatement(updateFlightSQL);


            connection.setAutoCommit(false);


            updateFlightStatement.setLong(1, flightId);
            updateTicketStatement.executeUpdate();

            if (true)
                throw new RuntimeException();

            updateTicketStatement.setLong(1, flightId);
            updateTicketStatement.executeUpdate();

            connection.commit();

        } catch (Exception e) {
            if (connection != null)
                connection.rollback();
            throw e;
        } finally {
            if (connection != null)
                connection.close();
            if (updateFlightSQL != null)
                updateFlightStatement.close();
            if (updateTicketSQL != null)
                updateTicketStatement.close();
        }
    }
}
