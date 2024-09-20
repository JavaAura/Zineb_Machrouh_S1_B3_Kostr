package main.java.com.kostr.services;

import main.java.com.kostr.dto.ProjectDTO;
import main.java.com.kostr.models.enums.ProjectStatus;
import main.java.com.kostr.repositories.MaterialRepository;
import main.java.com.kostr.repositories.ProjectRepository;
import main.java.com.kostr.services.interfaces.ProjectServiceInterface;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

public class ProjectService implements ProjectServiceInterface {
    private final Connection connection;
    private final ProjectRepository projectRepository;

    private static final Logger logger = Logger.getLogger(MaterialService.class.getName());

    public ProjectService(Connection connection, ProjectRepository projectRepository) {
        this.connection = connection;
        this.projectRepository = projectRepository;
    }

    @Override
    public void addProject(ProjectDTO project) throws SQLException {
        projectRepository.addProject(project);
        logger.info("Project added successfully");
    }

    @Override
    public void removeProject(String id) throws SQLException {
        if (projectRepository.getProjectById(id).next()) {
            projectRepository.removeProject(id);
            logger.info("Project removed successfully");
        } else {
            logger.warning("Project not found");
        }
    }

    @Override
    public void updateProject(ProjectDTO project) throws SQLException {
        if (projectRepository.getProjectById(project.getId().toString()).next()) {
            projectRepository.updateProject(project);
            logger.info("Project updated successfully");
        } else {
            logger.warning("Project not found");
        }
    }

    @Override
    public ResultSet getProjectById(String id) throws SQLException {
        if (id.isEmpty()) {
            logger.severe("ID field must be filled in");
        } else {
            projectRepository.getProjectById(id);
        }
    }

    @Override
    public ResultSet getClientProjects(String clientId) throws SQLException {

    }

    @Override
    public ResultSet getProjects() throws SQLException {

    }

    @Override
    public Integer getClientProjectsCount(String clientId) throws SQLException {
        return 0;
    }

    @Override
    public void addClientProject(String clientId, String projectId) throws SQLException {

    }

    @Override
    public void updateStatus(String projectId, ProjectStatus status) {

    }
}
