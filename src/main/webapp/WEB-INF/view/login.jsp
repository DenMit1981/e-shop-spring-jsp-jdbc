<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <title>https://www.online-shop.com</title>
    </head>
    <body>
        <div style="text-align: center">
            <form method="post" action="e-shop">
                <p><h2>Welcome to Online Shop!</h2>
                    <input name="login" type="text" size="40" required placeholder="Enter your name">
                </p>
                <p>
                    <input name="password" type="password" size="40" required placeholder="Enter your password">
                </p>
                <p>
                    <label>
                        <input type="checkbox" name="isUserCheck" value="yes">
                    </label>I agree with the terms of service
                </p>
                <p><input name="submit" type="submit" value="Enter"></p>
                <p><input name="submit" type="submit" value="Register"></p>
            </form>
        </div>
    </body>
</html>


