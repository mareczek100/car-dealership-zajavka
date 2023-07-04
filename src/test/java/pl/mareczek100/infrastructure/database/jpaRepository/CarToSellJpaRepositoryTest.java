package pl.mareczek100.infrastructure.database.jpaRepository;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import pl.mareczek100.config.PersistenceTestConfig;
import pl.mareczek100.infrastructure.database.entity.CarToSellEntity;
import pl.mareczek100.test_data_storage.TestInputData;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.yaml")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(PersistenceTestConfig.class)
@AllArgsConstructor(onConstructor = @__(@Autowired))
class CarToSellJpaRepositoryTest {

    private final CarToSellJpaRepository carToSellJpaRepository;

    @BeforeEach
    void setUp() {
        Assertions.assertNotNull(carToSellJpaRepository);
    }

    @Test
    void savedCorrectly() {
        //given
        CarToSellEntity carMercedes = TestInputData.exampleCars().get(0);
        CarToSellEntity carFord = TestInputData.exampleCars().get(1);
        CarToSellEntity carDacia = TestInputData.exampleCars().get(2);

        int allCarsSizeBefore = carToSellJpaRepository.findAll().size();
        List<CarToSellEntity> carToSellEntities = List.of(carMercedes, carFord, carDacia);
        List<CarToSellEntity> carToSellEntitiesSaved = carToSellJpaRepository.saveAllAndFlush(carToSellEntities);

        //when
        CarToSellEntity carMercedesSaved = carToSellJpaRepository.findByVin(carMercedes.getVin()).orElse(null);
        CarToSellEntity carFordSaved = carToSellJpaRepository.findByVin(carFord.getVin()).orElse(null);
        CarToSellEntity carDaciaSaved = carToSellJpaRepository.findByVin(carDacia.getVin()).orElse(null);

        int carsToSaveSize = carToSellEntities.size();
        int carsSavedSize = carToSellEntitiesSaved.size();
        int allCarsSizeAfterAddNewCars = carToSellJpaRepository.findAll().size();

        //then
        Assertions.assertEquals(carMercedes, carMercedesSaved);
        Assertions.assertEquals(carFord, carFordSaved);
        Assertions.assertEquals(carDacia, carDaciaSaved);
        Assertions.assertEquals(allCarsSizeAfterAddNewCars, (allCarsSizeBefore + carsToSaveSize));
        Assertions.assertEquals(carsToSaveSize, carsSavedSize);
    }

    @Test
    void findByVin() {
        //given
        String mercedesVin = "c11";
        String fordVin = "c22";
        String daciaVin = "c33";

        CarToSellEntity carMercedes = TestInputData.exampleCars().get(0).withVin(mercedesVin);
        CarToSellEntity carFord = TestInputData.exampleCars().get(1).withVin(fordVin);
        CarToSellEntity carDacia = TestInputData.exampleCars().get(2).withVin(daciaVin);
        carToSellJpaRepository.saveAndFlush(carMercedes);
        carToSellJpaRepository.saveAndFlush(carFord);
        carToSellJpaRepository.saveAndFlush(carDacia);


        //when
        CarToSellEntity carMercedesSaved = carToSellJpaRepository.findByVin(mercedesVin).orElse(null);
        CarToSellEntity carFordSaved = carToSellJpaRepository.findByVin(fordVin).orElse(null);
        CarToSellEntity carDaciaSaved = carToSellJpaRepository.findByVin(daciaVin).orElse(null);

        //then
        org.assertj.core.api.Assertions.assertThat(carMercedesSaved).isNotNull();
        org.assertj.core.api.Assertions.assertThat(carFordSaved).isNotNull();
        org.assertj.core.api.Assertions.assertThat(carDaciaSaved).isNotNull();
        org.assertj.core.api.Assertions.assertThat(carMercedesSaved.getVin()).isEqualTo(mercedesVin);
        org.assertj.core.api.Assertions.assertThat(carFordSaved.getVin()).isEqualTo(fordVin);
        org.assertj.core.api.Assertions.assertThat(carDaciaSaved.getVin()).isEqualTo(daciaVin);
    }

    @Test
    void deleteByVin() {
        //given
        List<CarToSellEntity> cars = TestInputData.exampleCars();
        List<CarToSellEntity> carEntityList = carToSellJpaRepository.saveAllAndFlush(cars);

        //when
        Assertions.assertEquals(cars, carEntityList);
        Assertions.assertEquals(cars.size(), carEntityList.size());
        carEntityList.forEach(carSaved -> carToSellJpaRepository.deleteByVin(carSaved.getVin()));

        //then
        for (CarToSellEntity carEntity : carEntityList) {
            Optional<CarToSellEntity> deletedCar = carToSellJpaRepository.findByVin(carEntity.getVin());
            org.assertj.core.api.Assertions.assertThat(deletedCar).isEmpty();
        }

    }
}