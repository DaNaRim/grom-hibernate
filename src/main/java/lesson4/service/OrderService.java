package lesson4.service;

import lesson4.DAO.OrderDAO;
import lesson4.DAO.RoomDAO;
import lesson4.DAO.UserDAO;
import lesson4.exception.*;
import lesson4.model.Order;
import lesson4.model.Room;
import lesson4.model.User;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class OrderService {

    private static final UserService userService = new UserService();
    private static final OrderDAO orderDAO = new OrderDAO();
    private static final RoomDAO roomDAO = new RoomDAO();
    private static final UserDAO userDAO = new UserDAO();

    public void bookRoom(long roomId, long userId, Date dateFrom, Date dateTo)
            throws InternalServerException, NoAccessException, BadRequestException, NotLogInException {

        userService.isLoggedUser(userId);
        validateRoomAndUser(roomId, userId);
        validateBookRoom(roomId, dateFrom, dateTo);

        orderDAO.save(new Order(
                new User(userId),
                new Room(roomId),
                dateFrom,
                dateTo,
                roomDAO.getPriceById(roomId)));
    }

    public void cancelReservation(long roomId, long userId)
            throws InternalServerException, NoAccessException, BadRequestException, NotLogInException {

        validateRoomAndUser(roomId, userId);
        userService.isLoggedUser(userId);
        validateCancellation(roomId, userId);

        orderDAO.delete(new Order(orderDAO.getIdByRoomAndUser(roomId, userId)));
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
            throws InternalServerException, BadRequestException {

        if (dateFrom == null || dateTo == null) {
            throw new BadRequestException("Not all fields are filled correctly");
        }
        if (dateTo.before(dateFrom) || dateTo.equals(dateFrom) || dateFrom.before(new Date())) {
            throw new BadRequestException("Dates filled is incorrect");
        }
        checkRoomForBusy(roomId, dateFrom, dateTo);
    }

    private void checkRoomForBusy(long roomId, Date dateFrom, Date dateTo)
            throws InternalServerException, BadRequestException {

        List<Order> orders;
        try {
            orders = orderDAO.getActualOrdersByRoom(roomId);
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
                        "The room is busy from %s to %s",
                        sdf.format(busyTimeFrom), sdf.format(busyTimeTo)));
            }
        }
    }

    private void validateCancellation(long roomId, long userId) throws InternalServerException, BadRequestException {
        Date orderDateFrom;
        try {
            orderDateFrom = orderDAO.findOrderByRoomAndUser(roomId, userId).getDateFrom();
        } catch (NotFoundException e) {
            throw new BadRequestException("Missing order");
        }
        if (orderDateFrom.before(new Date())) {
            throw new BadRequestException("Possible cancellation has expired");
        }
    }
}
