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



    @Override
    public Project addProject(Project project) throws SQLException {
        String query = "INSERT INTO Projects (name, profitMargin, surfaceArea, type, clientId) VALUES (?, ?, ?, ?, ?) RETURNING *";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, project.getName());
            ps.setDouble(2, project.getProfitMargin());
            ps.setDouble(3, project.getSurfaceArea());
            ps.setObject(4, project.getType(), java.sql.Types.OTHER);
            ps.setObject(5, project.getClientId());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Project(
                            UUID.fromString(rs.getString("id")),
                            rs.getString("name"),
                            rs.getDouble("profitMargin"),
                            rs.getDouble("totalCost"),
                            rs.getDouble("surfaceArea"),
                            ProjectType.valueOf(rs.getString("type")),
                            ProjectStatus.valueOf(rs.getString("status")),
                            UUID.fromString(rs.getString("clientId"))
                    );
                } else {
                    throw new SQLException("Failed to retrieve inserted project.");
                }
            }
        }
    }


    @Override
    public Project getProjectById(String id) throws SQLException {
        String query = "SELECT * FROM Projects WHERE id = ?::uuid LIMIT 1";
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
        String query = "SELECT * FROM Projects WHERE clientId = ?::uuid";
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
        String query = "SELECT DISTINCT * FROM Projects LIMIT 10";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            try(ResultSet rs = ps.executeQuery()){
                ArrayList<Project> projects = new ArrayList<>();
                while(rs.next()){
                    UUID id = rs.getString("id") != null ? UUID.fromString(rs.getString("id")) : null;
                    UUID clientId = rs.getString("clientId") != null ? UUID.fromString(rs.getString("clientId")) : null;

                    projects.add(new Project(
                            id,
                            rs.getString("name"),
                            rs.getDouble("profitMargin"),
                            rs.getDouble("totalCost"),
                            rs.getDouble("surfaceArea"),
                            ProjectType.valueOf(rs.getString("type")),
                            ProjectStatus.valueOf(rs.getString("status")),
                            clientId
                    ));                }
                return projects;
            }
        }
    }

    @Override
    public Integer getClientProjectsCount(String clientId) throws SQLException {
        String query = "SELECT COUNT(*) FROM Projects WHERE clientId = ?::uuid";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, clientId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                } else {
                    return 0;
                }
            }
        }
    }


    @Override
    public Project updateStatus(String projectId, ProjectStatus status) throws SQLException{
        String query = "UPDATE Projects SET status = ? WHERE id = ?::uuid";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setObject(1, status, java.sql.Types.OTHER);
            ps.setString(2, projectId);

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Failed to update project status, no rows affected.");
            }

            return getProjectById(projectId);
        }
    }

    @Override
    public Project updateTotalCost(String projectId, double totalCost) throws SQLException {
        String query = "UPDATE Projects SET totalCost = ? WHERE id = ?::uuid";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setDouble(1, totalCost);
            ps.setString(2, projectId);

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Failed to update project total cost, no rows affected.");
            }

            return getProjectById(projectId);
        }
    }

}
