package service;

import model.Order;

import java.math.BigDecimal;
import java.util.List;

public interface OrderService {

    void save(Long userId, BigDecimal totalPrice);

    List<Order> getAll();

    String orderResult(BigDecimal totalPrice);
}

