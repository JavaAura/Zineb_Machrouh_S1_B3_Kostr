package main.java.com.kostr.repositories.interfaces;

import main.java.com.kostr.dto.ProjectDTO;
import main.java.com.kostr.models.enums.ProjectStatus;

import java.sql.SQLException;

public interface ProjectRepositoryInterface {
    public void addProject(ProjectDTO project) throws SQLException;
    public void removeProject(String id) throws SQLException;
    public void updateProject(ProjectDTO project) throws SQLException;
    public void getProjectById(String id) throws SQLException;
    public void getClientProjects(String clientId) throws SQLException;
    public void getProjects() throws SQLException;
    public Integer getClientProjectsCount(String clientId) throws SQLException;
    public void addClientProject(String clientId, String projectId) throws SQLException;
    public void updateStatus(String projectId, ProjectStatus status);
}
