package Presentation;

import Domain.Entities.Devis;
import Domain.Entities.Projet;
import Services.DevisService;
import Utils.ConsolePrinter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class DevisView {
    private static final Scanner scanner = new Scanner(System.in);

    static public void devisMain(){
        devisLoop:
        while (true) {
            ConsolePrinter.devisMenu();
            int devisChoice = scanner.nextInt();


            switch (devisChoice) {
                case 1:
                    getAllDevis();
                    break;
                case 2:
                    //
                    break;
                case 3:
                    //
                    break;
                case 4:
                    //
                    break;
                case 5:
                    break devisLoop;
                default:
                    ConsolePrinter.printError("Invalid choice. Please try again.");
            }
        }
    }

    static public void addDevisView(Projet projet){
        System.out.print(" ==> Do you want to Create Devis? [y/n]: ");
        String devisChoice = scanner.nextLine();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String  issueDate = "";
        String validity = "";


        if(devisChoice.equals("y")){
            System.out.print(" ==> Entre the issue date  [YYYY-MM-DD]: ");
            issueDate = scanner.nextLine();
            System.out.print(" ==> Valid Until [YYYY-MM-DD]: ");
            validity = scanner.nextLine();
        }

        DevisService devisService = new DevisService();
        Devis devis = new Devis(
                null,
                projet.getTotalCost(),
                LocalDate.parse(issueDate, formatter),
                LocalDate.parse(validity, formatter),
                Boolean.FALSE,
                projet
        );

        ConsolePrinter.printDevis(devis);
        devisService.createDevis(devis);

    }

    static public void getAllDevis(){
        DevisService devisService = new DevisService();
        List<Devis> devisList =  devisService.getAllDevis();
        devisList.forEach(ConsolePrinter::printDevis);
    }

}
