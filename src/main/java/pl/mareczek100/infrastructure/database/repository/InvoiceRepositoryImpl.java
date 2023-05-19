package pl.mareczek100.infrastructure.database.repository;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import pl.mareczek100.infrastructure.configuration.HibernateConfig;
import pl.mareczek100.infrastructure.database.entity.Invoice;
import pl.mareczek100.service.dao.InvoiceRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class InvoiceRepositoryImpl implements InvoiceRepository {


    @Override
    public void insertInvoice(Invoice invoice) {

            try (Session session = HibernateConfig.getSession()) {
                if (Objects.isNull(session)) {
                    throw new RuntimeException("Session is null");
                }
                Transaction transaction = session.beginTransaction();
                session.persist(invoice);
                session.flush();
                transaction.commit();
            }
    }

    @Override
    public Optional<Invoice> findInvoice(String invoiceNumber) {
        Optional<Invoice> invoice;
        try (Session session = HibernateConfig.getSession()) {
            if (Objects.isNull(session)) {
                throw new RuntimeException("Session is null");
            }
            Transaction transaction = session.beginTransaction();
            invoice = session.createQuery
                            ("FROM Invoice inv WHERE inv.invoiceNumber = :invoiceNumber", Invoice.class)
                    .setParameter("invoiceNumber", invoiceNumber)
                    .uniqueResultOptional();
            transaction.commit();
        }
        return invoice;
    }

    @Override
    public List<Invoice> findAllInvoices() {
        List<Invoice> invoiceList;
        try (Session session = HibernateConfig.getSession()) {
            if (Objects.isNull(session)) {
                throw new RuntimeException("Session is null");
            }
            Transaction transaction = session.beginTransaction();
            invoiceList = session.createQuery("FROM Invoice", Invoice.class).getResultList();
            transaction.commit();
        }
        return invoiceList;
    }
}
