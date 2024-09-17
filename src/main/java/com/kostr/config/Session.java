package main.java.com.kostr.config;

import java.util.UUID;

public class Session {
    private UUID id;
    private String name;
    private String role;

    private static Session instance = null;
    private static boolean loggedIn = false;

    private Session() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        Session.loggedIn = loggedIn;
    }
}
