package main.java.com.kostr.repositories.interfaces;

import main.java.com.kostr.dto.WorkforceDTO;
import main.java.com.kostr.models.Workforce;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface WorkforceRepositoryInterface {
    public Workforce addWorkforce(Workforce workforce) throws SQLException;
    public Workforce getWorkforceById(String id) throws SQLException;
}
