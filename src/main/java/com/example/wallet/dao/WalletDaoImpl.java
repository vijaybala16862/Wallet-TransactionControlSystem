package com.example.wallet.dao;

import com.example.wallet.model.Wallet;
import com.example.wallet.util.DBConnection;

import java.sql.*;

public class WalletDaoImpl implements WalletDao {

    @Override
    public void save(Wallet wallet) {
        String sql = "INSERT INTO WALLET (WALLET_ID, BALANCE, STATUS) VALUES (?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, wallet.getWalletId());
            ps.setDouble(2, wallet.getBalance());
            ps.setString(3, wallet.getStatus());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error creating wallet", e);
        }
    }

    @Override
    public Wallet findById(String walletId) {
        String sql = "SELECT * FROM WALLET WHERE WALLET_ID = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, walletId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Wallet(
                        rs.getString("WALLET_ID"),
                        rs.getDouble("BALANCE"),
                        rs.getString("STATUS")
                );
            }
            return null;

        } catch (SQLException e) {
            throw new RuntimeException("Error fetching wallet", e);
        }
    }

    @Override
    public void update(Wallet wallet) {
        String sql = "UPDATE WALLET SET BALANCE = ?, STATUS = ? WHERE WALLET_ID = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setDouble(1, wallet.getBalance());
            ps.setString(2, wallet.getStatus());
            ps.setString(3, wallet.getWalletId());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error updating wallet", e);
        }
    }

    @Override
    public void delete(String walletId) {
        String sql = "DELETE FROM WALLET WHERE WALLET_ID = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, walletId);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error deleting wallet", e);
        }
    }
}
