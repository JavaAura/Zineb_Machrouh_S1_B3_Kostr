package main.java.com.kostr.dto;

import main.java.com.kostr.models.Workforce;
import main.java.com.kostr.models.enums.ComponentType;

import java.util.UUID;

public class WorkforceDTO extends ComponentDTO{
    private double hourlyRate;
    private double hoursWorked;
    private double workerProductivity;

    public WorkforceDTO(){}

    public WorkforceDTO(UUID id, String name, UUID type, double vatRate, double totalPrice, UUID projectId, double hourlyRate, double hoursWorked, double workerProductivity) {
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

    @Override
    public String toString() {
        return super.toString() +
                ", hourlyRate=" + hourlyRate + "$/h"+
                ", hoursWorked=" + hoursWorked + "h" +
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

    public Workforce dtoToModel(){
        return new Workforce(getId(), getName(), getType(), getVatRate(), getTotalPrice(), getProjectId(), hourlyRate, hoursWorked, workerProductivity);
    }

    public static WorkforceDTO modelToDTO(Workforce workforce){
        return new WorkforceDTO(workforce.getId(), workforce.getName(), workforce.getType(), workforce.getVatRate(), workforce.getTotalPrice(), workforce.getProjectId(), workforce.getHourlyRate(), workforce.getHoursWorked(), workforce.getWorkerProductivity());
    }
}
