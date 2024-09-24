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



}
