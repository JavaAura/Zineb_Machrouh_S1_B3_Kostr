package main.java.com.kostr.repositories.interfaces;

import main.java.com.kostr.dto.MaterialDTO;
import main.java.com.kostr.models.Material;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface MaterialRepositoryInterface {
    public Material addMaterial(Material material) throws SQLException;
    public void removeMaterial(String id) throws SQLException;
    public Material updateMaterial(Material material) throws SQLException;
    public Material getMaterialById(String id) throws SQLException;
    public ArrayList<Material> getMaterialsByProject(String projectId) throws SQLException;
}
