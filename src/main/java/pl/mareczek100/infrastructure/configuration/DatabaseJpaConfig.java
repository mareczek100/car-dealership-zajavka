package pl.mareczek100.infrastructure.configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import lombok.AllArgsConstructor;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.Location;
import org.flywaydb.core.api.configuration.ClassicConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import pl.mareczek100.infrastructure.database.entity._EntityMarker;
import pl.mareczek100.infrastructure.database.jpaRepository._JpaMarker;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

@Configuration
@AllArgsConstructor
@EnableTransactionManagement
@EnableJpaRepositories(basePackageClasses = _JpaMarker.class)
@PropertySource("classpath:database.properties")
public class DatabaseJpaConfig  {

    private final Environment environment;

    @Bean(destroyMethod = "close")
    public DataSource dataSource() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName(Objects.requireNonNull(environment.getProperty("jdbc.driver")));
        hikariConfig.setJdbcUrl(environment.getProperty("jdbc.url"));
        hikariConfig.setUsername(environment.getProperty("jdbc.user"));
        hikariConfig.setPassword(environment.getProperty("jdbc.pass"));
        hikariConfig.setMaximumPoolSize(20);
        hikariConfig.setConnectionTimeout(20000);
        hikariConfig.setMinimumIdle(10);
        hikariConfig.setIdleTimeout(200000);
        hikariConfig.setPoolName("springHikariCP");
        return new HikariDataSource(hikariConfig);
    }

    @Bean(initMethod = "migrate")
    Flyway flyway() throws IOException {
        ClassicConfiguration configuration = new ClassicConfiguration();
        configuration.setBaselineOnMigrate(true);
        configuration.setLocations(new Location("classpath:flyway/migrations"));
        configuration.setDataSource(dataSource());
        return new Flyway(configuration);
    }

    @Bean
    @DependsOn("flyway")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean.setPackagesToScan(_EntityMarker.class.getPackageName());
        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManagerFactoryBean.setJpaProperties(jpaProperties());
        return entityManagerFactoryBean;
    }

    @Bean
    public PlatformTransactionManager transactionManager(final EntityManagerFactory entityManagerFactory) {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslator() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    private Properties jpaProperties() {
        final Properties properties = new Properties();
        properties.setProperty(org.hibernate.cfg.Environment.DIALECT, environment.getProperty("dialect"));
        properties.setProperty(org.hibernate.cfg.Environment.HBM2DDL_AUTO, environment.getProperty("hbm2ddl_auto"));
        properties.setProperty(org.hibernate.cfg.Environment.SHOW_SQL, environment.getProperty("show_sql"));
        properties.setProperty(org.hibernate.cfg.Environment.FORMAT_SQL, environment.getProperty("format_sql"));
        return properties;
    }
}