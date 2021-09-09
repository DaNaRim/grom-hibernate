package lesson4.demo;

import lesson4.controller.HotelController;
import lesson4.controller.UserController;
import lesson4.exception.BadRequestException;
import lesson4.exception.InternalServerException;
import lesson4.exception.NoAccessException;

public class DemoHotel {
    private static final HotelController hotelController = new HotelController();
    private static final UserController userController = new UserController();

    public static void main(String[] args)
            throws BadRequestException, NoAccessException, InternalServerException {

//        userController.login("TEST1", "SuperPassword2");

//        userController.login("DANARIM", "SuperPassword");

//        Hotel hotel = new Hotel("TestHotel3", "Ukraine", "TestCity3", "TestStreet");
////
//        hotelController.addHotel(hotel);

//        System.out.println(hotelController.findHotelByCity("TestCity3").toString());
//
//        System.out.println(hotelController.findHotelByName("TestHotel3").toString());
//
//        hotelController.deleteHotel(1);
    }
}