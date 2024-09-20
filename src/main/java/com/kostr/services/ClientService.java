package main.java.com.kostr.services;

import main.java.com.kostr.dto.ClientDTO;
import main.java.com.kostr.repositories.ClientRepository;
import main.java.com.kostr.services.interfaces.ClientServiceInterface;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

public class ClientService implements ClientServiceInterface {
    private final ClientRepository clientRepository;
    private static final Logger logger = Logger.getLogger(ClientService.class.getName());

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public void addClient(ClientDTO client) throws SQLException {
        if (client.getName().isEmpty() || client.getAddress().isEmpty() || client.getEmail().isEmpty() || client.getPhoneNumber().isEmpty()) {
            logger.severe("All fields must be filled in");
        }else{
            clientRepository.addClient(client);
            logger.info("Client added successfully");
        }
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
