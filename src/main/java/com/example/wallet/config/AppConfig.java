package com.example.wallet.config;

import com.example.wallet.exception.BusinessException;
import com.example.wallet.util.DBUtil;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.io.InputStream;
import java.util.Properties;
@WebListener
public class AppConfig implements ServletContextListener {

    private HikariDataSource dataSource;

    public AppConfig() {
        this.dataSource = null;
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            Properties props = new Properties();

            ClassLoader cl = Thread.currentThread().getContextClassLoader();
            try (InputStream is = cl.getResourceAsStream("application.properties")) {
                if (is == null) {
                    throw new IllegalStateException("application.properties not found");
                }
                props.load(is);
            }

            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(props.getProperty("db.url"));
            config.setUsername(props.getProperty("db.username"));
            config.setPassword(props.getProperty("db.password"));
            config.setDriverClassName(props.getProperty("db.driver"));
            config.setMaximumPoolSize(
                    Integer.parseInt(props.getProperty("db.pool.size"))
            );
            config.setConnectionTimeout(
                    Long.parseLong(props.getProperty("db.pool.timeout"))
            );

            dataSource = new HikariDataSource(config);
            DBUtil.setDataSource(dataSource);

        } catch (Exception e) {
            throw new BusinessException("DB initialization failed",e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (dataSource != null) {
            dataSource.close();
        }
    }
}
