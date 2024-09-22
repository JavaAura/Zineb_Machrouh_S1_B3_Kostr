package main.java.com.kostr.models;

import java.util.UUID;

public class ComponentType {
    private UUID id;
    private String name;
    private main.java.com.kostr.models.enums.ComponentType type;

    public ComponentType(){}

    public ComponentType(UUID id, String name, main.java.com.kostr.models.enums.ComponentType type) {
        this.id = id;
        this.name = name;
        this.type = type;
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

    public main.java.com.kostr.models.enums.ComponentType getType() {
        return type;
    }
    public void setType(main.java.com.kostr.models.enums.ComponentType type) {
        this.type = type;
    }
}
