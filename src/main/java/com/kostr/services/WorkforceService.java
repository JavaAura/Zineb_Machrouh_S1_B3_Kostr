package main.java.com.kostr.services;

import main.java.com.kostr.dto.WorkforceDTO;
import main.java.com.kostr.models.Workforce;
import main.java.com.kostr.repositories.interfaces.QuoteRepositoryInterface;
import main.java.com.kostr.repositories.interfaces.WorkforceRepositoryInterface;
import main.java.com.kostr.services.interfaces.WorkforceServiceInterface;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Logger;

public class WorkforceService implements WorkforceServiceInterface {
    private final WorkforceRepositoryInterface workforceRepository;
    private static final Logger logger = Logger.getLogger(WorkforceService.class.getName());

        public WorkforceService(WorkforceRepositoryInterface workforceRepository) {
        this.workforceRepository = workforceRepository;
    }


    @Override
    public Workforce addWorkforce(WorkforceDTO workforceDTO) throws SQLException {
        Workforce workforce = workforceDTO.dtoToModel();
        logger.info("Attempting to add a new workforce.");

        Workforce addedWorkforce = workforceRepository.addWorkforce(workforce);
        if (addedWorkforce != null) {
            logger.info("Workforce added successfully.");
        } else {
            logger.severe("Failed to add workforce.");
        }
        return addedWorkforce;
    }

    @Override
    public void removeWorkforce(String id) throws SQLException {
        if (id == null || id.isEmpty()) {
            logger.severe("ID field must be filled in");
            return;
        }

        Workforce workforce = workforceRepository.getWorkforceById(id);
        if (workforce == null) {
            logger.severe("Workforce not found for the provided ID: " + id);
        } else {
            workforceRepository.removeWorkforce(id);
            logger.info("Workforce removed successfully.");
        }
    }

    @Override
    public Workforce updateWorkforce(WorkforceDTO workforceDTO) throws SQLException {
        if (workforceDTO == null || workforceRepository.getWorkforceById(workforceDTO.getId().toString()) == null) {
            logger.severe("Workforce not found for the provided ID: " + workforceDTO.getId());
            return null;
        }

        Workforce workforce = workforceDTO.dtoToModel();
        Workforce updatedWorkforce = workforceRepository.updateWorkforce(workforce);
        if (updatedWorkforce != null) {
            logger.info("Workforce updated successfully.");
        } else {
            logger.severe("Failed to update workforce.");
        }
        return updatedWorkforce;
    }

    @Override
    public Workforce getWorkforceById(String id) throws SQLException {
        if (id == null || id.isEmpty()) {
            logger.severe("ID field must be filled in");
            return null;
        }

        Workforce workforce = workforceRepository.getWorkforceById(id);
        if (workforce == null) {
            logger.severe("No workforce found for the provided ID: " + id);
        } else {
            logger.info("Workforce found for the provided ID: " + id);
        }
        return workforce;
    }

    @Override
    public ArrayList<Workforce> getWorkforcesByProject(String projectId) throws SQLException {
        if (projectId == null || projectId.isEmpty()) {
            logger.severe("Project ID field must be filled in");
            return null;
        }

        ArrayList<Workforce> workforces = workforceRepository.getWorkforcesByProject(projectId);
        if (workforces.isEmpty()) {
            logger.severe("No workforces found for the provided Project ID: " + projectId);
        } else {
            logger.info(workforces.size() + " workforces found for the Project ID: " + projectId);
        }
        return workforces;
    }
}
