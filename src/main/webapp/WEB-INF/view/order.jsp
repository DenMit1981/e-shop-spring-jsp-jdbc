<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="service.*, org.springframework.context.annotation.*, config.*, java.util.*, java.math.BigDecimal;" %>

<!DOCTYPE html>
<html>
    <head>
        <title>https://www.online-shop.com</title>
    </head>
    <body>
        <h2 align="center">Dear <%= (String) session.getAttribute("login") %>,
            <%
                final AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(Config.class);

                OrderService orderService = context.getBean(OrderService.class);

                BigDecimal totalPrice = (BigDecimal) session.getAttribute("totalPrice");

                out.println(orderService.orderResult(totalPrice));
            %>
        </h2>
        <div align="center">
            <%
                out.println("<h2><pre>" + (String) session.getAttribute("order") + "</pre></h2>");
            %>
        </div>
        <h2 align="center">Total: $ <span> <%= totalPrice %></span></h2>
        <form action="/e-shop" method="get" align="center">
            <input name="submit" type="submit" value="Log out">
        </form>
    </body>
</html>









