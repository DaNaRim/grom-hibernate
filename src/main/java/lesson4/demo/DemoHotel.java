package lesson4.demo;

import lesson4.controller.HotelController;
import lesson4.controller.UserController;
import lesson4.model.Hotel;

public class DemoHotel {

    private static final HotelController hotelController = new HotelController();
    private static final UserController userController = new UserController();

    public static void main(String[] args) throws Exception {

        userController.login("First", "SuperPassword");

        userController.login("Second", "SuperPassword2");

        Hotel hotel = new Hotel("GoodHotel2", "USA", "SeaCity", "TestStreet2");
        hotelController.addHotel(hotel);

        System.out.println(hotelController.findHotelByCity("SeaCity").toString());
        System.out.println(hotelController.findHotelByName("HotelToDelete").toString());

        hotelController.deleteHotel(2L);
    }
}
