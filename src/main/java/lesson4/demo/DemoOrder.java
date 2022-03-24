package lesson4.demo;

import lesson4.controller.OrderController;
import lesson4.controller.UserController;

import java.text.SimpleDateFormat;

public class DemoOrder {

    private static final OrderController orderController = new OrderController();
    private static final UserController userController = new UserController();

    public static void main(String[] args) throws Exception {

        userController.login("First", "SuperPassword");

        userController.login("Second", "SuperPassword2");

        orderController.bookRoom(3L, 2L,
                new SimpleDateFormat("dd.MM.yyyy kk:00").parse("27.04.2022 11:00"),
                new SimpleDateFormat("dd.MM.yyyy kk:00").parse("28.04.2022 12:00"));

        orderController.cancelReservation(3L, 2L);
    }
}
