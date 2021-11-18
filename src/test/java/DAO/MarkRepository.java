package DAO;
import Entity.Mark;

import java.util.List;
import java.util.Optional;

public interface MarkRepository {
    void add(Mark mark);
    List<Mark> getAll();
    Optional<Long> getMarkId(Mark mark);
    Optional<String> getMark(long id);
}
