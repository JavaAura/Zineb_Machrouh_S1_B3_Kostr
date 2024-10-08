package main.java.com.kostr.repositories;

import main.java.com.kostr.dto.QuoteDTO;
import main.java.com.kostr.models.Quote;
import main.java.com.kostr.repositories.interfaces.QuoteRepositoryInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class QuoteRepository implements QuoteRepositoryInterface {
    private final Connection connection;

    public QuoteRepository(Connection connection) {
        this.connection = connection;
    }

    private Quote getQuoteModel(Quote quote, PreparedStatement ps) throws SQLException{
        try(ResultSet generatedKeys = ps.getGeneratedKeys()){
            if(generatedKeys.next()){
                UUID id = (UUID) generatedKeys.getObject(1);

                return new Quote(id, quote.getProjectId(), quote.getEstimatedCost(), quote.getIssueDate(), quote.getValidityDate(), quote.isAccepted());
            }else{
                return null;
            }
        }
    }

    @Override
    public Quote addQuote(Quote quote) throws SQLException {
        String query = "INSERT INTO Quotes (projectId, estimatedCost, issueDate, validityDate) VALUES (?::uuid, ?, ? , ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, quote.getProjectId().toString());
            ps.setDouble(2, quote.getEstimatedCost());
            ps.setObject(3, quote.getIssueDate(), java.sql.Types.DATE);
            ps.setObject(4, quote.getValidityDate(), java.sql.Types.DATE);

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Failed to insert quote, no rows affected.");
            }

            return getQuoteModel(quote, ps);
        }
    }


    @Override
    public Quote getQuoteByProject(String projectId) throws SQLException {
        String query = "SELECT DISTINCT * FROM Quotes WHERE projectId = ?::uuid";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, projectId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Quote((UUID) rs.getObject("id"), (UUID) rs.getObject("projectId"), rs.getDouble("estimatedCost"), rs.getDate("issueDate").toLocalDate(), rs.getDate("validityDate").toLocalDate(), rs.getBoolean("isAccepted"));
                } else {
                    return null;
                }
            }
        }
    }


    @Override
    public void updateStatus(String id, boolean status) throws SQLException {
        String query = "UPDATE Quotes SET isAccepted = ? WHERE id = ?::uuid";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setBoolean(1, status);
            ps.setString(2, id);
            ps.executeUpdate();
        }
    }

    @Override
    public Quote getQuote(String id) throws SQLException {
        String query = "SELECT DISTINCT * FROM Quotes WHERE id = ?::uuid";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Quote((UUID) rs.getObject("id"), (UUID) rs.getObject("projectId"), rs.getDouble("estimatedCost"), rs.getDate("issueDate").toLocalDate(), rs.getDate("validityDate").toLocalDate(), rs.getBoolean("isAccepted"));
                } else {
                    return null;
                }
            }
        }
    }
}
