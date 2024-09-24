package main.java.com.kostr.repositories.interfaces;

import main.java.com.kostr.dto.QuoteDTO;
import main.java.com.kostr.models.Quote;

import java.sql.SQLException;

public interface QuoteRepositoryInterface {
    public Quote addQuote(Quote quote) throws SQLException;
    public Quote getQuoteByProject(String projectId) throws SQLException;
    public void updateStatus(String id, boolean status) throws SQLException;
    public Quote getQuote(String id) throws SQLException;
}
