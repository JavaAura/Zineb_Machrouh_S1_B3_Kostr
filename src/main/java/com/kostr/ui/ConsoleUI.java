package main.java.com.kostr.ui;

import main.java.com.kostr.config.Session;
import main.java.com.kostr.services.MaterialService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;

public class ConsoleUI {
    private final Connection connection;
    private static Session session = Session.getInstance();
    private static final Logger logger = Logger.getLogger(ConsoleUI.class.getName());

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

    public void menu(){
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
        System.out.println(BLUE + "++++++++++++++++++++++++++++++++++++++++++" + RESET);

        System.out.println("Please select an option: ");
        int option = session.getScanner().nextInt();

        switch (option) {
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                break;
            case 8:
                break;
            default:
                System.out.println(RED+ "Invalid option. Please try again."+ RESET);
                menu();
        }

    }
}
