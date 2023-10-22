package controller;

import config.Config;
import model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import service.CartService;
import service.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/e-shop")
@Component
public class LoginServlet extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(LoginServlet.class.getName());

    private static final String LOGIN_PAGE = "WEB-INF/view/login.jsp";
    private static final String GOODS_PAGE = "WEB-INF/view/goods.jsp";
    private static final String REGISTER_PAGE = "WEB-INF/view/register.jsp";
    private static final String ERROR_PAGE = "WEB-INF/view/error.jsp";

    private UserService userService;
    private CartService cartService;

    @Override
    public void init() throws ServletException {
        final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);

        userService = context.getBean(UserService.class);
        cartService = context.getBean(CartService.class);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        dispatcherForward(request, response, LOGIN_PAGE);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        HttpSession session = request.getSession();

        User user = userService.getByLoginAndPassword(login, password);

        LOGGER.info(user);

        cartService.updateData(session);

        clickingActions(request, response, user);
    }

    private void dispatcherForward(HttpServletRequest request, HttpServletResponse response, String path) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(path);

        dispatcher.forward(request, response);
    }

    private void clickingActions(HttpServletRequest request, HttpServletResponse response, User user) throws IOException, ServletException {
        if (request.getParameter("submit").equals("Enter") && !user.equals(new User("Unknown"))) {
            HttpSession session = request.getSession();

            session.setAttribute("userId", user.getId());
            session.setAttribute("login", user.getLogin());

            eventsWithCheckbox(request, response, GOODS_PAGE);
        } else {
            eventsWithCheckbox(request, response, REGISTER_PAGE);
        }
    }

    private void eventsWithCheckbox(HttpServletRequest request, HttpServletResponse response, String path) throws IOException, ServletException {
        if (request.getParameter("isUserCheck") != null) {
            dispatcherForward(request, response, path);
        } else {
            dispatcherForward(request, response, ERROR_PAGE);
        }
    }
}
