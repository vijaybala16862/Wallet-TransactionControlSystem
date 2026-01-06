package com.example.wallet.config;

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
import java.sql.DriverManager;
import java.util.Properties;

@WebListener
public class LiquibaseInitializer implements ServletContextListener {

    private static final Logger LOG =
            LoggerFactory.getLogger(LiquibaseInitializer.class);

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

            String url = props.getProperty("db.url");
            String username = props.getProperty("db.username");
            String password = props.getProperty("db.password");
            String driver = props.getProperty("db.driver");

            Class.forName(driver);

            try (Connection con = DriverManager.getConnection(url, username, password)) {

                Database database = DatabaseFactory.getInstance()
                        .findCorrectDatabaseImplementation(
                                new JdbcConnection(con));

                Liquibase liquibase = new Liquibase(
                        "db/changelog/db.changelog-master.xml",
                        new ClassLoaderResourceAccessor(),
                        database
                );

                liquibase.update();
            }

        } catch (Exception e) {
            LOG.error("Liquibase initialization failed", e);
            throw new IllegalStateException("Application startup failed", e);
        }
    }
}
