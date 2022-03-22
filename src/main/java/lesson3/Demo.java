package lesson3;

import lesson3.DAO.HotelDAO;
import lesson3.DAO.RoomDAO;
import lesson3.model.Hotel;
import lesson3.model.Room;

import java.util.Date;

public class Demo {

    public static void main(String[] args) throws Exception {
        HotelDAO hotelDAO = new HotelDAO();
        RoomDAO roomDAO = new RoomDAO();

        Hotel hotel = new Hotel("2", "Test", "Test", "Test");
        hotelDAO.save(hotel);

        hotel.setCity("Test2");
        hotelDAO.update(hotel);

        hotelDAO.findById(2);
        hotelDAO.delete(2);

        Room room = new Room(2, 202.00, 1, 1, new Date(), hotel);
        roomDAO.save(room);

        room.setNumberOfGuests(3);
        roomDAO.update(room);

        roomDAO.findById(2);
        roomDAO.delete(2);
    }
}
