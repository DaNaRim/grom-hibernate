package lesson4.DAO;

import lesson4.exception.InternalServerException;
import lesson4.exception.NotFoundException;
import lesson4.model.Hotel;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import javax.persistence.NoResultException;
import java.util.List;

public class HotelDAO extends DAO<Hotel> {

    public HotelDAO() {
        super(Hotel.class);
    }

    private static final String QUERY_FIND_HOTEL_BY_NAME = "SELECT * FROM hotel WHERE name = :name";
    private static final String QUERY_FIND_HOTEL_BY_CITY = "SELECT * FROM hotel WHERE city = :city";
    private static final String QUERY_IS_HOTEL_WITH_PARAMETERS_EXIST =
            "SELECT 1 FROM hotel"
                    + " WHERE name = :name"
                    + "   AND country = :country"
                    + "   AND city = :city"
                    + "   AND street = :street";

    private static final String QUERY_IS_HOTEL_EXIST = "SELECT 1 FROM hotel WHERE id = :id";

    public List<Hotel> findHotelByName(String name) throws InternalServerException, NotFoundException {
        try (Session session = HibernateUtil.createSessionFactory().openSession()) {

            List<Hotel> hotels = session.createNativeQuery(QUERY_FIND_HOTEL_BY_NAME, Hotel.class)
                    .setParameter("name", name)
                    .list();

            if (hotels.isEmpty()) {
                throw new NotFoundException("There are no hotels with name " + name);
            }
            return hotels;
        } catch (HibernateException e) {
            throw new InternalServerException("findHotelByName failed: " + e.getMessage());
        }
    }

    public List<Hotel> findHotelByCity(String city) throws InternalServerException, NotFoundException {
        try (Session session = HibernateUtil.createSessionFactory().openSession()) {

            List<Hotel> hotels = session.createNativeQuery(QUERY_FIND_HOTEL_BY_CITY, Hotel.class)
                    .setParameter("city", city)
                    .list();

            if (hotels.isEmpty()) {
                throw new NotFoundException("There are no hotels in city " + city);
            }
            return hotels;
        } catch (HibernateException e) {
            throw new InternalServerException("findHotelByCity failed: " + e.getMessage());
        }
    }

    public boolean isHotelWithParametersExists(Hotel hotel) throws InternalServerException {
        try (Session session = HibernateUtil.createSessionFactory().openSession()) {

            session.createNativeQuery(QUERY_IS_HOTEL_WITH_PARAMETERS_EXIST)
                    .setParameter("name", hotel.getName())
                    .setParameter("country", hotel.getCountry())
                    .setParameter("city", hotel.getCity())
                    .setParameter("street", hotel.getStreet())
                    .getSingleResult();

            return true;
        } catch (NoResultException e) {
            return false;
        } catch (HibernateException e) {
            throw new InternalServerException("isHotelWithParametersExists failed: " + e.getMessage());
        }
    }

    public boolean isExist(long hotelId) throws InternalServerException {
        try (Session session = HibernateUtil.createSessionFactory().openSession()) {

            session.createNativeQuery(QUERY_IS_HOTEL_EXIST)
                    .setParameter("id", hotelId)
                    .getSingleResult();

            return true;
        } catch (NoResultException e) {
            return false;
        } catch (HibernateException e) {
            throw new InternalServerException("isExist failed: " + e.getMessage());
        }
    }
}
