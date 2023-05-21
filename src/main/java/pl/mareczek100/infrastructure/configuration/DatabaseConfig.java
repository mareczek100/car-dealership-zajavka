package pl.mareczek100.infrastructure.configuration;

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
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import pl.mareczek100.infrastructure.database.entity._EntityMarker;
import pl.mareczek100.infrastructure.database.jpaRepository._JpaMarker;

import javax.sql.DataSource;
import java.util.Objects;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
public class DatabaseConfig {
    @Configuration
    @AllArgsConstructor
    @EnableTransactionManagement
    @EnableJpaRepositories(basePackageClasses = _JpaMarker.class)
    @PropertySource("classpath:database.properties")
    public class DatabaseJPAConfig {
        private final Environment environment;

        @Bean
        public DataSource dataSource() {
            DriverManagerDataSource dataSource = new DriverManagerDataSource();
            dataSource.setDriverClassName(Objects.requireNonNull(environment.getProperty("jdbc.driver")));
            dataSource.setUrl(environment.getProperty("jdbc.url"));
            dataSource.setUsername(environment.getProperty("jdbc.user"));
            dataSource.setPassword(environment.getProperty("jdbc.pass"));
            return dataSource;
        }

        @Bean(initMethod = "migrate")
        Flyway flyway() {
            ClassicConfiguration configuration = new ClassicConfiguration();
            configuration.setBaselineOnMigrate(true);
            configuration.setLocations(new Location("filesystem:src/main/resources/flyway/migrations"));
            configuration.setDataSource(dataSource());
            return new Flyway(configuration);
        }

        @Bean
        @DependsOn("flyway")
        public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {
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
            properties.setProperty(org.hibernate.cfg.Environment.DIALECT, "org.hibernate.dialect.PostgreSQLDialect");
            properties.setProperty(org.hibernate.cfg.Environment.HBM2DDL_AUTO, "validate");
            properties.setProperty(org.hibernate.cfg.Environment.SHOW_SQL, "true");
            properties.setProperty(org.hibernate.cfg.Environment.FORMAT_SQL, "false");
            return properties;
        }

    }
}