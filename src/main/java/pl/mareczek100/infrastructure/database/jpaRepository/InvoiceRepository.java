package pl.mareczek100.infrastructure.database.jpaRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mareczek100.infrastructure.database.entity.InvoiceEntity;

import java.util.List;
import java.util.Optional;


public interface InvoiceRepository extends JpaRepository<Object, Integer> {


    void insertInvoice(InvoiceEntity invoiceEntity);

    Optional<InvoiceEntity> findInvoice(String invoiceNumber);

    List<InvoiceEntity> findAllInvoices();
}
