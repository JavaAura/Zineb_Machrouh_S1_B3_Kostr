package main.java.com.kostr.ui;

import main.java.com.kostr.config.Session;
import main.java.com.kostr.controllers.ComponentTypeController;
import main.java.com.kostr.dto.ComponentTypeDTO;
import main.java.com.kostr.models.enums.ComponentType;
import main.java.com.kostr.utils.InputValidator;

import java.sql.SQLException;
import java.util.UUID;

public class ComponentsMenu {
    private static Session session = Session.getInstance();

    private final InputValidator inputValidator = new InputValidator();

    public static final String RESET = "\033[0m";
    public static final String RED = "\033[0;31m";
    public static final String BLUE = "\033[0;34m";
    public static final String YELLOW = "\u001b[93m";

    private final ComponentTypeController componentTypeController;
    public ComponentsMenu(ComponentTypeController componentTypeController) throws SQLException {
        this.componentTypeController = componentTypeController;
        componentsMenu();
    }
    public void componentsMenu() throws SQLException {
        System.out.println(YELLOW + "+ Manage Components Types +" + RESET);
        System.out.println(BLUE + "+ 1. " + RESET + "Add Component Type");
        System.out.println(BLUE + "+ 2. " + RESET + "Update Component Type");
        System.out.println(BLUE + "+ 3. " + RESET + "Delete Component Type");
        System.out.println(BLUE + "+ 4. " + RESET + "View All Component Types");

        System.out.println("Please select an option: ");

        int componentOption = session.getScanner().nextInt();

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
                System.out.println(BLUE + "+ " + RESET + "Enter Component Name: ");
                do {
                    componentId = session.getScanner().nextLine();
                    if (!inputValidator.isUUID(componentId)) {
                        System.out.println(RED + "Invalid input! Please enter a valid component Name." + RESET);
                    }
                } while (!inputValidator.isUUID(componentId));

                ComponentTypeDTO componentTypeDTO2 = componentTypeController.getComponentTypeByName(componentId);

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
            default:
                System.out.println(RED + "Invalid option" + RESET);
                componentsMenu();
        }

    }
}
