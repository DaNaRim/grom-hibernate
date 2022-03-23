package lesson4.service;

import lesson4.DAO.OrderDAO;
import lesson4.DAO.RoomDAO;
import lesson4.DAO.UserDAO;
import lesson4.exception.BadRequestException;
import lesson4.exception.InternalServerException;
import lesson4.exception.NoAccessException;
import lesson4.exception.NotFoundException;
import lesson4.model.Order;
import lesson4.model.Room;
import lesson4.model.User;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class OrderService {

    private static final OrderDAO orderDAO = new OrderDAO(Order.class);
    private static final RoomDAO roomDAO = new RoomDAO(Room.class);
    private static final UserDAO userDAO = new UserDAO(User.class);
    private static final UserService userService = new UserService();

    public void bookRoom(long roomId, long userId, Date dateFrom, Date dateTo)
            throws InternalServerException, NoAccessException, BadRequestException, NotFoundException {

        validateRoomAndUser(roomId, userId);
        userService.checkUserForOperation(userId);
        validateOrder(roomId, dateFrom, dateTo);

        Order order = createOrder(roomId, userId, dateFrom, dateTo);
        orderDAO.save(order);
    }

    public void cancelReservation(long roomId, long userId)
            throws InternalServerException, NoAccessException, BadRequestException, NotFoundException {

        validateRoomAndUser(roomId, userId);
        userService.checkUserForOperation(userId);
        validateCancellation(roomId, userId);

        Order order = orderDAO.findOrderByRoomAndUser(roomId, userId);
        orderDAO.delete(order);
    }

    private void validateCancellation(long roomId, long userId)
            throws InternalServerException, BadRequestException, NotFoundException {

        Date orderDateFrom = orderDAO.findOrderByRoomAndUser(roomId, userId).getDateFrom();

        if (orderDateFrom.before(new Date())) {
            throw new BadRequestException("validateCancellation failed: possible cancellation has expired");
        }
    }

    private void validateOrder(long roomId, Date dateFrom, Date dateTo)
            throws InternalServerException, BadRequestException, NotFoundException {

        if (dateFrom == null || dateTo == null) {
            throw new BadRequestException("validateOrder failed: not all fields are filled correctly");
        }
        if (dateTo.before(dateFrom) || dateTo.equals(dateFrom) || dateFrom.before(new Date())) {
            throw new BadRequestException("validateOrder failed: date filled is incorrect");
        }
        //FIXME
        checkRoomForBusy(roomDAO.findById(roomId), dateFrom, dateTo);
    }

    private void checkRoomForBusy(Room room, Date dateFrom, Date dateTo)
            throws InternalServerException, BadRequestException {

//        updateRoomDateAvailableFrom(room);
//
//        if (room.getDateAvailableFrom().after(dateFrom)) {
//            throw new BadRequestException("Room is busy until " + room.getDateAvailableFrom());
//        }

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

    private void updateRoomDateAvailableFrom(Room room) throws InternalServerException {
        List<Order> orders;
        try {
            orders = orderDAO.getActualOrdersByRoom(room.getId());
        } catch (NotFoundException e) {
            return;
        }

        Date dateAvailableFrom = room.getDateAvailableFrom();
        for (Order order : orders) {

            if (order.getDateTo().after(dateAvailableFrom)
                    && order.getDateFrom().before(dateAvailableFrom)) {

                room.setDateAvailableFrom(order.getDateTo());
                break;
            }
        }
        roomDAO.update(room);
    }

    private void validateRoomAndUser(long roomId, long userId) throws InternalServerException, NotFoundException {
        roomDAO.findById(roomId);
        userDAO.findById(userId);
    }

    private Order createOrder(long roomId, long userId, Date dateFrom, Date dateTo)
            throws InternalServerException, NotFoundException {

        return new Order(
                userDAO.findById(userId),
                roomDAO.findById(roomId),
                dateFrom,
                dateTo,
                roomDAO.findById(roomId).getPrice());
    }
}
