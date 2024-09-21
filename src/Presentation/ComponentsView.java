package Presentation;

import Domain.Entities.MainDoeuvre;
import Domain.Entities.Materiaux;
import Domain.Enums.TypeComposant;

import java.util.Scanner;

public class ComponentsView {

    private static final Scanner scanner = new Scanner(System.in);

    static public Materiaux addMaterialsView(){
        System.out.print(" ==> Entre the name of the Material: ");
        String materialName = scanner.nextLine();

        System.out.print(" ==> Entre the Unit Cost of the material [MAD]: ");
        Double unitCost = scanner.nextDouble();

        System.out.print(" ==> Entre The Quantity of the Material: ");
        Double quanitity = scanner.nextDouble();

        System.out.print(" ==> Add the Tax Rate [%]: ");
        Double taxRate = scanner.nextDouble();

        System.out.print(" ==> Add Transport Cost [MAD]: ");
        Double transport = scanner.nextDouble();

        System.out.print(" ==> Add Material quality coefficient (1.0 = standard > 1.0 = high quality: ");
        Double coefficient = scanner.nextDouble();

        return new Materiaux(materialName, taxRate, TypeComposant.MATERIEL, null,  unitCost, quanitity, transport, coefficient);

    }

    static public MainDoeuvre addLaborView(){
        System.out.print(" ==> Entre the Type of the worker : ");
        String workerType = scanner.nextLine();

        System.out.print(" ==> Add the Tax Rate [%]: ");
        Double taxRate = scanner.nextDouble();

        System.out.print(" ==> Entre Hourly Rate of the Labor [MAD]: ");
        Double hourlyRate = scanner.nextDouble();

        System.out.print(" ==> Entre the number od working hours [Hours]: ");
        Double worKHoursCount = scanner.nextDouble();

        System.out.print(" ==> Add Labor Productivity factor (1.0 = standard > 1.0 = high quality: ");
        Double coefficient = scanner.nextDouble();

        return new MainDoeuvre(workerType, taxRate, TypeComposant.MAINDOUVRE, null,  hourlyRate, worKHoursCount, coefficient);

    }

}
