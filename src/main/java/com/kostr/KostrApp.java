package main.java.com.kostr;

import main.java.com.kostr.config.DatabaseConnection;
import main.java.com.kostr.ui.ConsoleUI;

import java.sql.Connection;
import java.sql.SQLException;

public class KostrApp {
    public static void main(String[] args) throws SQLException {
        Connection connection = DatabaseConnection.getConnection();
        ConsoleUI consoleUI = new ConsoleUI(connection);
    }
}
