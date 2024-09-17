package main.java.com.kostr.ui;

import main.java.com.kostr.config.Session;

import java.sql.Connection;
import java.sql.SQLException;

public class ConsoleUI {
    private final Connection connection;
    private static Session session = Session.getInstance();


    public ConsoleUI(Connection connection) throws SQLException {
        this.connection = connection;

        menu();
    }

    public void menu(){
        System.out.println("Helooooooooooo Kostr!");
    }
}
