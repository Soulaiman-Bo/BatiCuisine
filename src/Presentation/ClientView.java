package Presentation;

import Domain.Entities.*;
import Domain.Enums.EtatProject;
import Services.ClientService;
import Services.DevisService;
import Services.ProjetService;
import Utils.ConsolePrinter;
import Utils.InputValidator;
import Utils.Types.CostBreakdown;
import repositories.Client.ClientRepository;
import repositories.Client.ClientRepositoryImpl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;

public class ClientView {

    private static final Scanner scanner = new Scanner(System.in);
    private static final InputValidator validator = new InputValidator();

    static public void clientMain(){
        clientLoop:
        while (true) {
            ConsolePrinter.clientMenu();
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
                    // Update Client
                    updateClient();
                    break;
                case 5:
                    break clientLoop;
                default:
                    ConsolePrinter.printError("Invalid choice. Please try again.");
            }
        }
    }

    static public void createClient(){
        String name = validator.validateString(" ==> Entre FullName: ");
        String address = validator.validateString(" ==> Entre Address: ");
        String phoneNumber = validator.validateString(" ==> Entre Phone Number: ");
        String isProfessional = validator.validateYesNo(" ==> is Professional ?");

        Boolean ispro = isProfessional.equals("y") ? Boolean.TRUE : Boolean.FALSE;
        ClientRepository clientRepository = new ClientRepositoryImpl();
        ClientService clientService = new ClientService(clientRepository);
        clientService.createClient(new Client(null, name, address, phoneNumber, ispro));
        System.out.println("Client created Successfully");

    }

    static public void acceptDevis(){
        Integer clientId = validator.validateInteger(" ==> Entre Client's ID: ");

        ClientRepository clientRepository = new ClientRepositoryImpl();
        ClientService clientService = new ClientService(clientRepository);
        Optional<Client> client =  clientService.getClientById(clientId);

        if(client.isPresent()){
            ConsolePrinter.printClient(client.get());

            DevisService devisService = new DevisService();
            List<Devis> devisList = devisService.getDevisWithProject(client.get());

            for(Devis devis : devisList){
                ConsolePrinter.printDevis(devis);
            }
            String saveChoice = validator.validateYesNo(" ==> Do you want to accept a devis?");
            if(saveChoice.equals("y")){

                Integer devisId = validator.validateInteger(" ==> Enter Devis ID [To Accept]: ");
                Devis currentdevis = devisList.stream()
                        .filter(devis -> Objects.equals(devis.getId(), devisId))
                        .findFirst()
                        .orElse(null);

                assert currentdevis != null;
                if(!currentdevis.getAccepted()){
                    try {
                        Devis returnedDevis = devisService.acceptDevis(currentdevis);
                        ConsolePrinter.printSuccess("Devis Accepted Successfully: ID " + returnedDevis.getId());
                    } catch (Exception e){
                        ConsolePrinter.printError(e.getMessage());
                    }

                }else {
                    ConsolePrinter.printError("Devis Already Accepted");
                }
            }

        }else {
            System.out.println("Client not found");
        }

    }

    static public void getAllClients(){
        ClientRepository clientRepository = new ClientRepositoryImpl();
        ClientService clientService = new ClientService(clientRepository);
        List<Client> clientList =  clientService.getAllClients();
        clientList.forEach(ConsolePrinter::printClient);
    }

    static public void getClientById(){
        int clientID = validator.validateInteger(" ==> Entre the ID of The Client: ");
        ClientRepository clientRepository = new ClientRepositoryImpl();
        ClientService clientService = new ClientService(clientRepository);
        Optional<Client> clientList =  clientService.getClientById(clientID);
        clientList.ifPresentOrElse(ConsolePrinter::printClient, () -> ConsolePrinter.printError("Client not found."));
    }

    static public void deleteClient(){
        int clientId = validator.validateInteger(" ==> Entre the ID of Client To Delete: ");

        ClientRepository clientRepository = new ClientRepositoryImpl();
        ClientService clientService = new ClientService(clientRepository);
        boolean isDeleted =  clientService.deleteClient(clientId);

        if (isDeleted) {
            ConsolePrinter.printSuccess("Deleted Successfully");
        } else {
            ConsolePrinter.printError("Failed to Delete");
        }
    }

    public static void updateClient() {
        System.out.print(" ==> Enter the ID of Client you want to Update: ");
        int clientID = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        ClientRepository clientRepository = new ClientRepositoryImpl();
        ClientService clientService = new ClientService(clientRepository);
        Optional<Client> client = clientService.getClientById(clientID);

        if (client.isEmpty()) {
            ConsolePrinter.printError("Client not found with ID: " + clientID);
            return;
        }

        System.out.println("Current Client details:");
        ConsolePrinter.printClient(client.get());

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
            ConsolePrinter.printSuccess("Client updated successfully");
        } else {
            ConsolePrinter.printError("Failed to update Client");
        }
    }

}
