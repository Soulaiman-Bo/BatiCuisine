package Presentation;

import Entities.*;
import Enums.EtatProject;
import Services.ClientService;
import Services.ProjetService;
import Utils.ConsolePrinter;
import repositories.Client.ClientRepository;
import repositories.Client.ClientRepositoryImpl;
import repositories.Projet.ProjetRepository;
import repositories.Projet.ProjetRepositoryImpl;

import java.util.Optional;
import java.util.Scanner;

public class MainView {
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

    static public void createProject(){
        System.out.print(" ==> Entre Client's ID: ");
        Integer clientId = scanner.nextInt();
        scanner.nextLine();

        ClientRepository clientRepository = new ClientRepositoryImpl();
        ClientService clientService = new ClientService(clientRepository);
        Optional<Client> client =  clientService.getClientById(clientId);


        if(client.isPresent()){
            ConsolePrinter.printClient(client.get());

            System.out.print(" ==> Entre Project Name: ");
            String projectName = scanner.nextLine();
            System.out.print(" ==> Entre Profit Margin (%): ");
            Double profit = scanner.nextDouble();
            scanner.nextLine();

            ProjetRepository projetRepository = new ProjetRepositoryImpl();
            ProjetService projetService = new ProjetService(projetRepository);

            Projet projet = new Projet(null, projectName, profit, 0.0, EtatProject.INPROGRESS);
            projet.setClient(client.get());

            System.out.print(" ==> Do you want to save this project? [y/n]: ");
            String saveChoice = scanner.nextLine();

            if(saveChoice.equals("y")){
                projetService.createProjet(projet);
            }

        }else {
            System.out.println("Client not found");
        }

    }

}
