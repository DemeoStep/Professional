package DAO;

import Entity.Mark;
import lombok.ToString;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

@ToString
public class MarkDAO implements MarkRepository {
    private SessionFactory factory;

    public MarkDAO(SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public void add(Mark mark) {
        try (Session session = factory.openSession()) {
            session.save(mark);
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Mark> getAll() {
        List<Mark> markList = new ArrayList<>();

        try (Session session = factory.openSession()){
            markList = session.createQuery("FROM Mark", Mark.class).list();
        } catch (HibernateException e) {
            e.printStackTrace();
        }

        return markList;
    }

    @Override
    public long getMarkId(Mark mark) {
        try(Session session = factory.openSession()) {
            Query<Mark> query = session.createQuery("FROM Mark WHERE mark = :markName", Mark.class);
            query.setParameter("markName", mark.getMark());

            if (query.uniqueResultOptional().isPresent()) {
                return query.getSingleResult().getId();
            }
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public String getMark(long id) {
        try (Session session = factory.openSession()) {
            return session.get(Mark.class, id).getMark();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return "";
    }

}
