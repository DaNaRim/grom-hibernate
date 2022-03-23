package lesson4.controller;

import lesson4.exception.*;
import lesson4.service.OrderService;

import java.util.Date;

public class OrderController {

    private static final OrderService orderService = new OrderService();

    public void bookRoom(long roomId, long userId, Date dateFrom, Date dateTo)
            throws NoAccessException, InternalServerException, BadRequestException, NotFoundException, NotLogInException {

        orderService.bookRoom(roomId, userId, dateFrom, dateTo);
    }

    public void cancelReservation(long roomId, long userId)
            throws NoAccessException, InternalServerException, BadRequestException, NotFoundException, NotLogInException {

        orderService.cancelReservation(roomId, userId);
    }
}
