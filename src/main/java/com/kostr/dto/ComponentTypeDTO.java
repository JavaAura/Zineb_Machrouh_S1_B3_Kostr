package main.java.com.kostr.dto;

import main.java.com.kostr.models.enums.ComponentType;

import java.util.Objects;
import java.util.UUID;

public class ComponentTypeDTO {
    private UUID id;
    private String name;
    private ComponentType type;

    public ComponentTypeDTO(){}

    public ComponentTypeDTO(UUID id, String name, ComponentType type) {
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

    public ComponentType getType() {
        return type;
    }
    public void setType(ComponentType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "ComponentTypeDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ComponentTypeDTO that = (ComponentTypeDTO) o;

        if (!Objects.equals(id, that.id)) return false;
        if (!Objects.equals(name, that.name)) return false;
        return type == that.type;
    }

    public main.java.com.kostr.models.ComponentType dtoToModel() {
        return new main.java.com.kostr.models.ComponentType(id, name, type);
    }

    public static ComponentTypeDTO modelToDTO(main.java.com.kostr.models.ComponentType model) {
        return new ComponentTypeDTO(model.getId(), model.getName(), model.getType());
    }
}
