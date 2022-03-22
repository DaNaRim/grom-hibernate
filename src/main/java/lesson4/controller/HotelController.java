package lesson4.controller;

import lesson4.exception.BadRequestException;
import lesson4.exception.InternalServerException;
import lesson4.exception.NoAccessException;
import lesson4.exception.NotFoundException;
import lesson4.model.Hotel;
import lesson4.service.HotelService;

import java.util.List;

public class HotelController {

    private static final HotelService hotelService = new HotelService();

    public List<Hotel> findHotelByName(String name) throws BadRequestException, InternalServerException {
        return hotelService.findHotelByName(name);
    }

    public List<Hotel> findHotelByCity(String city) throws BadRequestException, InternalServerException {
        return hotelService.findHotelByCity(city);
    }

    public Hotel addHotel(Hotel hotel) throws InternalServerException, NoAccessException, BadRequestException {
        return hotelService.addHotel(hotel);
    }

    public void deleteHotel(long hotelId) throws InternalServerException, NoAccessException, NotFoundException {
        hotelService.deleteHotel(hotelId);
    }
}
