package Presentation;

import Domain.Entities.Client;
import Domain.Entities.Devis;
import Domain.Entities.Projet;
import Domain.Enums.EtatProject;
import Services.ClientService;
import Services.DevisService;
import Services.ProjetService;
import Utils.ConsolePrinter;
import Utils.InputValidator;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;

public class ClientView {

    Scanner scanner;
    InputValidator validator;
    ClientService clientService;
    DevisService devisService;
    ProjetService projetService;
    ConsolePrinter consolePrinter;

    public ClientView(Scanner scanner, InputValidator validator, ClientService clientService, DevisService devisService, ProjetService projetService, ConsolePrinter consolePrinter) {
        this.scanner = scanner;
        this.validator = validator;
        this.clientService = clientService;
        this.devisService = devisService;
        this.projetService = projetService;
        this.consolePrinter = consolePrinter;
    }

    public void clientMain() {
        clientLoop:
        while (true) {
            consolePrinter.clientMenu();
            int clientChoice = validator.validateInteger("");

            switch (clientChoice) {
                case 1:
                    getAllClients();
                    break;
                case 2:
                    getClientById();
                    break;
                case 3:
                    deleteClient();
                    break;
                case 4:
                    updateClient();
                    break;
                case 5:
                    break clientLoop;
                default:
                    consolePrinter.printError("Invalid choice. Please try again.");
            }
        }
    }

    public void createClient() {
        String name = validator.validateString(" ==> Entre FullName: ");
        String address = validator.validateString(" ==> Entre Address: ");
        String phoneNumber = validator.validateString(" ==> Entre Phone Number: ");
        String isProfessional = validator.validateYesNo(" ==> is Professional ?");

        Boolean ispro = isProfessional.equals("y") ? Boolean.TRUE : Boolean.FALSE;
        clientService.createClient(new Client(null, name, address, phoneNumber, ispro));
        System.out.println("Client created Successfully");
    }

    public void acceptDevis() {
        Integer clientId = validator.validateInteger(" ==> Entre Client's ID: ");
        Optional<Client> client = clientService.getClientById(clientId);

        if (client.isPresent()) {
            consolePrinter.printClient(client.get());

            List<Devis> devisList = devisService.getDevisWithProject(client.get());

            for (Devis devis : devisList) {
                devis.getProjet().setClient(client.get());
                consolePrinter.printDevis(devis);
            }

            String saveChoice = validator.validateYesNo(" ==> Do you want to accept a devis?");
            if (saveChoice.equals("y")) {

                Integer devisId = validator.validateInteger(" ==> Enter Devis ID [To Accept]: ");
                Devis currentdevis = devisList.stream()
                        .filter(devis -> Objects.equals(devis.getId(), devisId))
                        .findFirst()
                        .orElse(null);


                if (currentdevis != null && !currentdevis.getAccepted()) {
                    try {

                        Devis returnedDevis = devisService.acceptDevis(currentdevis);
                        Projet projet = currentdevis.getProjet();
                        projet.setProjectStatus(EtatProject.INPROGRESS);
                        projetService.updateProjet(projet);

                        consolePrinter.printSuccess("Devis Accepted Successfully: ID " + returnedDevis.getId());
                    } catch (Exception e) {
                        consolePrinter.printError(e.getMessage());
                    }

                } else {
                    consolePrinter.printError("Devis Already Accepted");
                }
            }

        } else {
            System.out.println("Client not found");
        }

    }

    public void getAllClients() {
        List<Client> clientList = clientService.getAllClients();
        clientList.forEach(client -> consolePrinter.printClient(client));
    }

    public void getClientById() {
        int clientID = validator.validateInteger(" ==> Entre the ID of The Client: ");
        Optional<Client> clientList = clientService.getClientById(clientID);
        clientList.ifPresentOrElse(client -> consolePrinter.printClient(client), () -> consolePrinter.printError("Client not found."));
    }

    public void deleteClient() {
        int clientId = validator.validateInteger(" ==> Entre the ID of Client To Delete: ");
        boolean isDeleted = clientService.deleteClient(clientId);

        if (isDeleted) {
            consolePrinter.printSuccess("Deleted Successfully");
        } else {
            consolePrinter.printError("Failed to Delete");
        }
    }

    public void updateClient() {
        System.out.print(" ==> Enter the ID of Client you want to Update: ");
        int clientID = scanner.nextInt();
        scanner.nextLine();

        Optional<Client> client = clientService.getClientById(clientID);

        if (client.isEmpty()) {
            consolePrinter.printError("Client not found with ID: " + clientID);
            return;
        }

        System.out.println("Current Client details:");
        consolePrinter.printClient(client.get());

        System.out.println("\nEnter new values for the fields you want to update (press Enter to skip):");

        System.out.print("Name (current: " + client.get().getName() + "): ");
        String nameInput = scanner.nextLine();
        if (!nameInput.isEmpty()) {
            client.get().setName(nameInput);
        }

        System.out.print("Address (current: " + client.get().getAddress() + "): ");
        String addressInput = scanner.nextLine();
        if (!addressInput.isEmpty()) {
            client.get().setAddress(addressInput);
        }

        System.out.print("Phone Number (current: " + client.get().getPhoneNumber() + "): ");
        String phoneNumberInput = scanner.nextLine();
        if (!phoneNumberInput.isEmpty()) {
            client.get().setPhoneNumber(phoneNumberInput);
        }

        System.out.print("Is Professional (current: " + client.get().getProfessional() + ") (true/false): ");
        String isProfessionalInput = scanner.nextLine();
        if (!isProfessionalInput.isEmpty()) {
            client.get().setProfessional(Boolean.parseBoolean(isProfessionalInput));
        }

        Client updatedClient = clientService.updateClient(client.get());

        if (updatedClient != null) {
            consolePrinter.printSuccess("Client updated successfully");
        } else {
            consolePrinter.printError("Failed to update Client");
        }
    }

}
