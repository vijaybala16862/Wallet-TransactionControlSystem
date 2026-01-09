package com.example.wallet.servlet;

import com.example.wallet.dao.UserDaoImpl;
import com.example.wallet.model.User;
import com.example.wallet.service.UserService;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.Serial;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Serial
    private static final long serialVersionUID=1L;

    private UserService userService;

    @Override
    public void init() {
        userService = new UserService(new UserDaoImpl());
    }

    @Override
    protected void doPost(HttpServletRequest req,
                          HttpServletResponse resp) throws IOException {

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        User user = userService.login(username, password);

        HttpSession session = req.getSession(true);
        session.setAttribute("userId", user.getId());
        session.setMaxInactiveInterval(30 * 60);

        resp.getWriter().write("Login successful");
    }
}
