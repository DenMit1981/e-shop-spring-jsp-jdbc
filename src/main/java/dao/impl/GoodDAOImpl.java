package dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import utils.DBUtils;
import dao.GoodDAO;
import model.Good;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Repository
public class GoodDAOImpl implements GoodDAO {

    private static final Logger LOGGER = LogManager.getLogger(GoodDAOImpl.class.getName());

    private static final String QUERY_SELECT_FROM_GOOD = "SELECT * FROM good";

    @Override
    public List<Good> getAll() {
        List<Good> goods = new ArrayList<>();

        try (PreparedStatement statement = DBUtils.getConnection().prepareStatement(QUERY_SELECT_FROM_GOOD);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                Good good = new Good(rs.getLong("id"),
                        rs.getString("title"),
                        rs.getBigDecimal("price"));

                goods.add(good);
            }
        } catch (SQLException e) {
            LOGGER.error("Wrong SQL statement");
        }

        return goods;
    }

    @Override
    public Good getByTitleAndPrice(String title, String price) {
        return getAll().stream()
                .filter(good -> title.equals(good.getTitle())
                        && price.equals(String.valueOf(good.getPrice())))
                .findAny()
                .orElseThrow(() -> new NoSuchElementException(String.format("Good with title %s and price %s not found", title, price)));
    }
}
