package main.java.com.kostr.repositories.interfaces;

import main.java.com.kostr.dto.QuoteDTO;

import java.sql.SQLException;

public interface QuoteRepositoryInterface {
    public void addQuote(QuoteDTO quote) throws SQLException;
    public void getQuoteByProject(String projectId) throws SQLException;
    public void updateDates(String id, String issueDate, String validityDate) throws SQLException;
    public void updateStatus(String id, boolean status) throws SQLException;
}
