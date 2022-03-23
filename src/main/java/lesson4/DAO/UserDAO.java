package lesson4.DAO;

import lesson4.exception.InternalServerException;
import lesson4.exception.NotFoundException;
import lesson4.model.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import javax.persistence.NoResultException;

public class UserDAO extends DAO<User> {

    public UserDAO() {
        super(User.class);
    }

    private static final String QUERY_FIND_BY_USERNAME = "SELECT * FROM users WHERE username = :username";
    private static final String QUERY_IS_USERNAME_UNIQUE = "SELECT 1 FROM users WHERE username = :username";

    public User findByUsername(String username) throws NotFoundException, InternalServerException {
        try (Session session = HibernateUtil.createSessionFactory().openSession()) {

            return session.createNativeQuery(QUERY_FIND_BY_USERNAME, User.class)
                    .setParameter("username", username)
                    .getSingleResult();

        } catch (NoResultException e) {
            throw new NotFoundException("Missing user with username " + username);
        } catch (HibernateException e) {
            throw new InternalServerException("findByUsername failed: " + e.getMessage());
        }
    }

    public boolean isUsernameUnique(String username) throws InternalServerException {
        try (Session session = HibernateUtil.createSessionFactory().openSession()) {

            session.createNativeQuery(QUERY_IS_USERNAME_UNIQUE)
                    .setParameter("username", username)
                    .getSingleResult();

            return false;
        } catch (NoResultException e) {
            return true;
        } catch (HibernateException e) {
            throw new InternalServerException("isUsernameUnique failed: " + e.getMessage());
        }
    }
}
