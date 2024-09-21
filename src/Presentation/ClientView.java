package Presentation;

import Domain.Entities.Client;
import Domain.Entities.Devis;
import Domain.Entities.Materiaux;
import Services.ClientService;
import Services.DevisService;
import Utils.ConsolePrinter;
import repositories.Client.ClientRepository;
import repositories.Client.ClientRepositoryImpl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;

public class ClientView {

    private static final Scanner scanner = new Scanner(System.in);

    static public void createClient(){
        System.out.print(" ==> Entre FullName: ");
        String name = scanner.nextLine();
        System.out.print(" ==> Entre Address: ");
        String address = scanner.nextLine();
        System.out.print(" ==> Entre Phone Number: ");
        String phoneNumber = scanner.nextLine();
        System.out.print(" ==> is Professional ? (n/y): ");
        String isProfessional = scanner.nextLine();


        Boolean ispro = isProfessional.equals("y") ? Boolean.TRUE : Boolean.FALSE;
        ClientRepository clientRepository = new ClientRepositoryImpl();
        ClientService clientService = new ClientService(clientRepository);
        clientService.createClient(new Client(null, name, address, phoneNumber, ispro));
        System.out.println("Client created Successfully");

    }

    static public void acceptDevis(){
        System.out.print(" ==> Entre Client's ID: ");
        Integer clientId = scanner.nextInt();
        scanner.nextLine();

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

            System.out.print(" ==> Do you want to accept a devis? [y/n]: ");
            String saveChoice = scanner.nextLine();

            if(saveChoice.equals("y")){
                System.out.print(" ==> Enter Devis ID [To Accept]: ");
                Integer devisId = scanner.nextInt();
                scanner.nextLine();

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

}
