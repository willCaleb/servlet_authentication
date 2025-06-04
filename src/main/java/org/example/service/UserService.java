package org.example.service;

import jakarta.persistence.Query;
import org.example.model.entity.User;
import org.example.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Optional;

public class UserService {

    public static Optional<User> findByUsername(String username) {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String queryString = "SELECT * FROM auth_user WHERE username = '" + username + "';";

            Transaction transaction = session.beginTransaction();

            Query query = session.createNativeQuery(queryString, User.class);

            return  Optional.of((User) query.getSingleResult());
        }catch (Exception ignored) {
            return Optional.empty();
        }
    }
}
