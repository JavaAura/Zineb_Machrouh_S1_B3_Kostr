package main.java.com.kostr.repositories;

import main.java.com.kostr.dto.WorkforceDTO;
import main.java.com.kostr.repositories.interfaces.WorkforceRepositoryInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WorkforceRepository implements WorkforceRepositoryInterface {
    private final Connection connection;

    public WorkforceRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void addWorkforce(WorkforceDTO workforce) throws SQLException {
        String query = "INSERT INTO Workforce (name, type, vatRate, totalPrice, projectId, hourlyRate, hoursWorked, workerProductivity) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, workforce.getName());
            ps.setString(2, workforce.getType().toString());
            ps.setDouble(3, workforce.getVatRate());
            ps.setDouble(4, workforce.getTotalPrice());
            ps.setString(5, workforce.getProjectId().toString());
            ps.setDouble(6, workforce.getHourlyRate());
            ps.setDouble(7, workforce.getHoursWorked());
            ps.setDouble(8, workforce.getWorkerProductivity());
            ps.executeUpdate();
        }
    }

    @Override
    public void removeWorkforce(String id) throws SQLException {
        String query = "DELETE FROM Workforce WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, id);
            ps.executeUpdate();
        }
    }

    @Override
    public void updateWorkforce(WorkforceDTO workforce) throws SQLException {
        String query = "UPDATE Workforce SET name = ?, type = ?, vatRate = ?, totalPrice = ?, projectId = ?, hourlyRate = ?, hoursWorked = ?, workerProductivity = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, workforce.getName());
            ps.setString(2, workforce.getType().toString());
            ps.setDouble(3, workforce.getVatRate());
            ps.setDouble(4, workforce.getTotalPrice());
            ps.setString(5, workforce.getProjectId().toString());
            ps.setDouble(6, workforce.getHourlyRate());
            ps.setDouble(7, workforce.getHoursWorked());
            ps.setDouble(8, workforce.getWorkerProductivity());
            ps.setString(9, workforce.getId().toString());
            ps.executeUpdate();
        }
    }

    @Override
    public ResultSet getWorkforceById(String id) throws SQLException {
        String query = "SELECT DISTINCT * FROM Workforce WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, id);
            return ps.executeQuery();
        }
    }

    @Override
    public ResultSet getWorkforcesByProject(String projectId) throws SQLException {
        String query = "SELECT DISTINCT * FROM Workforce WHERE projectId = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, projectId);
            return ps.executeQuery();
        }
    }
}
