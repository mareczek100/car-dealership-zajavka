package pl.mareczek100.domain.repository;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.ParameterExpression;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import pl.mareczek100.domain.CarHistory;
import pl.mareczek100.infrastructure.configuration.HibernateConfig;
import pl.mareczek100.infrastructure.database.entity.*;
import pl.mareczek100.service.dao.CarToServiceRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Repository
public class CarToServiceRepositoryImpl implements CarToServiceRepository {

    @Override
    public void carToServiceInsert(CarToServiceEntity carToServiceEntity) {
        try (Session session = HibernateConfig.getSession()) {
            if (Objects.isNull(session)) {
                throw new RuntimeException("Session is null");
            }
            Transaction transaction = session.beginTransaction();
            if (Objects.isNull(carToServiceEntity.getCarToServiceId())) {
                session.persist(carToServiceEntity);
            }
            session.flush();
            transaction.commit();
        }
    }

    @Override
    public Optional<CarToServiceEntity> findCarToService(String vin) {
        Optional<CarToServiceEntity> carToService;
        try (Session session = HibernateConfig.getSession()) {
            if (Objects.isNull(session)) {
                throw new RuntimeException("Session is null");
            }
            Transaction transaction = session.beginTransaction();
            carToService = session.createQuery
                            ("FROM CarToService car WHERE car.vin = :vin", CarToServiceEntity.class)
                    .setParameter("vin", vin)
                    .uniqueResultOptional();
            transaction.commit();
        }
        return carToService;
    }

    @Override
    public List<CarToServiceEntity> findAllCarsToService() {
        List<CarToServiceEntity> carToServiceEntity;
        try (Session session = HibernateConfig.getSession()) {
            if (Objects.isNull(session)) {
                throw new RuntimeException("Session is null");
            }
            Transaction transaction = session.beginTransaction();
            carToServiceEntity = session.createQuery("FROM CarToService", CarToServiceEntity.class).getResultList();
            transaction.commit();
        }
        return carToServiceEntity;
    }

    @Override
    public CarHistory findCarHistoryByVin(String vin) {
        try (Session session = HibernateConfig.getSession()) {
            if (Objects.isNull(session)) {
                throw new RuntimeException("Session is null");
            }
            session.beginTransaction();

            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<CarToServiceEntity> criteriaQuery = criteriaBuilder.createQuery(CarToServiceEntity.class);
            Root<CarToServiceEntity> root = criteriaQuery.from(CarToServiceEntity.class);

            ParameterExpression<String> param1 = criteriaBuilder.parameter(String.class);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("vin"), param1));

            Query<CarToServiceEntity> query = session.createQuery(criteriaQuery);
            query.setParameter(param1, vin);
            CarToServiceEntity carToServiceEntity = query.getSingleResult();
            CarHistory result = CarHistory.builder()
                    .carVin(vin)
                    .serviceRequests(carToServiceEntity.getCarServiceRequestEntities().stream().map(this::mapServiceRequest).toList())
                    .build();
            session.getTransaction().commit();
            return result;
        }
    }

    private CarHistory.ServiceRequest mapServiceRequest(CarServiceRequestEntity serviceRequest) {
        return CarHistory.ServiceRequest.builder()
                .serviceRequestNumber(serviceRequest.getCarServiceRequestNumber())
                .receivedDateTime(serviceRequest.getReceivedDateTime())
                .completedDateTime(serviceRequest.getCompletedDateTime())
                .customerComment(serviceRequest.getCustomerComment())
                .services(mapServices(serviceRequest.getCarServiceHandlingEntities().stream().map(CarServiceHandlingEntity::getServiceEntity).toList()))
                .parts(mapParts(serviceRequest.getCarServicePartEntities().stream().map(CarServicePartsEntity::getPartEntity).toList()))
                .build();
    }

    private List<CarHistory.Service> mapServices(List<ServiceEntity> entities) {
        return entities.stream()
                .map(service -> CarHistory.Service.builder()
                        .serviceCode(service.getServiceCode())
                        .description(service.getDescription())
                        .price(service.getPrice())
                        .build())
                .toList();
    }

    private List<CarHistory.Part> mapParts(List<PartEntity> entities) {
        return entities.stream()
                .map(part -> CarHistory.Part.builder()
                        .serialNumber(part.getSerialNumber())
                        .description(part.getDescription())
                        .price(part.getPrice())
                        .build())
                .toList();
    }

}