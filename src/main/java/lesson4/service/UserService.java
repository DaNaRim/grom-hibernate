package lesson4.service;

import lesson4.DAO.UserDAO;
import lesson4.exception.*;
import lesson4.model.User;
import lesson4.model.UserType;

public class UserService {

    private static final UserDAO userDAO = new UserDAO();
    private static User loggedUser = null;

    public User registerUser(User user) throws InternalServerException, BadRequestException {
        validateUser(user);
        return userDAO.save(user);
    }

    public void login(String username, String password) throws InternalServerException, BadRequestException {

        if (username == null || password == null) {
            throw new BadRequestException("Not all fields are filed");
        }
        validateLoggedUser(username);
        loggedUser = validateLoginAndGetUser(username, password);
    }

    public void logout() throws NotLogInException {
        isSomeUserLogged();
        loggedUser = null;
    }

    public void setUserType(long id, UserType userType)
            throws NoAccessException, BadRequestException, InternalServerException, NotFoundException,
            NotLogInException {

        checkForAdminPermissions();
        User user = userDAO.findById(id);

        if (userType == null) {
            throw new BadRequestException("Can`t set null type");
        }
        if (user.getUserType().equals(userType.toString())) {
            throw new BadRequestException("User already has this type");
        }
        user.setUserType(userType.toString());
        userDAO.update(user);
    }

    public void checkForAdminPermissions() throws NoAccessException, NotLogInException {
        isSomeUserLogged();
        if (!loggedUser.getUserType().equals(UserType.ADMIN.toString())) {
            throw new NoAccessException("User don`t have enough rights");
        }
    }

    public void isLoggedUser(long id) throws NoAccessException, NotLogInException {
        isSomeUserLogged();
        if (!loggedUser.getId().equals(id)) {
            throw new NoAccessException("User can`t do this operation in the name of another user");
        }
    }

    private void isSomeUserLogged() throws NotLogInException {
        if (loggedUser == null) throw new NotLogInException("User is not log in");
    }

    private void validateLoggedUser(String userName) throws BadRequestException {
        if (loggedUser == null) return;

        if (loggedUser.getUserName().equals(userName)) {
            throw new BadRequestException("User already log in");
        }
        throw new BadRequestException("Another user is logged in now");
    }

    private User validateLoginAndGetUser(String username, String password)
            throws BadRequestException, InternalServerException {

        User user;
        try {
            user = userDAO.findByUsername(username);
        } catch (NotFoundException e) {
            throw new BadRequestException("Wrong username");
        }
        if (!user.getPassword().equals(password)) {
            throw new BadRequestException("Wrong password");
        }
        return user;
    }

    private void validateUser(User user) throws BadRequestException, InternalServerException {
        if (user == null) {
            throw new BadRequestException("Impossible to process null user");
        }
        if (user.getUserName() == null
                || user.getPassword() == null
                || user.getCountry() == null
                || user.getUserName().isBlank()
                || user.getPassword().isBlank()
                || user.getCountry().isBlank()) {
            throw new BadRequestException("Not all fields are filled");
        }
        if (user.getUserName().contains(" ")
                || user.getPassword().contains(" ")
                || user.getCountry().contains(" ")) {
            throw new BadRequestException("Fields must not contain spaces");
        }
        if (user.getUserName().length() > 20
                || user.getPassword().length() > 50
                || user.getCountry().length() > 50) {
            throw new BadRequestException("Fields size is too long");
        }
        if (user.getPassword().length() < 8) {
            throw new BadRequestException("Password must be at least 8 characters");
        }
        if (!userDAO.isUsernameUnique(user.getUserName())) {
            throw new BadRequestException("Username is already taken");
        }
    }
}
