package DAO;

import Entity.Car;

import java.util.List;
import java.util.Optional;

public interface ICarDAO {
    void add(Car car);
    List<Car> getAll();
    Optional<Car> getById(long id);
    Optional<Long> getCarId(Car car);
    void updateCar(Car car);
    int removeById(long id);
    int removeByMark(String mark);
    int removeByModel(String model);
}
