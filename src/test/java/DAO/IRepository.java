package DAO;

import java.util.List;
import java.util.Optional;

public interface IRepository<T> {
    Optional<Long> add(T entity);
    List<T> getAll();
    Optional<T> getById(long id);
    void updateById(long id, T entity);
    void removeById(long id);
}
