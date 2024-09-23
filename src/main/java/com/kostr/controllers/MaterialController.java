package main.java.com.kostr.controllers;

import main.java.com.kostr.dto.MaterialDTO;
import main.java.com.kostr.models.Material;
import main.java.com.kostr.services.interfaces.MaterialServiceInterface;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MaterialController {
    private MaterialServiceInterface materialService;

    public static final String RESET = "\033[0m";
    public static final String RED = "\033[0;31m";
    public static final String YELLOW = "\u001b[93m";

    public MaterialController(MaterialServiceInterface materialService) {
        this.materialService = materialService;
    }

    public void createMaterial(MaterialDTO materialDTO) throws SQLException {
        if (materialDTO == null) {
            System.out.println(RED + "MaterialDTO is null" + RESET);
            //return null;
        }

        try {
            Material material = materialService.addMaterial(materialDTO);
            System.out.println(YELLOW + "Material created successfully" + RESET);
            //return MaterialDTO.modelToDTO(material);
        } catch (SQLException e) {
            System.out.println(RED + "Error creating material" + RESET);
            throw e;
        }
    }

    public void deleteMaterial(String id) throws SQLException {
        if (id == null || id.isEmpty()) {
            System.out.println(RED + "Material ID is null or empty" + RESET);
            return;
        }

        try {
            materialService.removeMaterial(id);
            System.out.println(YELLOW + "Material deleted successfully" + RESET);
        } catch (SQLException e) {
            System.out.println(RED + "Error deleting material" + RESET);
            throw e;
        }
    }

    public MaterialDTO updateMaterial(MaterialDTO materialDTO) throws SQLException {
        if (materialDTO == null || materialDTO.getId() == null) {
            System.out.println(RED + "Invalid MaterialDTO provided for update" + RESET);
            return null;
        }

        try {
            Material updatedMaterial = materialService.updateMaterial(materialDTO);
            if (updatedMaterial != null) {
                System.out.println(YELLOW + "Material updated successfully" + RESET);
                return MaterialDTO.modelToDTO(updatedMaterial);
            } else {
                System.out.println(RED + "Material not found or update failed" + RESET);
                return null;
            }
        } catch (SQLException e) {
            System.out.println(RED + "Error updating material" + RESET);
            throw e;
        }
    }

    public MaterialDTO getMaterialById(String id) throws SQLException {
        if (id == null || id.isEmpty()) {
            System.out.println(RED + "Material ID is null or empty" + RESET);
            return null;
        }

        try {
            Material material = materialService.getMaterialById(id);
            if (material != null) {
                return MaterialDTO.modelToDTO(material);
            } else {
                System.out.println(RED + "Material not found" + RESET);
                return null;
            }
        } catch (SQLException e) {
            System.out.println(RED + "Error fetching material" + RESET);
            throw e;
        }
    }

    public List<MaterialDTO> getMaterialsByProject(String projectId) throws SQLException {
        if (projectId == null || projectId.isEmpty()) {
            System.out.println(RED + "Project ID is null or empty" + RESET);
            return null;
        }

        try {
            System.out.println("Fetching materials for project with ID: " + projectId);
            ArrayList<Material> materials = materialService.getMaterialsByProject(projectId);
            if (materials != null) {
                List<MaterialDTO> materialDTOList = new ArrayList<>();
                for (Material material : materials) {
                    materialDTOList.add(MaterialDTO.modelToDTO(material));
                }
                return materialDTOList;
            } else {
                System.out.println(RED + "No materials found for project" + RESET);
                return null;
            }
        } catch (SQLException e) {
            System.out.println(RED + "Error fetching materials for project" + RESET);
            throw e;
        }
    }
}
