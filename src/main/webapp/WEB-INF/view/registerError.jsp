<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="service.*, org.springframework.context.annotation.*, config.*" %>

<!DOCTYPE html>
<html>
    <head>
        <title>https://www.online-shop.com</title>
    </head>
    <body>
        <div align="center">
            <h2>
                <%
                    final AnnotationConfigApplicationContext context =
                    new AnnotationConfigApplicationContext(Config.class);

                    UserService userService = context.getBean(UserService.class);

                    String login = request.getParameter("login");
                    String password = request.getParameter("password");

                    out.println(userService.invalidUser(login, password));
                %>
            </h2>
            <h2>Please, try again <a href="http://localhost:8081/register">here</a></h2>
        </div>
    </body>
</html>