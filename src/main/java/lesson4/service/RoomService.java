package lesson4.service;

import lesson4.DAO.HotelDAO;
import lesson4.DAO.RoomDAO;
import lesson4.exception.*;
import lesson4.model.Filter;
import lesson4.model.Room;

import java.util.List;

public class RoomService {

    private static final UserService userService = new UserService();
    private static final RoomDAO roomDAO = new RoomDAO();
    private static final HotelDAO hotelDAO = new HotelDAO();

    public List<Room> findRooms(Filter filter) throws InternalServerException, BadRequestException, NotFoundException {
        validateFilter(filter);
        return roomDAO.findRooms(filter);
    }

    public Room addRoom(Room room)
            throws InternalServerException, BadRequestException, NoAccessException, NotLogInException {

        userService.checkForAdminPermissions();
        validateRoom(room);
        return roomDAO.save(room);
    }

    public void deleteRoom(long roomId)
            throws InternalServerException, NoAccessException, NotLogInException, BadRequestException {

        userService.checkForAdminPermissions();

        if (!roomDAO.isExists(roomId)) {
            throw new BadRequestException("Missing room with id: " + roomId);
        }
        Room room = new Room();
        room.setId(roomId);
        roomDAO.delete(room);
    }

    private void validateFilter(Filter filter) throws BadRequestException {
        if (filter.getNumberOfGuests() != null
                    && filter.getNumberOfGuests() < 0
                || filter.getPrice() != null
                    && filter.getPrice() < 0) {
            throw new BadRequestException("One of numeric options is incorrect");
        }
    }

    private void validateRoom(Room room) throws InternalServerException, BadRequestException {
        if (room == null) {
            throw new BadRequestException("Impossible to process null room");
        }
        if (room.getNumberOfGuests() == null
                || room.getPrice() == null
                || room.getBreakfastIncluded() == null
                || room.getPetsAllowed() == null
                || room.getDateAvailableFrom() == null
                || room.getHotel() == null
                || room.getNumberOfGuests() < 1
                || room.getPrice() <= 0.0) {
            throw new BadRequestException("Not all fields are filled correctly");
        }
        if (!hotelDAO.isExist(room.getHotel().getId())) {
            throw new BadRequestException("Missing hotel with id: " + room.getHotel().getId());
        }
    }
}
