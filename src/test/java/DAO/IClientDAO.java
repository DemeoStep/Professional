package DAO;

import Entity.Client;

import java.util.List;

public interface IClientDAO {
    void addClient(Client client);
    Client getClient(long id);
    List<Client> getAllClients();
    void updateClient(long id, Client client);
    int deleteClient(long id);
}
