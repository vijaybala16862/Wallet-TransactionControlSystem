package com.example.wallet.servlet;

import com.example.wallet.dao.UserDaoImpl;
import com.example.wallet.service.UserService;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

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
        String role = req.getParameter("role"); // optional

        userService.register(username, password, role);

        resp.getWriter().write("User registered successfully");
    }
}
