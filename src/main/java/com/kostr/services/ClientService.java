package main.java.com.kostr.services;

import main.java.com.kostr.dto.ClientDTO;
import main.java.com.kostr.repositories.ClientRepository;
import main.java.com.kostr.services.interfaces.ClientServiceInterface;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientService implements ClientServiceInterface {
    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public void addClient(ClientDTO client) throws SQLException {
        clientRepository.addClient(client);
        System.out.println("Client added successfully");
    }

    @Override
    public void removeClient(String id) throws SQLException {

    }

    @Override
    public void updateClient(ClientDTO client) throws SQLException {

    }

    @Override
    public ResultSet getClientById(String id) throws SQLException {
        return null;
    }
}
