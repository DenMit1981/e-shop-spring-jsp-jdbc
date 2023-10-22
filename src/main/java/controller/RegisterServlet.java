package controller;

import config.Config;
import model.User;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import service.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/register")
@Component
public class RegisterServlet extends HttpServlet {

    private static final String REGISTER_PAGE = "WEB-INF/view/register.jsp";
    private static final String REGISTER_ERROR_PAGE = "WEB-INF/view/registerError.jsp";
    private static final String REGISTER_SUCCESS_PAGE = "WEB-INF/view/registerSuccess.jsp";

    private UserService userService;

    @Override
    public void init() throws ServletException {
        final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);

        userService = context.getBean(UserService.class);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        dispatcherForward(request, response, REGISTER_PAGE);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        if (userService.isInvalidUser(login, password)) {
            dispatcherForward(request, response, REGISTER_ERROR_PAGE);
        } else {
            User user = userService.save(login, password);

            HttpSession session = request.getSession();

            session.setAttribute("userId", user.getId());
            session.setAttribute("login", user.getLogin());

            dispatcherForward(request, response, REGISTER_SUCCESS_PAGE);
        }
    }

    private void dispatcherForward(HttpServletRequest request, HttpServletResponse response, String path) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(path);

        dispatcher.forward(request, response);
    }
}
