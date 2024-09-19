package main.java.com.kostr.repositories.interfaces;

import main.java.com.kostr.dto.WorkforceDTO;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface WorkforceRepositoryInterface {
    public void addWorkforce(WorkforceDTO workforce) throws SQLException;
    public void removeWorkforce(String id) throws SQLException;
    public void updateWorkforce(WorkforceDTO workforce) throws SQLException;
    public ResultSet getWorkforceById(String id) throws SQLException;
    public ResultSet getWorkforcesByProject(String projectId) throws SQLException;
}
