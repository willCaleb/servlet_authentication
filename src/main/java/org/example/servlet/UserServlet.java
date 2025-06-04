package org.example.servlet;

import org.example.model.dto.UserDTO;
import org.example.model.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "UserServlet", urlPatterns = {"/user", "/user/id/*"})
public class UserServlet extends AbstractServlet<User> {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User byId = (User)findById(getId(req));

        write(resp, byId, UserDTO.class);
    }
}
