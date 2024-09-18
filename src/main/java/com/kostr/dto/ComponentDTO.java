package main.java.com.kostr.dto;

import main.java.com.kostr.types.ComponentType;

import java.util.UUID;

public class ComponentDTO {
    private UUID id;
    private String name;
    private ComponentType type;
    private double vatRate;
    private double totalPrice;
    private UUID projectId;

    public ComponentDTO(){}

    public ComponentDTO(UUID id, String name, ComponentType type, double vatRate, double totalPrice, UUID projectId) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.vatRate = vatRate;
        this.totalPrice = totalPrice;
        this.projectId = projectId;
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

    public double getVatRate() {
        return vatRate;
    }
    public void setVatRate(double vatRate) {
        this.vatRate = vatRate;
    }

    public double getTotalPrice() {
        return totalPrice;
    }
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public UUID getProjectId() {
        return projectId;
    }
    public void setProjectId(UUID projectId) {
        this.projectId = projectId;
    }

    public String[] getAttributes() {
        return new String[]{id.toString(), name, type.toString(), String.valueOf(vatRate), String.valueOf(totalPrice), projectId.toString()};
    }

    @Override
    public String toString() {
        return "ComponentDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", vatRate=" + vatRate +
                ", totalPrice=" + totalPrice +
                ", projectId=" + projectId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ComponentDTO)) return false;

        ComponentDTO that = (ComponentDTO) o;

        if (Double.compare(that.vatRate, vatRate) != 0) return false;
        if (Double.compare(that.totalPrice, totalPrice) != 0) return false;
        if (!id.equals(that.id)) return false;
        if (!name.equals(that.name)) return false;
        if (type != that.type) return false;
        return projectId.equals(that.projectId);
    }

}
