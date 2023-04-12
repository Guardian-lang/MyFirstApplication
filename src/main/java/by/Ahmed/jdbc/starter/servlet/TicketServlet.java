package by.Ahmed.jdbc.starter.servlet;

import by.Ahmed.jdbc.starter.service.TicketService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebServlet("/tickets")
public class TicketServlet extends HttpServlet {

    private final TicketService ticketService = TicketService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());

        Long flightId = Long.valueOf(req.getParameter("flightId"));

        try (var writer = resp.getWriter()) {
            writer.write("<h1>Купленные билеты:</h1>");
            writer.write("<ul>");
            ticketService.findAllByFlightId(flightId).stream().forEach(
                    e -> writer.write(
                            """
                            <li>
                            %s
                            </li>
                            """.formatted(e.getSeatNo())));
            writer.write("</ul>");

        }
    }
}
