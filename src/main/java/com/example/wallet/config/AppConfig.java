package com.example.wallet.config;

import com.example.wallet.util.DBUtil;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.util.Properties;

@WebListener
public class AppConfig implements ServletContextListener {

    private HikariDataSource dataSource;

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        try {
            Properties props = new Properties();
            props.load(getClass().getClassLoader()
                    .getResourceAsStream("application.properties"));

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
            throw new RuntimeException("DB initialization failed", e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (dataSource != null) {
            dataSource.close();
        }
    }
}
