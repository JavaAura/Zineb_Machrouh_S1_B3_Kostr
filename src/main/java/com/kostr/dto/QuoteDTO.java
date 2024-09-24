package main.java.com.kostr.dto;

import main.java.com.kostr.models.Quote;

import java.time.LocalDate;
import java.util.UUID;

public class QuoteDTO {
    private UUID id;
    private UUID projectId;
    private double estimatedCost;
    private LocalDate issueDate;
    private LocalDate validityDate;
    private boolean isAccepted;

    public QuoteDTO() {}

    public QuoteDTO(UUID id, UUID projectId, double estimatedCost, LocalDate issueDate, LocalDate validityDate, boolean isAccepted) {
        this.id = id;
        this.projectId = projectId;
        this.estimatedCost = estimatedCost;
        this.issueDate = issueDate;
        this.validityDate = validityDate;
        this.isAccepted = isAccepted;
    }

    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getProjectId() {
        return projectId;
    }
    public void setProjectId(UUID projectId) {
        this.projectId = projectId;
    }

    public double getEstimatedCost() {
        return estimatedCost;
    }
    public void setEstimatedCost(double estimatedCost) {
        this.estimatedCost = estimatedCost;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }
    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public LocalDate getValidityDate() {
        return validityDate;
    }
    public void setValidityDate(LocalDate validityDate) {
        this.validityDate = validityDate;
    }

    public boolean isAccepted() {
        return isAccepted;
    }
    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }

    public String[] getAttributes() {
        return new String[]{id.toString(), projectId.toString(), String.valueOf(estimatedCost), issueDate.toString(), validityDate.toString(), String.valueOf(isAccepted)};
    }

    @Override
    public String toString() {
        return "QuoteDTO{" +
                "id=" + id +
                ", projectId=" + projectId +
                ", estimatedCost=" + estimatedCost + "$"+
                ", issueDate=" + issueDate +
                ", validityDate=" + validityDate +
                ", isAccepted=" + isAccepted +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        QuoteDTO quoteDTO = (QuoteDTO) o;

        if (Double.compare(quoteDTO.estimatedCost, estimatedCost) != 0) return false;
        if (isAccepted != quoteDTO.isAccepted) return false;
        if (!id.equals(quoteDTO.id)) return false;
        if (!projectId.equals(quoteDTO.projectId)) return false;
        if (!issueDate.equals(quoteDTO.issueDate)) return false;
        return validityDate.equals(quoteDTO.validityDate);
    }

    public Quote dtoToModel() {
        return new Quote(id, projectId, estimatedCost, issueDate, validityDate, isAccepted);
    }

    public static QuoteDTO modelToDTO(Quote quote) {
        return new QuoteDTO(quote.getId(), quote.getProjectId(), quote.getEstimatedCost(), quote.getIssueDate(), quote.getValidityDate(), quote.isAccepted());
    }
}
