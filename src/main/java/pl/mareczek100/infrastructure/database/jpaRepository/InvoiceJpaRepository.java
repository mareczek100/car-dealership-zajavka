package pl.mareczek100.infrastructure.database.jpaRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mareczek100.infrastructure.database.entity.InvoiceEntity;

import java.util.List;
import java.util.Optional;


public interface InvoiceJpaRepository extends JpaRepository<InvoiceEntity, Integer> {
    Optional<InvoiceEntity> findInvoiceByInvoiceNumber(String invoiceNumber);

}
