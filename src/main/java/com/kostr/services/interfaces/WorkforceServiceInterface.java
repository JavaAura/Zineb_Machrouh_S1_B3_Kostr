package main.java.com.kostr.services.interfaces;

import main.java.com.kostr.dto.WorkforceDTO;
import main.java.com.kostr.models.Workforce;

import java.sql.SQLException;
import java.util.ArrayList;

public interface WorkforceServiceInterface {
    public Workforce addWorkforce(WorkforceDTO workforce) throws SQLException;
    public void removeWorkforce(String id) throws SQLException;
    public Workforce updateWorkforce(WorkforceDTO workforce) throws SQLException;
    public Workforce getWorkforceById(String id) throws SQLException;
    public ArrayList<Workforce> getWorkforcesByProject(String projectId) throws SQLException;
}
