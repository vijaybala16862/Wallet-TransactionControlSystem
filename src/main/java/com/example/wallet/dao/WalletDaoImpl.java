package com.example.wallet.dao;

import com.example.wallet.model.Wallet;
import com.example.wallet.util.DBUtil;

import java.sql.*;

public class WalletDaoImpl implements WalletDao {

    @Override
    public void save(Wallet wallet) {
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     "INSERT INTO wallet(wallet_id,user_id,balance,status) VALUES(?,?,?,?)")) {

            ps.setString(1, wallet.getWalletId());
            ps.setString(2, wallet.getUserId());
            ps.setDouble(3, wallet.getBalance());
            ps.setString(4, wallet.getStatus());
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Wallet findById(String walletId) {
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     "SELECT * FROM wallet WHERE wallet_id=?")) {

            ps.setString(1, walletId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Wallet(
                        rs.getString("wallet_id"),
                        rs.getString("user_id"),
                        rs.getDouble("balance"),
                        rs.getString("status")
                );
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Wallet wallet) {
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     "UPDATE wallet SET balance=? WHERE wallet_id=?")) {

            ps.setDouble(1, wallet.getBalance());
            ps.setString(2, wallet.getWalletId());
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(String walletId) {
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     "DELETE FROM wallet WHERE wallet_id=?")) {

            ps.setString(1, walletId);
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
