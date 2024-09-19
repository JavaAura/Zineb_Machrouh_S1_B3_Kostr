package main.java.com.kostr.repositories.interfaces;

import main.java.com.kostr.dto.ClientDTO;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ClientRepositoryInterface {
    public void addClient(ClientDTO client) throws SQLException;
    public void removeClient(String id) throws SQLException;
    public void updateClient(ClientDTO client) throws SQLException;
    public ResultSet getClientById(String id) throws SQLException;

}
