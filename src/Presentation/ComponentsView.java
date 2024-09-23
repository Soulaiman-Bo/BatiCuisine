package Presentation;

import Domain.Entities.MainDoeuvre;
import Domain.Entities.Materiaux;
import Domain.Enums.TypeComposant;
import Utils.InputValidator;

import java.util.Scanner;

public class ComponentsView {

    private static final Scanner scanner = new Scanner(System.in);
    private static final InputValidator validator = new InputValidator();

    static public Materiaux addMaterialsView(){
        String materialName = validator.validateString(" ==> Entre the name of the Material: ");
        Double unitCost = validator.validateDouble(" ==> Entre the Unit Cost of the material [MAD]: ");
        Double quantity = validator.validateDouble(" ==> Entre The Quantity of the Material: ");
        Double taxRate = validator.validateDouble(" ==> Tax Rate [%]: ");
        Double transport = validator.validateDouble(" ==> Add Transport Cost [MAD]: ");;
        Double coefficient = validator.validateDouble(" ==> Add Material quality coefficient (1.0 = standard > 1.0 = high quality: ");

        return new Materiaux(materialName, taxRate, TypeComposant.MATERIEL, null,  unitCost, quantity, transport, coefficient);
    }

    static public MainDoeuvre addLaborView(){
        String workerType = validator.validateString(" ==> Entre The Type of the worker : ");
        Double taxRate = validator.validateDouble(" ==> Add the Tax Rate [%]: ");
        Double hourlyRate = validator.validateDouble(" ==> Entre Hourly Rate of the Labor [MAD]: ");
        Double worKHoursCount = validator.validateDouble(" ==> Entre the number od working hours [Hours]: ");
        Double coefficient = validator.validateDouble(" ==> Add Labor Productivity factor (1.0 = standard > 1.0 = high quality: ");

        return new MainDoeuvre(workerType, taxRate, TypeComposant.MAINDOUVRE, null,  hourlyRate, worKHoursCount, coefficient);

    }


}
