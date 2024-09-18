package main.java.com.kostr.models;

import main.java.com.kostr.types.ProjectStatus;

import java.util.UUID;

public class Project {
    private UUID id;
    private String name;
    private double profitMargin;
    private double totalCost;
    private ProjectStatus status;
    private UUID clientId;

    public Project() {}

    public Project(UUID id, String name, double profitMargin, double totalCost, ProjectStatus status, UUID clientId) {
        this.id = id;
        this.name = name;
        this.profitMargin = profitMargin;
        this.totalCost = totalCost;
        this.status = status;
        this.clientId = clientId;
    }

    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public double getProfitMargin() {
        return profitMargin;
    }
    public void setProfitMargin(double profitMargin) {
        this.profitMargin = profitMargin;
    }

    public double getTotalCost() {
        return totalCost;
    }
    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public ProjectStatus getStatus() {
        return status;
    }
    public void setStatus(ProjectStatus status) {
        this.status = status;
    }

    public UUID getClientId() {
        return clientId;
    }
    public void setClientId(UUID clientId) {
        this.clientId = clientId;
    }
}
