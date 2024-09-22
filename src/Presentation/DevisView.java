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
                    getDevisByID();
                    break;
                case 3:
                    deleteDevis();
                    break;
                case 4:
                    updateDevis();
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

    static public void  getDevisByID(){
        System.out.print(" ==> Entre the ID of Devis: ");
        int devisID = scanner.nextInt();

        DevisService devisService = new DevisService();
        Optional<Devis> devisList =  devisService.getDevisById(devisID);
        devisList.ifPresentOrElse(ConsolePrinter::printDevis, () -> ConsolePrinter.printError("Devis not found."));
    }

    static public void  deleteDevis(){
        System.out.print(" ==> Entre the ID of Devis To Delete: ");
        int devisID = scanner.nextInt();

        DevisService devisService = new DevisService();
        boolean isDeleted =  devisService.deleteDevis(devisID);

        if (isDeleted) {
            ConsolePrinter.printSuccess("Deleted Successfully");
        } else {
            ConsolePrinter.printError("Failed to Delete");
        }

    }

    static public void updateDevis() {
        System.out.print(" ==> Enter the ID of Devis You want to Update: ");
        int devisID = scanner.nextInt();
        scanner.nextLine();

        DevisService devisService = new DevisService();
        Optional<Devis> devis = devisService.getDevisById(devisID);

        if (devis.isEmpty()) {
            ConsolePrinter.printError("Devis not found with ID: " + devisID);
            return;
        }

        System.out.println("Current Devis details:");
        ConsolePrinter.printDevis(devis.get());

        System.out.println("\nEnter new values for the fields you want to update (press Enter to skip):");

        System.out.print("Issue Date (current: " + devis.get().getIssueDate() + ") (yyyy-MM-dd): ");
        String issueDateInput = scanner.nextLine();
        if (!issueDateInput.isEmpty()) {
            devis.get().setIssueDate(LocalDate.parse(issueDateInput));
        }

        System.out.print("Validity Date (current: " + devis.get().getValidityDate() + ") (yyyy-MM-dd): ");
        String validityDateInput = scanner.nextLine();
        if (!validityDateInput.isEmpty()) {
            devis.get().setValidityDate(LocalDate.parse(validityDateInput));
        }

        System.out.print("Accepted (current: " + devis.get().getAccepted() + ") (true/false): ");
        String acceptedInput = scanner.nextLine();
        if (!acceptedInput.isEmpty()) {
            devis.get().setAccepted(Boolean.parseBoolean(acceptedInput));
        }

        Devis updatedDevis = devisService.updateDevis(devis.get());

        if (updatedDevis != null) {
            ConsolePrinter.printSuccess("Devis updated successfully");
            ConsolePrinter.printDevis(updatedDevis);
        } else {
            ConsolePrinter.printError("Failed to update Devis");
        }
    }

}
