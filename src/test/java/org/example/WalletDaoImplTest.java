//package org.example;
//
//import com.example.wallet.dao.WalletDaoImpl;
//import com.example.wallet.exception.DataAccessException;
//import com.example.wallet.model.Wallet;
//import com.example.wallet.util.DBUtil;
//
//import org.junit.jupiter.api.Test;
//import org.mockito.MockedStatic;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//class WalletDaoImplTest {
//
//    @Test
//    void save_success() throws Exception {
//        Wallet wallet = new Wallet("W1", "USER1", 100.0, "ACTIVE");
//
//        Connection con = mock(Connection.class);
//        PreparedStatement ps = mock(PreparedStatement.class);
//
//        try (MockedStatic<DBUtil> mocked = mockStatic(DBUtil.class)) {
//            mocked.when(DBUtil::getConnection).thenReturn(con);
//            when(con.prepareStatement(anyString())).thenReturn(ps);
//
//            WalletDaoImpl dao = new WalletDaoImpl();
//            dao.save(wallet);
//
//            verify(ps).setString(1, "W1");
//            verify(ps).setString(2, "USER1");
//            verify(ps).setDouble(3, 100.0);
//            verify(ps).setString(4, "ACTIVE");
//            verify(ps).executeUpdate();
//        }
//    }
//
//    @Test
//    void findById_success() throws Exception {
//        Connection con = mock(Connection.class);
//        PreparedStatement ps = mock(PreparedStatement.class);
//        ResultSet rs = mock(ResultSet.class);
//
//        try (MockedStatic<DBUtil> mocked = mockStatic(DBUtil.class)) {
//            mocked.when(DBUtil::getConnection).thenReturn(con);
//            when(con.prepareStatement(anyString())).thenReturn(ps);
//            when(ps.executeQuery()).thenReturn(rs);
//
//            when(rs.next()).thenReturn(true);
//            when(rs.getString("wallet_id")).thenReturn("W1");
//            when(rs.getString("user_id")).thenReturn("USER1");
//            when(rs.getDouble("balance")).thenReturn(100.0);
//            when(rs.getString("status")).thenReturn("ACTIVE");
//
//            WalletDaoImpl dao = new WalletDaoImpl();
//            Wallet wallet = dao.findById("W1");
//
//            assertNotNull(wallet);
//            assertEquals("W1", wallet.getWalletId());
//            assertEquals("USER1", wallet.getUserId());
//            assertEquals(100.0, wallet.getBalance());
//            assertEquals("ACTIVE", wallet.getStatus());
//        }
//    }
//
//    @Test
//    void update_success() throws Exception {
//        Wallet wallet = new Wallet("W1", "USER1", 200.0, "ACTIVE");
//
//        Connection con = mock(Connection.class);
//        PreparedStatement ps = mock(PreparedStatement.class);
//
//        try (MockedStatic<DBUtil> mocked = mockStatic(DBUtil.class)) {
//            mocked.when(DBUtil::getConnection).thenReturn(con);
//            when(con.prepareStatement(anyString())).thenReturn(ps);
//
//            WalletDaoImpl dao = new WalletDaoImpl();
//            dao.update(wallet);
//
//            verify(ps).setDouble(1, 200.0);
//            verify(ps).setString(2, "W1");
//            verify(ps).executeUpdate();
//        }
//    }
//
//    @Test
//    void delete_success() throws Exception {
//        Connection con = mock(Connection.class);
//        PreparedStatement ps = mock(PreparedStatement.class);
//
//        try (MockedStatic<DBUtil> mocked = mockStatic(DBUtil.class)) {
//            mocked.when(DBUtil::getConnection).thenReturn(con);
//            when(con.prepareStatement(anyString())).thenReturn(ps);
//
//            WalletDaoImpl dao = new WalletDaoImpl();
//            dao.delete("W1");
//
//            verify(ps).setString(1, "W1");
//            verify(ps).executeUpdate();
//        }
//    }
//
//
//    @Test
//    void save_shouldThrowException_whenSqlFails() throws Exception {
//        Wallet wallet = new Wallet("W1", "USER1", 100.0, "ACTIVE");
//
//        Connection con = mock(Connection.class);
//        PreparedStatement ps = mock(PreparedStatement.class);
//
//        try (MockedStatic<DBUtil> mocked = mockStatic(DBUtil.class)) {
//            mocked.when(DBUtil::getConnection).thenReturn(con);
//            when(con.prepareStatement(anyString())).thenReturn(ps);
//            when(ps.executeUpdate()).thenThrow(new SQLException("DB error"));
//
//            WalletDaoImpl dao = new WalletDaoImpl();
//
//            assertThrows(DataAccessException.class, () -> dao.save(wallet));
//        }
//    }
//
//    @Test
//    void findById_shouldThrowException_whenSqlFails() throws Exception {
//        Connection con = mock(Connection.class);
//        PreparedStatement ps = mock(PreparedStatement.class);
//
//        try (MockedStatic<DBUtil> mocked = mockStatic(DBUtil.class)) {
//            mocked.when(DBUtil::getConnection).thenReturn(con);
//            when(con.prepareStatement(anyString())).thenReturn(ps);
//            when(ps.executeQuery()).thenThrow(new SQLException());
//
//            WalletDaoImpl dao = new WalletDaoImpl();
//
//            assertThrows(DataAccessException.class, () -> dao.findById("W1"));
//        }
//    }
//
//    @Test
//    void update_shouldThrowException_whenSqlFails() throws Exception {
//        Wallet wallet = new Wallet("W1", "USER1", 200.0, "ACTIVE");
//
//        Connection con = mock(Connection.class);
//        PreparedStatement ps = mock(PreparedStatement.class);
//
//        try (MockedStatic<DBUtil> mocked = mockStatic(DBUtil.class)) {
//            mocked.when(DBUtil::getConnection).thenReturn(con);
//            when(con.prepareStatement(anyString())).thenReturn(ps);
//            when(ps.executeUpdate()).thenThrow(new SQLException());
//
//            WalletDaoImpl dao = new WalletDaoImpl();
//
//            assertThrows(DataAccessException.class, () -> dao.update(wallet));
//        }
//    }
//
//    @Test
//    void delete_shouldThrowException_whenSqlFails() throws Exception {
//        Connection con = mock(Connection.class);
//        PreparedStatement ps = mock(PreparedStatement.class);
//
//        try (MockedStatic<DBUtil> mocked = mockStatic(DBUtil.class)) {
//            mocked.when(DBUtil::getConnection).thenReturn(con);
//            when(con.prepareStatement(anyString())).thenReturn(ps);
//            when(ps.executeUpdate()).thenThrow(new SQLException());
//
//            WalletDaoImpl dao = new WalletDaoImpl();
//
//            assertThrows(DataAccessException.class, () -> dao.delete("W1"));
//        }
//    }
//}
