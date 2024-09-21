package main.java.com.kostr.services;

import main.java.com.kostr.dto.QuoteDTO;
import main.java.com.kostr.models.Quote;
import main.java.com.kostr.repositories.interfaces.QuoteRepositoryInterface;
import main.java.com.kostr.services.interfaces.QuoteServiceInterface;

import java.sql.SQLException;
import java.util.logging.Logger;

public class QuoteService implements QuoteServiceInterface {
    private final QuoteRepositoryInterface serviceRepository;
    private static final Logger logger = Logger.getLogger(QuoteService.class.getName());

    public QuoteService(QuoteRepositoryInterface serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    @Override
    public Quote addQuote(QuoteDTO quote) throws SQLException {
        Quote quoteModel = quote.dtoToModel();
        logger.info("Quote added successfully");
        return serviceRepository.addQuote(quoteModel);
    }

    @Override
    public Quote getQuoteByProject(String projectId) throws SQLException {
        if (projectId.isEmpty()) {
            logger.severe("Project ID field must be filled in");
            return null;
        } else {
            return serviceRepository.getQuoteByProject(projectId);
        }
    }

    @Override
    public void updateDates(String id, String issueDate, String validityDate) throws SQLException {
        if (id.isEmpty() || issueDate.isEmpty() || validityDate.isEmpty()) {
            logger.severe("ID, issue date and validity date fields must be filled in");
        } else {
            serviceRepository.updateDates(id, issueDate, validityDate);
            logger.info("Dates updated successfully");
        }
    }

    @Override
    public void updateStatus(String id, boolean status) throws SQLException {
        if (id.isEmpty()) {
            logger.severe("ID field must be filled in");
        } else {
            serviceRepository.updateStatus(id, status);
            logger.info("Status updated successfully");
        }
    }
}
