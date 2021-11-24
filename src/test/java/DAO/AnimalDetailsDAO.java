package DAO;

import Entity.AnimalDetails;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AnimalDetailsDAO implements AnimalDetailsRepository{

    SessionFactory factory;

    public AnimalDetailsDAO(SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public Optional<Long> add(AnimalDetails details) {
        Optional<Long> detailsId = Optional.empty();
        try (Session session = factory.openSession()) {
            detailsId = Optional.of((Long) session.save(details));
        } catch (HibernateException e) {
            e.printStackTrace();
        }

        return detailsId;
    }

    @Override
    public List<AnimalDetails> getAll() {
        List<AnimalDetails> list = new ArrayList<>();
        try (Session session = factory.openSession()) {
            list = session.createQuery("FROM AnimalDetails", AnimalDetails.class).list();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Optional<AnimalDetails> getById(long id) {
        Optional<AnimalDetails> result = Optional.empty();
        try (Session session = factory.openSession()) {

            result = session.createQuery("FROM AnimalDetails WHERE id = :id", AnimalDetails.class)
                    .setParameter("id", id)
                    .uniqueResultOptional();

        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void updateById(long id, AnimalDetails details) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();

            session.createQuery("FROM AnimalDetails WHERE id = :id", AnimalDetails.class)
                    .setParameter("id", id)
                    .uniqueResultOptional()
                    .ifPresent(value -> value.copyFrom(details));

            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeById(long id) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();

            session.createQuery("FROM AnimalDetails WHERE id = :id", AnimalDetails.class)
                    .setParameter("id", id)
                    .uniqueResultOptional()
                    .ifPresent(session::delete);

            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }
}
