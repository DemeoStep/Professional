package DAO;

import Entity.Animal;
import Entity.AnimalDetails;

import java.util.Optional;

public interface AnimalDetailsRepository {
    Optional<Long> add(AnimalDetails details);

    Optional<AnimalDetails> get(long id);

    void update(long id, AnimalDetails details);

    void remove(long id);
}
