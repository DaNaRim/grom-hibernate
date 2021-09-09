package lesson2.homework2;

import org.hibernate.*;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class ProductDAO {
    private static SessionFactory sessionFactory;

    public static Product findById(long id) throws Exception {
        try (Session session = createSessionFactory().openSession()) {

            String sql = "SELECT * FROM Product WHERE id = :Id";

            return session.createNativeQuery(sql, Product.class)
                    .setParameter("Id", id)
                    .getSingleResult();

        } catch (HibernateException e) {
            throw new Exception("findById failed" + e.getMessage());
        }
    }

    public static List<Product> findByName(String name) throws Exception {
        try (Session session = createSessionFactory().openSession()) {

            String sql = "SELECT * FROM Product WHERE name = :Name";

            return session.createNativeQuery(sql, Product.class)
                    .setParameter("Name", name)
                    .list();

        } catch (HibernateException e) {
            throw new Exception("findByName failed" + e.getMessage());
        }
    }

    public static List<Product> findByContainedName(String name) throws Exception {
        try (Session session = createSessionFactory().openSession()) {

            String sql = "SELECT * FROM Product WHERE name LIKE :ContainedName";

            return session.createNativeQuery(sql, Product.class)
                    .setParameter("ContainedName", "%" + name + "%")
                    .list();

        } catch (HibernateException e) {
            throw new Exception("findByContainedName failed" + e.getMessage());
        }
    }

    public static List<Product> findByPrice(int price, int delta) throws Exception {
        try (Session session = createSessionFactory().openSession()) {

            String sql = "SELECT * FROM Product WHERE price BETWEEN :minValue AND :maxValue";

            return session.createNativeQuery(sql, Product.class)
                    .setParameter("minValue", price - delta)
                    .setParameter("maxValue", price + delta)
                    .list();

        } catch (HibernateException e) {
            throw new Exception("findByPrice failed" + e.getMessage());
        }
    }

    public static List<Product> findByNameSortedAsc(String name) throws Exception {
        try (Session session = createSessionFactory().openSession()) {

            String sql = "SELECT * FROM Product WHERE name LIKE :ContainedName ORDER BY name";

            return session.createNativeQuery(sql, Product.class)
                    .setParameter("ContainedName", "%" + name + "%")
                    .list();

        } catch (HibernateException e) {
            throw new Exception("findByNameSortedAsc failed" + e.getMessage());
        }
    }

    public static List<Product> findByNameSortedDesc(String name) throws Exception {
        try (Session session = createSessionFactory().openSession()) {

            String sql = "SELECT * FROM Product WHERE name LIKE :ContainedName ORDER BY name DESC";

            return session.createNativeQuery(sql, Product.class)
                    .setParameter("ContainedName", "%" + name + "%")
                    .list();

        } catch (HibernateException e) {
            throw new Exception("findByNameSortedDesc failed" + e.getMessage());
        }
    }

    public static List<Product> findByPriceSortedDesc(int price, int delta) throws Exception {
        try (Session session = createSessionFactory().openSession()) {

            String sql = "SELECT * FROM Product WHERE price BETWEEN :minValue AND :maxValue ORDER BY price DESC";

            return session.createNativeQuery(sql, Product.class)
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