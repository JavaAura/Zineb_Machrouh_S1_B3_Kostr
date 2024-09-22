package main.java.com.kostr.dto;

import main.java.com.kostr.models.Project;
import main.java.com.kostr.models.enums.ProjectStatus;
import main.java.com.kostr.models.enums.ProjectType;

import java.util.UUID;

public class ProjectDTO {
    private UUID id;
    private String name;
    private double profitMargin;
    private double totalCost;
    private double surfaceArea;
    private ProjectType type;
    private ProjectStatus status;
    private UUID clientId;

    public ProjectDTO() {}

    public ProjectDTO(UUID id, String name, double profitMargin, double totalCost, double surfaceArea, ProjectType type, ProjectStatus status, UUID clientId) {
        this.id = id;
        this.name = name;
        this.profitMargin = profitMargin;
        this.totalCost = totalCost;
        this.surfaceArea = surfaceArea;
        this.type = type;
        this.status = status;
        this.clientId = clientId;
    }

    public ProjectDTO(String projectName, double projectProfitMargin, double projectSurfaceArea, String projectType, UUID clientId) {
        this.name = projectName;
        this.profitMargin = projectProfitMargin;
        this.surfaceArea = projectSurfaceArea;
        this.type = ProjectType.valueOf(projectType);
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

    public double getSurfaceArea() {
        return surfaceArea;
    }
    public void setSurfaceArea(double surfaceArea) {
        this.surfaceArea = surfaceArea;
    }

    public ProjectType getType() {
        return type;
    }
    public void setType(ProjectType type) {
        this.type = type;
    }

    public String[] getAttributes() {
        return new String[]{id.toString(), name, String.valueOf(profitMargin), String.valueOf(totalCost), String.valueOf(surfaceArea), type.toString(), status.toString(), clientId.toString()};
    }

    @Override
    public String toString() {
        return "Project : {" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", profitMargin=" + profitMargin +
                ", totalCost=" + totalCost +
                ", surfaceArea=" + surfaceArea +
                ", type=" + type +
                ", status=" + status +
                ", clientId=" + clientId +
                '}'+ "\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProjectDTO that = (ProjectDTO) o;

        if (Double.compare(that.profitMargin, profitMargin) != 0) return false;
        if (Double.compare(that.totalCost, totalCost) != 0) return false;
        if (Double.compare(that.surfaceArea, surfaceArea) != 0) return false;
        if (!id.equals(that.id)) return false;
        if (!name.equals(that.name)) return false;
        if (type != that.type) return false;
        if (status != that.status) return false;
        return clientId.equals(that.clientId);
    }

    public Project dtoToModel() {
        return new Project(id, name, profitMargin, totalCost, surfaceArea, type, status, clientId);
    }

    public static ProjectDTO modelToDTO(Project project) {
        return new ProjectDTO(
                project.getId(),
                project.getName(),
                project.getProfitMargin(),
                project.getTotalCost(),
                project.getSurfaceArea(),
                project.getType(),
                project.getStatus(),
                project.getClientId());
    }
}
