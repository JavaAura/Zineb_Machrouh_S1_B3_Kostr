package main.java.com.kostr.repositories;

import main.java.com.kostr.dto.MaterialDTO;
import main.java.com.kostr.models.Material;
import main.java.com.kostr.repositories.interfaces.MaterialRepositoryInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

public class MaterialRepository implements MaterialRepositoryInterface {
    private final Connection connection;

    public MaterialRepository(Connection connection) {
        this.connection = connection;
    }

    private Material getMaterialModel(Material material, PreparedStatement ps) throws SQLException {
        try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                UUID id = (UUID) generatedKeys.getObject(1);

                return new Material(id, material.getName(), material.getType(), material.getVatRate(), material.getTotalPrice(), material.getProjectId(), material.getUnitCost(), material.getQuantity(), material.getTransportCost(), material.getQualityCoefficient());
            }else {
                return null;
            }
        }
    }

    @Override
    public Material addMaterial(Material material) throws SQLException {
        String query = "INSERT INTO Materials (name, type, vatRate, totalPrice, projectId, unitCost, quantity, transportCost, qualityCoefficient) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, material.getName());
            ps.setString(2, material.getType().toString());
            ps.setDouble(3, material.getVatRate());
            ps.setDouble(4, material.getTotalPrice());
            ps.setString(5, material.getProjectId().toString());
            ps.setDouble(6, material.getUnitCost());
            ps.setDouble(7, material.getQuantity());
            ps.setDouble(8, material.getTransportCost());
            ps.setDouble(9, material.getQualityCoefficient());

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Failed to insert material, no rows affected.");
            }

            return getMaterialModel(material, ps);
        }
    }

    @Override
    public void removeMaterial(String id) throws SQLException {
        String query = "DELETE FROM Materials WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, id);
            ps.executeUpdate();
        }
    }

    @Override
    public Material updateMaterial(Material material) throws SQLException {
        String query = "UPDATE Materials SET name = ?, vatRate = ?, type = ?, totalPrice = ?, projectId = ?, unitCost = ?, quantity = ?, transportCost = ?, qualityCoefficient = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, material.getName());
            ps.setDouble(2, material.getVatRate());
            ps.setString(3, material.getType().toString());
            ps.setDouble(4, material.getTotalPrice());
            ps.setString(5, material.getProjectId().toString());
            ps.setDouble(6, material.getUnitCost());
            ps.setDouble(7, material.getQuantity());
            ps.setDouble(8, material.getTransportCost());
            ps.setDouble(9, material.getQualityCoefficient());
            ps.setString(10, material.getId().toString());


            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Failed to update material, no rows affected.");
            }
            return getMaterialModel(material, ps);
        }
    }

    @Override
    public Material getMaterialById(String id) throws SQLException {
        String query = "SELECT * FROM Materials WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, id);

            try(ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Material(UUID.fromString(rs.getString("id")), rs.getString("name"), UUID.fromString(rs.getString("type")), rs.getDouble("vatRate"), rs.getDouble("totalPrice"), UUID.fromString(rs.getString("projectId")), rs.getDouble("unitCost"), rs.getDouble("quantity"), rs.getDouble("transportCost"), rs.getDouble("qualityCoefficient"));
                }else{
                    return null;
                }
            }
        }
    }

    @Override
    public ArrayList<Material> getMaterialsByProject(String projectId) throws SQLException {
        String query = "SELECT DISTINCT * FROM Materials WHERE projectId = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, projectId);

            try(ResultSet rs = ps.executeQuery()) {
                ArrayList<Material> materials = new ArrayList<>();
                while (rs.next()) {
                    materials.add(new Material(UUID.fromString(rs.getString("id")), rs.getString("name"), UUID.fromString(rs.getString("type")), rs.getDouble("vatRate"), rs.getDouble("totalPrice"), UUID.fromString(rs.getString("projectId")), rs.getDouble("unitCost"), rs.getDouble("quantity"), rs.getDouble("transportCost"), rs.getDouble("qualityCoefficient")));
                }
                return materials;
            }
        }
    }
}
