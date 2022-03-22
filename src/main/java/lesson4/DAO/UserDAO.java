package lesson4.DAO;

import lesson4.exception.BadRequestException;
import lesson4.exception.InternalServerException;
import lesson4.model.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import javax.persistence.NoResultException;
import java.util.List;

public class UserDAO extends DAO<User> {

    private static final String QUERY_FIND_BY_USERNAME = "SELECT * FROM users WHERE username = :username";
    private static final String QUERY_IS_USERNAME_UNIQUE = "SELECT 1 FROM users WHERE username = :username";

    public UserDAO(Class<User> userClass) {
        super(userClass);
    }

    public User logIn(String userName, String password) throws InternalServerException, BadRequestException {
        try (Session session = HibernateUtil.createSessionFactory().openSession()) {

            List<User> users = session.createNativeQuery(QUERY_FIND_BY_USERNAME, User.class)
                    .setParameter("username", userName)
                    .list();

            if (users.size() == 0) {
                throw new BadRequestException("logIn failed: wrong username or user not registered");
            }

            for (User user : users) {
                if (user.getPassword().equals(password)) {
                    return user;
                }
            }
            throw new BadRequestException("logIn failed: wrong password");

        } catch (HibernateException e) {
            throw new InternalServerException("logIn failed: something went wrong: " + e.getMessage());
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
            throw new InternalServerException("checkUsername failed: " + e.getMessage());
        }
    }
}
