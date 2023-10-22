package service.impl;

import dao.UserDAO;
import model.User;
import org.springframework.stereotype.Service;
import service.UserService;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class.getName());

    private static final String FIELD_IS_EMPTY = "Login or password shouldn't be empty";
    private static final String INVALID_FIELD = "Login or password shouldn't be less than 4 symbols";
    private static final String USER_IS_PRESENT = "This user is already present";

    private final UserDAO userDAO;

    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public User save(String login, String password) {
        User user = new User();

        user.setId(getAll().size() + 1L);
        user.setLogin(login);
        user.setPassword(password);

        userDAO.save(user);

        LOGGER.info("New user : {}", user);

        return user;
    }

    @Override
    public User getByLoginAndPassword(String login, String password) {
        return userDAO.getByLoginAndPassword(login, password);
    }

    @Override
    public List<User> getAll() {
        return userDAO.getAll();
    }

    @Override
    public boolean isInvalidUser(String login, String password) {
        return login.length() < 4 || password.length() < 4 || isUserPresent(login, password);
    }

    @Override
    public String invalidUser(String login, String password) {
        if (login.isEmpty() || password.isEmpty()) {
            LOGGER.error(FIELD_IS_EMPTY);

            return FIELD_IS_EMPTY;
        }

        if (isUserPresent(login, password)) {
            LOGGER.error(USER_IS_PRESENT);

            return USER_IS_PRESENT;
        }

        LOGGER.error(INVALID_FIELD);

        return INVALID_FIELD;
    }

    private boolean isUserPresent(String login, String password) {
        User user = getByLoginAndPassword(login, password);

        return user.getLogin().equals(login) && user.getPassword().equals(password);
    }
}
