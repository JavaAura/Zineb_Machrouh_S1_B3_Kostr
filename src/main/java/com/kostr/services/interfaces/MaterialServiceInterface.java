package main.java.com.kostr.services.interfaces;

import main.java.com.kostr.dto.MaterialDTO;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface MaterialServiceInterface {
    public void addMaterial(MaterialDTO material) throws SQLException;
    public void removeMaterial(String id) throws SQLException;
    public void updateMaterial(MaterialDTO material) throws SQLException;
    public ResultSet getMaterialById(String id) throws SQLException;
    public ResultSet getMaterialsByProject(String projectId) throws SQLException;
}
