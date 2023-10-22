package config;

import dao.GoodDAO;
import dao.OrderDAO;
import dao.UserDAO;
import dao.impl.GoodDAOImpl;
import dao.impl.OrderDAOImpl;
import dao.impl.UserDAOImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import service.CartService;
import service.GoodService;
import service.OrderService;
import service.UserService;
import service.impl.CartServiceImpl;
import service.impl.GoodServiceImpl;
import service.impl.OrderServiceImpl;
import service.impl.UserServiceImpl;

@Configuration
@ComponentScan(value = "main")
public class Config {

    @Bean
    public UserDAO userDAO() {
        return new UserDAOImpl();
    }

    @Bean
    public GoodDAO goodDAO() {
        return new GoodDAOImpl();
    }

    @Bean
    public OrderDAO orderDAO() {
        return new OrderDAOImpl();
    }

    @Bean
    public UserService userService() {
        return new UserServiceImpl(userDAO());
    }

    @Bean
    public GoodService goodService() {
        return new GoodServiceImpl(goodDAO());
    }

    @Bean
    public OrderService orderService() {
        return new OrderServiceImpl(orderDAO(), userDAO());
    }

    @Bean
    public CartService cartService() {
        return new CartServiceImpl(userDAO(), goodDAO());
    }
}
