package lesson4.service;

import lesson4.DAO.OrderDAO;
import lesson4.DAO.RoomDAO;
import lesson4.DAO.UserDAO;
import lesson4.exception.*;
import lesson4.model.Order;
import lesson4.model.Room;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class OrderService {

    private static final UserService userService = new UserService();
    private static final OrderDAO orderDAO = new OrderDAO();
    private static final RoomDAO roomDAO = new RoomDAO();
    private static final UserDAO userDAO = new UserDAO();

    public void bookRoom(long roomId, long userId, Date dateFrom, Date dateTo)
            throws InternalServerException, NoAccessException, BadRequestException, NotFoundException,
            NotLogInException {

        userService.isLoggedUser(userId);
        validateRoomAndUser(roomId, userId);
        validateBookRoom(roomId, dateFrom, dateTo);

        orderDAO.save(new Order(
                userDAO.findById(userId), //TODO check and simplify
                roomDAO.findById(roomId),
                dateFrom,
                dateTo,
                roomDAO.findById(roomId).getPrice())); //TODO check and simplify
    }

    public void cancelReservation(long roomId, long userId)
            throws InternalServerException, NoAccessException, BadRequestException, NotFoundException,
            NotLogInException {

        validateRoomAndUser(roomId, userId);
        userService.isLoggedUser(userId);
        validateCancellation(roomId, userId);

        Order order = new Order();
        order.setId(orderDAO.getIdByRoomAndUser(roomId, userId));
        orderDAO.delete(order);
    }

    private void validateRoomAndUser(long roomId, long userId) throws InternalServerException, BadRequestException {
        if (!roomDAO.isExists(roomId)) {
            throw new BadRequestException("Missing room with id " + roomId);
        }
        if (!userDAO.isExists(userId)) {
            throw new BadRequestException("Missing user with id " + userId);
        }
    }

    private void validateBookRoom(long roomId, Date dateFrom, Date dateTo)
            throws InternalServerException, BadRequestException, NotFoundException {

        if (dateFrom == null || dateTo == null) {
            throw new BadRequestException("Not all fields are filled correctly");
        }
        if (dateTo.before(dateFrom) || dateTo.equals(dateFrom) || dateFrom.before(new Date())) {
            throw new BadRequestException("Dates filled is incorrect");
        }
        checkRoomForBusy(roomDAO.findById(roomId), dateFrom, dateTo);
    }

    private void checkRoomForBusy(Room room, Date dateFrom, Date dateTo)
            throws InternalServerException, BadRequestException {

        List<Order> orders;
        try {
            orders = orderDAO.getActualOrdersByRoom(room.getId());
        } catch (NotFoundException e) {
            return;
        }

        for (Order order : orders) {
            Date busyTimeFrom = order.getDateFrom();
            Date busyTimeTo = order.getDateTo();

            //isDatesCrossOrderRange
            if (!(busyTimeTo.before(dateFrom) || busyTimeFrom.after(dateTo))) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy kk:00");

                throw new BadRequestException(String.format(
                        "checkRoomForBusy failed: the room is busy from %s to %s",
                        sdf.format(busyTimeFrom), sdf.format(busyTimeTo)));
            }
        }
    }

    private void validateCancellation(long roomId, long userId)
            throws InternalServerException, BadRequestException, NotFoundException {

        Date orderDateFrom = orderDAO.findOrderByRoomAndUser(roomId, userId).getDateFrom();

        if (orderDateFrom.before(new Date())) {
            throw new BadRequestException("validateCancellation failed: possible cancellation has expired");
        }
    }
}
