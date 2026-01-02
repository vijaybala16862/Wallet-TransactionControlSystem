package com.example.wallet.servlet;

import com.example.wallet.exception.BusinessException;
import com.example.wallet.model.Wallet;
import com.example.wallet.service.WalletService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/wallet")
public class WalletServlet extends HttpServlet {

    private final WalletService walletService = new WalletService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        try {
            String walletId = req.getParameter("walletId");
            Wallet wallet = walletService.getWallet(walletId);

            resp.setStatus(200);
            resp.getWriter().write(
                    "WalletId=" + wallet.getWalletId() +
                            ", Balance=" + wallet.getBalance() +
                            ", Status=" + wallet.getStatus()
            );
        } catch (BusinessException e) {
            resp.setStatus(400);
            resp.getWriter().write(e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        try {
            String action = req.getParameter("action");

            if ("create".equalsIgnoreCase(action)) {
                Wallet wallet = walletService.createWallet(req.getParameter("userId"));
                resp.setStatus(201);
                resp.getWriter().write("Wallet created: " + wallet.getWalletId());

            } else if ("credit".equalsIgnoreCase(action)) {
                walletService.credit(
                        req.getParameter("walletId"),
                        Double.parseDouble(req.getParameter("amount"))
                );
                resp.getWriter().write("Amount credited");

            } else if ("debit".equalsIgnoreCase(action)) {
                walletService.debit(
                        req.getParameter("walletId"),
                        Double.parseDouble(req.getParameter("amount"))
                );
                resp.getWriter().write("Amount debited");
            }
        } catch (BusinessException e) {
            resp.setStatus(400);
            resp.getWriter().write(e.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        try {
            walletService.deleteWallet(req.getParameter("walletId"));
            resp.getWriter().write("Wallet deleted successfully");
        } catch (BusinessException e) {
            resp.setStatus(400);
            resp.getWriter().write(e.getMessage());
        }
    }
}
