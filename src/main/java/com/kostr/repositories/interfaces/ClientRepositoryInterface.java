package main.java.com.kostr.repositories.interfaces;

import main.java.com.kostr.models.Client;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface ClientRepositoryInterface {
    public Client addClient(Client client) throws SQLException;
    public void removeClient(String id) throws SQLException;
    public Client updateClient(Client client) throws SQLException;
    public Client getClientById(String id) throws SQLException;
    public ArrayList<Client> getAllClients() throws SQLException;
    public Client getClientByEMail(String email) throws SQLException;
}
