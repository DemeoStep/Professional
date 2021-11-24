package DAO;

import Entity.Vet;

import java.util.Optional;

public interface VetRepository extends IRepository<Vet> {
    Optional<Long> getId(Vet vet);
}
