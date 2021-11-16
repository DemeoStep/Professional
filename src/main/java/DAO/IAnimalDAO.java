package DAO;

import Entity.Animal;

import java.util.List;
import java.util.Optional;

public interface IAnimalDAO {
    void addAnimal(Animal animal);
    Optional<Animal> getAnimal(long id);
    void updateAnimal (long id, Animal animal);
    void deleteAnimal (long id);
    List<Animal> getAllAnimals();
}
