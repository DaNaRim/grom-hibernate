package lesson4.demo;

import lesson4.DAO.HotelDAO;
import lesson4.controller.RoomController;
import lesson4.controller.UserController;
import lesson4.exception.BadRequestException;
import lesson4.exception.InternalServerException;
import lesson4.exception.NoAccessException;
import lesson4.model.Hotel;

import java.text.ParseException;

public class DemoRoom {

    private static final RoomController roomController = new RoomController();
    private static final UserController userController = new UserController();
    private static final HotelDAO hotelDAO = new HotelDAO(Hotel.class);


    public static void main(String[] args)
            throws BadRequestException, NoAccessException, InternalServerException, ParseException {

//        userController.login("TEST1", "SuperPassword2");

        userController.login("DANARIM", "SuperPassword");

//        Hotel hotel = hotelDAO.findById(3);
//        Room room = new Room(3, 150.40, true, false, new Date(), hotel);
//
//        roomController.addRoom(room);


//        Filter filter = new Filter(3, 150.40, null, null,
//                /*new SimpleDateFormat("dd.MM.yyyy").parse("25.02.2020")*/ null, "Ukraine", "TestCity3");
//
//        System.out.println(roomController.findRooms(filter).toString());

        roomController.deleteRoom(4);
    }
}
