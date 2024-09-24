package main.java.com.kostr.services;

import main.java.com.kostr.dto.WorkforceDTO;
import main.java.com.kostr.models.Workforce;
import main.java.com.kostr.repositories.interfaces.QuoteRepositoryInterface;
import main.java.com.kostr.repositories.interfaces.WorkforceRepositoryInterface;
import main.java.com.kostr.services.interfaces.WorkforceServiceInterface;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Logger;

public class WorkforceService implements WorkforceServiceInterface {
    private final WorkforceRepositoryInterface workforceRepository;
    private static final Logger logger = Logger.getLogger(WorkforceService.class.getName());

        public WorkforceService(WorkforceRepositoryInterface workforceRepository) {
        this.workforceRepository = workforceRepository;
    }


    @Override
    public Workforce addWorkforce(WorkforceDTO workforceDTO) throws SQLException {
        Workforce workforce = workforceDTO.dtoToModel();
        logger.info("Attempting to add a new workforce.");

        Workforce addedWorkforce = workforceRepository.addWorkforce(workforce);
        if (addedWorkforce != null) {
            logger.info("Workforce added successfully.");
        }
        return addedWorkforce;
    }

}
