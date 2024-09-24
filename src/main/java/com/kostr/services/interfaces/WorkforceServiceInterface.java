package main.java.com.kostr.services.interfaces;

import main.java.com.kostr.dto.WorkforceDTO;
import main.java.com.kostr.models.Workforce;

import java.sql.SQLException;
import java.util.ArrayList;

public interface WorkforceServiceInterface {
    public Workforce addWorkforce(WorkforceDTO workforce) throws SQLException;
}
