package main.java.com.kostr.models;

public class Workforce {
    private double hourlyRate;
    private double hoursWorked;
    private double workerProductivity;

    public Workforce(){}

    public Workforce( double hourlyRate, double hoursWorked, double workerProductivity) {
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
}
