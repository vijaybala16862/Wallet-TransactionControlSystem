package com.example.wallet.servlet;

import com.example.wallet.dao.WalletDaoImpl;
import com.example.wallet.model.Wallet;
import com.example.wallet.service.WalletService;
import com.example.wallet.service.WalletServiceImpl;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/wallet/*")
public class WalletServlet extends HttpServlet {

    private final WalletService service =
            new WalletServiceImpl(new WalletDaoImpl());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        String walletId = req.getPathInfo().substring(1);
        Wallet wallet = service.get(walletId);
        resp.getWriter().write(wallet.getWalletId() + " : " + wallet.getBalance());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        Wallet wallet = service.create(req.getParameter("userId"));
        resp.setStatus(201);
        resp.getWriter().write(wallet.getWalletId());
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        String walletId = req.getPathInfo().substring(1);
        double amount = Double.parseDouble(req.getParameter("amount"));
        service.credit(walletId, amount);
        resp.getWriter().write("Credited");
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        String walletId = req.getPathInfo().substring(1);
        service.delete(walletId);
        resp.getWriter().write("Deleted");
    }
}
