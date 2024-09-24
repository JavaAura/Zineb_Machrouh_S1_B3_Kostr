package main.java.com.kostr.controllers;

import main.java.com.kostr.dto.WorkforceDTO;
import main.java.com.kostr.models.Workforce;
import main.java.com.kostr.services.interfaces.WorkforceServiceInterface;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WorkforceController {

    private WorkforceServiceInterface workforceService;

    public static final String RESET = "\033[0m";
    public static final String RED = "\033[0;31m";
    public static final String YELLOW = "\u001b[93m";

    public WorkforceController(WorkforceServiceInterface workforceService) {
        this.workforceService = workforceService;
    }

    public void createWorkforce(WorkforceDTO workforceDTO) throws SQLException {
        if (workforceDTO == null) {
            System.out.println(RED + "WorkforceDTO is null" + RESET);
            //return null;
        }

        try {
            Workforce workforce = workforceService.addWorkforce(workforceDTO);
            System.out.println(YELLOW + "Workforce created successfully" + RESET);
            //return WorkforceDTO.modelToDTO(workforce);
        } catch (SQLException e) {
            System.out.println(RED + "Error creating workforce" + RESET);
            throw e;
        }
    }


}
