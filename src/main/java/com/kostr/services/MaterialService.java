package main.java.com.kostr.services;

import main.java.com.kostr.dto.MaterialDTO;
import main.java.com.kostr.repositories.MaterialRepository;
import main.java.com.kostr.services.interfaces.MaterialServiceInterface;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

public class MaterialService implements MaterialServiceInterface {
    private final Connection connection;
    private MaterialRepository materialRepository;

    private static final Logger logger = Logger.getLogger(MaterialService.class.getName());

    public MaterialService(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void addMaterial(MaterialDTO material) throws SQLException {
        materialRepository.addMaterial(material);
        logger.info("Material added successfully");
    }

    @Override
    public void removeMaterial(String id) throws SQLException {
        if (materialRepository.getMaterialById(id).next()) {
            materialRepository.removeMaterial(id);
            logger.info("Material removed successfully");
        } else {
            logger.warning("Material not found");
        }
    }

    @Override
    public void updateMaterial(MaterialDTO material) throws SQLException {
        if (materialRepository.getMaterialById(material.getId().toString()).next()) {
            materialRepository.updateMaterial(material);
            logger.info("Material updated successfully");
        } else {
            logger.warning("Material not found");
        }
    }

    @Override
    public ResultSet getMaterialById(String id) throws SQLException {
        if (id.isEmpty()) {
            logger.severe("ID field must be filled in");
            return null;
        } else {
            return materialRepository.getMaterialById(id);
        }
    }

    @Override
    public ResultSet getMaterialsByProject(String projectId) throws SQLException {
        if (projectId.isEmpty()) {
            logger.severe("Project ID field must be filled in");
            return null;
        } else {
            return materialRepository.getMaterialsByProject(projectId);
        }
    }
}
