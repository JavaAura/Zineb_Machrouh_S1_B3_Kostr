package main.java.com.kostr.services;

import main.java.com.kostr.dto.ProjectDTO;
import main.java.com.kostr.models.Project;
import main.java.com.kostr.models.enums.ProjectStatus;
import main.java.com.kostr.repositories.interfaces.ProjectRepositoryInterface;
import main.java.com.kostr.services.interfaces.ProjectServiceInterface;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Logger;

public class ProjectService implements ProjectServiceInterface {
    private final ProjectRepositoryInterface projectRepository;
    private static final Logger logger = Logger.getLogger(ProjectService.class.getName());

    public ProjectService(ProjectRepositoryInterface projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public Project addProject(ProjectDTO project) throws SQLException {
        Project projectModel = project.dtoToModel();
        return projectRepository.addProject(projectModel);
    }


    @Override
    public ArrayList<Project> getClientProjects(String clientId) throws SQLException {
        if (clientId.isEmpty()) {
            return null;
        } else {
            return projectRepository.getClientProjects(clientId);
        }
    }

    @Override
    public ArrayList<Project> getProjects() throws SQLException {
        return projectRepository.getProjects();
    }

    @Override
    public Integer getClientProjectsCount(String clientId) throws SQLException {
        if (clientId.isEmpty()) {
            return 0;
        } else {
            return projectRepository.getClientProjectsCount(clientId);
        }
    }



    @Override
    public Project updateStatus(String projectId, ProjectStatus status) throws SQLException {
        if (projectRepository.getProjectById(projectId) == null) {
            return null;
        } else {
            return projectRepository.updateStatus(projectId, status);
        }
    }

    @Override
    public Project updateTotalCost(String projectId, double totalCost) throws SQLException {
        if (projectRepository.getProjectById(projectId) == null) {
            return null;
        } else {
            return projectRepository.updateTotalCost(projectId, totalCost);
        }
    }
}
