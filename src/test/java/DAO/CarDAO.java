package DAO;

import Entity.Car;
import Connection.Connector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CarDAO implements ICarDAO{

    private Connection getConnection() {
        Optional<Connection> optional = Connector.getConnection();

        if (optional.isEmpty()) {
            throw new RuntimeException("DB connection error");
        }

        return optional.get();
    }

    private void closeConnections(Connection connection, Statement statement) {
        try {
            if(statement != null) {
                statement.close();
            }

            if(!connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void add(Car car) {
        Connection connection = getConnection();
        PreparedStatement statement = null;

        if(getCarId(car).isEmpty()) {

            try {

                if (getMarkId(car.getMark()).isEmpty()) {
                    addMark(car.getMark());
                }

                long markId = getMarkId(car.getMark()).get();

                statement = connection.prepareStatement("INSERT INTO cars (mark_id, model, price) VALUES (?, ?, ?)");
                statement.setLong(1, markId);
                statement.setString(2, car.getModel());
                statement.setInt(3, car.getPrice());

                statement.executeUpdate();


            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                closeConnections(connection, statement);
            }
        } else {
            System.out.println("Car is present in DB!");
        }

    }

    private void addMark(String mark) {
        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement("INSERT INTO marks (mark) VALUE (?)");
            statement.setString(1, mark);
            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnections(connection, statement);
        }
    }

    private Optional<Long> getMarkId(String mark) {
        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement("SELECT id FROM marks WHERE mark = ?");
            statement.setString(1, mark);
            ResultSet result = statement.executeQuery();

            if(result.next()) {
                return Optional.of(result.getLong("id"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnections(connection, statement);
        }

        return Optional.empty();

    }

    @Override
    public List<Car> getAll() {
        List<Car> carList = new ArrayList<>();
        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement("SELECT cars.id, marks.mark, cars.model, cars.price FROM cars JOIN marks ON marks.id = cars.mark_id;");
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                Car car = new Car(
                        result.getLong("id"),
                        result.getString("mark"),
                        result.getString("model"),
                        result.getInt("price")
                );

                carList.add(car);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnections(connection, statement);
        }

        return carList;
    }

    @Override
    public Optional<Car> getById(long id) {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        Car car = new Car();

        try {
            statement = connection.prepareStatement("SELECT cars.id, marks.mark, cars.model, cars.price FROM cars JOIN marks ON marks.id = cars.mark_id WHERE cars.id = ?;");
            statement.setLong(1, id);

            ResultSet result = statement.executeQuery();

            if (result.next()) {
                car.setId(result.getInt("id"));
                car.setMark(result.getString("mark"));
                car.setModel(result.getString("model"));
                car.setPrice(result.getInt("price"));
            }
            return Optional.of(car);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnections(connection, statement);
        }

        return Optional.empty();
    }

    @Override
    public Optional<Long> getCarId(Car car) {
        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement("SELECT id FROM cars WHERE mark_id = ? AND model = ? AND price = ?");
            if(getMarkId(car.getMark()).isPresent()) {
                statement.setLong(1, getMarkId(car.getMark()).get());
            } else {
                return Optional.empty();
            }
            statement.setString(2, car.getModel());
            statement.setInt(3, car.getPrice());

            ResultSet result = statement.executeQuery();

            if(result.next()) {
                return Optional.of(result.getLong("id"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnections(connection, statement);
        }


        return Optional.empty();
    }

    @Override
    public void updateCar(Car car) {
        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement("UPDATE cars SET mark_id = (SELECT id FROM marks WHERE mark = ?), model = ?, price = ? WHERE id = ?");
            statement.setString(1, car.getMark());
            statement.setString(2, car.getModel());
            statement.setInt(3, car.getPrice());
            statement.setLong(4, car.getId());

            System.out.println("Values updated: " + statement.executeUpdate());

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnections(connection, statement);
        }

    }

    @Override
    public int removeById(long id) {
        Connection connection = getConnection();
        PreparedStatement statement = null;

        if(getById(id).isPresent()) {
            try {
                statement = connection.prepareStatement("DELETE FROM cars WHERE id = ?");
                statement.setLong(1, id);
                return statement.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                closeConnections(connection, statement);
            }
        }

        return 0;
    }

    @Override
    public int removeByMark(String mark) {
        Connection connection = getConnection();
        PreparedStatement statement = null;

        if(getMarkId(mark).isPresent()) {
            try {
                statement = connection.prepareStatement("DELETE FROM cars WHERE mark_id = ?");
                statement.setLong(1, getMarkId(mark).get());
                return statement.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                closeConnections(connection, statement);
            }
        }

        return 0;
    }

    @Override
    public int removeByModel(String model) {
        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement("DELETE FROM cars WHERE model = ?");
            statement.setString(1, model);
            return statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnections(connection, statement);
        }

        return 0;
    }
}
