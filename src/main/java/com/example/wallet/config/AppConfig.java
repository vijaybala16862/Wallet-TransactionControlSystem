package com.example.wallet.config;

import com.example.wallet.util.DBUtil;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

@WebListener
public class AppConfig implements ServletContextListener {

    private static final Logger LOG =
            LoggerFactory.getLogger(AppConfig.class);

    private HikariDataSource dataSource;

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        try {
            Properties props = new Properties();
            try (InputStream is =
                         Thread.currentThread()
                                 .getContextClassLoader()
                                 .getResourceAsStream("application.properties")) {

                if (is == null) {
                    LOG.error("application.properties not found");
                    return;
                }
                props.load(is);
            }

            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(props.getProperty("db.url"));
            config.setUsername(props.getProperty("db.username"));
            config.setPassword(props.getProperty("db.password"));
            config.setDriverClassName(props.getProperty("db.driver"));
            config.setMaximumPoolSize(Integer.parseInt(props.getProperty("db.pool.size")));
            config.setConnectionTimeout(Long.parseLong(props.getProperty("db.pool.timeout")));

            dataSource = new HikariDataSource(config);
            DBUtil.setDataSource(dataSource);

            LOG.info("Datasource initialized successfully");

            runLiquibaseSafely();

        } catch (Exception e) {
            LOG.error("Application startup failed", e);
        }
    }

    private void runLiquibaseSafely() {
        try (Connection con = DBUtil.getConnection()) {

            Database database = DatabaseFactory.getInstance()
                    .findCorrectDatabaseImplementation(
                            new JdbcConnection(con));

            Liquibase liquibase = new Liquibase(
                    "changelog/db.changelog-master.xml",
                    new ClassLoaderResourceAccessor(),
                    database
            );

            liquibase.update();
            LOG.info("Liquibase migration completed successfully");

        } catch (Exception e) {
            LOG.error("Liquibase migration failed", e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (dataSource != null) {
            dataSource.close();
        }
    }
}
