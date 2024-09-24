package main.java.com.kostr.controllers;

import main.java.com.kostr.dto.ClientDTO;
import main.java.com.kostr.models.Client;
import main.java.com.kostr.services.interfaces.ClientServiceInterface;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientController {
    private ClientServiceInterface clientService;

    public static final String RESET = "\033[0m";
    public static final String RED = "\033[0;31m";
    public static final String YELLOW = "\u001b[93m";

    public ClientController(ClientServiceInterface clientService) {
        this.clientService = clientService;
    }

    public ClientDTO createClient(ClientDTO clientDTO) throws SQLException {
        if (clientDTO == null) {
            System.out.println(RED + "ClientDTO is null" + RESET);
            return null;
        }

        try {
            if (clientService.getClientByEMail(clientDTO.getEmail()) != null) {
                System.out.println(RED + "Client with email already exists" + RESET);
                return null;
            }else {
                Client client = clientService.addClient(clientDTO);
                System.out.println(YELLOW + "Client created successfully" + RESET);
                return ClientDTO.modelToDTO(client);
            }
        } catch (SQLException e) {
            System.out.println(RED + "Error creating client" + RESET);
            throw e;
        }
    }

    public List<ClientDTO> getAllClients() throws SQLException {
        try {
            System.out.println("Fetching all clients");
            ArrayList<Client> clients = clientService.getAllClients();
            if (clients != null) {
                List<ClientDTO> clientDTOList = new ArrayList<>();
                for (Client client : clients) {
                    clientDTOList.add(ClientDTO.modelToDTO(client));
                }
                return clientDTOList;
            } else {
                System.out.println(RED + "No clients found" + RESET);
                return null;
            }
        } catch (SQLException e) {
            System.out.println(RED + "Error fetching clients" + RESET);
            throw e;
        }
    }

    public ClientDTO getClientByEmail(String email) throws SQLException {
        if (email == null || email.isEmpty()) {
            System.out.println(RED + "Email is null or empty" + RESET);
            return null;
        }

        try {
            Client client = clientService.getClientByEMail(email);
            if (client != null) {
                return ClientDTO.modelToDTO(client);
            } else {
                System.out.println(RED + "Client not found" + RESET);
                return null;
            }
        } catch (SQLException e) {
            System.out.println(RED + "Error fetching client by email" + RESET);
            throw e;
        }
    }
}
