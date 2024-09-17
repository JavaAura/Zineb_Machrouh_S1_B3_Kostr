package main.java.com.kostr.utils;

public class CostCalculator {
    public double materialCost(double unitCost, double quantity, double qualityCoefficient, double transportCost) {
        return (unitCost * quantity * qualityCoefficient) + transportCost;
    }

    public double workforceCost(double hourlyRate, double hours , double productivity) {
        return hourlyRate * hours * productivity;
    }

    public double totalCostWithoutVAT(double materialCostWithoutVAT, double workforceCostWithoutVAT) {
        return materialCostWithoutVAT + workforceCostWithoutVAT;
    }

    public double totalCostWithVAT(double totalCostWithoutVAT, double VAT) {
        return totalCostWithoutVAT *  (1 + VAT/100);
    }

    public double totalCostWithMargin(double totalCostWithoutMargin, double margin) {
        return totalCostWithoutMargin * (0 + margin/100);
    }
}
