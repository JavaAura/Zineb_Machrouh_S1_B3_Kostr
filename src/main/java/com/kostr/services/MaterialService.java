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

}
