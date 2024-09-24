package main.java.com.kostr.utils;

public class CostCalculator {
    public double materialCost(double unitCost, double quantity, double qualityCoefficient, double transportCost) {
        return (unitCost * quantity * qualityCoefficient) + transportCost;
    }

    public double workforceCost(double hourlyRate, double hours , double productivity) {
        return hourlyRate * hours * productivity;
    }


    public double totalCostWithVAT(double price, double VAT) {
        return price *  (1 + VAT/100);
    }

    public double totalCostMargin(double price, double margin) {
        return price * (0 + margin/100);
    }

    public double totalCostWithDiscount(int price, int projects, boolean isProfessional) {
        if (isProfessional){
            if (projects == 0){
                return price;
            } else if (projects>= 1 && projects <= 3){
                return price * 0.90;
            }else if (projects > 4 && projects <= 7) {
                return price * 0.85;
            } else if (projects > 8 && projects <= 10) {
                return price * 0.80;
            }else {
                return price * 0.75;
            }
        }else {
            if (projects == 0){
                return price;
            } else if (projects>= 1 && projects <= 3){
                return price * 0.98;
            }else if (projects > 4 && projects <= 7) {
                return price * 0.94;
            } else if (projects > 8 && projects <= 10) {
                return price * 0.90;
            }else {
                return price * 0.86;
            }
        }

    }
}
