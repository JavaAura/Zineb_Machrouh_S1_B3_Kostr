package main.java.com.kostr.controllers;

import main.java.com.kostr.dto.QuoteDTO;
import main.java.com.kostr.models.Quote;
import main.java.com.kostr.services.interfaces.QuoteServiceInterface;

import java.sql.SQLException;

public class QuoteController {

    private final QuoteServiceInterface quoteService;

    public static final String RESET = "\033[0m";
    public static final String RED = "\033[0;31m";
    public static final String YELLOW = "\u001b[93m";

    public QuoteController(QuoteServiceInterface quoteService) {
        this.quoteService = quoteService;
    }

    public void  createQuote(QuoteDTO quoteDTO) throws SQLException {
        if (quoteDTO == null) {
            System.out.println(RED + "QuoteDTO is null" + RESET);
        }

        try {
            Quote newQuote = quoteService.addQuote(quoteDTO);
            if (newQuote != null) {
                System.out.println(YELLOW + "Quote created successfully" + RESET);
            }
        } catch (SQLException e) {
            System.out.println(RED + "Error creating quote" + RESET);
            throw e;
        }
    }

    public void deleteQuote(String id) throws SQLException {
        if (id == null || id.isEmpty()) {
            System.out.println(RED + "Quote ID is null or empty" + RESET);
            return;
        }

        try {
            quoteService.updateStatus(id, false); // Assuming disabling is part of the service
            System.out.println(YELLOW + "Quote deleted successfully" + RESET);
        } catch (SQLException e) {
            System.out.println(RED + "Error deleting quote" + RESET);
            throw e;
        }
    }

    public QuoteDTO updateQuoteDates(String id, String issueDate, String validityDate) throws SQLException {
        if (id == null || id.isEmpty() || issueDate == null || validityDate == null) {
            System.out.println(RED + "Invalid data provided for quote update" + RESET);
            return null;
        }

        try {
            quoteService.updateDates(id, issueDate, validityDate);
            System.out.println(YELLOW + "Quote dates updated successfully" + RESET);
            Quote updatedQuote = quoteService.getQuote(id);
            return updatedQuote != null ? QuoteDTO.modelToDTO(updatedQuote) : null;
        } catch (SQLException e) {
            System.out.println(RED + "Error updating quote dates" + RESET);
            throw e;
        }
    }

    public QuoteDTO getQuoteByProject(String projectId) throws SQLException {
        if (projectId == null || projectId.isEmpty()) {
            System.out.println(RED + "Project ID is null or empty" + RESET);
            return null;
        }

        try {
            Quote quote = quoteService.getQuoteByProject(projectId);
            if (quote != null) {
                System.out.println(YELLOW + "Quote found for project ID: " + projectId + RESET);
                return QuoteDTO.modelToDTO(quote);
            } else {
                System.out.println(RED + "No quote found for project ID: " + projectId + RESET);
                return null;
            }
        } catch (SQLException e) {
            System.out.println(RED + "Error fetching quote for project" + RESET);
            throw e;
        }
    }

    public QuoteDTO getQuote(String id) throws SQLException {
        if (id == null || id.isEmpty()) {
            System.out.println(RED + "Quote ID is null or empty" + RESET);
            return null;
        }

        try {
            Quote quote = quoteService.getQuote(id);
            if (quote != null) {
                System.out.println(YELLOW + "Quote found for ID: " + id + RESET);
                return QuoteDTO.modelToDTO(quote);
            } else {
                System.out.println(RED + "No quote found for ID: " + id + RESET);
                return null;
            }
        } catch (SQLException e) {
            System.out.println(RED + "Error fetching quote" + RESET);
            throw e;
        }
    }

    public QuoteDTO updateQuoteStatus(String id, boolean status) throws SQLException {
        if (id == null || id.isEmpty()) {
            System.out.println(RED + "Quote ID is null or empty" + RESET);
            return null;
        }

        try {
            quoteService.updateStatus(id, status);
            System.out.println(YELLOW + "Quote status updated successfully" + RESET);
            Quote updatedQuote = quoteService.getQuote(id);
            return updatedQuote != null ? QuoteDTO.modelToDTO(updatedQuote) : null;
        } catch (SQLException e) {
            System.out.println(RED + "Error updating quote status" + RESET);
            throw e;
        }
    }
}
