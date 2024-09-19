package main.java.com.kostr.repositories;

import main.java.com.kostr.dto.MaterialDTO;
import main.java.com.kostr.repositories.interfaces.MaterialRepositoryInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MaterialRepository implements MaterialRepositoryInterface {
    private final Connection connection;

    public MaterialRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void addMaterial(MaterialDTO material) throws SQLException {
        String query = "INSERT INTO Materials (name, type, vatRate, totalPrice, projectId, unitCost, quantity, transportCost, qualityCoefficient) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(2, material.getName());
            ps.setObject(3, material.getType());
            ps.setDouble(4, material.getVatRate());
            ps.setDouble(5, material.getTotalPrice());
            ps.setString(6, material.getProjectId().toString());
            ps.setDouble(7, material.getUnitCost());
            ps.setDouble(8, material.getQuantity());
            ps.setDouble(9, material.getTransportCost());
            ps.setDouble(10, material.getQualityCoefficient());
            ps.executeUpdate();
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
    public void updateMaterial(MaterialDTO material) {
        String query = "UPDATE Materials SET name = ?, vatRate = ?, totalPrice = ?, projectId = ?, unitCost = ?, quantity = ?, transportCost = ?, qualityCoefficient = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, material.getName());
            ps.setDouble(2, material.getVatRate());
            ps.setDouble(3, material.getTotalPrice());
            ps.setString(4, material.getProjectId().toString());
            ps.setDouble(5, material.getUnitCost());
            ps.setDouble(6, material.getQuantity());
            ps.setDouble(7, material.getTransportCost());
            ps.setDouble(8, material.getQualityCoefficient());
            ps.setString(9, material.getId().toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ResultSet getMaterialById(String id) throws SQLException {
        String query = "SELECT * FROM Materials WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, id);
            return ps.executeQuery();
        }
    }

    @Override
    public ResultSet getMaterialsByProject(String projectId) throws SQLException {
        String query = "SELECT DISTINCT * FROM Materials WHERE projectId = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, projectId);
            return ps.executeQuery();
        }
    }
}
