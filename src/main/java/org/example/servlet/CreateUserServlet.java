package org.example.servlet;

import org.example.annotation.NoAuth;
import org.example.model.entity.User;
import org.example.service.UserService;
import org.example.utils.HibernateUtil;
import org.example.utils.PasswordUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@NoAuth
@WebServlet(name = "CreateUserServlet", urlPatterns = {"/user/create"})
public class CreateUserServlet extends AbstractServlet{

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try(Session session = HibernateUtil.getSessionFactory().openSession()) {

            String jsonString = getJsonString(req);

            User user = getGjon().fromJson(jsonString, User.class);

            validateExistingUsername(UserService.findByUsername(user.getUsername()).orElse(null));

            user.setPassword(PasswordUtil.encode(user.getPassword()));

            Transaction transaction = session.beginTransaction();

            session.merge(user);

            transaction.commit();
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println();
    }

    private static void validateExistingUsername(User existingUser) {
        if (existingUser != null) {
            throw new RuntimeException("O nome de usuário já está sendo usado");
        }
    }
}
