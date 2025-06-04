package org.example.filter;

import org.example.context.UserContext;
import org.example.model.entity.User;
import org.example.pattern.Constants;
import org.example.service.UserService;
import org.example.servlet.context.NoAuthServletContextResolver;
import org.example.utils.TokenUtils;
import org.example.utils.Utils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebFilter(urlPatterns = {"/*"})
public class AuthFilter implements Filter {

     List<String> publicEndpoints;

    {
        publicEndpoints = NoAuthServletContextResolver.getNoAuthPaths();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String requestURI = req.getRequestURI();

        if(isPublic(requestURI)) {
            chain.doFilter(request, response);
            return;
        }

        String token = req.getHeader(Constants.AUTH_HEADER);

        if (notAuthorized(token, resp)) return;

        User user = defineContextUserFromToken(token);

        chain.doFilter(request, response);
    }

    private User defineContextUserFromToken(String token) {
        User user = UserService.findByUsername(TokenUtils.getClaim(token, Constants.USERNAME_CLAIM)).orElseThrow(() -> new RuntimeException("Usuario não encontrado!"));

        UserContext.setContextUser(user);

        return user;
    }

    private boolean notAuthorized(String token, HttpServletResponse resp) throws IOException {
        if (Utils.isEmpty(token) || !isValidToken(token)) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.getWriter().write("Faça login novamente para acessar.");
            return true;
        }
        return false;
    }

    private boolean isPublic(String requestURI) {
        return publicEndpoints.contains(requestURI);
    }

    private boolean isValidToken(String token) {
        try {
            TokenUtils.validarToken(token);
            return true;
        }catch (Exception e) {
            return false;
        }
    }
}
