package main.java.com.kostr.controllers;

import main.java.com.kostr.dto.ComponentTypeDTO;
import main.java.com.kostr.models.ComponentType;
import main.java.com.kostr.services.ComponentTypeServiceImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ComponentTypeController {
    private ComponentTypeServiceImpl componentTypeService;

    public static final String RESET = "\033[0m";
    public static final String RED = "\033[0;31m";
    public static final String BLUE = "\033[0;34m";
    public static final String YELLOW = "\u001b[93m";

    public ComponentTypeController(ComponentTypeServiceImpl componetTypeService) {
        this.componentTypeService = componetTypeService;
    }

    public void createComponentType(ComponentTypeDTO componentTypeDTO) throws SQLException {
        if (componentTypeDTO == null) {
            System.out.println(RED + "ComponentTypeDTO is null" + RESET);
            return;
        }

        try {
            ComponentType componentType = componentTypeService.addComponentType(componentTypeDTO);
            System.out.println(YELLOW + "Component Type created successfully" + RESET);
        } catch (SQLException e) {
            System.out.println(RED + "Error creating Component Type" + RESET);
            throw e;
        }
    }

    public void deleteComponentType(String id) throws SQLException {
        if (id == null || id.isEmpty()) {
            System.out.println(RED + "Component Type ID is null or empty" + RESET);
            return;
        }

        try {
            componentTypeService.removeComponentType(id);
            System.out.println(YELLOW + "Component Type deleted successfully" + RESET);
        } catch (SQLException e) {
            System.out.println(RED + "Error deleting Component Type" + RESET);
            throw e;
        }
    }

    public ComponentTypeDTO updateComponentType(ComponentTypeDTO componentTypeDTO) throws SQLException {
        if (componentTypeDTO == null || componentTypeDTO.getId() == null) {
            System.out.println(RED + "Invalid ComponentTypeDTO provided for update" + RESET);
            return null;
        }

        try {
            ComponentType updatedComponentType = componentTypeService.updateComponentType(componentTypeDTO);
            if (updatedComponentType != null) {
                System.out.println(YELLOW + "Component Type updated successfully" + RESET);
                return ComponentTypeDTO.modelToDTO(updatedComponentType);
            } else {
                System.out.println(RED + "Component Type not found or update failed" + RESET);
                return null;
            }
        } catch (SQLException e) {
            System.out.println(RED + "Error updating Component Type" + RESET);
            throw e;
        }
    }

    public ComponentTypeDTO getComponentTypeByName(String name) throws SQLException {
        if (name == null || name.isEmpty()) {
            System.out.println(RED + "Component Name is null or empty" + RESET);
            return null;
        }

        try {
            ComponentType componentType = componentTypeService.getComponentTypeByName(name);
            if (componentType != null) {
                return ComponentTypeDTO.modelToDTO(componentType);
            } else {
                System.out.println(RED + "Component Type not found" + RESET);
                return null;
            }
        } catch (SQLException e) {
            System.out.println(RED + "Error fetching Component Type" + RESET);
            throw e;
        }
    }

    public List<ComponentTypeDTO> getAllComponentTypes() throws SQLException {
        try {
            System.out.println(BLUE + "Fetching all Component Types" + RESET);
            List<ComponentType> componentTypes = componentTypeService.getComponentTypes();
            if (componentTypes != null) {
                List<ComponentTypeDTO> componentTypeDTOList = new ArrayList<>();
                for (ComponentType componentType : componentTypes) {
                    componentTypeDTOList.add(ComponentTypeDTO.modelToDTO(componentType));
                }
                return componentTypeDTOList;
            } else {
                System.out.println(RED + "No Component Types found" + RESET);
                return null;
            }
        } catch (SQLException e) {
            System.out.println(RED + "Error fetching Component Types" + RESET);
            throw e;
        }
    }
}

