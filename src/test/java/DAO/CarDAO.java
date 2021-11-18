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

        if(getCarId(car).isEmpty()) {

            try (Session session = factory.openSession()){
                if(markDAO.getMarkId(new Mark(car.getMark())) == 0) {
                    markDAO.add(new Mark(car.getMark()));
                }

                car.setMarkId(markDAO.getMarkId(new Mark(car.getMark())));
                session.save(car);

            } catch (HibernateException e) {
                e.printStackTrace();
            }

        } else {
            System.out.println("Car is present in DB!");
        }

    }

    @Override
    public List<Car> getAll() {
        List<Car> carList = new ArrayList<>();

        try (Session session = factory.openSession()){

            carList = session.createQuery("FROM Car", Car.class).list();

            for (Car car: carList) {
                car.setMark(markDAO.getMark(car.getMarkId()));
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

            Query<Car> query = session.createQuery("FROM Car WHERE id = :id", Car.class);
            query.setParameter("id", id);

            if (query.uniqueResultOptional().isPresent()) {
                car = query.getSingleResult();
            }

            car.setMark(markDAO.getMark(car.getMarkId()));

            return Optional.of(car);

        } catch (HibernateException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    @Override
    public Optional<Long> getCarId(Car car) {
        try (Session session = factory.openSession()){
            car.setMarkId(markDAO.getMarkId(new Mark(car.getMark())));

            System.out.println(car.getMarkId());

            Query<Car> query = session.createQuery("FROM Car WHERE markId = :markId AND model = :model AND price = :price", Car.class);

            query.setParameter("markId", car.getMarkId());
            query.setParameter("model", car.getModel());
            query.setParameter("price", car.getPrice());

            if (query.uniqueResultOptional().isPresent()) {
                return Optional.of(query.getSingleResult().getId());
            }

        } catch (HibernateException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

//    @Override
//    public void updateCar(Car car) {
//        Connection connection = Connector.getConnection();
//        PreparedStatement statement = null;
//
//        try {
//            statement = connection.prepareStatement("UPDATE cars SET mark_id = (SELECT id FROM marks WHERE mark = ?), model = ?, price = ? WHERE id = ?");
//            statement.setString(1, car.getMark());
//            statement.setString(2, car.getModel());
//            statement.setInt(3, car.getPrice());
//            statement.setLong(4, car.getId());
//
//            System.out.println("Values updated: " + statement.executeUpdate());
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            Connector.closeConnections(connection, statement);
//        }
//
//    }
//
//    @Override
//    public int removeById(long id) {
//        Connection connection = Connector.getConnection();
//        PreparedStatement statement = null;
//
//        if(getById(id).isPresent()) {
//            try {
//                statement = connection.prepareStatement("DELETE FROM cars WHERE id = ?");
//                statement.setLong(1, id);
//                return statement.executeUpdate();
//
//            } catch (SQLException e) {
//                e.printStackTrace();
//            } finally {
//                Connector.closeConnections(connection, statement);
//            }
//        }
//
//        return 0;
//    }
//
//    @Override
//    public int removeByMark(String mark) {
//        Connection connection = Connector.getConnection();
//        PreparedStatement statement = null;
//
//        if(getMarkId(mark).isPresent()) {
//            try {
//                statement = connection.prepareStatement("DELETE FROM cars WHERE mark_id = ?");
//                statement.setLong(1, getMarkId(mark).get());
//                return statement.executeUpdate();
//
//            } catch (SQLException e) {
//                e.printStackTrace();
//            } finally {
//                Connector.closeConnections(connection, statement);
//            }
//        }
//
//        return 0;
//    }
//
//    @Override
//    public int removeByModel(String model) {
//        Connection connection = Connector.getConnection();
//        PreparedStatement statement = null;
//
//        try {
//            statement = connection.prepareStatement("DELETE FROM cars WHERE model = ?");
//            statement.setString(1, model);
//            return statement.executeUpdate();
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            Connector.closeConnections(connection, statement);
//        }
//
//        return 0;
//    }
}
