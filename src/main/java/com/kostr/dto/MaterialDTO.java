package main.java.com.kostr.dto;

import main.java.com.kostr.models.enums.ComponentType;

import java.util.UUID;

public class MaterialDTO extends ComponentDTO{
    private double unitCost;
    private double quantity;
    private double transportCost;
    private double qualityCoefficient;

    public MaterialDTO(){}

    public MaterialDTO(UUID id, String name, ComponentType type, double vatRate, double totalPrice, UUID projectId, double unitCost, double quantity, double transportCost, double qualityCoefficient) {
        super(id, name, type, vatRate, totalPrice, projectId);
        this.unitCost = unitCost;
        this.quantity = quantity;
        this.transportCost = transportCost;
        this.qualityCoefficient = qualityCoefficient;
    }

    public double getUnitCost() {
        return unitCost;
    }
    public void setUnitCost(double unitCost) {
        this.unitCost = unitCost;
    }

    public double getQuantity() {
        return quantity;
    }
    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getTransportCost() {
        return transportCost;
    }
    public void setTransportCost(double transportCost) {
        this.transportCost = transportCost;
    }

    public double getQualityCoefficient() {
        return qualityCoefficient;
    }
    public void setQualityCoefficient(double qualityCoefficient) {
        this.qualityCoefficient = qualityCoefficient;
    }

    public String[] getAttributes() {
        return new String[]{getId().toString(), getName(), getType().toString(), String.valueOf(getVatRate()), String.valueOf(getTotalPrice()), getProjectId().toString(), String.valueOf(unitCost), String.valueOf(quantity), String.valueOf(transportCost), String.valueOf(qualityCoefficient)};
    }

    @Override
    public String toString() {
        return super.toString() +
                ", unitCost=" + unitCost +
                ", quantity=" + quantity +
                ", transportCost=" + transportCost +
                ", qualityCoefficient=" + qualityCoefficient +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MaterialDTO)) return false;
        if (!super.equals(o)) return false;
        MaterialDTO that = (MaterialDTO) o;
        return Double.compare(that.getUnitCost(), getUnitCost()) == 0 &&
                Double.compare(that.getQuantity(), getQuantity()) == 0 &&
                Double.compare(that.getTransportCost(), getTransportCost()) == 0 &&
                Double.compare(that.getQualityCoefficient(), getQualityCoefficient()) == 0;
    }
}
