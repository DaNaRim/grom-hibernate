package lesson4.controller;

import lesson4.exception.BadRequestException;
import lesson4.exception.InternalServerException;
import lesson4.exception.NoAccessException;
import lesson4.exception.NotLogInException;
import lesson4.model.User;
import lesson4.model.UserType;
import lesson4.service.UserService;

public class UserController {

    private static final UserService userService = new UserService();

    public User registerUser(User user) throws InternalServerException, BadRequestException {
        return userService.registerUser(user);
    }

    public void login(String userName, String password) throws BadRequestException, InternalServerException {
        userService.login(userName, password);
    }

    public void logout() throws NotLogInException {
        userService.logout();
    }

    public void setUserType(Long id, UserType userType)
            throws InternalServerException, NoAccessException, BadRequestException {

        userService.setUserType(id, userType);
    }
}
