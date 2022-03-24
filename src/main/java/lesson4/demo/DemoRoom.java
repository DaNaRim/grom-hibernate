package lesson4.demo;

import lesson4.controller.RoomController;
import lesson4.controller.UserController;
import lesson4.model.Filter;
import lesson4.model.Hotel;
import lesson4.model.Room;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DemoRoom {

    private static final RoomController roomController = new RoomController();
    private static final UserController userController = new UserController();

    public static void main(String[] args) throws Exception {

        userController.login("First", "SuperPassword");

        userController.login("Second", "SuperPassword2");

        Hotel hotel = new Hotel(2L);
        Room room = new Room(3, 150.40, true, true, new Date(), hotel);
        roomController.addRoom(room);

        Filter filter = new Filter(
                0,
                0.0,
                true,
                true,
                new SimpleDateFormat("dd.MM.yyyy").parse("25.02.2020"),
                "Ukraine",
                null);

        System.out.println(roomController.findRooms(filter).toString());

        roomController.deleteRoom(2L);
    }
}
