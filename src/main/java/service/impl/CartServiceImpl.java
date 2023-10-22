package service.impl;

import dao.GoodDAO;
import dao.UserDAO;
import model.Cart;
import model.Good;
import model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import service.CartService;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;

@Service
public class CartServiceImpl implements CartService {

    private static final Logger LOGGER = LogManager.getLogger(CartServiceImpl.class.getName());

    private static final String REGEX_ONLY_LETTERS = "[^A-Za-z]";
    private static final String REGEX_ONLY_FIGURES = "[A-Za-z]";
    private static final String ORDER_NOT_MADE = "Make your order\n";
    private static final String CHOSEN_GOODS = "You have already chosen:\n";

    private final UserDAO userDAO;
    private final GoodDAO goodDAO;

    private Cart cart;

    public CartServiceImpl(UserDAO userDAO, GoodDAO goodDAO) {
        this.userDAO = userDAO;
        this.goodDAO = goodDAO;
    }

    @Override
    public void getCart(HttpSession session) {
        if (session.getAttribute("cart") != null) {
            cart = (Cart) session.getAttribute("cart");
        }
    }

    @Override
    public void addGoodToCart(String option, Long userId) {
        User user = userDAO.getById(userId);

        Good good = getGoodFromOption(option);

        cart.addGood(new Good(good.getId(), good.getTitle(), good.getPrice()));
        cart.setUser(user);

        LOGGER.info("New cart: {}", cart);
    }

    @Override
    public void deleteGoodFromCart(String option) {
        Good good = getGoodFromOption(option);

        cart.deleteGood(new Good(good.getId(), good.getTitle(), good.getPrice()));

        LOGGER.info("Cart after removing {} : {}", good.getTitle(), cart);
    }

    @Override
    public void updateData(HttpSession session) {
        cart = new Cart();
        cart.deleteGoods();

        session.setAttribute("cart", cart);

        session.setAttribute("chosenGoods", ORDER_NOT_MADE);
    }

    @Override
    public String printChosenGoods(Cart cart) {
        if (cart.getGoods().isEmpty()) {
            return ORDER_NOT_MADE;
        }

        StringBuilder sb = new StringBuilder(CHOSEN_GOODS);

        int count = 1;

        for (Good good : cart.getGoods()) {
            sb.append(count)
                    .append(") ")
                    .append(good.getTitle())
                    .append(" ")
                    .append(good.getPrice())
                    .append(" $\n");

            count++;
        }

        return sb.toString();
    }

    @Override
    public String printOrder(Cart cart) {
        if (cart.getGoods().isEmpty()) {
            return "";
        }

        return printChosenGoods(cart).replace(CHOSEN_GOODS, "");
    }

    @Override
    public BigDecimal getTotalPrice(Cart cart) {
        BigDecimal count = BigDecimal.valueOf(0);

        for (Good good : cart.getGoods()) {
            count = count.add(good.getPrice());
        }

        LOGGER.info("Total price: {}", count);

        return count;
    }

    private Good getGoodFromOption(String option) {
        String name = option.replaceAll(REGEX_ONLY_LETTERS, "");
        String price = option.replaceAll(REGEX_ONLY_FIGURES, "");

        return goodDAO.getByTitleAndPrice(name, price);
    }
}
