<%@ page import="by.Ahmed.jdbc.starter.service.TicketService" %>
<%@ page import="by.Ahmed.jdbc.starter.dto.TicketDto" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: Ahmed
  Date: 12.04.2023
  Time: 20:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>Купленные билеты: </h1>
<ul>
    <%
        TicketService ticketService = TicketService.getInstance();
        Long flightId = Long.valueOf(request.getParameter("flightId"));
        List<TicketDto> ticketDtoList = ticketService.findAllByFlightId(flightId);
        for (TicketDto ticketDto : ticketDtoList) {
            out.write(String.format("<li>%s</li>", ticketDto.getSeatNo()));
        }
    %>
</ul>
</body>
</html>
