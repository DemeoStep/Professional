package DAO;

import Entity.Aviary;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class AviaryDAO implements AviaryRepository{
    SessionFactory factory;

    public AviaryDAO(SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public Optional<Long> add(Aviary aviary) {
        Optional<Long> aviaryId = Optional.empty();

        if (getId(aviary).isEmpty()) {
            try (Session session = factory.openSession()) {
                session.beginTransaction();
                aviaryId = Optional.of((Long) session.save(aviary));

                session.getTransaction().commit();
            } catch (HibernateException e) {
                e.printStackTrace();
            }
        }

        return aviaryId;
    }

    @Override
    public List<Aviary> getAll() {
        try (Session session = factory.openSession()) {

        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Optional<Aviary> getById(long id) {
        Optional<Aviary> result = Optional.empty();

        try (Session session = factory.openSession()) {
            session.beginTransaction();
            result = Optional.of(session.get(Aviary.class, id));

            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void updateById(long id, Aviary aviary) {
        try (Session session = factory.openSession()) {

        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeById(long id) {
        try (Session session = factory.openSession()) {

        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Long> getId(Aviary aviary) {
        Optional<Long> aviaryId = Optional.empty();
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            Query<Aviary> query = session.createQuery("FROM Aviary a WHERE biome = :biome AND a.size = :size", Aviary.class)
                    .setParameter("biome", aviary.getBiome())
                    .setParameter("size", aviary.getSize());

            Optional<Aviary> optionalAviary = query.uniqueResultOptional();
            if(optionalAviary.isPresent()) {
                aviaryId = Optional.of(optionalAviary.get().getId());
            }

            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return aviaryId;
    }
}
