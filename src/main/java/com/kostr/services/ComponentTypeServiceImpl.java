package main.java.com.kostr.services;

import main.java.com.kostr.dto.ComponentTypeDTO;
import main.java.com.kostr.models.ComponentType;
import main.java.com.kostr.repositories.interfaces.ComponentTypeRepository;
import main.java.com.kostr.repositories.interfaces.MaterialRepositoryInterface;
import main.java.com.kostr.services.interfaces.ComponetTypeService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Logger;

public class ComponentTypeServiceImpl implements ComponetTypeService {
    private ComponentTypeRepository componentTypeRepository;
    private static final Logger logger = Logger.getLogger(ComponentTypeServiceImpl.class.getName());

    public ComponentTypeServiceImpl(ComponentTypeRepository componentTypeRepository) {
        this.componentTypeRepository = componentTypeRepository;
    }

    @Override
    public ComponentType addComponentType(ComponentTypeDTO componentType) throws SQLException {
        ComponentType componentTypeModel = componentType.dtoToModel();
        logger.info("ComponentType added successfully");
        return componentTypeRepository.addComponentType(componentTypeModel);
    }

    @Override
    public void removeComponentType(String id) throws SQLException {
        if (id.isEmpty()) {
            logger.severe("ID field must be filled in");
        } else {
            if (componentTypeRepository.getComponentTypeById(id) == null) {
                logger.severe("ComponentType not found");
            } else {
                componentTypeRepository.removeComponentType(id);
                logger.info("ComponentType removed successfully");
            }
        }
    }

    @Override
    public ComponentType updateComponentType(ComponentTypeDTO componentType) throws SQLException {
        if (componentTypeRepository.getComponentTypeById(componentType.getId().toString()) == null) {
            logger.severe("ComponentType not found");
            return null;
        } else {
            ComponentType componentTypeModel = componentType.dtoToModel();
            logger.info("ComponentType updated successfully");
            return componentTypeRepository.updateComponentType(componentTypeModel);
        }

    }

    @Override
    public ArrayList<ComponentType> getComponentTypes() throws SQLException {
        return componentTypeRepository.getComponentTypes();
    }

    @Override
    public ComponentType getComponentTypeByName(String name) throws SQLException {
        if (name.isEmpty()) {
            logger.severe("Name field must be filled in");
            return null;
        } else {
            return componentTypeRepository.getComponentTypeByName(name);
        }
    }
}
