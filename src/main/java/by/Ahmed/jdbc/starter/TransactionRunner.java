package by.Ahmed.jdbc.starter;

import by.Ahmed.jdbc.starter.utils.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class TransactionRunner {
    public static void main(String[] args) throws SQLException {
        Long flightId = 7l;
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
            connection = ConnectionManager.open();
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

