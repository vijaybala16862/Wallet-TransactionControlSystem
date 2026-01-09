package org.example;

import com.example.wallet.dao.WalletDaoImpl;
import com.example.wallet.exception.DataAccessException;
import com.example.wallet.model.Wallet;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WalletDaoImplTest {

    private final WalletDaoImpl walletDao = new WalletDaoImpl();

    @Test
    void save_wallet_db_error() {
        Wallet wallet = new Wallet("W1", "U1", 0, "ACTIVE");
        assertThrows(DataAccessException.class,
                () -> walletDao.save(wallet));
    }

    @Test
    void find_wallet_not_found() {
        assertThrows(DataAccessException.class,
                () -> walletDao.findById("INVALID"));
    }
}
