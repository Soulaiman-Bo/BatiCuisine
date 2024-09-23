package Presentation;

import Utils.ConsolePrinter;

import java.util.Scanner;

public class MainView {

    ProjectView projectView;
    ClientView clientView;
    DevisView devisView;
    ConsolePrinter consolePrinter;
    Scanner scanner;

    public MainView(ProjectView projectView, ClientView clientView, DevisView devisView, ConsolePrinter consolePrinter, Scanner scanner) {
        this.projectView = projectView;
        this.clientView = clientView;
        this.devisView = devisView;
        this.consolePrinter = consolePrinter;
        this.scanner = scanner;
    }

    public void main() {
        while (true) {
            consolePrinter.mainMenu();
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    projectView.createProject();
                    break;
                case 2:
                    projectView.getDetailsOfProject();
                    break;
                case 3:
                    clientView.createClient();
                    break;
                case 4:
                    clientView.acceptDevis();
                    break;
                case 5:
                    devisView.devisMain();
                    break;
                case 6:
                    projectView.projectMain();
                    break;
                case 7:
                    clientView.clientMain();
                    break;
                case 8:
                    System.exit(0);
                    break;
                default:
                    consolePrinter.printError("Invalid choice. Please try again.");
            }
        }
    }

}
