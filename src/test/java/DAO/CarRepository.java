package DAO;

import Entity.Car;

import java.util.List;
import java.util.Optional;

public interface CarRepository {
    void add(Car car);
    List<Car> getAll();
    Optional<Car> getById(long id);
    Optional<Long> getCarId(Car car);
    void updateCar(long id, Car car);
    void removeById(long id);
    void removeByMark(String mark);
    void removeByModel(String model);
}
