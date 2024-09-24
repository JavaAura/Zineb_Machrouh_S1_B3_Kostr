package main.java.com.kostr.ui;

import main.java.com.kostr.config.Session;
import main.java.com.kostr.controllers.*;
import main.java.com.kostr.dto.*;
import main.java.com.kostr.utils.InputValidator;

import java.sql.SQLException;
import java.util.ArrayList;

public class ConsoleUI {
    private static Session session = Session.getInstance();

    private final InputValidator inputValidator = new InputValidator();
    private String projectId;

    ArrayList<ComponentDTO> components = new ArrayList<>();

    private final ProjectController projectController;
    private final ClientController clientController;
    private final MaterialController materialController;
    private final WorkforceController workforceController;
    private final QuoteController quoteController;
    private final ComponentTypeController componentTypeController;

    public static final String RESET = "\033[0m";
    public static final String RED = "\033[0;31m";
    public static final String BLUE = "\033[0;34m";
    public static final String YELLOW = "\u001b[93m";

    public ConsoleUI(ProjectController projectController, ClientController clientController, MaterialController materialController, WorkforceController workforceController, QuoteController quoteController, ComponentTypeController componentTypeController) throws SQLException {
        this.projectController = projectController;
        this.clientController = clientController;
        this.materialController = materialController;
        this.workforceController = workforceController;
        this.quoteController = quoteController;
        this.componentTypeController = componentTypeController;

        menu();
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }


    public void menu() throws SQLException {
        boolean exit = false;

        while(!exit){
            System.out.println(BLUE + "+----------+" + RESET + " Welcome To Kostr " + BLUE + "+----------+" + RESET);
            System.out.println(BLUE+ "+ Projects :                             +");
            System.out.println(BLUE + "+ 1. " + RESET + "View All Projects" + BLUE + "                   +");
            System.out.println(BLUE + "+ 2. " + RESET + "Add New Project" + BLUE + "                     +");
            System.out.println(BLUE + "+ 3. " + RESET + "Accept/Decline Quote" + BLUE + "                +");
            System.out.println(BLUE + "+ Clients :                              +");
            System.out.println(BLUE + "+ 4. " + RESET + "View All Clients" + BLUE + "                    +");
            System.out.println(BLUE + "+ 5. " + RESET + "Get Client Projects" + BLUE + "                 +");
            System.out.println(BLUE + "+ Components :                           +");
            System.out.println(BLUE + "+ 6. " + RESET + "Manage Components Types" + BLUE + "             +");
            System.out.println(YELLOW + "+ 7. " + RESET + "EXIT" + YELLOW + "                                +");
            System.out.println(BLUE + "++++++++++++++++++++++++++++++++++++++++++" + RESET);

            System.out.println("Please select an option: ");
            int option = session.getScanner().nextInt();

            if (option == 1 || option == 2 || option == 3) {
                ProjectsMenu projectsMenu = new ProjectsMenu(projectController, clientController, materialController, workforceController,quoteController, componentTypeController);
                projectsMenu.projectsMenu(option);
            }else if (option == 4 || option == 5 ) {
                ClientsMenu clientsMenu = new ClientsMenu(projectController, clientController);
                clientsMenu.clientsMenu(option);

            }else if (option == 6) {
                ComponentsMenu componentsMenu = new ComponentsMenu(componentTypeController);
                componentsMenu.componentsMenu();
            }else if (option == 7) {
                System.out.println(YELLOW + "+ See You Soon +"+ RESET);
                exit = true;
                System.exit(0);
            }
            else {
                System.out.println(RED + "Invalid option" + RESET);
                menu();
            }
        }
    }


}
