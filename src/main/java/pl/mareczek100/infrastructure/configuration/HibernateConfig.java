package pl.mareczek100.infrastructure.configuration;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Map;

public class HibernateConfig {
    private static final Map<String, Object> HIKARI_CP_SETTINGS = Map.ofEntries(
            Map.entry("hibernate.hikari.connectionTimeout", "20000"),
            Map.entry("hibernate.hikari.minimumIdle", "10"),
            Map.entry("hibernate.hikari.maximumPoolSize", "20"),
            Map.entry("hibernate.hikari.idleTimeout", "300000")
    );
    private static SessionFactory sessionFactory = null;

    static {
        try {
            loadSessionFactory();
        } catch (Exception ex) {
            throw new ExceptionInInitializerError("Exception while initializing: " + ex);
        }
    }

    private static void loadSessionFactory() {
        Configuration configuration = new Configuration();
        configuration.configure("/META-INF/hibernate.cfg.xml");
        sessionFactory = configuration.buildSessionFactory();
    }

    public static Session getSession() {
        try {
            return sessionFactory.openSession();
        } catch (Throwable ex) {
            System.err.println("Exception while getting Session: " + ex);
        }
        return null;
    }

    public static void closeSessionFactory() {
        try {
            sessionFactory.close();
        } catch (Throwable ex) {
            System.err.println("Exception while closing SessionFactory: " + ex);
        }
    }

}