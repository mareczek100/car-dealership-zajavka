package pl.mareczek100.domain;

import lombok.*;

import java.util.Set;

@With
@Value
@Builder
@EqualsAndHashCode(of = "pesel")
@ToString(exclude = "carServiceHandlings")
public class Mechanic {

    Integer mechanicId;
    String name;
    String surname;
    String pesel;
    Set<CarServiceHandling> carServiceHandlings;

}
