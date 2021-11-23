package DAO;

import Entity.Animal;

import java.util.List;
import java.util.Optional;

public interface AnimalRepository {

    Optional<Long> add(Animal animal);

    List<Animal> getAll();

    Animal getById(long id);

    void updateById(long id, Animal animal);

    void removeById(long id);
}
