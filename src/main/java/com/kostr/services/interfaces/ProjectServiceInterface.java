package main.java.com.kostr.services.interfaces;

import main.java.com.kostr.dto.ProjectDTO;
import main.java.com.kostr.models.Project;
import main.java.com.kostr.models.enums.ProjectStatus;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ProjectServiceInterface {
    public Project addProject(ProjectDTO project) throws SQLException;
    public ArrayList<Project> getClientProjects(String clientId) throws SQLException;
    public ArrayList<Project> getProjects() throws SQLException;
    public Integer getClientProjectsCount(String clientId) throws SQLException;
    public Project updateStatus(String projectId, ProjectStatus status) throws SQLException;
    public Project updateTotalCost(String projectId, double totalCost) throws SQLException;
}
