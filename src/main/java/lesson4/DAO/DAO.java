package lesson4.DAO;

import lesson4.exception.InternalServerException;
import lesson4.exception.NotFoundException;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class DAO<T> {

    private final Class<T> tClass;

    public DAO(Class<T> tClass) {
        this.tClass = tClass;
    }

    public final T findById(long id) throws InternalServerException, NotFoundException {
        try (Session session = HibernateUtil.createSessionFactory().openSession()) {

            T object = session.get(this.tClass, id);

            if (object == null) {
                throw new NotFoundException("findById failed: missing object with id: " + id);
            }
            return object;
        } catch (HibernateException e) {
            throw new InternalServerException("findById failed: " + e.getMessage());
        }
    }

    public final T save(T object) throws InternalServerException {
        try (Session session = HibernateUtil.createSessionFactory().openSession()) {
            Transaction tr = session.getTransaction();
            tr.begin();

            session.save(object);

            tr.commit();
            return object;
        } catch (HibernateException e) {
            throw new InternalServerException("save failed: " + e.getMessage());
        }
    }

    public final T update(T object) throws InternalServerException {
        try (Session session = HibernateUtil.createSessionFactory().openSession()) {
            Transaction tr = session.getTransaction();
            tr.begin();

            session.update(object);

            tr.commit();
            return object;
        } catch (HibernateException e) {
            throw new InternalServerException("update failed: " + e.getMessage());
        }
    }

    public final void delete(T object) throws InternalServerException {
        try (Session session = HibernateUtil.createSessionFactory().openSession()) {
            Transaction tr = session.getTransaction();
            tr.begin();

            session.delete(object);

            tr.commit();
        } catch (HibernateException e) {
            throw new InternalServerException("delete failed: " + e.getMessage());
        }
    }
}
