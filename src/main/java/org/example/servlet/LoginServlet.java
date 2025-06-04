package org.example.servlet;

import org.example.annotation.NoAuth;
import org.example.model.entity.User;
import org.example.model.bean.UserAuthBean;
import org.example.service.UserService;
import org.example.utils.PasswordUtil;
import org.example.utils.TokenUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@NoAuth
@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends AbstractServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        User user = getUser(req);

        Optional<User> optionalUser = UserService.findByUsername(user.getUsername());

        if (optionalUser.isPresent()) {
            User userManaged = optionalUser.get();

            validateLogin(user, userManaged);

            UserAuthBean authBean = createAuthBean(userManaged);

            resp.getWriter().write(authBean.toString());

            return;
        }
        throw new RuntimeException("Usuario não encontrado");
    }

    private UserAuthBean createAuthBean(User userManaged) {
        UserAuthBean authBean = new UserAuthBean();

        String token = TokenUtils.generateToken(userManaged.getUsername(), userManaged.getId().toString());

        authBean.setUsername(userManaged.getUsername());

        authBean.setToken(token);

        authBean.setExpirationDate(TokenUtils.getExpirationDate(token));

        return authBean;
    }

    private User getUser(HttpServletRequest req) throws IOException {
        String jsonString = getJsonString(req);

        return getGjon().fromJson(jsonString, User.class);
    }

    private void validateLogin(User user, User userManaged) {

        boolean validPassword = PasswordUtil.isValidPassword(user.getPassword(), userManaged.getPassword());

        if (!validPassword) {
            throw new RuntimeException("Credenciais invalidas!");
        }
    }
}
