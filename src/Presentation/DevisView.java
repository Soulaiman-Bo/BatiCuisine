package Presentation;

import Domain.Entities.Devis;
import Domain.Entities.Projet;
import Services.DevisService;
import Utils.ConsolePrinter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class DevisView {
    private static final Scanner scanner = new Scanner(System.in);

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

}
