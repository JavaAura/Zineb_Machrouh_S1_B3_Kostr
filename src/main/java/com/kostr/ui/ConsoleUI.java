package main.java.com.kostr.ui;

import main.java.com.kostr.config.Session;
import main.java.com.kostr.controllers.ClientController;
import main.java.com.kostr.controllers.ProjectController;
import main.java.com.kostr.dto.ClientDTO;
import main.java.com.kostr.dto.ProjectDTO;
import main.java.com.kostr.models.Client;
import main.java.com.kostr.repositories.ClientRepository;
import main.java.com.kostr.repositories.ProjectRepository;
import main.java.com.kostr.repositories.interfaces.ProjectRepositoryInterface;
import main.java.com.kostr.services.ClientService;
import main.java.com.kostr.services.MaterialService;
import main.java.com.kostr.services.ProjectService;
import main.java.com.kostr.utils.InputValidator;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;

public class ConsoleUI {
    private final Connection connection;
    private static Session session = Session.getInstance();
    private static final Logger logger = Logger.getLogger(ConsoleUI.class.getName());
    private final InputValidator inputValidator = new InputValidator();

    public static final String RESET = "\033[0m";
    public static final String RED = "\033[0;31m";
    public static final String BLUE = "\033[0;34m";
    public static final String MAGENTA = "\033[0;35m";
    public static final String CYAN = "\033[0;36m";
    public static final String PINK = "\033[38;5;13m";
    public static final String GREEN = "\u001b[92m";
    public static final String YELLOW = "\u001b[93m";

    public ConsoleUI(Connection connection) throws SQLException {
        this.connection = connection;
        menu();
    }

    public void menu() throws SQLException {
        boolean exit = false;

        while(!exit){
            System.out.println(BLUE + "+----------+" + RESET + " Welcome To Kostr " + BLUE + "+----------+" + RESET);
            System.out.println(BLUE+ "+ Projects :                             +");
            System.out.println(BLUE + "+ 1. " + RESET + "View All Projects" + BLUE + "                   +");
            System.out.println(BLUE + "+ 2. " + RESET + "Search Projects" + BLUE + "                     +");
            System.out.println(BLUE + "+ 3. " + RESET + "Manage Projects" + BLUE + "                     +");
            System.out.println(BLUE + "+ 4. " + RESET + "Accept/Decline Quote" + BLUE + "                +");
            System.out.println(BLUE + "+ Clients :                              +");
            System.out.println(BLUE + "+ 5. " + RESET + "View All Clients" + BLUE + "                    +");
            System.out.println(BLUE + "+ 6. " + RESET + "Get Client Projects" + BLUE + "                 +");
            System.out.println(BLUE + "+ 7. " + RESET + "Manage Clients" + BLUE + "                      +");
            System.out.println(BLUE + "+ Components :                           +");
            System.out.println(BLUE + "+ 8. " + RESET + "Manage Components Types" + BLUE + "             +");
            System.out.println(YELLOW + "+ 9. " + RESET + "EXIT" + YELLOW + "                                +");
            System.out.println(BLUE + "++++++++++++++++++++++++++++++++++++++++++" + RESET);

            System.out.println("Please select an option: ");
            int option = session.getScanner().nextInt();

            if (option == 1 || option == 2 || option == 3 || option == 4) {
                projectsMenu(option);

            }else if (option == 5 || option == 6 || option == 7) {
                clientsMenu(option);

            }else if (option == 8) {
                componentsMenu(option);

            }else if (option == 9) {
                System.out.println(YELLOW + "+ Exiting Kostr +"+ RESET);
                exit = true;
            }
            else {
                System.out.println(RED + "Invalid option" + RESET);
                menu();
            }
        }
    }


    public void projectsMenu(Integer option) throws SQLException {
        ProjectRepository projectRepository = new ProjectRepository(connection);
        ProjectService projectService = new ProjectService(projectRepository);
        ProjectController projectController = new ProjectController(projectService);

        ClientRepository clientRepository = new ClientRepository(connection);
        ClientService clientService = new ClientService(clientRepository);
        ClientController clientController = new ClientController(clientService);

        switch (option){
            case 1:

                break;
            case 2:

                break;
            case 3:
                System.out.println(YELLOW + "+ Manage Projects Selected +"+ RESET);

                System.out.println(BLUE + "+ 1. " + RESET + "Add Project");
                System.out.println(BLUE + "+ 2. " + RESET + "Update Project");
                System.out.println(BLUE + "+ 3. " + RESET + "Delete Project");

                System.out.println(BLUE + "+ " + RESET + "Please select an option: ");
                int projectOption = session.getScanner().nextInt();

                switch (projectOption){
                    case 1:
                        try {
                            System.out.println(YELLOW + "+ Associate Client +" + RESET);
                            boolean clientAssociated = associateClient(clientController);

                            if (clientAssociated) {
                                System.out.println(YELLOW + "+ Create Project +" + RESET);
                                projectController.createProject(addProject());
                            } else {
                                System.out.println(RED + "Client association failed. Cannot create project." + RESET);
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 2:

                        break;
                    case 3:

                        break;
                    default:
                        System.out.println(RED + "Invalid option" + RESET);
                        projectsMenu(3);
                        break;
                }
                break;
            case 4:

                break;
        }
    }

    public void clientsMenu(Integer option){

    }

    public void componentsMenu(Integer option){

    }

    private ProjectDTO addProject(){
        String projectName;
        System.out.println(BLUE + "+ " + RESET + "Enter Project Name: ");
        do {
            projectName = session.getScanner().nextLine();
            if (!inputValidator.handleString(projectName)) {
                System.out.println(RED + "Invalid input! Please enter a valid project name (only alphabetic characters)." + RESET);
            }
        } while (!inputValidator.handleString(projectName));

        double projectProfitMargin;
        String profitMarginInput;
        System.out.println(BLUE + "+ " + RESET + "Enter Project Profit Margin: ");
        do {
            profitMarginInput = session.getScanner().nextLine();
            if (!inputValidator.handleDouble(profitMarginInput)) {
                System.out.println(RED + "Invalid input! Please enter a valid profit margin (must be greater than 0)." + RESET);
            }
        } while (!inputValidator.handleDouble(profitMarginInput) || (projectProfitMargin = Double.parseDouble(profitMarginInput)) <= 0);

        double projectSurfaceArea;
        String surfaceAreaInput;
        System.out.println(BLUE + "+ " + RESET + "Enter Project Surface Area: ");
        do {
            surfaceAreaInput = session.getScanner().nextLine();
            if (!inputValidator.handleDouble(surfaceAreaInput)) {
                System.out.println(RED + "Invalid input! Please enter a valid surface area (must be greater than 0)." + RESET);
            }
        } while (!inputValidator.handleDouble(surfaceAreaInput) || (projectSurfaceArea = Double.parseDouble(surfaceAreaInput)) <= 0);

        String projectType;
        System.out.println(BLUE + "+ " + RESET + "Enter Project Type (Renovation / Construction): ");
        do {
            projectType = session.getScanner().nextLine();
            if (!projectType.equalsIgnoreCase("Renovation") && !projectType.equalsIgnoreCase("Construction")) {
                System.out.println(RED + "Invalid input! Please enter either 'Renovation' or 'Construction'." + RESET);
            }
        } while (!projectType.equalsIgnoreCase("Renovation") && !projectType.equalsIgnoreCase("Construction"));

        ProjectDTO projectDTO = new ProjectDTO(projectName, projectProfitMargin, projectSurfaceArea, projectType.toUpperCase(), session.getId());

        return projectDTO;
    }

    private boolean associateClient(ClientController clientController) throws SQLException {
        InputValidator inputValidator = new InputValidator();

        System.out.println(BLUE + "+ 1. " + RESET + "Search Clients");
        System.out.println(BLUE + "+ 2. " + RESET + "Add Client");
        int clientOption = session.getScanner().nextInt();

        switch (clientOption) {
            case 1:
                String clientEmail;
                do {
                    System.out.println(BLUE + "+ " + RESET + "Enter Client Email: ");
                    clientEmail = session.getScanner().nextLine();
                    if (!inputValidator.handleEmail(clientEmail)) {
                        System.out.println(RED + "Invalid email format. Please try again." + RESET);
                    }
                } while (!inputValidator.handleEmail(clientEmail));

                ClientDTO client = clientController.getClientByEmail(clientEmail);
                if (client != null) {
                    session.setId(client.getId());
                } else {
                    projectsMenu(3);
                }
                return client != null;

            case 2:
                String clientName;
                do {
                    System.out.println(BLUE + "+ " + RESET + "Enter Client Name: ");
                    clientName = session.getScanner().nextLine();
                    if (!inputValidator.handleString(clientName)) {
                        System.out.println(RED + "Invalid name format. Please try again." + RESET);
                    }
                } while (!inputValidator.handleString(clientName));

                String clientAddress;
                do {
                    System.out.println(BLUE + "+ " + RESET + "Enter Client Address: ");
                    clientAddress = session.getScanner().nextLine();
                    if (!inputValidator.handleAddress(clientAddress)) {
                        System.out.println(RED + "Invalid address format. Please try again." + RESET);
                    }
                } while (!inputValidator.handleAddress(clientAddress));

                String clientEmail1;
                do {
                    System.out.println(BLUE + "+ " + RESET + "Enter Client Email: ");
                    clientEmail1 = session.getScanner().nextLine();
                    if (!inputValidator.handleEmail(clientEmail1)) {
                        System.out.println(RED + "Invalid email format. Please try again." + RESET);
                    }
                } while (!inputValidator.handleEmail(clientEmail1));

                String clientPhoneNumber;
                do {
                    System.out.println(BLUE + "+ " + RESET + "Enter Client Phone Number: ");
                    clientPhoneNumber = session.getScanner().nextLine();
                    if (!inputValidator.handlePhone(clientPhoneNumber)) {
                        System.out.println(RED + "Invalid phone number format. Please try again." + RESET);
                    }
                } while (!inputValidator.handlePhone(clientPhoneNumber));

                String clientIsProfessional;
                do {
                    System.out.println(BLUE + "+ " + RESET + "Is Client Professional? (yes/no): ");
                    clientIsProfessional = session.getScanner().nextLine();
                    if (!clientIsProfessional.equalsIgnoreCase("yes") && !clientIsProfessional.equalsIgnoreCase("no")) {
                        System.out.println(RED + "Please answer with 'yes' or 'no'." + RESET);
                    }
                } while (!clientIsProfessional.equalsIgnoreCase("yes") && !clientIsProfessional.equalsIgnoreCase("no"));

                ClientDTO newClient = new ClientDTO(
                        null,
                        clientName,
                        clientAddress,
                        clientEmail1,
                        clientPhoneNumber,
                        clientIsProfessional.equalsIgnoreCase("yes")
                );

                ClientDTO clientDTO = clientController.createClient(newClient);
                if (clientDTO != null) {
                    session.setId(clientDTO.getId());
                } else {
                    System.out.println(RED + "Failed to create client" + RESET);
                }
                return clientDTO != null;


            default:
                System.out.println(RED + "Invalid option" + RESET);
                projectsMenu(3);
                return false;
        }
    }


}
