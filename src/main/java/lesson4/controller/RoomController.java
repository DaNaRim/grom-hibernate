package lesson4.controller;

import lesson4.exception.*;
import lesson4.model.Filter;
import lesson4.model.Room;
import lesson4.service.RoomService;

import java.util.List;

public class RoomController {

    private static final RoomService roomService = new RoomService();

    public List<Room> findRooms(Filter filter) throws BadRequestException, InternalServerException, NotFoundException {
        return roomService.findRooms(filter);
    }

    public Room addRoom(Room room)
            throws NoAccessException, InternalServerException, BadRequestException, NotFoundException, NotLogInException {
        return roomService.addRoom(room);
    }

    public void deleteRoom(long roomId) throws NoAccessException, InternalServerException, NotFoundException, NotLogInException {
        roomService.deleteRoom(roomId);
    }
}
