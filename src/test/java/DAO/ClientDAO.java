package DAO;

import Entity.Client;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClientDAO implements ClientRepository {
    SessionFactory factory;

    public ClientDAO(SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public void addClient(Client client) {
        if (getClientId(client).isEmpty()) {

            try (Session session = factory.openSession()){
                session.save(client);

            } catch (HibernateException e) {
                e.printStackTrace();
            }

        } else {
            System.err.println("Client " + client + " is present in DB");
        }

    }

    @Override
    public Optional<Client> getClient(long id) {
        try (Session session = factory.openSession()){
            Query<Client> query = session
                    .createQuery("FROM Client WHERE id = :id", Client.class)
                    .setParameter("id", id);

            Optional<Client> result = query.uniqueResultOptional();

            if(result.isPresent()) {
                return result;
            }

        } catch (HibernateException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    @Override
    public Optional<Long> getClientId(Client client) {
        try (Session session = factory.openSession()){
            Query<Client> query = session
                    .createQuery("FROM Client WHERE name = :name AND age = :age AND phone = :phone", Client.class)
                    .setParameter("name", client.getName())
                    .setParameter("age", client.getAge())
                    .setParameter("phone", client.getPhone());

            Optional<Client> result = query.uniqueResultOptional();
            if (result.isPresent()) {
                return Optional.of(result.get().getId());
            }

        } catch (HibernateException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    @Override
    public List<Client> getAllClients() {
        List<Client> clients = new ArrayList<>();

        try (Session session = factory.openSession()){
            clients = session.createQuery("FROM Client", Client.class).list();

        } catch (HibernateException e) {
            e.printStackTrace();
        }

        return clients;
    }

    @Override
    public void updateClient(long id, Client client) {
        try (Session session = factory.openSession()){
            session.beginTransaction();
            Client oldClient = session.get(Client.class, id);

            if (oldClient != null) {
                oldClient.setName(client.getName());
                oldClient.setAge(client.getAge());
                oldClient.setPhone(client.getPhone());
            }
            session.save(oldClient);

            session.getTransaction().commit();

        } catch (HibernateException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void deleteClient(long id) {
        try (Session session = factory.openSession()){
            session.beginTransaction();

            Client client = session.get(Client.class, id);
            if(client != null) {
                session.delete(client);
            }

            session.getTransaction().commit();

        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

}
