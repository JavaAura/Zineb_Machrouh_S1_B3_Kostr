package main.java.com.kostr.config;

import java.util.Random;
import java.util.Scanner;
import java.util.UUID;

public class Session {
    private UUID id;
    private boolean isProfesional;

    private static Session instance = null;


    private Session() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public boolean isProfesional() {
        return isProfesional;
    }

    public void setProfesional(boolean isProfesional) {
        this.isProfesional = isProfesional;
    }

    public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }


    public Scanner getScanner() {
        return new Scanner(System.in);
    }


}
