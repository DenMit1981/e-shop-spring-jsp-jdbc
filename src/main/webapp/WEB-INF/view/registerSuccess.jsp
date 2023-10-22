<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <title>https://www.online-shop.com</title>
    </head>
    <body>
        <h1 align="center">Welcome to Online Shop, <span> <%= (String) session.getAttribute("login") %></span>!</h1>
        <h2 align="center">Look our <a href="http://localhost:8081/goods">goods</a></h2>
    </body>
</html>