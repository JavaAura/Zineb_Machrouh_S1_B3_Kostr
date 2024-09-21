package main.java.com.kostr.services.interfaces;

import main.java.com.kostr.dto.QuoteDTO;
import main.java.com.kostr.models.Quote;

import java.sql.SQLException;

public interface QuoteServiceInterface {
    public Quote addQuote(QuoteDTO quote) throws SQLException;
    public Quote getQuoteByProject(String projectId) throws SQLException;
    public void updateDates(String id, String issueDate, String validityDate) throws SQLException;
    public void updateStatus(String id, boolean status) throws SQLException;
}
