package com.example.wallet.dao;

import com.example.wallet.exception.DataAccessException;
import com.example.wallet.model.Wallet;
import com.example.wallet.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class WalletDaoImpl implements WalletDao {

    private static final int IDX_WALLET_ID = 1;
    private static final int IDX_USER_ID   = 2;
    private static final int IDX_BALANCE   = 3;
    private static final int IDX_STATUS    = 4;

    @Override
    public void save(Wallet wallet) {
        String sql = "INSERT INTO wallets (wallet_id, user_id, balance, status) VALUES (?, ?, ?, ?)";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(IDX_WALLET_ID, wallet.getWalletId());
            ps.setString(IDX_USER_ID, wallet.getUserId());
            ps.setDouble(IDX_BALANCE, wallet.getBalance());
            ps.setString(IDX_STATUS, wallet.getStatus());

            ps.executeUpdate();

        } catch (Exception e) {
            throw new DataAccessException("Error saving wallet", e);
        }
    }

    @Override
    public Wallet findById(String walletId) {
        String sql = "SELECT wallet_id, user_id, balance, status FROM wallets WHERE wallet_id = ?";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, walletId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Wallet(
                            rs.getString("wallet_id"),
                            rs.getString("user_id"),
                            rs.getDouble("balance"),
                            rs.getString("status")
                    );
                }
                return null;
            }

        } catch (Exception e) {
            throw new DataAccessException("Error fetching wallet", e);
        }
    }

    @Override
    public void update(Wallet wallet) {
        String sql = "UPDATE wallets SET balance = ?, status = ? WHERE wallet_id = ?";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setDouble(1, wallet.getBalance());
            ps.setString(2, wallet.getStatus());
            ps.setString(3, wallet.getWalletId());

            ps.executeUpdate();

        } catch (Exception e) {
            throw new DataAccessException("Error updating wallet", e);
        }
    }

    @Override
    public void delete(String walletId) {
        String sql = "DELETE FROM wallets WHERE wallet_id = ?";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, walletId);
            ps.executeUpdate();

        } catch (Exception e) {
            throw new DataAccessException("Error deleting wallet", e);
        }
    }

    @Override
    public void saveTransaction(String walletId, String txnType, double amount, double balanceAfter) {

        String sql = """
        INSERT INTO wallet_transactions(wallet_id, txn_type, amount, balance_after)VALUES (?, ?, ?, ?)
        """;

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, walletId);
            ps.setString(2, txnType);
            ps.setDouble(3, amount);
            ps.setDouble(4, balanceAfter);

            ps.executeUpdate();

        } catch (Exception e) {
            throw new DataAccessException("Error saving wallet transaction", e);
        }
    }

}
