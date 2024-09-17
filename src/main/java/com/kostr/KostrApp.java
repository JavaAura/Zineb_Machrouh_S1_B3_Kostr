package main.java.com.kostr;

import main.java.com.kostr.config.DatabaseConnection;
import main.java.com.kostr.ui.ConsoleUI;

import java.sql.Connection;

public class KostrApp {
    Connection connection = DatabaseConnection.getConnection();
    ConsoleUI consoleUI = new ConsoleUI(connection);
}
