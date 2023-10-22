package controller;

import config.Config;
import model.Cart;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import service.CartService;
import service.GoodService;
import service.OrderService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/goods")
@Component
public class GoodServlet extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(GoodServlet.class.getName());

    private static final String LOGIN_PAGE = "WEB-INF/view/login.jsp";
    private static final String GOODS_PAGE = "WEB-INF/view/goods.jsp";
    private static final String ORDER_PAGE = "WEB-INF/view/order.jsp";

    private GoodService goodService;
    private CartService cartService;
    private OrderService orderService;
    private Cart cart;

    @Override
    public void init() throws ServletException {
        final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);

        goodService = context.getBean(GoodService.class);
        cartService = context.getBean(CartService.class);
        orderService = context.getBean(OrderService.class);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        dispatcherForward(request, response, GOODS_PAGE);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        createCart(session);

        String command = request.getParameter("submit");

        clickingActions(command, request, response, session);
    }

    private void dispatcherForward(HttpServletRequest request, HttpServletResponse response, String path) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(path);

        dispatcher.forward(request, response);
    }

    private void createCart(HttpSession session) {
        cartService.getCart(session);

        if (session.getAttribute("cart") == null) {
            cart = new Cart();
        } else {
            cart = (Cart) session.getAttribute("cart");
        }
    }

    private void clickingActions(String command, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException, ServletException {
        BigDecimal totalPrice = cartService.getTotalPrice(cart);

        session.setAttribute("cart", cart);
        session.setAttribute("totalPrice", totalPrice);

        String option = goodService.getStringOfNameAndPriceFromOptionMenu(request.getParameter("goodName"));
        Long userId = (Long) session.getAttribute("userId");

        switch (command) {
            case "Add Good":
                cartService.addGoodToCart(option, userId);

                getChosenGoods(session);

                dispatcherForward(request, response, GOODS_PAGE);

                break;
            case "Remove Good":
                cartService.deleteGoodFromCart(option);

                getChosenGoods(session);

                dispatcherForward(request, response, GOODS_PAGE);

                break;
            case "Submit":
                String order = cartService.printOrder(cart);
                session.setAttribute("order", order);

                orderService.save(userId, totalPrice);

                dispatcherForward(request, response, ORDER_PAGE);

                break;
            case "Log out":
                dispatcherForward(request, response, LOGIN_PAGE);

                break;
        }
    }

    private void getChosenGoods(HttpSession session) {
        String chosenGoods = cartService.printChosenGoods(cart);

        LOGGER.info("Chosen goods: {}", chosenGoods);

        session.setAttribute("chosenGoods", chosenGoods);
    }
}
