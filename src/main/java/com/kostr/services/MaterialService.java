package main.java.com.kostr.services;

import main.java.com.kostr.dto.MaterialDTO;
import main.java.com.kostr.models.Material;
import main.java.com.kostr.repositories.MaterialRepository;
import main.java.com.kostr.repositories.interfaces.MaterialRepositoryInterface;
import main.java.com.kostr.services.interfaces.MaterialServiceInterface;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Logger;

public class MaterialService implements MaterialServiceInterface {
    private MaterialRepositoryInterface materialRepository;
    private static final Logger logger = Logger.getLogger(MaterialService.class.getName());

    public MaterialService(MaterialRepositoryInterface materialRepository) {
        this.materialRepository = materialRepository;
    }

    @Override
    public Material addMaterial(MaterialDTO material) throws SQLException {
        Material materialModel = material.dtoToModel();
        logger.info("Material added successfully");
        return materialRepository.addMaterial(materialModel);
    }

    @Override
    public void removeMaterial(String id) throws SQLException {
        if (id.isEmpty()) {
            logger.severe("ID field must be filled in");
        } else {
            if (materialRepository.getMaterialById(id) == null) {
                logger.severe("Material not found");
            } else {
                materialRepository.removeMaterial(id);
                logger.info("Material removed successfully");
            }
        }
    }

    @Override
    public Material updateMaterial(MaterialDTO material) throws SQLException {
        if (materialRepository.getMaterialById(material.getId().toString()) == null) {
            logger.severe("Material not found");
            return null;
        } else {
            Material materialModel = material.dtoToModel();
            logger.info("Material updated successfully");
            return materialRepository.updateMaterial(materialModel);
        }
    }

    @Override
    public Material getMaterialById(String id) throws SQLException {
        if (id.isEmpty()) {
            logger.severe("ID field must be filled in");
            return null;
        } else {
            return materialRepository.getMaterialById(id);
        }
    }

    @Override
    public ArrayList<Material> getMaterialsByProject(String projectId) throws SQLException {
        if (projectId.isEmpty()) {
            logger.severe("Project ID field must be filled in");
            return null;
        } else {
            return materialRepository.getMaterialsByProject(projectId);
        }
    }
}
