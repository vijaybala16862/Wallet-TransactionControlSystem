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

@WebServlet("/wallet/")
public class WalletServlet extends HttpServlet {

    private WalletService service;

    @Override
    public void init() {
        this.service = new WalletServiceImpl(new WalletDaoImpl());
    }

    private String extractWalletId(HttpServletRequest req,
                                   HttpServletResponse resp) throws IOException {

        String pathInfo = req.getPathInfo();

        if (pathInfo == null || pathInfo.length() <= 1) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "WalletId required");
            return null;
        }
        return pathInfo.substring(1);
    }

    @Override
    public void doPost(HttpServletRequest req,
                       HttpServletResponse resp) throws IOException {

        String userId = req.getParameter("userId");
        Wallet wallet = service.create(userId);

        resp.setStatus(HttpServletResponse.SC_CREATED);
        resp.getWriter().write(wallet.getWalletId());
    }

    @Override
    public void doGet(HttpServletRequest req,
                      HttpServletResponse resp) throws IOException {

        String walletId = extractWalletId(req, resp);
        if (walletId == null) return;

        Wallet wallet = service.get(walletId);
        resp.getWriter().write(
                wallet.getWalletId() + " : " + wallet.getBalance()
        );
    }

    @Override
    public void doPut(HttpServletRequest req,
                      HttpServletResponse resp) throws IOException {

        String walletId = extractWalletId(req, resp);
        if (walletId == null) return;

        double amount = Double.parseDouble(req.getParameter("amount"));
        service.credit(walletId, amount);

        resp.getWriter().write("Credited");
    }

    @Override
    public void doDelete(HttpServletRequest req,
                         HttpServletResponse resp) throws IOException {

        String walletId = extractWalletId(req, resp);
        if (walletId == null) return;

        service.delete(walletId);
        resp.getWriter().write("Deleted");
    }
}
