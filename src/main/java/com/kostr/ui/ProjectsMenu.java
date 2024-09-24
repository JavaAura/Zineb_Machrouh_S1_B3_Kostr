package main.java.com.kostr.ui;

import main.java.com.kostr.config.Session;
import main.java.com.kostr.controllers.*;
import main.java.com.kostr.dto.*;
import main.java.com.kostr.models.enums.ComponentType;
import main.java.com.kostr.models.enums.ProjectStatus;
import main.java.com.kostr.utils.CostCalculator;
import main.java.com.kostr.utils.DateUtils;
import main.java.com.kostr.utils.InputValidator;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ProjectsMenu {
    private static Session session = Session.getInstance();
    private final InputValidator inputValidator = new InputValidator();

    private double estimatedCost = 0;
    private String projectId;

    private final ProjectController projectController;
    private final ClientController clientController;
    private final MaterialController materialController;
    private final WorkforceController workforceController;
    private final QuoteController quoteController;
    private final ComponentTypeController componentTypeController;

    public double getEstimatedCost() {
        return estimatedCost;
    }
    public void setEstimatedCost(double estimatedCost) {
        this.estimatedCost = estimatedCost;
    }
    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    ArrayList<ComponentDTO> components = new ArrayList<>();

    public static final String RESET = "\033[0m";
    public static final String RED = "\033[0;31m";
    public static final String BLUE = "\033[0;34m";
    public static final String YELLOW = "\u001b[93m";

    public ProjectsMenu(ProjectController projectController, ClientController clientController, MaterialController materialController, WorkforceController workforceController, QuoteController quoteController, ComponentTypeController componentTypeController) {
        this.projectController = projectController;
        this.clientController = clientController;
        this.materialController = materialController;
        this.workforceController = workforceController;
        this.quoteController = quoteController;
        this.componentTypeController = componentTypeController;
    }

    public void projectsMenu(Integer option) throws SQLException {

        switch (option){
            case 1:
                System.out.println(YELLOW + "+ View All Projects +" + RESET);
                projectController.getAllProjects().forEach(System.out::println);
                break;
            case 2:
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

                            calculateCost(projectDTO, projectController);
                            saveQuote(projectDTO,projectController);
                        } else {
                            System.out.println(RED + "Project creation failed." + RESET);
                        }
                    } else {
                        System.out.println(RED + "Client association failed. Cannot create project." + RESET);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                break;
            case 3:
                System.out.println(YELLOW + "+ Accept/Decline Quote +" + RESET);

                String projectId;
                System.out.println(BLUE + "+ " + RESET + "Enter Project ID: ");
                do {
                    projectId = session.getScanner().nextLine();
                    if (!inputValidator.isUUID(projectId)) {
                        System.out.println(RED + "Invalid input! Please enter a valid project ID." + RESET);
                    }
                } while (!inputValidator.isUUID(projectId));

                QuoteDTO quoteDTO = quoteController.getQuoteByProject(projectId);

                if (quoteDTO.isAccepted()){
                    System.out.println(BLUE + "+ " + RESET + "Quote already accepted! Do you want to decline it? (yes/no): ");
                    String declineQuote = session.getScanner().nextLine();
                    if (declineQuote.equalsIgnoreCase("yes")) {
                        quoteController.updateQuoteStatus(quoteDTO.getId().toString(), false);

                    }else {
                        System.out.println(RED + "Quote already accepted." + RESET);
                    }
                }else{
                    System.out.println(BLUE + "+ " + RESET + "Do you want to accept the quote? (yes/no): ");
                    String acceptQuote = session.getScanner().nextLine();
                    if (acceptQuote.equalsIgnoreCase("yes")) {
                        quoteController.updateQuoteStatus(quoteDTO.getId().toString(), true);
                    }else {
                        System.out.println(RED + "Quote not accepted." + RESET);
                    }
                }

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
        List<ComponentTypeDTO> materialTypes = componentTypeController.getAllComponentTypes().stream()
                .filter(componentTypeDTO -> componentTypeDTO.getType().equals(ComponentType.MATERIALS))
                .collect(Collectors.toList());

        materialTypes.forEach(System.out::println);

        System.out.println(BLUE + "+ " + RESET + "Enter Component Type Name: ");

        boolean isValidComponentType;
        do {
            componentType = session.getScanner().nextLine();

            String finalComponentType = componentType;
            isValidComponentType = materialTypes.stream()
                    .anyMatch(componentTypeDTO -> componentTypeDTO.getName().equalsIgnoreCase(finalComponentType));

            if (!isValidComponentType) {
                System.out.println(RED + "Invalid input! Please enter a valid component type name from the list." + RESET);
            }

        } while (!isValidComponentType);

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
                UUID.fromString(componentTypeController.getComponentTypeByName(componentType).getId().toString()),
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
        List<ComponentTypeDTO> workforceTypes = componentTypeController.getAllComponentTypes().stream()
                .filter(componentTypeDTO -> componentTypeDTO.getType().equals(ComponentType.WORKFORCE))
                .collect(Collectors.toList());

        workforceTypes.forEach(System.out::println);

        System.out.println(BLUE + "+ " + RESET + "Enter Component Type Name: ");

        boolean isValidComponentTypeName;
        do {
            componentType = session.getScanner().nextLine();
            String finalComponentType = componentType;
            isValidComponentTypeName = inputValidator.handleString(componentType) &&
                    workforceTypes.stream().anyMatch(componentTypeDTO -> componentTypeDTO.getName().equalsIgnoreCase(finalComponentType));

            if (!isValidComponentTypeName) {
                System.out.println(RED + "Invalid input! Please enter a valid component type name from the list." + RESET);
            }

        } while (!isValidComponentTypeName);


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
                UUID.fromString(componentTypeController.getComponentTypeByName(componentType).getId().toString()),
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


    private void saveQuote(ProjectDTO projectDTO, ProjectController projectController) throws SQLException {


        System.out.println(YELLOW + "+ Save Quote +" + RESET);

        String issueDate;
        System.out.println(BLUE + "+ " + RESET + "Enter Issue Date (yyyy-MM-dd):");
        do {
            issueDate = session.getScanner().nextLine();
            if (!DateUtils.handleDate(issueDate)) {
                System.out.println(RED + "Invalid date format. Please try again." + RESET);
            }
        } while (!DateUtils.handleDate(issueDate));

        String validityDate;
        System.out.println(BLUE + "+ " + RESET + "Enter Validity Date (yyyy-MM-dd):");
        do {
            validityDate = session.getScanner().nextLine();
            if (!DateUtils.handleDate(validityDate)) {
                System.out.println(RED + "Invalid date format. Please try again." + RESET);
            }
        } while (!DateUtils.handleDate(validityDate));

        QuoteDTO quoteDTO = new QuoteDTO(null, UUID.fromString(getProjectId()), getEstimatedCost(), DateUtils.fromDateString(issueDate), DateUtils.fromDateString(validityDate), false);

        System.out.println(YELLOW +"+ "+ RESET +"Do you want to save the quote? (yes/no): ");
        String saveQuote = session.getScanner().nextLine();

        if (saveQuote.equalsIgnoreCase("yes")) {
            quoteController.createQuote(quoteDTO);
            projectController.updateProjectStatus(projectDTO.getId().toString(), ProjectStatus.DONE);
            System.out.println(YELLOW + "Quote saved successfully." + RESET);
        } else {
            projectController.updateProjectStatus(projectDTO.getId().toString(), ProjectStatus.CANCELLED);
            System.out.println(RED + "Quote not saved." + RESET);
        }
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
