package main.java.com.kostr.repositories;

import main.java.com.kostr.dto.WorkforceDTO;
import main.java.com.kostr.models.Workforce;
import main.java.com.kostr.repositories.interfaces.WorkforceRepositoryInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

public class WorkforceRepository implements WorkforceRepositoryInterface {
    private final Connection connection;

    public WorkforceRepository(Connection connection) {
        this.connection = connection;
    }

    private Workforce getWorkforceModel(Workforce workforce, PreparedStatement ps) throws SQLException{
        try(ResultSet generatedKeys = ps.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                UUID id = (UUID) generatedKeys.getObject(1);

                return new Workforce(id, workforce.getName(), workforce.getType(), workforce.getVatRate(), workforce.getTotalPrice(), workforce.getProjectId(), workforce.getHourlyRate(), workforce.getHoursWorked(), workforce.getWorkerProductivity());
            }else{
                return null;
            }
        }
    }

    @Override
    public Workforce addWorkforce(Workforce workforce) throws SQLException {
        String query = "INSERT INTO Workforce (name, type, vatRate, totalPrice, projectId, hourlyRate, hoursWorked, workerProductivity) VALUES (?, ?::uuid, ?, ?, ?::uuid, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, workforce.getName());
            ps.setString(2, workforce.getType().toString());
            ps.setDouble(3, workforce.getVatRate());
            ps.setDouble(4, workforce.getTotalPrice());
            ps.setString(5, workforce.getProjectId().toString());
            ps.setDouble(6, workforce.getHourlyRate());
            ps.setDouble(7, workforce.getHoursWorked());
            ps.setDouble(8, workforce.getWorkerProductivity());

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Failed to insert workforce, no rows affected.");
            }

            return getWorkforceModel(workforce, ps);
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
    public Workforce updateWorkforce(Workforce workforce) throws SQLException {
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

            int affectedRows = ps.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Failed to update workforce, no rows affected.");
            }

            return getWorkforceModel(workforce, ps);
        }
    }

    @Override
    public Workforce getWorkforceById(String id) throws SQLException {
        String query = "SELECT DISTINCT * FROM Workforce WHERE id = ?::uuid";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Workforce((UUID) rs.getObject("id"), rs.getString("name"), UUID.fromString(rs.getString("type")), rs.getDouble("vatRate"), rs.getDouble("totalPrice"), (UUID) rs.getObject("projectId"), rs.getDouble("hourlyRate"), rs.getDouble("hoursWorked"), rs.getDouble("workerProductivity"));
                } else {
                    return null;
                }
            }
        }
    }

    @Override
    public ArrayList<Workforce> getWorkforcesByProject(String projectId) throws SQLException {
        String query = "SELECT DISTINCT * FROM Workforce WHERE projectId = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, projectId);

            try (ResultSet rs = ps.executeQuery()) {
                ArrayList<Workforce> workforces = new ArrayList<>();
                while (rs.next()) {
                    workforces.add(new Workforce((UUID) rs.getObject("id"), rs.getString("name"), UUID.fromString(rs.getString("type")), rs.getDouble("vatRate"), rs.getDouble("totalPrice"), (UUID) rs.getObject("projectId"), rs.getDouble("hourlyRate"), rs.getDouble("hoursWorked"), rs.getDouble("workerProductivity")));
                }
                return workforces;
            }
        }
    }
}
