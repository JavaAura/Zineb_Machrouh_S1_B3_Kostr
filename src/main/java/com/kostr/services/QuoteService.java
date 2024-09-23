package main.java.com.kostr.services;

import main.java.com.kostr.dto.QuoteDTO;
import main.java.com.kostr.models.Quote;
import main.java.com.kostr.repositories.QuoteRepository;
import main.java.com.kostr.services.interfaces.QuoteServiceInterface;

import java.sql.SQLException;
import java.util.logging.Logger;

public class QuoteService implements QuoteServiceInterface {
    private final QuoteRepository quoteRepository;
    private static final Logger logger = Logger.getLogger(QuoteService.class.getName());

    public QuoteService(QuoteRepository quoteRepository) {
        this.quoteRepository = quoteRepository;
    }

    @Override
    public Quote addQuote(QuoteDTO quote) throws SQLException {
        Quote quoteModel = quote.dtoToModel();

        Quote addedQuote = quoteRepository.addQuote(quoteModel);

        if (addedQuote != null) {
            System.out.println("Quote added successfully.");
            return addedQuote;
        } else {
            return null;
        }
    }

    @Override
    public Quote getQuoteByProject(String projectId) throws SQLException {
        if (projectId.isEmpty()) {
            logger.severe("Project ID field must be filled in");
            return null;
        }

        Quote quote = quoteRepository.getQuoteByProject(projectId);
        if (quote == null) {
            logger.severe("Quote not found for the provided project ID: " + projectId);
        } else {
            logger.info("Quote found successfully.");
        }

        return quote;
    }

    @Override
    public void updateDates(String id, String issueDate, String validityDate) throws SQLException {
        if (id == null || id.isEmpty()) {
            logger.severe("ID field must be filled in");
            return;
        }

        Quote quote = quoteRepository.getQuote(id);
        if (quote == null) {
            logger.severe("Quote not found for the provided ID: " + id);
        } else {
            quoteRepository.updateDates(id, issueDate, validityDate);
            logger.info("Quote dates updated successfully.");
        }
    }

    @Override
    public void updateStatus(String id, boolean status) throws SQLException {
        if (id == null || id.isEmpty()) {
            logger.severe("ID field must be filled in");
            return;
        }

        Quote quote = quoteRepository.getQuote(id);
        if (quote == null) {
            logger.severe("Quote not found for the provided ID: " + id);
        } else {
            quoteRepository.updateStatus(id, status);
            logger.info("Quote status updated successfully.");
        }
    }

    @Override
    public Quote getQuote(String id) throws SQLException {
        if (id == null || id.isEmpty()) {
            logger.severe("ID field must be filled in");
            return null;
        }

        Quote quote = quoteRepository.getQuote(id);
        if (quote == null) {
            logger.severe("Quote not found for the provided ID: " + id);
        } else {
            logger.info("Quote found successfully.");
        }

        return quote;
    }
}
