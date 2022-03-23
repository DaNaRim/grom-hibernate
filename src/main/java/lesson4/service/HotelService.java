package lesson4.service;

import lesson4.DAO.HotelDAO;
import lesson4.exception.*;
import lesson4.model.Hotel;

import java.util.List;

public class HotelService {

    private static final UserService userService = new UserService();
    private static final HotelDAO hotelDAO = new HotelDAO();

    public List<Hotel> findHotelByName(String name)
            throws BadRequestException, InternalServerException, NotFoundException {

        if (name == null) {
            throw new BadRequestException("Name is not filled");
        }
        return hotelDAO.findHotelByName(name);
    }

    public List<Hotel> findHotelByCity(String city)
            throws BadRequestException, InternalServerException, NotFoundException {

        if (city == null) {
            throw new BadRequestException("City is not filled");
        }
        return hotelDAO.findHotelByCity(city);
    }

    public Hotel addHotel(Hotel hotel)
            throws NoAccessException, BadRequestException, InternalServerException, NotLogInException {

        userService.checkForAdminPermissions();
        validateHotel(hotel);

        return hotelDAO.save(hotel);
    }

    public void deleteHotel(long hotelId)
            throws NoAccessException, InternalServerException, NotLogInException, BadRequestException {

        userService.checkForAdminPermissions();

        if (!hotelDAO.isExist(hotelId)) {
            throw new BadRequestException("Missing hotel with id: " + hotelId);
        }
        hotelDAO.delete(new Hotel(hotelId));
    }

    private void validateHotel(Hotel hotel) throws BadRequestException, InternalServerException {
        if (hotel == null) {
            throw new BadRequestException("Impossible to process null hotel");
        }
        if (hotel.getName() == null
                || hotel.getCity() == null
                || hotel.getCountry() == null
                || hotel.getStreet() == null
                || hotel.getName().isBlank()
                || hotel.getCity().isBlank()
                || hotel.getCountry().isBlank()
                || hotel.getStreet().isBlank()) {
            throw new BadRequestException("Not all fields are filled");
        }
        if (hotel.getName().contains(" ")
                || hotel.getCountry().contains(" ")
                || hotel.getCity().contains(" ")
                || hotel.getStreet().contains(" ")) {
            throw new BadRequestException("Fields must not contain spaces");
        }
        if (hotel.getName().length() > 50
                || hotel.getCity().length() > 50
                || hotel.getCountry().length() > 50
                || hotel.getStreet().length() > 50) {
            throw new BadRequestException("Fields size is too long");
        }
        if (hotelDAO.isHotelWithParametersExists(hotel)) {
            throw new BadRequestException("The hotel with this parameters already exist");
        }
    }
}
