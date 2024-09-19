
package Presentation;

import Entities.*;
import Enums.EtatProject;
import Enums.TypeComposant;
import Services.ClientService;
import Services.ProjetService;
import Utils.ConsolePrinter;
import Utils.Types.CostBreakdown;
import repositories.Client.ClientRepository;
import repositories.Client.ClientRepositoryImpl;
import repositories.Projet.ProjetRepository;
import repositories.Projet.ProjetRepositoryImpl;

import java.util.List;
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

            Projet projet = new Projet(null, projectName, profit, null, null);
            projet.setClient(client.get());

            materialsLoop:
            while (true){
                scanner.nextLine();
                System.out.print(" ==> Do you want to add Materials to this Project? [y/n]: ");
                String componentsChoice = scanner.nextLine();
                switch (componentsChoice){
                    case "y":
                        Materiaux materiaux =  addMaterialsView();
                        projet.getComposants().add(materiaux);
                        ConsolePrinter.printSuccess("Material Has Been Added Successfully");
                        break;
                    case "n":
                        break materialsLoop;
                }
            }

            laborLoop:
            while (true){
                scanner.nextLine();
                System.out.print(" ==> Do you want to add Labor to this Project? [y/n]: ");
                String laborChoice = scanner.nextLine();
                switch (laborChoice){
                    case "y":
                        MainDoeuvre mainDoeuvre =  addLaborView();
                        projet.getComposants().add(mainDoeuvre);
                        ConsolePrinter.printSuccess("Labor Has Been Added Successfully");
                        break;
                    case "n":
                        break laborLoop;
                }
            }


        }else {
            System.out.println("Client not found");
        }

    }

    static private Materiaux addMaterialsView(){
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

    static private MainDoeuvre addLaborView(){
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
