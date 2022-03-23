package lesson4.DAO;

import lesson4.exception.InternalServerException;
import lesson4.exception.NotFoundException;
import lesson4.model.Order;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import javax.persistence.NoResultException;
import java.util.List;

public class OrderDAO extends DAO<Order> {

    public OrderDAO() {
        super(Order.class);
    }

    private static final String QUERY_FIND_ORDER_BY_ROOM_AND_USER =
            "SELECT * FROM orders"
                    + " WHERE room_id = :roomId"
                    + "   AND user_id = :userId";

    private static final String QUERY_GET_ID_BY_ROOM_AND_USER =
            "SELECT id FROM orders"
                    + " WHERE room_id = :roomId"
                    + "   AND user_id = :userId";

    private static final String QUERY_GET_ACTUAL_ORDERS_BY_ROOM =
            "SELECT * FROM orders"
                    + " WHERE room_id = :roomId"
                    + "   AND date_to > CURRENT_DATE";

    public Order findOrderByRoomAndUser(long roomId, long userId) throws InternalServerException, NotFoundException {
        try (Session session = HibernateUtil.createSessionFactory().openSession()) {

            return session.createNativeQuery(QUERY_FIND_ORDER_BY_ROOM_AND_USER, Order.class)
                    .setParameter("roomId", roomId)
                    .setParameter("userId", userId)
                    .getSingleResult();

        } catch (NoResultException e) {
            throw new NotFoundException("There is no order with user " + userId + " and room " + roomId);
        } catch (HibernateException e) {
            throw new InternalServerException("findOrderByRoomAndUser failed: " + e.getMessage());
        }
    }

    public long getIdByRoomAndUser(long roomId, long userId) throws InternalServerException {
        try (Session session = HibernateUtil.createSessionFactory().openSession()) {

            return session.createNativeQuery(QUERY_GET_ID_BY_ROOM_AND_USER)
                    .setParameter("roomId", roomId)
                    .setParameter("userId", userId)
                    .getFirstResult();

        } catch (HibernateException | NoResultException e) {
            throw new InternalServerException("getIdByRoomAndUser failed: " + e.getMessage());
        }
    }

    public List<Order> getActualOrdersByRoom(long roomId) throws InternalServerException, NotFoundException {
        try (Session session = HibernateUtil.createSessionFactory().openSession()) {

            List<Order> orders = session.createNativeQuery(QUERY_GET_ACTUAL_ORDERS_BY_ROOM, Order.class)
                    .setParameter("roomId", roomId)
                    .list();

            if (orders.isEmpty()) {
                throw new NotFoundException("There are no actual orders with room " + roomId);
            }
            return orders;
        } catch (HibernateException e) {
            throw new InternalServerException("getActualOrdersByRoom failed: " + e.getMessage());
        }
    }
}
