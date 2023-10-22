package dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import utils.DBUtils;
import dao.OrderDAO;
import model.Order;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderDAOImpl implements OrderDAO {

    private static final Logger LOGGER = LogManager.getLogger(OrderDAOImpl.class.getName());

    private static final String WRONG_SQL_STATEMENT = "Wrong SQL statement";
    private static final String QUERY_SELECT_FROM_ORDER = "SELECT * FROM orders";
    private static final String QUERY_INSERT_INTO_ORDER = "INSERT INTO orders (user_id, total_price) VALUES (?,?)";

    @Override
    public void save(Order order) {
        try (PreparedStatement statement = DBUtils.getConnection().prepareStatement(QUERY_INSERT_INTO_ORDER)) {
            statement.setLong(1, order.getUserId());
            statement.setBigDecimal(2, order.getTotalPrice());

            statement.executeUpdate();

        } catch (SQLException e) {
            LOGGER.error(WRONG_SQL_STATEMENT);
        }
    }

    @Override
    public List<Order> getAll() {
        List<Order> orders = new ArrayList<>();

        try (PreparedStatement statement = DBUtils.getConnection().prepareStatement(QUERY_SELECT_FROM_ORDER);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                Order order = new Order(rs.getLong("id"),
                        rs.getLong("user_id"),
                        rs.getBigDecimal("total_price"));

                orders.add(order);
            }
        } catch (SQLException e) {
            LOGGER.error(WRONG_SQL_STATEMENT);
        }

        return orders;
    }
}
