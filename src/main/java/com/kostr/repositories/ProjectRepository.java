package main.java.com.kostr.repositories;

import main.java.com.kostr.dto.ProjectDTO;
import main.java.com.kostr.models.enums.ProjectStatus;
import main.java.com.kostr.repositories.interfaces.ProjectRepositoryInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProjectRepository implements ProjectRepositoryInterface {
    private final Connection connection;

    public ProjectRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void addProject(ProjectDTO project) throws SQLException {
        String query = "INSERT INTO Projects (name, profitMargin, totalCost, surfaceArea, status, clientId) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, project.getName());
            ps.setDouble(2, project.getProfitMargin());
            ps.setDouble(3, project.getTotalCost());
            ps.setDouble(4, project.getSurfaceArea());
            ps.setObject(5, project.getStatus());
            ps.setString(6, project.getClientId().toString());
            ps.executeUpdate();
        }
    }

    @Override
    public void removeProject(String id) throws SQLException {
        String query = "DELETE FROM Projects WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, id);
            ps.executeUpdate();
        }
    }

    @Override
    public void updateProject(ProjectDTO project) throws SQLException {
        String query = "UPDATE Projects SET name = ?, profitMargin = ?, totalCost = ?, surfaceArea = ?, status = ?, clientId = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, project.getName());
            ps.setDouble(2, project.getProfitMargin());
            ps.setDouble(3, project.getTotalCost());
            ps.setDouble(3, project.getTotalCost());
            ps.setObject(4, project.getStatus());
            ps.setString(5, project.getClientId().toString());
            ps.setString(6, project.getId().toString());
            ps.executeUpdate();
        }
    }

    @Override
    public void getProjectById(String id) throws SQLException {
        String query = "SELECT * FROM Projects WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, id);
            ps.executeQuery();
        }
    }

    @Override
    public void getClientProjects(String clientId) throws SQLException {
        String query = "SELECT * FROM Projects WHERE clientId = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, clientId);
            ps.executeQuery();
        }
    }

    @Override
    public void getProjects() throws SQLException {
        String query = "SELECT DISTINCT * FROM Projects";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.executeQuery();
        }
    }

    @Override
    public Integer getClientProjectsCount(String clientId) throws SQLException {
        String query = "SELECT COUNT(*) FROM Projects WHERE clientId = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, clientId);
            return ps.executeQuery().getInt(1);
        }
    }

    @Override
    public void addClientProject(String clientId, String projectId) throws SQLException {
        String query = "UPDATE Projects SET clientId = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, clientId);
            ps.setString(2, projectId);
            ps.executeUpdate();
        }
    }

    @Override
    public void updateStatus(String projectId, ProjectStatus status) {
        String query = "UPDATE Projects SET status = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setObject(1, status);
            ps.setString(2, projectId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
