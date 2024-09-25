package Presentation;

import Domain.Entities.Devis;
import Domain.Entities.Projet;
import Services.DevisService;
import Utils.ConsolePrinter;
import Utils.InputValidator;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class DevisView {
    Scanner scanner;
    InputValidator validator;
    ConsolePrinter printer;
    DevisService devisService;

    public DevisView(Scanner scanner, InputValidator validator, ConsolePrinter printer, DevisService devisService) {
        this.scanner = scanner;
        this.validator = validator;
        this.printer = printer;
        this.devisService = devisService;
    }

    public void devisMain() {
        devisLoop:
        while (true) {
            printer.devisMenu();
            int devisChoice = validator.validateInteger("");

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
                    printer.printError("Invalid choice. Please try again.");
            }
        }
    }


    public void addDevisView(Projet projet) {
        LocalDate issueDate = null;
        LocalDate validity = null;

        issueDate = validator.validateLocalDate(" ==> Enter the issue date [YYYY-MM-DD]: ");

        while (true) {
            validity = validator.validateLocalDate(" ==> Valid Until [YYYY-MM-DD]: ");


            if (validity.isBefore(LocalDate.now())) {
                System.out.println("Validity date must not be in the past. Please try again.");
                continue;
            }


            if (issueDate.isAfter(validity)) {
                System.out.println("Issue date must not be after the validity date. Please try again.");
                continue;
            }

            break;
        }


        Devis devis = new Devis(
                null,
                projet.getTotalCost(),
                issueDate,
                validity,
                Boolean.FALSE,
                projet
        );

        Devis createdDevis = devisService.createDevis(devis);
        printer.printDevis(createdDevis);
    }


    public void getAllDevis() {
        List<Devis> devisList = devisService.getAllDevis();
        devisList.forEach(devis -> printer.printDevis(devis));
    }

    public void getDevisByID() {
        int devisID = validator.validateInteger(" ==> Entre the ID of Devis: ");
        Optional<Devis> devisList = devisService.getDevisById(devisID);
        devisList.ifPresentOrElse(devis -> printer.printDevis(devis), () -> printer.printError("Devis not found."));
    }

    public void deleteDevis() {
        int devisID = validator.validateInteger(" ==> Entre the ID of Devis To Delete: ");
        boolean isDeleted = devisService.deleteDevis(devisID);

        if (isDeleted) {
            printer.printSuccess("Deleted Successfully");
        } else {
            printer.printError("Failed to Delete");
        }

    }

    public void updateDevis() {
        int devisID = validator.validateInteger(" ==> Enter the ID of Devis You want to Update: ");
        Optional<Devis> devis = devisService.getDevisById(devisID);

        if (devis.isEmpty()) {
            printer.printError("Devis not found with ID: " + devisID);
            return;
        }

        System.out.println("Current Devis details:");
        printer.printDevis(devis.get());

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
            printer.printSuccess("Devis updated successfully");
            printer.printDevis(updatedDevis);
        } else {
            printer.printError("Failed to update Devis");
        }
    }

}
