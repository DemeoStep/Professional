package DAO;

import Entity.Client;
import Connection.Connector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClientDAO implements IClientDAO {
    @Override
    public void addClient(Client client) {
        Connection connection = Connector.getConnection();
        PreparedStatement statement = null;

        if (getClientId(client).isEmpty()) {

            try {
                statement = connection.prepareStatement("INSERT INTO clients (name, age, phone) VALUE (?, ?, ?)");
                statement.setString(1, client.getName());
                statement.setInt(2, client.getAge());
                statement.setString(3, client.getPhone());
                statement.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                Connector.closeConnections(connection, statement);
            }

        } else {
            System.out.println("Client is present in DB");
        }

    }

    @Override
    public Optional<Client> getClient(long id) {
        Connection connection = Connector.getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement("SELECT * FROM clients WHERE id = ?");
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                Client client = new Client(
                        result.getLong("id"),
                        result.getString("name"),
                        result.getInt("age"),
                        result.getString("phone")
                );

                return Optional.of(client);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Connector.closeConnections(connection, statement);
        }

        return Optional.empty();
    }

    @Override
    public Optional<Long> getClientId(Client client) {
        Connection connection = Connector.getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement("SELECT id FROM clients WHERE name = ? AND age = ? AND phone = ?");
            statement.setString(1, client.getName());
            statement.setInt(2, client.getAge());
            statement.setString(3, client.getPhone());
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                return Optional.of(result.getLong("id"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Connector.closeConnections(connection, statement);
        }

        return Optional.empty();
    }

    @Override
    public List<Client> getAllClients() {
        List<Client> clients = new ArrayList<>();
        Connection connection = Connector.getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement("SELECT * FROM clients");
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                Client client = new Client(
                        result.getLong("id"),
                        result.getString("name"),
                        result.getInt("age"),
                        result.getString("phone"));

                clients.add(client);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Connector.closeConnections(connection, statement);
        }


        return clients;
    }

    @Override
    public int updateClient(long id, Client client) {
        Connection connection = Connector.getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement("UPDATE clients SET name = ?, age = ?, phone = ? WHERE id = ?");
            statement.setString(1, client.getName());
            statement.setInt(2, client.getAge());
            statement.setString(3, client.getPhone());
            statement.setLong(4, id);
            return statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Connector.closeConnections(connection, statement);
        }

        return 0;
    }

    @Override
    public int deleteClient(long id) {
        Connection connection = Connector.getConnection();
        PreparedStatement statement = null;

        if(getClient(id).isPresent()) {
            try {
                statement = connection.prepareStatement("DELETE FROM clients WHERE id = ?");
                statement.setLong(1, id);
                return statement.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                Connector.closeConnections(connection, statement);
            }
        }

        return 0;
    }

}
