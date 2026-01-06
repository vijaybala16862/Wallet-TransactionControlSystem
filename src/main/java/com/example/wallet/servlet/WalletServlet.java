package com.example.wallet.servlet;

import com.example.wallet.dao.WalletDaoImpl;
import com.example.wallet.model.Wallet;
import com.example.wallet.service.WalletService;
import com.example.wallet.service.WalletServiceImpl;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.Serial;

@WebServlet("/wallet/*")
public class WalletServlet extends HttpServlet {

    @Serial
    private static final long serialVersionUID = 1L;

    private transient WalletService service;

    public WalletServlet() {
        this.service = null;
    }

    @Override
    public void init() {
        this.service = new WalletServiceImpl(new WalletDaoImpl());
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        String walletId = req.getPathInfo().substring(1);
        Wallet wallet = service.get(walletId);

        resp.getWriter().write(wallet.getWalletId() + " : " + wallet.getBalance());
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        Wallet wallet = service.create(req.getParameter("userId"));
        resp.setStatus(HttpServletResponse.SC_CREATED);
        resp.getWriter().write(wallet.getWalletId());
    }

    @Override
    public void doPut(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        String walletId = req.getPathInfo().substring(1);
        double amount = Double.parseDouble(req.getParameter("amount"));

        service.credit(walletId, amount);
        resp.getWriter().write("Credited");
    }

    @Override
    public void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        String walletId = req.getPathInfo().substring(1);
        service.delete(walletId);

        resp.getWriter().write("Deleted");
    }
}
