package main.java.com.kostr.repositories;

import main.java.com.kostr.dto.ClientDTO;
import main.java.com.kostr.repositories.interfaces.ClientRepositoryInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientRepository implements ClientRepositoryInterface {
    private final Connection connection;

    public ClientRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void addClient(ClientDTO client) throws SQLException {
        String query = "INSERT INTO Clients (name, address, phoneNumber, isProfessional) VALUES ( ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(2, client.getName());
            ps.setString(3, client.getAddress());
            ps.setString(4, client.getPhoneNumber());
            ps.setBoolean(5, client.isProfessional());
            ps.executeUpdate();
        }
    }

    @Override
    public void removeClient(String id) throws SQLException {
        String query = "DELETE FROM Clients WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, id);
            ps.executeUpdate();
        }
    }

    @Override
    public void updateClient(ClientDTO client) throws SQLException {
        String query = "UPDATE Clients SET name = ?, address = ?, phoneNumber = ?, isProfessional = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, client.getName());
            ps.setString(2, client.getAddress());
            ps.setString(3, client.getPhoneNumber());
            ps.setBoolean(4, client.isProfessional());
            ps.setString(5, client.getId().toString());
            ps.executeUpdate();
        }
    }

    @Override
    public ResultSet getClientById(String id) throws SQLException {
        String query = "SELECT * FROM Clients WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, id);
            return ps.executeQuery();
        }
    }
}
