package main.java.com.kostr.services;

import main.java.com.kostr.dto.ClientDTO;
import main.java.com.kostr.models.Client;
import main.java.com.kostr.repositories.interfaces.ClientRepositoryInterface;
import main.java.com.kostr.services.interfaces.ClientServiceInterface;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Logger;

public class ClientService implements ClientServiceInterface {
    private final ClientRepositoryInterface clientRepository;
    private static final Logger logger = Logger.getLogger(ClientService.class.getName());

    public ClientService(ClientRepositoryInterface clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Client addClient(ClientDTO client) throws SQLException {
        if (client.getName().isEmpty() || client.getAddress().isEmpty() || client.getEmail().isEmpty() || client.getPhoneNumber().isEmpty()) {
            logger.severe("All fields must be filled in");
            return null;
        }else{
            logger.info("Client added successfully");
            Client clientModel = client.dtoToModel();
            return clientRepository.addClient(clientModel);
        }
    }

    @Override
    public void removeClient(String id) throws SQLException {
        if (id.isEmpty()) {
            logger.severe("ID field must be filled in");
        }else{
            if (clientRepository.getClientById(id) == null) {
                logger.severe("Client not found");
            }else{
                clientRepository.removeClient(id);
                logger.info("Client removed successfully");
            }
        }
    }

    @Override
    public Client updateClient(ClientDTO client) throws SQLException {
        if (client.getName().isEmpty() || client.getAddress().isEmpty() || client.getEmail().isEmpty() || client.getPhoneNumber().isEmpty()) {
            logger.severe("All fields must be filled in");
            return null;
        }else{
            logger.info("Client updated successfully");
            Client clientModel = client.dtoToModel();
            return clientRepository.updateClient(clientModel);
        }
    }

    @Override
    public Client getClientById(String id) throws SQLException {
        if (id.isEmpty()) {
            logger.severe("ID field must be filled in");
            return null;
        }else{
            return clientRepository.getClientById(id);
        }
    }

    @Override
    public ArrayList<Client> getAllClients() throws SQLException {
        return clientRepository.getAllClients();
    }
}
