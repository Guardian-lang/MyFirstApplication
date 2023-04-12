<%--
  Created by IntelliJ IDEA.
  User: Ahmed
  Date: 12.04.2023
  Time: 20:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>Купленные билеты: </h1>
<ul>
    <c:forEach var="ticket" items="${requestScope.tickets}">
        <li>
            ${ticket.seatNo}
        </li>
    </c:forEach>
</ul>
</body>
</html>
