<%--
  Created by IntelliJ IDEA.
  User: Ahmed
  Date: 12.04.2023
  Time: 20:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <div>
        <span>CONTENT РУССКИЙ</span>
        <p>Size: ${requestScope.flights.size()}</p>
        <p>id: ${requestScope.flights.get(0).id()}</p>
        <p>id: ${requestScope.flights[0].description()}</p>
        <p>JSESSIONID: ${cookie.get("JSESSIONID")}</p>
        <p>PARAM id: ${param.id}</p>
        <p>HEADER: ${header["cookie"]}</p>
        <p>IS NOT EMPTY: ${not empty flights}</p>
    </div>
</body>
</html>
