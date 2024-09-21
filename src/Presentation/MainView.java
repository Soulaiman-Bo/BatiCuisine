
package Presentation;

import Domain.Entities.*;
import Domain.Enums.EtatProject;
import Domain.Enums.TypeComposant;
import Services.ClientService;
import Services.DevisService;
import Services.ProjetService;
import Utils.ConsolePrinter;
import Utils.Types.CostBreakdown;
import repositories.Client.ClientRepository;
import repositories.Client.ClientRepositoryImpl;
import repositories.Projet.ProjetRepository;
import repositories.Projet.ProjetRepositoryImpl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class MainView {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main() {
        while (true) {
            ConsolePrinter.mainMenu();
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    ProjectView.createProject();
                    break;
                case 2:
                    ProjectView.getDetailsOfProject();
                    break;
                case 3:
                    //
                    break;
                case 4:
                    ClientView.createClient();
                    break;
                case 5:
                    ClientView.acceptDevis();
                    break;
                case 6:
                    System.exit(0);
                    break;
                default:
                    ConsolePrinter.printError("Invalid choice. Please try again.");
            }
        }


    }

}
