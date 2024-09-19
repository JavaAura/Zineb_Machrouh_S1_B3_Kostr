package main.java.com.kostr.repositories;

import main.java.com.kostr.dto.QuoteDTO;
import main.java.com.kostr.repositories.interfaces.QuoteRepositoryInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class QuoteRepository implements QuoteRepositoryInterface {
    private final Connection connection;

    public QuoteRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void addQuote(QuoteDTO quote) throws SQLException {
        String query = "INSERT INTO Quotes (projectId, estimatedCost) VALUES (?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, quote.getProjectId().toString());
            ps.setDouble(2, quote.getEstimatedCost());
            ps.executeUpdate();
        }
    }


    @Override
    public void getQuoteByProject(String projectId) throws SQLException {
        String query = "SELECT DISTINCT * FROM Quotes WHERE projectId = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, projectId);
            ps.executeQuery();
        }
    }

    @Override
    public void updateDates(String id, String issueDate, String validityDate) throws SQLException {
        String query = "UPDATE Quotes SET issueDate = ?, validityDate = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setObject(1, issueDate);
            ps.setObject(2, validityDate);
            ps.setString(3, id);
            ps.executeUpdate();
        }
    }

    @Override
    public void updateStatus(String id, boolean status) throws SQLException {
        String query = "UPDATE Quotes SET isAccepted = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setBoolean(1, status);
            ps.setString(2, id);
            ps.executeUpdate();
        }
    }
}
