package main.java.com.kostr.ui;

import main.java.com.kostr.config.Session;
import main.java.com.kostr.controllers.*;
import main.java.com.kostr.dto.*;
import main.java.com.kostr.models.Client;
import main.java.com.kostr.models.Component;
import main.java.com.kostr.models.Workforce;
import main.java.com.kostr.models.enums.ComponentType;
import main.java.com.kostr.repositories.*;
import main.java.com.kostr.repositories.interfaces.ProjectRepositoryInterface;
import main.java.com.kostr.services.*;
import main.java.com.kostr.utils.CostCalculator;
import main.java.com.kostr.utils.InputValidator;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;
import java.util.logging.Logger;

public class ConsoleUI {
    private final Connection connection;
    private static Session session = Session.getInstance();

    private static final Logger logger = Logger.getLogger(ConsoleUI.class.getName());
    private final InputValidator inputValidator = new InputValidator()
            ;
    private String projectId;
    private double estimatedCost = 0;

    ArrayList<ComponentDTO> components = new ArrayList<>();

    public static final String RESET = "\033[0m";
    public static final String RED = "\033[0;31m";
    public static final String BLUE = "\033[0;34m";
    public static final String YELLOW = "\u001b[93m";

    public ConsoleUI(Connection connection) throws SQLException {
        this.connection = connection;
        menu();
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public double getEstimatedCost() {
        return estimatedCost;
    }
    public void setEstimatedCost(double estimatedCost) {
        this.estimatedCost = estimatedCost;
    }

    public void menu() throws SQLException {
        boolean exit = false;

        while(!exit){
            System.out.println(BLUE + "+----------+" + RESET + " Welcome To Kostr " + BLUE + "+----------+" + RESET);
            System.out.println(BLUE+ "+ Projects :                             +");
            System.out.println(BLUE + "+ 1. " + RESET + "View All Projects" + BLUE + "                   +");
            System.out.println(BLUE + "+ 2. " + RESET + "Search Projects" + BLUE + "                     +");
            System.out.println(BLUE + "+ 3. " + RESET + "Add New Project" + BLUE + "                     +");
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
                System.out.println(YELLOW + "+ Manage Components Types +" + RESET);
                System.out.println(BLUE + "+ 1. " + RESET + "Add Component Type");
                System.out.println(BLUE + "+ 2. " + RESET + "Update Component Type");
                System.out.println(BLUE + "+ 3. " + RESET + "Delete Component Type");
                System.out.println(BLUE + "+ 4. " + RESET + "View All Component Types");
                System.out.println(BLUE + "+ 5. " + RESET + "Go Back");

                System.out.println("Please select an option: ");

                int componentOption = session.getScanner().nextInt();

                componentsMenu(componentOption);
            }else if (option == 9) {
                System.out.println(YELLOW + "+ See You Soon +"+ RESET);
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

        MaterialRepository materialRepository = new MaterialRepository(connection);
        MaterialService materialService = new MaterialService(materialRepository);
        MaterialController materialController = new MaterialController(materialService);

        WorkforceRepository workforceRepository = new WorkforceRepository(connection);
        WorkforceService workforceService = new WorkforceService(workforceRepository);
        WorkforceController workforceController = new WorkforceController(workforceService);

        switch (option){
            case 1:
                System.out.println(YELLOW + "+ View All Projects +" + RESET);
                projectController.getAllProjects().forEach(System.out::println);
                break;
            case 2:

                break;
            case 3:
                try {
                    System.out.println(YELLOW + "+ Associate Client +" + RESET);
                    boolean clientAssociated = associateClient(clientController);

                    if (clientAssociated) {
                        System.out.println(YELLOW + "+ Create Project +" + RESET);
                        ProjectDTO projectDTO = projectController.createProject(addProject());

                        setProjectId(projectDTO.getId().toString());
                        if (projectDTO != null) {
                            boolean addMoreMaterials = false;
                            boolean addMoreWorkforce = false;

                            while (!addMoreMaterials) {
                                System.out.println(BLUE + "+ Add Material +" + RESET);
                                MaterialDTO materialDTO = addMaterial();
                                materialController.createMaterial(materialDTO);

                                System.out.println(BLUE + "+ " + RESET + "Add another material? (yes/no): ");
                                String addMaterial = session.getScanner().nextLine();
                                if (!addMaterial.equalsIgnoreCase("yes")) {
                                    addMoreMaterials = true;
                                }
                            }

                            while (!addMoreWorkforce) {
                                System.out.println(BLUE + "+ Add Workforce +" + RESET);
                                WorkforceDTO workforceDTO = addWorkforce();
                                workforceController.createWorkforce(workforceDTO);

                                System.out.println(BLUE + "+ " + RESET + "Add another workforce? (yes/no): ");
                                String addWorkforce = session.getScanner().nextLine();
                                if (!addWorkforce.equalsIgnoreCase("yes")) {
                                    addMoreWorkforce = true;
                                }
                            }

                            QuoteDTO quoteDTO = generateQuote();

                            calculateCost(projectDTO, projectController);

                        } else {
                            System.out.println(RED + "Project creation failed." + RESET);
                        }
                    } else {
                        System.out.println(RED + "Client association failed. Cannot create project." + RESET);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            case 4:

                break;
        }
    }

    public void clientsMenu(Integer option){

    }

    public void componentsMenu(Integer componentOption) throws SQLException {
        ComponentTypeRepositoryImpl componentTypeRepository = new ComponentTypeRepositoryImpl(connection);
        ComponentTypeServiceImpl componentTypeService = new ComponentTypeServiceImpl(componentTypeRepository);
        ComponentTypeController componentTypeController = new ComponentTypeController(componentTypeService);

        switch (componentOption){
            case 1:
                System.out.println(BLUE + "+ Add Component Type +" + RESET);

                String componentName;
                System.out.println(BLUE + "+ " + RESET + "Enter Component Name: ");
                do {
                    componentName = session.getScanner().nextLine();
                    if (!inputValidator.handleString(componentName)) {
                        System.out.println(RED + "Invalid input! Please enter a valid component name (only alphabetic characters)." + RESET);
                    }
                } while (!inputValidator.handleString(componentName));

                ComponentType componentType = null;
                System.out.println(BLUE + "+ " + RESET + "Enter Component Type (Materials, Workforce): ");
                do {
                    String componentTypeInput = session.getScanner().nextLine();
                    if (!componentTypeInput.equalsIgnoreCase("Materials") && !componentTypeInput.equalsIgnoreCase("Workforce")) {
                        System.out.println(RED + "Invalid input! Please enter either 'Materials' or 'Workforce'." + RESET);
                    } else {
                        componentType = ComponentType.valueOf(componentTypeInput.toUpperCase());
                    }
                } while (!componentType.equals(ComponentType.MATERIALS) && !componentType.equals(ComponentType.WORKFORCE));

                ComponentTypeDTO componentTypeDTO = new ComponentTypeDTO(null, componentName, componentType);
                componentTypeController.createComponentType(componentTypeDTO);

                break;
            case 2:
                System.out.println(BLUE + "+ Update Component Type +" + RESET);

                String componentId;
                System.out.println(BLUE + "+ " + RESET + "Enter Component ID: ");
                do {
                    componentId = session.getScanner().nextLine();
                    if (!inputValidator.isUUID(componentId)) {
                        System.out.println(RED + "Invalid input! Please enter a valid component ID." + RESET);
                    }
                } while (!inputValidator.isUUID(componentId));

                ComponentTypeDTO componentTypeDTO2 = componentTypeController.getComponentTypeById(componentId);

                String componentName1;
                System.out.println(BLUE + "+ " + RESET + "Enter Component Name (or press Enter to keep the existing value): ");
                String input = session.getScanner().nextLine().trim();

                if (input.isEmpty()) {
                    componentName1 = componentTypeDTO2.getName();
                } else {
                    componentName1 = input;
                }

                ComponentType componentType1 = null;
                System.out.println(BLUE + "+ " + RESET + "Enter Component Type (Materials, Workforce) or press Enter to keep the existing value: ");
                String componentTypeInput = session.getScanner().nextLine().trim().toUpperCase();

                if (componentTypeInput.isEmpty()) {
                    componentType1 = componentTypeDTO2.getType();
                } else {
                    try {
                        componentType1 = ComponentType.valueOf(componentTypeInput);
                    } catch (IllegalArgumentException e) {
                        System.out.println(RED + "Invalid component type. Please enter 'Materials' or 'Workforce'." + RESET);
                        componentType1 = componentTypeDTO2.getType();
                    }
                }

                ComponentTypeDTO componentTypeDTO1 = new ComponentTypeDTO(UUID.fromString(componentId), componentName1, componentType1);
                componentTypeController.updateComponentType(componentTypeDTO1);


                break;
            case 3:
                System.out.println(BLUE + "+ Delete Component Type +" + RESET);

                String componentId1;
                System.out.println(BLUE + "+ " + RESET + "Enter Component ID: ");
                do {
                    componentId1 = session.getScanner().nextLine();
                    if (!inputValidator.isUUID(componentId1)) {
                        System.out.println(RED + "Invalid input! Please enter a valid component ID." + RESET);
                    }
                } while (!inputValidator.isUUID(componentId1));

                componentTypeController.deleteComponentType(componentId1);

                break;
                case 4:
                    System.out.println(YELLOW + "+ View All Component Types +" + RESET);

                    componentTypeController.getAllComponentTypes().forEach(System.out::println);

                    break;
                case 5:
                    menu();
                    break;
        }

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
                    session.setProfesional(client.isProfessional());
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
                    session.setProfesional(clientDTO.isProfessional());
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

    private MaterialDTO addMaterial() throws SQLException {
        ComponentTypeRepositoryImpl componentTypeRepository = new ComponentTypeRepositoryImpl(connection);
        ComponentTypeServiceImpl componentTypeService = new ComponentTypeServiceImpl(componentTypeRepository);
        ComponentTypeController componentTypeController = new ComponentTypeController(componentTypeService);

        String materialName;
        System.out.println(BLUE + "+ " + RESET + "Enter Material Name: ");
        do {
            materialName = session.getScanner().nextLine();
            if (!inputValidator.handleString(materialName)) {
                System.out.println(RED + "Invalid input! Please enter a valid material name (only alphabetic characters)." + RESET);
            }
        } while (!inputValidator.handleString(materialName));

        String componentType;
        System.out.println(BLUE + "+ All Material Types +" + RESET);
        componentTypeController.getAllComponentTypes().stream().filter(componentTypeDTO -> componentTypeDTO.getType().equals(ComponentType.MATERIALS)).forEach(System.out::println);
        System.out.println(BLUE + "+ " + RESET + "Enter Component Type Id: ");

        do {
            componentType = session.getScanner().nextLine();
            if (!inputValidator.isUUID(componentType)) {
                System.out.println(RED + "Invalid input! Please enter a valid component type (only alphabetic characters)." + RESET);
            }
        } while (!inputValidator.isUUID(componentType));

        double vatRate;
        String vatRateInput;

        System.out.println(BLUE + "+ " + RESET + "Enter VAT Rate: ");
        do {
            vatRateInput = session.getScanner().nextLine();
            if (!inputValidator.handleDouble(vatRateInput)) {
                System.out.println(RED + "Invalid input! Please enter a valid VAT rate (must be greater than 0)." + RESET);
            }
        } while (!inputValidator.handleDouble(vatRateInput) || (vatRate = Double.parseDouble(vatRateInput)) <= 0);

        String projectID = getProjectId();

        double unitCost;
        String unitCostInput;
        System.out.println(BLUE + "+ " + RESET + "Enter Unit Cost: ");
        do {
            unitCostInput = session.getScanner().nextLine();
            if (!inputValidator.handleDouble(unitCostInput)) {
                System.out.println(RED + "Invalid input! Please enter a valid unit cost (must be greater than 0)." + RESET);
            }
        } while (!inputValidator.handleDouble(unitCostInput) || (unitCost = Double.parseDouble(unitCostInput)) <= 0);

        double quantity;
        String quantityInput;
        System.out.println(BLUE + "+ " + RESET + "Enter Quantity: ");
        do {
            quantityInput = session.getScanner().nextLine();
            if (!inputValidator.handleDouble(quantityInput)) {
                System.out.println(RED + "Invalid input! Please enter a valid quantity (must be greater than 0)." + RESET);
            }
        } while (!inputValidator.handleDouble(quantityInput) || (quantity = Double.parseDouble(quantityInput)) <= 0);

        double transportCost;
        String transportCostInput;
        System.out.println(BLUE + "+ " + RESET + "Enter Transport Cost: ");
        do {
            transportCostInput = session.getScanner().nextLine();
            if (!inputValidator.handleDouble(transportCostInput)) {
                System.out.println(RED + "Invalid input! Please enter a valid transport cost (must be greater than 0)." + RESET);
            }
        } while (!inputValidator.handleDouble(transportCostInput) || (transportCost = Double.parseDouble(transportCostInput)) <= 0);

        double qualityCoefficient;
        String qualityCoefficientInput;
        System.out.println(BLUE + "+ " + RESET + "Enter Quality Coefficient (1.0 or 1.1): ");
        do {
            qualityCoefficientInput = session.getScanner().nextLine();
            if (!inputValidator.handleDouble(qualityCoefficientInput)) {
                System.out.println(RED + "Invalid input! Please enter a valid quality coefficient (must be 1.0 or 1.1)." + RESET);
            }
        } while (!inputValidator.handleDouble(qualityCoefficientInput) || (qualityCoefficient = Double.parseDouble(qualityCoefficientInput)) != 1.0 && qualityCoefficient != 1.1);

        CostCalculator costCalculator = new CostCalculator();
        double totalPrice = costCalculator.materialCost(unitCost, quantity, qualityCoefficient, transportCost);

        MaterialDTO materialDTO = new MaterialDTO(
                null,
                materialName,
                UUID.fromString(componentType),
                vatRate,
                totalPrice,
                UUID.fromString(getProjectId()),
                unitCost,
                quantity,
                transportCost,
                qualityCoefficient
        );

        components.add(materialDTO);
        setEstimatedCost(getEstimatedCost() + totalPrice);

        return materialDTO;
    }

    private WorkforceDTO addWorkforce() throws SQLException{
        ComponentTypeRepositoryImpl componentTypeRepository = new ComponentTypeRepositoryImpl(connection);
        ComponentTypeServiceImpl componentTypeService = new ComponentTypeServiceImpl(componentTypeRepository);
        ComponentTypeController componentTypeController = new ComponentTypeController(componentTypeService);

        String workforceName;
        System.out.println(BLUE + "+ " + RESET + "Enter Workforce Name: ");
        do {
            workforceName = session.getScanner().nextLine();
            if (!inputValidator.handleString(workforceName)) {
                System.out.println(RED + "Invalid input! Please enter a valid workforce name (only alphabetic characters)." + RESET);
            }
        } while (!inputValidator.handleString(workforceName));

        String componentType;
        System.out.println(BLUE + "+ All Workforce Types +" + RESET);
        componentTypeController.getAllComponentTypes().stream().filter(componentTypeDTO -> componentTypeDTO.getType().equals(ComponentType.WORKFORCE)).forEach(System.out::println);
        System.out.println(BLUE + "+ " + RESET + "Enter Component Type Id: ");

        do {
            componentType = session.getScanner().nextLine();
            if (!inputValidator.isUUID(componentType)) {
                System.out.println(RED + "Invalid input! Please enter a valid component type (only alphabetic characters)." + RESET);
            }
        } while (!inputValidator.isUUID(componentType));

        double vatRate;
        String vatRateInput;
        System.out.println(BLUE + "+ " + RESET + "Enter VAT Rate: ");
        do {
            vatRateInput = session.getScanner().nextLine();
            if (!inputValidator.handleDouble(vatRateInput)) {
                System.out.println(RED + "Invalid input! Please enter a valid VAT rate (must be greater than 0)." + RESET);
            }
        } while (!inputValidator.handleDouble(vatRateInput) || (vatRate = Double.parseDouble(vatRateInput)) <= 0);

        double hourlyRate;
        String hourlyRateInput;
        System.out.println(BLUE + "+ " + RESET + "Enter Hourly Rate: ");
        do {
            hourlyRateInput = session.getScanner().nextLine();
            if (!inputValidator.handleDouble(hourlyRateInput)) {
                System.out.println(RED + "Invalid input! Please enter a valid hourly rate (must be greater than 0)." + RESET);
            }
        } while (!inputValidator.handleDouble(hourlyRateInput) || (hourlyRate = Double.parseDouble(hourlyRateInput)) <= 0);

        double hoursWorked;
        String hoursWorkedInput;
        System.out.println(BLUE + "+ " + RESET + "Enter Hours Worked: ");
        do {
            hoursWorkedInput = session.getScanner().nextLine();
            if (!inputValidator.handleDouble(hoursWorkedInput)) {
                System.out.println(RED + "Invalid input! Please enter a valid hours worked (must be greater than 0)." + RESET);
            }
        } while (!inputValidator.handleDouble(hoursWorkedInput) || (hoursWorked = Double.parseDouble(hoursWorkedInput)) <= 0);

        double workerProductivity;
        String workerProductivityInput;
        System.out.println(BLUE + "+ " + RESET + "Enter Worker Productivity (1.0 or 1.1): ");
        do {
            workerProductivityInput = session.getScanner().nextLine();
            if (!inputValidator.handleDouble(workerProductivityInput)) {
                System.out.println(RED + "Invalid input! Please enter a valid worker productivity (must be 1.0 or 1.1)." + RESET);
            }
        } while (!inputValidator.handleDouble(workerProductivityInput) || (workerProductivity = Double.parseDouble(workerProductivityInput)) != 1.0 && workerProductivity != 1.1);

        CostCalculator costCalculator = new CostCalculator();
        double totalPrice = costCalculator.workforceCost(hourlyRate, hoursWorked, workerProductivity);

        WorkforceDTO workforceDTO = new WorkforceDTO(
                null,
                workforceName,
                UUID.fromString(componentType),
                vatRate,
                totalPrice,
                UUID.fromString(getProjectId()),
                hourlyRate,
                hoursWorked,
                workerProductivity
        );

        components.add(workforceDTO);
        setEstimatedCost(getEstimatedCost() + totalPrice);

        return workforceDTO;
    }

    private QuoteDTO generateQuote() throws SQLException {
        QuoteRepository quoteRepository = new QuoteRepository(connection);
        QuoteService quoteService = new QuoteService(quoteRepository);
        QuoteController quoteController = new QuoteController(quoteService);

        QuoteDTO quoteDTO = new QuoteDTO(null, UUID.fromString(getProjectId()), getEstimatedCost(), null, null, false);

        return quoteController.createQuote(quoteDTO);
    }

    private void calculateCost(ProjectDTO project, ProjectController projectController) throws SQLException {
        CostCalculator costCalculator = new CostCalculator();

        System.out.println(YELLOW + "+ Calculate Costs +" + RESET);
        double materialsCost = 0;
        double materialsCostVAT = 0;

        double workforceCost = 0;
        double workforceCostVAT = 0;

        double totalCost = 0;
        double totalCostMargin = 0;

        System.out.println(BLUE + "+" + RESET +"Do you wanna apply vat? (yes/no): ");
        String applyVat = session.getScanner().nextLine();

        System.out.println(BLUE + "+" + RESET +"Do you wanna apply the profit margin? (yes/no): ");
        String applyProfitMargin = session.getScanner().nextLine();

        System.out.println(YELLOW + "Calculating costs..." + RESET);

        System.out.println(YELLOW + "+ Project Details +" + RESET);
        System.out.println(BLUE + "+ " + RESET + "Project Name: " + project.getName());
        System.out.println(BLUE + "+ " + RESET + "Project Profit Margin: " + project.getProfitMargin());
        System.out.println(BLUE + "+ " + RESET + "Project Surface Area: " + project.getSurfaceArea());
        System.out.println(BLUE + "+ " + RESET + "Project Type: " + project.getType());

        System.out.println(YELLOW + "+ Cost Detail +" + RESET);
        System.out.println(BLUE + "1. " + RESET + "Materials:");
        components.stream().filter(componentDTO -> componentDTO instanceof MaterialDTO).forEach(System.out::println);
        materialsCost += components.stream().filter(componentDTO -> componentDTO instanceof MaterialDTO).mapToDouble(ComponentDTO::getTotalPrice).sum();
        System.out.println(BLUE + "Total Materials Cost: " + RESET + materialsCost + "$");

        if (applyVat.equalsIgnoreCase("yes")) {
            materialsCostVAT += components.stream().filter(componentDTO -> componentDTO instanceof MaterialDTO).mapToDouble(componentDTO -> costCalculator.totalCostWithVAT(componentDTO.getTotalPrice(), componentDTO.getVatRate())).sum();
            System.out.println(BLUE + "Total Materials Cost with VAT: " + RESET + materialsCostVAT + "$");
        }

        System.out.println(BLUE + "2. " + RESET + "Workforce:");
        components.stream().filter(componentDTO -> componentDTO instanceof WorkforceDTO).forEach(System.out::println);
        workforceCost += components.stream().filter(componentDTO -> componentDTO instanceof WorkforceDTO).mapToDouble(ComponentDTO::getTotalPrice).sum();
        System.out.println(BLUE + "Total Workforce Cost: " + RESET + workforceCost + "$");

        if (applyVat.equalsIgnoreCase("yes")) {
            workforceCostVAT += components.stream().filter(componentDTO -> componentDTO instanceof WorkforceDTO).mapToDouble(componentDTO -> costCalculator.totalCostWithVAT(componentDTO.getTotalPrice(), componentDTO.getVatRate())).sum();
            System.out.println(BLUE + "Total Workforce Cost with VAT: " + RESET + workforceCostVAT + "$");
        }

        if (applyVat.equalsIgnoreCase("yes")) {
            totalCost = materialsCostVAT + workforceCostVAT;
            System.out.println(BLUE + "3. " + RESET + "Total Cost Without Margin: " + totalCost + "$");
        }else{
            totalCost = materialsCost + workforceCost;
            System.out.println(BLUE + "3. " + RESET + "Total Cost Without Margin: " + totalCost + "$");
        }

        if (applyProfitMargin.equalsIgnoreCase("yes")) {
            double profitMargin = project.getProfitMargin();
            totalCostMargin = costCalculator.totalCostMargin(totalCost, profitMargin);
            System.out.println(BLUE + "4. " + RESET + "Profit Margin: " + totalCostMargin);
        }

        double totalCostWithMargin = totalCost + totalCostMargin;
        System.out.println(BLUE + "5. " + RESET + "Total Cost: " + totalCostWithMargin + "$");

        int projects = projectController.getClientProjectsCount(session.getId().toString());
        boolean isProfessional = session.isProfesional();

        double totalCostWithDiscount = costCalculator.totalCostWithDiscount((int) totalCostWithMargin, projects, isProfessional);
        System.out.println(BLUE + "6. " + RESET + "Total Cost with Discount: " + totalCostWithDiscount + "$");

        projectController.updateTotalCost(project.getId().toString(), totalCostWithDiscount);
    }



}
