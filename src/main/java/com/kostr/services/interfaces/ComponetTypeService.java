package main.java.com.kostr.services.interfaces;

import main.java.com.kostr.dto.ComponentTypeDTO;
import main.java.com.kostr.models.ComponentType;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ComponetTypeService {
    public ComponentType addComponentType(ComponentTypeDTO componentType) throws SQLException;
    public void removeComponentType(String id) throws SQLException;
    public ComponentType updateComponentType(ComponentTypeDTO componentType) throws SQLException;
    public ArrayList<ComponentType> getComponentTypes() throws SQLException;
    public ComponentType getComponentTypeByName(String name) throws SQLException;
}
