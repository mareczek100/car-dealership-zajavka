package pl.mareczek100.infrastructure.database;

import lombok.Value;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.stereotype.Component;
import pl.mareczek100.infrastructure.configuration.HibernateConfig;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

@Value
@Component
public class TableCreator {
    private final static Path DDL_CREATE_TABLES = Path.of("./src/main/resources/car_dealership_ddl.sql");
    SimpleDriverDataSource dataSource;

    public void createCleanTables() {

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        try {
            jdbcTemplate.execute(Files.readString(DDL_CREATE_TABLES));
        } catch (IOException ioException) {
            System.err.println(ioException.getMessage());
        }
    }

    public void dropTables() {

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        try {
            Files.readAllLines(DDL_CREATE_TABLES).stream()
                    .limit(13)
                    .forEach(jdbcTemplate::execute);
        } catch (IOException ioException) {
            System.err.println(ioException.getMessage());
        }
    }

    public void deleteFromTables() {
        try (Session session = HibernateConfig.getSession()) {
            if (Objects.isNull(session)) {
                throw new RuntimeException("Session is null");
            }
            Transaction transaction = session.beginTransaction();
            session.createMutationQuery("DELETE FROM CarServiceHandling").executeUpdate();
            session.createMutationQuery("DELETE FROM CarServiceParts").executeUpdate();
            session.createMutationQuery("DELETE FROM CarServiceRequest").executeUpdate();
            session.createMutationQuery("DELETE FROM Invoice").executeUpdate();
            session.createMutationQuery("DELETE FROM Mechanic").executeUpdate();
            session.createMutationQuery("DELETE FROM Service").executeUpdate();
            session.createMutationQuery("DELETE FROM CarToService").executeUpdate();
            session.createMutationQuery("DELETE FROM CarToSell").executeUpdate();
            session.createMutationQuery("DELETE FROM CarToSellTempStorage").executeUpdate();
            session.createMutationQuery("DELETE FROM Part").executeUpdate();
            session.createMutationQuery("DELETE FROM Customer").executeUpdate();
            session.createMutationQuery("DELETE FROM Address").executeUpdate();
            session.createMutationQuery("DELETE FROM Salesman").executeUpdate();
            transaction.commit();
        }
    }

}