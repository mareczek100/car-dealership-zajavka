package pl.mareczek100.domain.repository;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import pl.mareczek100.infrastructure.configuration.HibernateConfig;
import pl.mareczek100.infrastructure.database.entity.InvoiceEntity;
import pl.mareczek100.service.dao.InvoiceRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class InvoiceRepositoryImpl implements InvoiceRepository {


    @Override
    public void insertInvoice(InvoiceEntity invoiceEntity) {

            try (Session session = HibernateConfig.getSession()) {
                if (Objects.isNull(session)) {
                    throw new RuntimeException("Session is null");
                }
                Transaction transaction = session.beginTransaction();
                session.persist(invoiceEntity);
                session.flush();
                transaction.commit();
            }
    }

    @Override
    public Optional<InvoiceEntity> findInvoice(String invoiceNumber) {
        Optional<InvoiceEntity> invoice;
        try (Session session = HibernateConfig.getSession()) {
            if (Objects.isNull(session)) {
                throw new RuntimeException("Session is null");
            }
            Transaction transaction = session.beginTransaction();
            invoice = session.createQuery
                            ("FROM Invoice inv WHERE inv.invoiceNumber = :invoiceNumber", InvoiceEntity.class)
                    .setParameter("invoiceNumber", invoiceNumber)
                    .uniqueResultOptional();
            transaction.commit();
        }
        return invoice;
    }

    @Override
    public List<InvoiceEntity> findAllInvoices() {
        List<InvoiceEntity> invoiceEntityList;
        try (Session session = HibernateConfig.getSession()) {
            if (Objects.isNull(session)) {
                throw new RuntimeException("Session is null");
            }
            Transaction transaction = session.beginTransaction();
            invoiceEntityList = session.createQuery("FROM Invoice", InvoiceEntity.class).getResultList();
            transaction.commit();
        }
        return invoiceEntityList;
    }
}
