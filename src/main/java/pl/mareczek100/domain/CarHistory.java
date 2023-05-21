package pl.mareczek100.domain;

import lombok.Builder;
import lombok.Value;

import java.time.OffsetDateTime;
import java.util.List;

@Value
@Builder
public class CarHistory {
    String carVin;
    List<ServiceRequest> serviceRequests;

    public record ServiceRequest(
            String serviceRequestNumber,
            OffsetDateTime receivedDateTime,
            OffsetDateTime completedDateTime,
            String customerComment,
            List<Service> services,
            List<Part> parts
    ) {


        @Override
        public String toString() {
            return "ServiceRequest{" +
                    "serviceRequestNumber='" + serviceRequestNumber + '\'' +
                    ", receivedDateTime=" + receivedDateTime +
                    ", completedDateTime=" + completedDateTime +
                    ", customerComment='" + customerComment +
                    '}';
        }
    }

}
