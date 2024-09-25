package Presentation;

import Utils.ConsolePrinter;
import Utils.InputValidator;

import java.util.Scanner;

public class MainView {

    ProjectView projectView;
    InputValidator inputValidator;
    ClientView clientView;
    DevisView devisView;
    ConsolePrinter consolePrinter;

    public MainView(ProjectView projectView, InputValidator inputValidator, ClientView clientView, DevisView devisView, ConsolePrinter consolePrinter) {
        this.projectView = projectView;
        this.inputValidator = inputValidator;
        this.clientView = clientView;
        this.devisView = devisView;
        this.consolePrinter = consolePrinter;
    }

    public void main() {
        while (true) {
            consolePrinter.mainMenu();
            int choice = inputValidator.validateInteger("");

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
