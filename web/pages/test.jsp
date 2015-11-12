<%-- 
    Document   : test
    Created on : 03-Sep-2015, 23:14:00
    Author     : Roman
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="cargo.web.db.Database" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        <%
            Database db = new Database();
            db.getConnection();
        %>
    </body>
</html>
