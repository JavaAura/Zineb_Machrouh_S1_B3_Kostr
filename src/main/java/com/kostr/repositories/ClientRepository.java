package main.java.com.kostr.repositories;

import main.java.com.kostr.dto.ClientDTO;
import main.java.com.kostr.models.Client;
import main.java.com.kostr.repositories.interfaces.ClientRepositoryInterface;

import java.sql.*;

import java.util.ArrayList;
import java.util.UUID;

public class ClientRepository implements ClientRepositoryInterface {
    private final Connection connection;

    public ClientRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Client addClient(Client client) throws SQLException {
        String query = "INSERT INTO Clients (name, address, email, phoneNumber, isProfessional) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, client.getName());
            ps.setString(2, client.getAddress());
            ps.setString(3, client.getEmail());
            ps.setString(4, client.getPhoneNumber());
            ps.setBoolean(5, client.isProfessional());

            int affectedRows = ps.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Failed to insert client, no rows affected.");
            }

            // Retrieve the generated UUID from the database
            return getClientModel(client, ps);
        }
    }


    private Client getClientModel(Client client, PreparedStatement ps) throws SQLException {
        try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                UUID clientId = (UUID) generatedKeys.getObject(1);

                return new Client(clientId, client.getName(), client.getAddress(), client.getEmail(), client.getPhoneNumber(), client.isProfessional());
            } else {
                return null;
            }
        }
    }

    @Override
    public Client getClientById(String id) throws SQLException {
        String query = "SELECT * FROM Clients WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Client(UUID.fromString(rs.getString("id")), rs.getString("name"), rs.getString("address"), rs.getString("email"), rs.getString("phoneNumber"), rs.getBoolean("isProfessional"));
                } else {
                    return null;
                }
            }
        }
    }

    @Override
    public ArrayList<Client> getAllClients() throws SQLException {
        String query = "SELECT DISTINCT * FROM Clients ORDER BY name";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            try (ResultSet rs = ps.executeQuery()) {
                ArrayList<Client> clients = new ArrayList<>();
                while (rs.next()) {
                    clients.add(new Client(UUID.fromString(rs.getString("id")), rs.getString("name"), rs.getString("address"), rs.getString("email"), rs.getString("phoneNumber"), rs.getBoolean("isProfessional")));
                }
                return clients;
            }
        }
    }

    @Override
    public Client getClientByEMail(String email) throws SQLException {
        String query = "SELECT * FROM Clients WHERE email = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Client(UUID.fromString(rs.getString("id")), rs.getString("name"), rs.getString("address"), rs.getString("email"), rs.getString("phoneNumber"), rs.getBoolean("isProfessional"));
                } else {
                    return null;
                }
            }
        }
    }
}
