package main.java.com.kostr;

import main.java.com.kostr.config.DatabaseConnection;
import main.java.com.kostr.controllers.*;
import main.java.com.kostr.repositories.*;
import main.java.com.kostr.services.*;
import main.java.com.kostr.ui.ConsoleUI;

import java.sql.Connection;
import java.sql.SQLException;

public class KostrApp {

    public static void main(String[] args) throws SQLException {
        Connection connection = DatabaseConnection.getConnection();

        ProjectRepository projectRepository = new ProjectRepository(connection);
        ProjectService projectService = new ProjectService(projectRepository);
        ProjectController projectController = new ProjectController(projectService);

        ClientRepository clientRepository = new ClientRepository(connection);
        ClientService clientService = new ClientService(clientRepository);
        ClientController clientController = new ClientController(clientService);

        MaterialRepository materialRepository = new MaterialRepository(connection);
        MaterialService materialService = new MaterialService(materialRepository);
        MaterialController materialController = new MaterialController(materialService);

        WorkforceRepository workforceRepository = new WorkforceRepository(connection);
        WorkforceService workforceService = new WorkforceService(workforceRepository);
        WorkforceController workforceController = new WorkforceController(workforceService);

        QuoteRepository quoteRepository = new QuoteRepository(connection);
        QuoteService quoteService = new QuoteService(quoteRepository);
        QuoteController quoteController = new QuoteController(quoteService);

        ComponentTypeRepositoryImpl componentTypeRepository = new ComponentTypeRepositoryImpl(connection);
        ComponentTypeServiceImpl componentTypeService = new ComponentTypeServiceImpl(componentTypeRepository);
        ComponentTypeController componentTypeController = new ComponentTypeController(componentTypeService);

        ConsoleUI consoleUI = new ConsoleUI(projectController, clientController, materialController, workforceController, quoteController, componentTypeController);
    }
}
