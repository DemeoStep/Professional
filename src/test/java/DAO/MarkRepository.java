package DAO;
import Entity.Mark;

import java.util.List;

public interface MarkRepository {
    void add(Mark mark);
    List<Mark> getAll();
    long getMarkId(Mark mark);
    String getMark(long id);
}
