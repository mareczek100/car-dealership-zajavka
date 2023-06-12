package pl.mareczek100.infrastructure.database.jpaRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.mareczek100.infrastructure.database.entity.InvoiceEntity;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.DoubleStream;


public interface InvoiceJpaRepository extends JpaRepository<InvoiceEntity, Integer> {
    Optional<InvoiceEntity> findInvoiceByInvoiceNumber(String invoiceNumber);
    @Query("""
            SELECT inv FROM InvoiceEntity inv 
            JOIN FETCH inv.customer cus
            WHERE cus.email = :email
            """)
    List<InvoiceEntity> findInvoiceByEmail(@Param("email") String email);
    @Query("""
            SELECT inv FROM InvoiceEntity inv 
            JOIN FETCH inv.carToSell car
            WHERE car.vin = :vin
            """)
    Optional<InvoiceEntity> findInvoiceByVin(@Param("vin") String vin);
}