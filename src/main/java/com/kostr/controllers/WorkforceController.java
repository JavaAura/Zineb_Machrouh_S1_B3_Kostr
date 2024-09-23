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

    public void deleteWorkforce(String id) throws SQLException {
        if (id == null || id.isEmpty()) {
            System.out.println(RED + "Workforce ID is null or empty" + RESET);
            return;
        }

        try {
            workforceService.removeWorkforce(id);
            System.out.println(YELLOW + "Workforce deleted successfully" + RESET);
        } catch (SQLException e) {
            System.out.println(RED + "Error deleting workforce" + RESET);
            throw e;
        }
    }

    public WorkforceDTO updateWorkforce(WorkforceDTO workforceDTO) throws SQLException {
        if (workforceDTO == null || workforceDTO.getId() == null) {
            System.out.println(RED + "Invalid WorkforceDTO provided for update" + RESET);
            return null;
        }

        try {
            Workforce updatedWorkforce = workforceService.updateWorkforce(workforceDTO);
            if (updatedWorkforce != null) {
                System.out.println(YELLOW + "Workforce updated successfully" + RESET);
                return WorkforceDTO.modelToDTO(updatedWorkforce);
            } else {
                System.out.println(RED + "Workforce not found or update failed" + RESET);
                return null;
            }
        } catch (SQLException e) {
            System.out.println(RED + "Error updating workforce" + RESET);
            throw e;
        }
    }

    public WorkforceDTO getWorkforceById(String id) throws SQLException {
        if (id == null || id.isEmpty()) {
            System.out.println(RED + "Workforce ID is null or empty" + RESET);
            return null;
        }

        try {
            Workforce workforce = workforceService.getWorkforceById(id);
            if (workforce != null) {
                return WorkforceDTO.modelToDTO(workforce);
            } else {
                System.out.println(RED + "Workforce not found" + RESET);
                return null;
            }
        } catch (SQLException e) {
            System.out.println(RED + "Error fetching workforce" + RESET);
            throw e;
        }
    }

    public List<WorkforceDTO> getWorkforcesByProject(String projectId) throws SQLException {
        if (projectId == null || projectId.isEmpty()) {
            System.out.println(RED + "Project ID is null or empty" + RESET);
            return null;
        }

        try {
            ArrayList<Workforce> workforces = workforceService.getWorkforcesByProject(projectId);
            if (workforces != null && !workforces.isEmpty()) {
                List<WorkforceDTO> workforceDTOList = new ArrayList<>();
                for (Workforce workforce : workforces) {
                    workforceDTOList.add(WorkforceDTO.modelToDTO(workforce));
                }
                return workforceDTOList;
            } else {
                System.out.println(RED + "No workforces found for the project" + RESET);
                return null;
            }
        } catch (SQLException e) {
            System.out.println(RED + "Error fetching workforces for project" + RESET);
            throw e;
        }
    }
}
