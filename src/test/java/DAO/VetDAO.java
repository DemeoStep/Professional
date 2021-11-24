package DAO;

import Entity.Animal;
import Entity.Vet;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class VetDAO implements VetRepository{
    SessionFactory factory;

    public VetDAO(SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public Optional<Long> add(Vet vet) {
        Optional<Long> vetId = Optional.empty();

        if (getId(vet).isEmpty()) {
            try (Session session = factory.openSession()) {
                vetId = Optional.of((Long) session.save(vet));

            } catch (HibernateException e) {
                e.printStackTrace();
            }
        }

        return vetId;
    }

    @Override
    public List<Vet> getAll() {
        List<Vet> list = new ArrayList<>();
        try (Session session = factory.openSession()) {
            list = session.createQuery("FROM Vet", Vet.class).list();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Optional<Vet> getById(long id) {
        Optional<Vet> result = Optional.empty();
        try (Session session = factory.openSession()) {
            result = session.createQuery("FROM Vet WHERE id = :id", Vet.class)
                    .setParameter("id", id)
                    .uniqueResultOptional();

        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void updateById(long id, Vet vet) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();

            session.createQuery("FROM Vet WHERE id = :id", Vet.class)
                    .setParameter("id", id)
                    .uniqueResultOptional()
                    .ifPresent(value -> value.copyFrom(vet));


            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeById(long id) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();

            session.createQuery("FROM Vet WHERE id = :id", Vet.class)
                    .setParameter("id", id)
                    .uniqueResultOptional()
                    .ifPresent(session::delete);

            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Long> getId(Vet vet) {
        Optional<Long> vetId = Optional.empty();
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            Query<Vet> query = session.createQuery("FROM Vet WHERE name = :name AND phone = :phone AND clinic = :clinic", Vet.class)
                    .setParameter("name", vet.getName())
                    .setParameter("phone", vet.getPhone())
                    .setParameter("clinic", vet.getClinic());
            Optional<Vet> optionalVet = query.uniqueResultOptional();
            if(optionalVet.isPresent()) {
                vetId = Optional.of(optionalVet.get().getId());
            }

            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }

        return vetId;
    }
}
