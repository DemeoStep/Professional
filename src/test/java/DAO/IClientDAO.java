package DAO;

import Entity.Client;

import java.util.List;
import java.util.Optional;

public interface IClientDAO {
    void addClient(Client client);
    Optional<Client> getClient(long id);
    Optional<Long> getClientId(Client client);
    List<Client> getAllClients();
    int updateClient(long id, Client client);
    int deleteClient(long id);
}
