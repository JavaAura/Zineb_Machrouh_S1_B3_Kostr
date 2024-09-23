package main.java.com.kostr.repositories.interfaces;

import main.java.com.kostr.dto.ProjectDTO;
import main.java.com.kostr.models.Project;
import main.java.com.kostr.models.enums.ProjectStatus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface ProjectRepositoryInterface {
    public Project addProject(Project project) throws SQLException;
    public void removeProject(String id) throws SQLException;
    public Project updateProject(Project project) throws SQLException;
    public Project getProjectById(String id) throws SQLException;
    public ArrayList<Project> getClientProjects(String clientId) throws SQLException;
    public ArrayList<Project> getProjects() throws SQLException;
    public Integer getClientProjectsCount(String clientId) throws SQLException;
    public Project addClientProject(String clientId, String projectId) throws SQLException;
    public Project updateStatus(String projectId, ProjectStatus status) throws SQLException;
    public Project updateTotalCost(String projectId, double totalCost) throws SQLException;
}
