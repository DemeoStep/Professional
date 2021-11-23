package DAO;

import Entity.AnimalDetails;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

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
            session.beginTransaction();
            detailsId = Optional.of((Long) session.save(details));
            session.getTransaction().commit();

        } catch (HibernateException e) {
            e.printStackTrace();
        }

        return detailsId;
    }

    @Override
    public Optional<AnimalDetails> get(long id) {
        Optional<AnimalDetails> result = Optional.empty();
        try (Session session = factory.openSession()) {
            result = Optional.of(session.get(AnimalDetails.class, id));
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void update(long id, AnimalDetails details) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            AnimalDetails oldDetails = session.get(AnimalDetails.class, id);
            oldDetails.copyFrom(details);

            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remove(long id) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            AnimalDetails details = session.get(AnimalDetails.class, id);
            session.delete(details);

            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }
}
