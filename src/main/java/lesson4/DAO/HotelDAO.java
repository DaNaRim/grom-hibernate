package lesson4.DAO;

import lesson4.exception.BadRequestException;
import lesson4.exception.InternalServerException;
import lesson4.exception.NotFoundException;
import lesson4.model.Hotel;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import javax.persistence.NoResultException;
import java.util.List;

public class HotelDAO extends DAO<Hotel> {

    private static final String QUERY_FIND_HOTEL_BY_NAME = "SELECT * FROM hotel WHERE name = :name";
    private static final String QUERY_FIND_HOTEL_BY_CITY = "SELECT * FROM hotel WHERE city = :city";
    private static final String QUERY_IS_HOTEL_EXIST =
            "SELECT * FROM hotel"
                    + " WHERE name = :name"
                    + "   AND country = :country"
                    + "   AND city = :city"
                    + "   AND street = :street";

    public HotelDAO(Class<Hotel> hotelClass) {
        super(hotelClass);
    }

    public List<Hotel> findHotelByName(String name) throws InternalServerException, NotFoundException {
        try (Session session = HibernateUtil.createSessionFactory().openSession()) {

            List<Hotel> hotels = session.createNativeQuery(QUERY_FIND_HOTEL_BY_NAME, Hotel.class)
                    .setParameter("name", name)
                    .list();

            if (hotels.isEmpty()) {
                throw new NotFoundException("findHotelByName failed: there is no hotels with this name");
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
                throw new NotFoundException("findHotelByCity failed: there is no hotels in this city");
            }
            return hotels;
        } catch (HibernateException e) {
            throw new InternalServerException("findHotelByCity failed: " + e.getMessage());
        }
    }

    //FIXME
    public void isHotelExist(Hotel hotel) throws InternalServerException, BadRequestException {
        try (Session session = HibernateUtil.createSessionFactory().openSession()) {

            Hotel hotel1 = session.createNativeQuery(QUERY_IS_HOTEL_EXIST, Hotel.class)
                    .setParameter("name", hotel.getName())
                    .setParameter("country", hotel.getCountry())
                    .setParameter("city", hotel.getCity())
                    .setParameter("street", hotel.getStreet())
                    .getSingleResult();

            throw new BadRequestException("isHotelExist failed: the hotel is already exist. Id = " + hotel1.getId());

        } catch (NoResultException e) {
            System.out.println("isHotelExist: Object not found in database. Will be saved");
        } catch (HibernateException e) {
            throw new InternalServerException("isHotelExist failed: something went wrong: " + e.getMessage());
        }
    }
}
