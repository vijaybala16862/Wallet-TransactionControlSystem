package com.example.wallet.servlet;

import com.example.wallet.dao.WalletDaoImpl;
import com.example.wallet.model.Wallet;
import com.example.wallet.service.WalletService;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/wallet/")
public class WalletServlet extends HttpServlet {

    private WalletService service;

    @Override
    public void init() {
        this.service = new WalletService(new WalletDaoImpl());
    }

    private boolean isLoggedIn(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        HttpSession session = req.getSession(false);

        if (session == null || session.getAttribute("userId") == null) {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                    "Please login first");
            return false;
        }
        return true;
    }

    private String extractWalletId(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String walletId = req.getParameter("walletId");

        if (walletId == null || walletId.isBlank()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    "walletId required");
            return null;
        }
        return walletId;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        if (!isLoggedIn(req, resp)) return;

        String userId = req.getParameter("userId");

        if (userId == null || userId.isBlank()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    "userId required");
            return;
        }

        Wallet wallet = service.create(userId);

        resp.setStatus(HttpServletResponse.SC_CREATED);
        resp.getWriter().write(wallet.getWalletId());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        if (!isLoggedIn(req, resp)) return;

        String walletId = extractWalletId(req, resp);
        if (walletId == null) return;

        Wallet wallet = service.get(walletId);

        resp.getWriter().write(
                wallet.getWalletId() + " : " + wallet.getBalance()
        );
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        if (!isLoggedIn(req, resp)) return;

        String walletId = extractWalletId(req, resp);
        if (walletId == null) return;

        String action = req.getParameter("action");
        String amountStr = req.getParameter("amount");

        if (action == null || amountStr == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    "action and amount required");
            return;
        }

        double amount;
        try {
            amount = Double.parseDouble(amountStr);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    "Invalid amount");
            return;
        }

        if ("credit".equalsIgnoreCase(action)) {
            service.credit(walletId, amount);
            resp.getWriter().write("Credited");

        } else if ("debit".equalsIgnoreCase(action)) {
            service.debit(walletId, amount);
            resp.getWriter().write("Debited");

        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    "Invalid action. Use credit or debit");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        if (!isLoggedIn(req, resp)) return;

        String walletId = extractWalletId(req, resp);
        if (walletId == null) return;

        service.delete(walletId);
        resp.getWriter().write("Deleted");
    }
}
