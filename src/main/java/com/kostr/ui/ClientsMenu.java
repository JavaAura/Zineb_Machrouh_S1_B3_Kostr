package main.java.com.kostr.ui;

import main.java.com.kostr.config.Session;
import main.java.com.kostr.controllers.ClientController;
import main.java.com.kostr.controllers.ProjectController;
import main.java.com.kostr.utils.InputValidator;

import java.sql.SQLException;

public class ClientsMenu {
    private final ProjectController projectController;
    private final ClientController clientController;
    private static Session session = Session.getInstance();

    private final InputValidator inputValidator = new InputValidator();

    public static final String RESET = "\033[0m";
    public static final String RED = "\033[0;31m";
    public static final String BLUE = "\033[0;34m";
    public static final String YELLOW = "\u001b[93m";
    public ClientsMenu(ProjectController projectController, ClientController clientController) {
        this.projectController = projectController;
        this.clientController = clientController;
    }

    public void clientsMenu(Integer option) throws SQLException {

        if (option == 4) {
            System.out.println(YELLOW + "+ View All Clients +" + RESET);
            clientController.getAllClients().forEach(System.out::println);
        }else {

            System.out.println(YELLOW + "+ Get Client Projects +" + RESET);

            String clientId;
            System.out.println(BLUE + "+ " + RESET + "Enter Client ID: ");
            do {
                clientId = session.getScanner().nextLine();
                if (!inputValidator.isUUID(clientId)) {
                    System.out.println(RED + "Invalid input! Please enter a valid client ID." + RESET);
                }
            } while (!inputValidator.isUUID(clientId));

            projectController.getClientProjects(clientId).forEach(System.out::println);
        }
    }
}
