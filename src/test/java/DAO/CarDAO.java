package DAO;

import Entity.Car;
import Entity.Mark;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CarDAO implements CarRepository {
    SessionFactory factory;
    MarkRepository markDAO;

    public CarDAO(SessionFactory factory, MarkRepository markDAO) {
        this.factory = factory;
        this.markDAO = markDAO;
    }

    @Override
    public void add(Car car) {

        if (getCarId(car).isEmpty()) {

            try (Session session = factory.openSession()){

                if(markDAO.getMarkId(new Mark(car.getMark())).isEmpty()) {
                    markDAO.add(new Mark(car.getMark()));
                }

                car.setMarkId(markDAO.getMarkId(new Mark(car.getMark())).get());
                session.save(car);

            } catch (HibernateException e) {
                e.printStackTrace();
            }

        } else {
            System.err.println("Car " + car + " is present in DB!");
        }

    }

    @Override
    public List<Car> getAll() {
        List<Car> carList = new ArrayList<>();

        try (Session session = factory.openSession()){

            carList = session.createQuery("FROM Car", Car.class).list();

            for (Car car: carList) {
                car.setMark(markDAO.getMark(car.getMarkId()).get());
            }

        } catch (HibernateException e) {
            e.printStackTrace();
        }

        return carList;
    }

    @Override
    public Optional<Car> getById(long id) {

        try (Session session = factory.openSession()) {
            Car car = new Car();

            Query<Car> query = session
                    .createQuery("FROM Car WHERE id = :id", Car.class)
                    .setParameter("id", id);

            Optional<Car> result = query.uniqueResultOptional();

            if (result.isPresent()) {
                car = result.get();
                car.setMark(markDAO.getMark(car.getMarkId()).get());
            }

            return Optional.of(car);

        } catch (HibernateException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    @Override
    public Optional<Long> getCarId(Car car) {
        Optional<Long> markId = markDAO.getMarkId(new Mark(car.getMark()));

        if(markId.isPresent()) {
            try (Session session = factory.openSession()) {
                Query<Car> query = session
                        .createQuery("FROM Car WHERE markId = :markId AND model = :model AND price = :price", Car.class)
                        .setParameter("markId", markId.get())
                        .setParameter("model", car.getModel())
                        .setParameter("price", car.getPrice());

                Optional<Car> result = query.uniqueResultOptional();

                if (result.isPresent()) {
                    return Optional.of(result.get().getId());
                }

            } catch (HibernateException e) {
                e.printStackTrace();
            }
        }

        return Optional.empty();
    }

    @Override
    public void updateCar(long id, Car car) {
        try (Session session = factory.openSession()){
            if (getById(id).isEmpty()) {
                return;
            }

            if(markDAO.getMarkId(new Mark(car.getMark())).isEmpty()) {
                markDAO.add(new Mark(car.getMark()));
            }

            session.beginTransaction();
            Car oldCar = session.get(Car.class, id);

            if(!oldCar.equals(car)) {
                oldCar.setMarkId(markDAO.getMarkId(new Mark(car.getMark())).get());
                oldCar.setMark(car.getMark());
                oldCar.setModel(car.getModel());
                oldCar.setPrice(car.getPrice());
            }

            session.getTransaction().commit();

        } catch (HibernateException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void removeById(long id) {
        try (Session session = factory.openSession()){
            session.beginTransaction();
            Car car = session.get(Car.class, id);
            if (car != null) {
                session.delete(car);
            }
            session.getTransaction().commit();

        } catch (HibernateException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void removeByMark(String mark) {
        Optional<Long> markId = markDAO.getMarkId(new Mark(mark));

        if(markId.isPresent()) {
            try (Session session = factory.openSession()){
                session.beginTransaction();
                List<Car> carList = session
                        .createQuery("FROM Car WHERE markId = :markId", Car.class)
                        .setParameter("markId", markId.get()).list();

                for (Car car: carList) {
                    session.delete(car);
                }

                session.getTransaction().commit();

            } catch (HibernateException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void removeByModel(String model) {
        try (Session session = factory.openSession()){
            session.beginTransaction();

            List<Car> carList = session
                    .createQuery("FROM Car WHERE model = :model", Car.class)
                    .setParameter("model", model).list();

            for (Car car: carList) {
                session.delete(car);
            }

            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }
}
