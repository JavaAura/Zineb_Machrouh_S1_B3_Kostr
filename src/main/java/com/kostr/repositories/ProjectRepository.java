package main.java.com.kostr.repositories;

import main.java.com.kostr.dto.ProjectDTO;
import main.java.com.kostr.models.Project;
import main.java.com.kostr.models.enums.ProjectStatus;
import main.java.com.kostr.models.enums.ProjectType;
import main.java.com.kostr.repositories.interfaces.ProjectRepositoryInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

public class ProjectRepository implements ProjectRepositoryInterface {
    private final Connection connection;

    public ProjectRepository(Connection connection) {
        this.connection = connection;
    }

    private Project getProjectModel(Project project, PreparedStatement ps) throws SQLException {
        try(ResultSet generatedKeys = ps.getGeneratedKeys()){
            if(generatedKeys.next()){
                UUID id = (UUID) generatedKeys.getObject(1);

                return new Project(id, project.getName(), project.getProfitMargin(), project.getTotalCost(), project.getSurfaceArea(), project.getType(), project.getStatus(), project.getClientId());
            }else {
                return null;
            }
        }
    }

    @Override
    public Project addProject(Project project) throws SQLException {
        String query = "INSERT INTO Projects (name, profitMargin, surfaceArea, type, clientId) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, project.getName());
            ps.setDouble(2, project.getProfitMargin());
            ps.setDouble(3, project.getSurfaceArea());
            ps.setObject(4, project.getType(), java.sql.Types.OTHER);
            ps.setObject(5, project.getClientId());

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Failed to insert project, no rows affected.");
            }
            return getProjectModel(project, ps);
        }
    }

    @Override
    public void removeProject(String id) throws SQLException {
        String query = "UPDATE Projects SET status = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setObject(1, ProjectStatus.CANCELLED);
            ps.setString(2, id);
            ps.executeUpdate();
        }
    }

    @Override
    public Project updateProject(Project project) throws SQLException {
        String query = "UPDATE Projects SET name = ?, profitMargin = ?, totalCost = ?, surfaceArea = ?, type = ?, status = ?, clientId = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, project.getName());
            ps.setDouble(2, project.getProfitMargin());
            ps.setDouble(3, project.getTotalCost());
            ps.setDouble(4, project.getSurfaceArea());
            ps.setObject(5, project.getType());
            ps.setObject(6, project.getStatus());
            ps.setString(7, project.getClientId().toString());
            ps.setString(8, project.getId().toString());

            int affectedRows = ps.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Failed to update project, no rows affected.");
            }
            return getProjectModel(project, ps);
        }
    }

    @Override
    public Project getProjectById(String id) throws SQLException {
        String query = "SELECT * FROM Projects WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, id);

            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    return new Project(UUID.fromString(rs.getString("id")), rs.getString("name"), rs.getDouble("profitMargin"), rs.getDouble("totalCost"), rs.getDouble("surfaceArea"), ProjectType.valueOf(rs.getString("type")), ProjectStatus.valueOf(rs.getString("status")), UUID.fromString(rs.getString("clientId")));
                }else{
                    return null;
                }
            }
        }
    }

    @Override
    public ArrayList<Project> getClientProjects(String clientId) throws SQLException {
        String query = "SELECT * FROM Projects WHERE clientId = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, clientId);

            try(ResultSet rs = ps.executeQuery()){
                ArrayList<Project> projects = new ArrayList<>();
                while(rs.next()){
                    projects.add(new Project(UUID.fromString(rs.getString("id")), rs.getString("name"), rs.getDouble("profitMargin"), rs.getDouble("totalCost"), rs.getDouble("surfaceArea"),ProjectType.valueOf(rs.getString("type")), ProjectStatus.valueOf(rs.getString("status")), UUID.fromString(rs.getString("clientId"))));
                }
                return projects;
            }
        }
    }

    @Override
    public ArrayList<Project> getProjects() throws SQLException {
        String query = "SELECT DISTINCT * FROM Projects";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            try(ResultSet rs = ps.executeQuery()){
                ArrayList<Project> projects = new ArrayList<>();
                while(rs.next()){
                    projects.add(new Project(UUID.fromString(rs.getString("id")), rs.getString("name"), rs.getDouble("profitMargin"), rs.getDouble("totalCost"), rs.getDouble("surfaceArea"),ProjectType.valueOf(rs.getString("type")), ProjectStatus.valueOf(rs.getString("status")), UUID.fromString(rs.getString("clientId"))));
                }
                return projects;
            }
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
    public Project addClientProject(String clientId, String projectId) throws SQLException {
        String query = "UPDATE Projects SET clientId = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, clientId);
            ps.setString(2, projectId);

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Failed to add client to project, no rows affected.");
            }

            return getProjectById(projectId);
        }
    }

    @Override
    public Project updateStatus(String projectId, ProjectStatus status) throws SQLException{
        String query = "UPDATE Projects SET status = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setObject(1, status);
            ps.setString(2, projectId);

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Failed to update project status, no rows affected.");
            }

            return getProjectById(projectId);
        }
    }


}
