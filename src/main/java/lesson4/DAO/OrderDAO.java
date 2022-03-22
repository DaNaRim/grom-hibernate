package lesson4.DAO;

import lesson4.exception.BadRequestException;
import lesson4.exception.InternalServerException;
import lesson4.exception.NotFoundException;
import lesson4.model.Order;
import lesson4.model.Room;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import javax.persistence.NoResultException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class OrderDAO extends DAO<Order> {

    private static final RoomDAO roomDAO = new RoomDAO(Room.class);

    private static final String QUERY_FIND_ORDER_BY_ROOM_AND_USER =
            "SELECT * FROM orders"
                    + " WHERE room_id = :roomId"
                    + "   AND user_id = :userId";
    private static final String QUERY_CHECK_ROOM_FOR_BUSY =
            "SELECT * FROM orders"
                    + " WHERE room_id = :roomId"
                    + "   AND date_to > CURRENT_DATE";

    public OrderDAO(Class<Order> orderClass) {
        super(orderClass);
    }

    public Order findOrderByRoomAndUser(long roomId, long userId) throws InternalServerException, BadRequestException {
        try (Session session = HibernateUtil.createSessionFactory().openSession()) {

            return session.createNativeQuery(QUERY_FIND_ORDER_BY_ROOM_AND_USER, Order.class)
                    .setParameter("roomId", roomId)
                    .setParameter("userId", userId)
                    .getSingleResult();

        } catch (NoResultException e) {
            throw new BadRequestException("findOrderByRoomAndUser failed: missing order");
        } catch (HibernateException e) {
            throw new InternalServerException("findOrderByRoomAndUser failed: something went wrong: " + e.getMessage());
        }
    }

    public void checkRoomForBusy(long roomId, Date dateFrom, Date dateTo)
            throws InternalServerException, BadRequestException, NotFoundException {

        List<Order> orders = null;
        try (Session session = HibernateUtil.createSessionFactory().openSession()) {

            orders = session.createNativeQuery(QUERY_CHECK_ROOM_FOR_BUSY, Order.class)
                    .setParameter("roomId", roomId)
                    .list();

        } catch (NoResultException e) {
            System.out.println("checkRoomForBusy: room is never booked");
        } catch (HibernateException e) {
            throw new InternalServerException("checkRoomForBusy failed: something went wrong: " + e.getMessage());
        }

        Room room = roomDAO.findById(roomId);
        Date dateAvailableFrom = room.getDateAvailableFrom();

        if (room.getDateAvailableFrom().after(dateFrom))
            throw new BadRequestException("checkRoomForBusy failed: the room is busy until " + dateAvailableFrom);

        if (orders != null) {
            Date busyTimeRoomFrom;
            Date busyTimeRoomTo;
            for (Order order : orders) {

                busyTimeRoomFrom = order.getDateFrom();
                busyTimeRoomTo = order.getDateTo();

                if (order.getDateTo().after(new Date()) &&
                        !(busyTimeRoomTo.before(dateFrom) || busyTimeRoomFrom.after(dateTo))) {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy kk:00");

                    throw new BadRequestException("checkRoomForBusy failed: the room is busy from " +
                            simpleDateFormat.format(busyTimeRoomFrom) + " to " +
                            simpleDateFormat.format(busyTimeRoomTo));
                }
            }
        }
    }
}
