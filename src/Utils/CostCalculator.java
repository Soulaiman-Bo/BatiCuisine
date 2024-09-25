package Utils;

import Domain.Entities.Composants;
import Domain.Entities.MainDoeuvre;
import Domain.Entities.Materiaux;
import Utils.Types.CostBreakdown;

import java.util.List;

public class CostCalculator {

    public static CostBreakdown calculateCost(List<Composants> composants) {
        return composants.stream()
                .map(CostCalculator::calculateBaseCost)
                .reduce(new CostBreakdown(0, 0), (subtotal, element) -> new CostBreakdown(
                        subtotal.getBaseCost() + element.getBaseCost(),
                        subtotal.getTaxAmount() + element.getTaxAmount()
                ));
    }

    public static CostBreakdown calculateBaseCost(Composants composant) {
        if (composant instanceof Materiaux materiaux) {
            return calculateBaseCostOfMateriaux(materiaux);
        } else if (composant instanceof MainDoeuvre mainDoeuvre) {
            return calculateBaseCostOfMainDoeuvre(mainDoeuvre);
        }
        return null;
    }

    public static CostBreakdown calculateBaseCostOfMateriaux(Materiaux materiaux) {
        double baseCost = materiaux.getUnitCost()
                          * materiaux.getQuantity()
                          * materiaux.getQualityCoefficient()
                          + materiaux.getTransportCost();
        double taxAmount = baseCost * (materiaux.getTaxRate() / 100);
        return new CostBreakdown(baseCost, taxAmount);
    }

    public static CostBreakdown calculateBaseCostOfMainDoeuvre(MainDoeuvre mainDoeuvre) {
        double baseCost = mainDoeuvre.getHourlyRate()
                          * mainDoeuvre.getWorkHoursCount()
                          * mainDoeuvre.getProductivityRate();
        double taxAmount = baseCost * (mainDoeuvre.getTaxRate() / 100);
        return new CostBreakdown(baseCost, taxAmount);
    }

}
