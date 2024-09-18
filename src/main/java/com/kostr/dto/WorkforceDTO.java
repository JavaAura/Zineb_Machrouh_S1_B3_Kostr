package main.java.com.kostr.dto;

import main.java.com.kostr.types.ComponentType;

import java.util.UUID;

public class WorkforceDTO extends ComponentDTO{
    private double hourlyRate;
    private double hoursWorked;
    private double workerProductivity;

    public WorkforceDTO(UUID id, String name, ComponentType type, double vatRate, double totalPrice, UUID projectId, double hourlyRate, double hoursWorked, double workerProductivity) {
        super(id, name, type, vatRate, totalPrice, projectId);
        this.hourlyRate = hourlyRate;
        this.hoursWorked = hoursWorked;
        this.workerProductivity = workerProductivity;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }
    public void setHourlyRate(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public double getHoursWorked() {
        return hoursWorked;
    }
    public void setHoursWorked(double hoursWorked) {
        this.hoursWorked = hoursWorked;
    }

    public double getWorkerProductivity() {
        return workerProductivity;
    }
    public void setWorkerProductivity(double workerProductivity) {
        this.workerProductivity = workerProductivity;
    }

    public String[] getAttributes() {
        return new String[]{getId().toString(), getName(), getType().toString(), String.valueOf(getVatRate()), String.valueOf(getTotalPrice()), getProjectId().toString(), String.valueOf(hourlyRate), String.valueOf(hoursWorked), String.valueOf(workerProductivity)};
    }

    @Override
    public String toString() {
        return super.toString() +
                ", hourlyRate=" + hourlyRate +
                ", hoursWorked=" + hoursWorked +
                ", workerProductivity=" + workerProductivity +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WorkforceDTO)) return false;
        if (!super.equals(o)) return false;
        WorkforceDTO that = (WorkforceDTO) o;
        return Double.compare(that.hourlyRate, hourlyRate) == 0 &&
                Double.compare(that.hoursWorked, hoursWorked) == 0 &&
                Double.compare(that.workerProductivity, workerProductivity) == 0;
    }
}
