package DAO;

import Entity.Vet;

import java.util.List;
import java.util.Optional;

public interface VetRepository {
    Optional<Long> add(Vet vet);
    List<Vet> getAll();
    Optional<Vet> getById(long id);
    void updateById(long id, Vet vet);
    void removeById(long id);
    Optional<Long> getId(Vet vet);
}
