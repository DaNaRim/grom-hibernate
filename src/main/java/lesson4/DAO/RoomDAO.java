package lesson4.DAO;

import lesson4.exception.InternalServerException;
import lesson4.exception.NotFoundException;
import lesson4.model.Filter;
import lesson4.model.Hotel;
import lesson4.model.Room;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import javax.persistence.NoResultException;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class RoomDAO extends DAO<Room> {

    public RoomDAO() {
        super(Room.class);
    }

    private static final String QUERY_IS_ROOM_EXIST = "SELECT 1 FROM room WHERE id = :id";

    public List<Room> findRooms(Filter filter) throws InternalServerException, NotFoundException {
        try (Session session = HibernateUtil.createSessionFactory().openSession()) {

            CriteriaQuery<Room> criteria = getRoomCriteriaQuery(filter, session);

            List<Room> rooms = session.createQuery(criteria).getResultList();

            if (rooms.isEmpty()) {
                throw new NotFoundException("Missing rooms with this filter parameters");
            }
            return rooms;
        } catch (HibernateException e) {
            throw new InternalServerException("findRooms failed: " + e.getMessage());
        }
    }

    public boolean isExist(long roomId) throws InternalServerException {
        try (Session session = HibernateUtil.createSessionFactory().openSession()) {

            session.createNativeQuery(QUERY_IS_ROOM_EXIST)
                    .setParameter("id", roomId)
                    .getSingleResult();

            return true;
        } catch (NoResultException e) {
            return false;
        } catch (HibernateException e) {
            throw new InternalServerException("isExist failed: " + e.getMessage());
        }
    }

    private CriteriaQuery<Room> getRoomCriteriaQuery(Filter filter, Session session) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Room> criteria = builder.createQuery(Room.class);

        Root<Room> roomRoot = criteria.from(Room.class);
        Join<Room, Hotel> hotelJoin = roomRoot.join("hotel");

        List<Predicate> predicates = new ArrayList<>();

        if (filter.getNumberOfGuests() != null) {
            predicates.add(builder.equal(roomRoot.get("numberOfGuests"), filter.getNumberOfGuests()));
        }
        if (filter.getPrice() != null) {
            predicates.add(builder.equal(roomRoot.get("price"), filter.getPrice()));
        }
        if (filter.getBreakfastIncluded() != null) {
            predicates.add(builder.equal(roomRoot.get("breakfastIncluded"), filter.getBreakfastIncluded() ? 1 : 0));
        }
        if (filter.getPetsAllowed() != null) {
            predicates.add(builder.equal(roomRoot.get("petsAllowed"), filter.getPetsAllowed() ? 1 : 0));
        }
        if (filter.getDateAvailableFrom() != null) {
            predicates.add(builder.equal(roomRoot.get("dateAvailableFrom"), filter.getDateAvailableFrom()));
        }
        if (filter.getCountry() != null) {
            predicates.add(builder.equal(hotelJoin.get("country"), filter.getCountry()));
        }
        if (filter.getCity() != null) {
            predicates.add(builder.equal(hotelJoin.get("city"), filter.getCity()));
        }
        criteria.select(roomRoot).where(predicates.toArray(new Predicate[0]));
        return criteria;
    }
}
