package by.Ahmed.jdbc.starter;

import by.Ahmed.jdbc.starter.dao.FlightDao;
import by.Ahmed.jdbc.starter.dao.TicketDao;
import by.Ahmed.jdbc.starter.dto.TicketFilter;
import by.Ahmed.jdbc.starter.entity.Ticket;
import by.Ahmed.jdbc.starter.utils.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class JdbcRunner {
    public static void main(String[] args) throws SQLException {
//        var ticketDao = TicketDao.getInstance();
//        System.out.println(ticketDao.findById(3L).get().getFlight());
        var flightDao = FlightDao.getInstance();
        System.out.println(flightDao.findById(3L));
    }

    private static void filteredFindAll(TicketDao ticketDao) {
        var filter = new TicketFilter(6, 0, null, null);
        System.out.println(ticketDao.findAll(filter));
    }

    private static void updateTicket(TicketDao ticketDao) {
        var ticket = ticketDao.findById(3L).get();
        System.out.println(ticket);
        ticket.setSeatNo("100A");
        System.out.println(ticketDao.update(ticket));
        System.out.println(ticketDao.findById(3L));
    }

    private static void findAllTickets(TicketDao ticketDao) {
        System.out.println(ticketDao.findAll());
    }

    private static void deleteTicket(TicketDao ticketDao) {
        System.out.println(ticketDao.delete(58L));
    }

   /*private static void saveTicket(TicketDao ticketDao) {
        Ticket ticket = new Ticket();
        ticket.setPassportNo("5023894");
        ticket.setPassengerName("Andrei");
        ticket.setFlight(3L);
        ticket.setSeatNo("3B");
        ticket.setCost(BigDecimal.TEN);

        System.out.println(ticketDao.save(ticket));
    }*/

    private static List<Long> getTicketsByFlightId(Long flightId) {
        String sql = """
                select * from ticket
                where flight_id = ?;
                """;
        List<Long> ticketsId = new ArrayList<>();
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(sql)) {

            statement.setLong(1, flightId);
            var result = statement.executeQuery();
            while (result.next()) {
                ticketsId.add(result.getLong("id"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ticketsId;
    }

    public static List<Long> getFlightsBetween(LocalDateTime start, LocalDateTime end) throws SQLException {
        List<Long> tickets = new ArrayList<>();

        String sql = """
                select * from flight
                where departure_date BETWEEN ? AND ?;
                """;

        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(sql)) {
            var metaData = connection.getMetaData();
            var catalogs = metaData.getCatalogs();
            while (catalogs.next())
                System.out.println(catalogs.getString(1));

            statement.setFetchSize(2);
            statement.setMaxRows(2);
            statement.setQueryTimeout(1);
//            System.out.println(statement);
            statement.setTimestamp(1, Timestamp.valueOf(start));
//            System.out.println(statement);
            statement.setTimestamp(2, Timestamp.valueOf(end));
//            System.out.println(statement);
            var result = statement.executeQuery();

            while (result.next()) {
                tickets.add(result.getLong("id"));
            }
        }
        return tickets;
    }

}
