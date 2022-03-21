package lesson2.homework1;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.NoResultException;
import java.util.List;

public class ProductDAO {

    private static SessionFactory sessionFactory;

    private static final String QUERY_FIND_BY_ID = "FROM Product WHERE id = :id";
    private static final String QUERY_FIND_BY_NAME = "FROM Product WHERE name = :name";
    private static final String QUERY_FIND_BY_CONTAINED_NAME = "FROM Product WHERE name LIKE CONCAT('%', :name, '%')";
    private static final String QUERY_FIND_BETWEEN_PRICES = "FROM Product WHERE price BETWEEN :minValue AND :maxValue";

    private static final String QUERY_FIND_BY_CONTAINED_NAME_SORTED_ASC =
            "FROM Product WHERE name LIKE CONCAT('%', :name, '%') ORDER BY name";

    private static final String QUERY_FIND_BY_CONTAINED_NAME_SORTED_DESC =
            "FROM Product WHERE name LIKE CONCAT('%', :name, '%') ORDER BY name DESC";

    private static final String QUERY_FIND_BETWEEN_PRICES_SORTED_DESC =
            "FROM Product WHERE price BETWEEN :minValue AND :maxValue ORDER BY price DESC";

    public static Product findById(long id) throws Exception {
        try (Session session = createSessionFactory().openSession()) {

            return session.createQuery(QUERY_FIND_BY_ID, Product.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException e) {
            throw new Exception("Missing product with id " + id);
        } catch (HibernateException e) {
            throw new Exception("findById failed: " + e.getMessage());
        }
    }

    public static List<Product> findByName(String name) throws Exception {
        try (Session session = createSessionFactory().openSession()) {

            return session.createQuery(QUERY_FIND_BY_NAME, Product.class)
                    .setParameter("name", name)
                    .list();
        } catch (HibernateException e) {
            throw new Exception("findByName failed: " + e.getMessage());
        }
    }

    public static List<Product> findByContainedName(String name) throws Exception {
        try (Session session = createSessionFactory().openSession()) {

            return session.createQuery(QUERY_FIND_BY_CONTAINED_NAME, Product.class)
                    .setParameter("name", name)
                    .list();
        } catch (HibernateException e) {
            throw new Exception("findByContainedName failed: " + e.getMessage());
        }
    }

    public static List<Product> findByPrice(int price, int delta) throws Exception {
        try (Session session = createSessionFactory().openSession()) {

            return session.createQuery(QUERY_FIND_BETWEEN_PRICES, Product.class)
                    .setParameter("minValue", price - delta)
                    .setParameter("maxValue", price + delta)
                    .list();
        } catch (HibernateException e) {
            throw new Exception("findByPrice failed: " + e.getMessage());
        }
    }

    public static List<Product> findByNameSortedAsc(String name) throws Exception {
        try (Session session = createSessionFactory().openSession()) {

            return session.createQuery(QUERY_FIND_BY_CONTAINED_NAME_SORTED_ASC, Product.class)
                    .setParameter("name", name)
                    .list();
        } catch (HibernateException e) {
            throw new Exception("findByNameSortedAsc failed: " + e.getMessage());
        }
    }

    public static List<Product> findByNameSortedDesc(String name) throws Exception {
        try (Session session = createSessionFactory().openSession()) {

            return session.createQuery(QUERY_FIND_BY_CONTAINED_NAME_SORTED_DESC, Product.class)
                    .setParameter("name", name)
                    .list();
        } catch (HibernateException e) {
            throw new Exception("findByNameSortedDesc failed" + e.getMessage());
        }
    }

    public static List<Product> findByPriceSortedDesc(int price, int delta) throws Exception {
        try (Session session = createSessionFactory().openSession()) {

            return session.createQuery(QUERY_FIND_BETWEEN_PRICES_SORTED_DESC, Product.class)
                    .setParameter("minValue", price - delta)
                    .setParameter("maxValue", price + delta)
                    .list();
        } catch (HibernateException e) {
            throw new Exception("findByPriceSortedDesc failed" + e.getMessage());
        }
    }

    private static SessionFactory createSessionFactory() {
        if (sessionFactory == null) {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        }
        return sessionFactory;
    }
}
