package lesson4.demo;

import lesson4.controller.UserController;
import lesson4.model.User;
import lesson4.model.UserType;

public class DemoUser {

    private static final UserController userController = new UserController();

    public static void main(String[] args) throws Exception {

        User user1 = new User("Second", "SuperPassword2", "Sweden");
        userController.registerUser(user1);

        userController.login("First", "SuperPassword");

        userController.logout();

        userController.login("DaNaRim", "f5urhg%89aohfol347hgfv93");

        userController.setUserType(2L, UserType.USER);
    }
}
