package DAO;

import Entity.Client;

import java.util.List;
import java.util.Optional;

public interface ClientRepository {
    void addClient(Client client);
    Optional<Client> getClient(long id);
    Optional<Long> getClientId(Client client);
    List<Client> getAllClients();
    void updateClient(long id, Client client);
    void deleteClient(long id);
}
