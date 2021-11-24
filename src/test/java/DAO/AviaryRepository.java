package DAO;

import Entity.Aviary;

import java.util.Optional;

public interface AviaryRepository extends IRepository<Aviary> {
    Optional<Long> getId(Aviary aviary);
}
