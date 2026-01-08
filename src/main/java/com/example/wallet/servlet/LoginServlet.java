package com.example.wallet.servlet;

import com.example.wallet.dao.UserDaoImpl;
import com.example.wallet.model.User;
import com.example.wallet.service.UserService;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init() {
        this.userService = new UserService(new UserDaoImpl());
    }

    @Override
    protected void doPost(HttpServletRequest req,
                          HttpServletResponse resp) throws IOException {

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        try {
            User user = userService.login(username, password);

            resp.getWriter().write(
                    "Login success | UserId=" + user.getId() +
                            " | Role=" + user.getRole()
            );

        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
        }
    }
}
