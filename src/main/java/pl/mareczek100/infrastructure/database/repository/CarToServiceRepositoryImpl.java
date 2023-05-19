package pl.mareczek100.infrastructure.database.repository;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.ParameterExpression;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import pl.mareczek100.infrastructure.configuration.HibernateConfig;
import pl.mareczek100.infrastructure.database.entity.*;
import pl.mareczek100.service.dao.CarToServiceRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Repository
public class CarToServiceRepositoryImpl implements CarToServiceRepository {

    @Override
    public void carToServiceInsert(CarToService carToService) {
        try (Session session = HibernateConfig.getSession()) {
            if (Objects.isNull(session)) {
                throw new RuntimeException("Session is null");
            }
            Transaction transaction = session.beginTransaction();
            if (Objects.isNull(carToService.getCarToServiceId())) {
                session.persist(carToService);
            }
            session.flush();
            transaction.commit();
        }
    }

    @Override
    public Optional<CarToService> findCarToService(String vin) {
        Optional<CarToService> carToService;
        try (Session session = HibernateConfig.getSession()) {
            if (Objects.isNull(session)) {
                throw new RuntimeException("Session is null");
            }
            Transaction transaction = session.beginTransaction();
            carToService = session.createQuery
                            ("FROM CarToService car WHERE car.vin = :vin", CarToService.class)
                    .setParameter("vin", vin)
                    .uniqueResultOptional();
            transaction.commit();
        }
        return carToService;
    }

    @Override
    public List<CarToService> findAllCarsToService() {
        List<CarToService> carToService;
        try (Session session = HibernateConfig.getSession()) {
            if (Objects.isNull(session)) {
                throw new RuntimeException("Session is null");
            }
            Transaction transaction = session.beginTransaction();
            carToService = session.createQuery("FROM CarToService", CarToService.class).getResultList();
            transaction.commit();
        }
        return carToService;
    }

    @Override
    public CarToServiceHistory findCarHistoryByVin(String vin) {
        try (Session session = HibernateConfig.getSession()) {
            if (Objects.isNull(session)) {
                throw new RuntimeException("Session is null");
            }
            session.beginTransaction();

            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<CarToService> criteriaQuery = criteriaBuilder.createQuery(CarToService.class);
            Root<CarToService> root = criteriaQuery.from(CarToService.class);

            ParameterExpression<String> param1 = criteriaBuilder.parameter(String.class);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("vin"), param1));

            Query<CarToService> query = session.createQuery(criteriaQuery);
            query.setParameter(param1, vin);
            CarToService carToService = query.getSingleResult();
            CarToServiceHistory result = CarToServiceHistory.builder()
                    .carVin(vin)
                    .serviceRequests(carToService.getCarServiceRequests().stream().map(this::mapServiceRequest).toList())
                    .build();
            session.getTransaction().commit();
            return result;
        }
    }

    private CarToServiceHistory.ServiceRequest mapServiceRequest(CarServiceRequest serviceRequest) {
        return CarToServiceHistory.ServiceRequest.builder()
                .serviceRequestNumber(serviceRequest.getCarServiceRequestNumber())
                .receivedDateTime(serviceRequest.getReceivedDateTime())
                .completedDateTime(serviceRequest.getCompletedDateTime())
                .customerComment(serviceRequest.getCustomerComment())
                .services(mapServices(serviceRequest.getCarServiceHandlings().stream().map(CarServiceHandling::getService).toList()))
                .parts(mapParts(serviceRequest.getCarServiceParts().stream().map(CarServiceParts::getPart).toList()))
                .build();
    }

    private List<CarToServiceHistory.Service> mapServices(List<Service> entities) {
        return entities.stream()
                .map(service -> CarToServiceHistory.Service.builder()
                        .serviceCode(service.getServiceCode())
                        .description(service.getDescription())
                        .price(service.getPrice())
                        .build())
                .toList();
    }

    private List<CarToServiceHistory.Part> mapParts(List<Part> entities) {
        return entities.stream()
                .map(part -> CarToServiceHistory.Part.builder()
                        .serialNumber(part.getSerialNumber())
                        .description(part.getDescription())
                        .price(part.getPrice())
                        .build())
                .toList();
    }

}