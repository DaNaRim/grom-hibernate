package lesson3.DAO;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class DAO<T> {

    private final Class<T> tClass;

    public DAO(Class<T> tClass) {
        this.tClass = tClass;
    }

    public T save(T object) throws Exception {
        try (Session session = HibernateUtil.createSessionFactory().openSession()) {
            Transaction tr = session.getTransaction();
            tr.begin();

            session.save(object);

            tr.commit();
            return object;
        } catch (HibernateException e) {
            throw new Exception("Save failed: " + e.getMessage());
        }
    }

    public void delete(long id) throws Exception {
        try (Session session = HibernateUtil.createSessionFactory().openSession()) {
            Transaction tr = session.getTransaction();
            tr.begin();

            session.delete(findById(id));

            tr.commit();
        } catch (Exception e) {
            throw new Exception("Delete failed: " + e.getMessage());
        }
    }

    public T update(T object) throws Exception {
        try (Session session = HibernateUtil.createSessionFactory().openSession()) {
            Transaction tr = session.getTransaction();
            tr.begin();

            session.update(object);

            tr.commit();
            return object;
        } catch (HibernateException e) {
            throw new Exception("Update failed: " + e.getMessage());
        }
    }

    public T findById(long id) throws Exception {
        try (Session session = HibernateUtil.createSessionFactory().openSession()) {

            return session.get(this.tClass, id);

        } catch (HibernateException e) {
            throw new Exception("FindById failed: " + e.getMessage());
        }
    }
}
