package service;

import model.User;

import java.util.List;

public interface UserService {

    User save(String login, String password);

    User getByLoginAndPassword(String login, String password);

    List<User> getAll();

    boolean isInvalidUser(String login, String password);

    String invalidUser(String login, String password);
}
