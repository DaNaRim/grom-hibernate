package lesson4.controller;

import lesson4.exception.BadRequestException;
import lesson4.exception.InternalServerException;
import lesson4.exception.NoAccessException;
import lesson4.exception.NotFoundException;
import lesson4.model.Filter;
import lesson4.model.Room;
import lesson4.service.RoomService;

import java.util.List;

public class RoomController {

    private static final RoomService roomService = new RoomService();

    public List<Room> findRooms(Filter filter) throws BadRequestException, InternalServerException {
        return roomService.findRooms(filter);
    }

    public Room addRoom(Room room)
            throws NoAccessException, InternalServerException, BadRequestException, NotFoundException {
        return roomService.addRoom(room);
    }

    public void deleteRoom(long roomId) throws NoAccessException, InternalServerException, NotFoundException {
        roomService.deleteRoom(roomId);
    }
}
