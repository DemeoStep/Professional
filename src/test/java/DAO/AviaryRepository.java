package DAO;

import Entity.Aviary;
import Entity.Vet;

import java.util.List;
import java.util.Optional;

public interface AviaryRepository {
    Optional<Long> add(Aviary aviary);
    List<Aviary> getAll();
    Optional<Aviary> getById(long id);
    void updateById(long id, Aviary aviary);
    void removeById(long id);
    Optional<Long> getId(Aviary aviary);
}
