package dao.impl;

import org.springframework.stereotype.Repository;
import utils.DBUtils;
import dao.UserDAO;
import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Repository
public class UserDAOImpl implements UserDAO {

    private static final Logger LOGGER = LogManager.getLogger(UserDAOImpl.class.getName());

    private static final String WRONG_SQL_STATEMENT = "Wrong SQL statement";
    private static final String QUERY_SELECT_FROM_USER = "SELECT * FROM user";
    private static final String QUERY_INSERT_INTO_USER = "INSERT INTO user (login, password) VALUES (?,?)";

    @Override
    public void save(User user) {
        try (PreparedStatement statement = DBUtils.getConnection().prepareStatement(QUERY_INSERT_INTO_USER)) {
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());

            statement.executeUpdate();

        } catch (SQLException e) {
            LOGGER.error(WRONG_SQL_STATEMENT);
        }
    }

    @Override
    public User getById(Long id) {
        return getAll().stream()
                .filter(user -> id.equals(user.getId()))
                .findAny()
                .orElseThrow((() -> new NoSuchElementException(String.format("User with id %s not found", id))));
    }

    @Override
    public User getByLoginAndPassword(String login, String password) {
        return getAll().stream()
                .filter(user -> login.equals(user.getLogin())
                        && password.equals(user.getPassword()))
                .findAny()
                .orElse(new User("Unknown"));
    }

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();

        try (PreparedStatement statement = DBUtils.getConnection().prepareStatement(QUERY_SELECT_FROM_USER);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                User user = new User(rs.getLong("id"),
                        rs.getString("login"),
                        rs.getString("password"));

                users.add(user);
            }
        } catch (SQLException e) {
            LOGGER.error(WRONG_SQL_STATEMENT);
        }

        return users;
    }
}
