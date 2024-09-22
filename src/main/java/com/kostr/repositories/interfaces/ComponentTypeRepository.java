package main.java.com.kostr.repositories.interfaces;

import main.java.com.kostr.models.ComponentType;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ComponentTypeRepository {
    public ComponentType addComponentType(ComponentType componentType) throws SQLException;
    public void removeComponentType(String id) throws SQLException;
    public ComponentType updateComponentType(ComponentType componentType) throws SQLException;
    public ArrayList<ComponentType> getComponentTypes() throws SQLException;
    public ComponentType getComponentTypeById(String id) throws SQLException;
}
