package Presentation;

import Domain.Entities.*;
import Domain.Enums.EtatProject;
import Services.ClientService;
import Services.ProjetService;
import Utils.ConsolePrinter;
import Utils.CostCalculator;
import Utils.InputValidator;
import Utils.Types.CostBreakdown;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class ProjectView {

    Scanner scanner;
    InputValidator validator;
    ConsolePrinter printer;
    ClientService clientService;
    ProjetService projetService;
    ComponentsView componentsView;
    DevisView devisView;

    public ProjectView(Scanner scanner, InputValidator validator, ConsolePrinter printer, ClientService clientService, ProjetService projetService, ComponentsView componentsView, DevisView devisView) {
        this.scanner = scanner;
        this.validator = validator;
        this.printer = printer;
        this.clientService = clientService;
        this.projetService = projetService;
        this.componentsView = componentsView;
        this.devisView = devisView;
    }

    public void projectMain() {
        projectLoop:
        while (true) {
            printer.projectMenu();
            int projectChoice = validator.validateInteger("");

            switch (projectChoice) {
                case 1:
                    getAllProjects();
                    break;
                case 2:
                    getProjectById();
                    break;
                case 3:
                    deleteProject();
                    break;
                case 4:
                    updateProject();
                    break;
                case 5:
                    break projectLoop;
                default:
                    printer.printError("Invalid choice. Please try again.");
            }
        }
    }

    public void createProject() {
        Integer clientId = validator.validateInteger(" ==> Entre Client's ID: ");
        Optional<Client> client = clientService.getClientById(clientId);

        if (client.isPresent()) {
            printer.printClient(client.get());

            String projectName = validator.validateString("==> Entre Project Name: ");
            Double profit = validator.validateDouble(" ==> Entre Profit Margin (%): ");

            Projet projet = new Projet(null, projectName, profit, 0.0, 0.0, EtatProject.PENDING);
            projet.setClient(client.get());

            materialsLoop:
            while (true) {
//                scanner.nextLine();
                String componentsChoice = validator.validateYesNo(" ==> Do you want to add Materials to this Project? ");
                switch (componentsChoice) {
                    case "Y":
                    case "y":
                        Materiaux materiaux = componentsView.addMaterialsView();
                        projet.getComposants().add(materiaux);
                        printer.printSuccess("Material Has Been Added Successfully");
                        break;
                    case "N":
                    case "n":
                        break materialsLoop;
                    default:
                        printer.printError("Entre the Right Choice");
                }
            }

            laborLoop:
            while (true) {
                scanner.nextLine();
                String laborChoice = validator.validateYesNo(" ==> Do you want to add Labor to this Project?");
                switch (laborChoice) {
                    case "Y":
                    case "y":
                        MainDoeuvre mainDoeuvre = componentsView.addLaborView();
                        projet.getComposants().add(mainDoeuvre);
                        printer.printSuccess("Labor Has Been Added Successfully");
                        break;
                    case "N":
                    case "n":
                        break laborLoop;
                }
            }

            CostBreakdown costBreakdown = CostCalculator.calculateCost(projet.getComposants());

            String marginChoice = validator.validateYesNo(" ==> Do you want to apply a profit margin?");

            if (marginChoice.equals("y")) {
                costBreakdown.setProfit(costBreakdown.getBaseCost() * (projet.getProfit() / 100));
            }

            String discountChoice = validator.validateYesNo(" ==> Do you want to apply a Discount to this Client? ");

            if (discountChoice.equals("y")) {
                Double discount = validator.validateDouble(" ==> Entre Discount percentage [%]: ");
                projet.setDiscount(discount);
            } else {
                projet.setDiscount(0.0);
            }

            costBreakdown.setDiscount(costBreakdown.getProfit() * (projet.getDiscount() / 100));

            projet.setTotalCost(costBreakdown.getTotalCost());
            projet.setProjectStatus(EtatProject.INPROGRESS);

            printer.printCostDetails(costBreakdown);
            String saveChoice = validator.validateYesNo(" ==> Do you want to save this project? ");

            if (saveChoice.equals("y")) {
                projetService.createProjetWithComponents(projet);
            }

            String devisChoice = validator.validateYesNo(" ==> Do you want to Create Devis?");
            if (devisChoice.equals("y")) {
                devisView.addDevisView(projet);
            }

        } else {
            printer.printError("Client not found");
        }

    }

    public void getDetailsOfProject() {
        Integer projectId = validator.validateInteger(" ==> Entre Projects's ID: ");
        scanner.nextLine();

        Projet project = projetService.getProjetWithComponents(projectId);

        if (project != null) {
            printer.printProjectDetails(project);
        } else {
            printer.printError("Project not found");
        }
    }

    public void getAllProjects() {
        List<Projet> projetList = projetService.getAllProjets();
        projetList.forEach(projet -> printer.printProject(projet));
    }

    public void getProjectById() {
        int projectID = validator.validateInteger(" ==> Entre the ID of Project: ");
        Optional<Projet> projetList = projetService.getProjetById(projectID);
        projetList.ifPresentOrElse(projet -> printer.printProject(projet), () -> printer.printError("Project not found."));
    }

    public void deleteProject() {
        int projectID = validator.validateInteger(" ==> Entre the ID of Project To Delete: ");
        boolean isDeleted = projetService.deleteProjet(projectID);

        if (isDeleted) {
            printer.printSuccess("Deleted Successfully");
        } else {
            printer.printError("Failed to Delete");
        }
    }

    public void updateProject() {
        int projectID = validator.validateInteger(" ==> Enter the ID of Project you want to Update: ");
        Optional<Projet> project = projetService.getProjetById(projectID);

        if (project.isEmpty()) {
            printer.printError("Project not found with ID: " + projectID);
            return;
        }

        System.out.println("Current Project details:");
        printer.printProjectDetails(project.get());

        System.out.println("\nEnter new values for the fields you want to update (press Enter to skip):");

        System.out.print("Project Name (current: " + project.get().getProjectName() + "): ");
        String projectNameInput = scanner.nextLine();
        if (!projectNameInput.isEmpty()) {
            project.get().setProjectName(projectNameInput);
        }

        System.out.print("Profit (current: " + project.get().getProfit() + "): ");
        String profitInput = scanner.nextLine();
        if (!profitInput.isEmpty()) {
            project.get().setProfit(Double.parseDouble(profitInput));
        }

        System.out.print("Total Cost (current: " + project.get().getTotalCost() + "): ");
        String totalCostInput = scanner.nextLine();
        if (!totalCostInput.isEmpty()) {
            project.get().setTotalCost(Double.parseDouble(totalCostInput));
        }

        System.out.print("Discount (current: " + project.get().getDiscount() + "): ");
        String discountInput = scanner.nextLine();
        if (!discountInput.isEmpty()) {
            project.get().setDiscount(Double.parseDouble(discountInput));
        }

        System.out.print("Project Status (current: " + project.get().getProjectStatus() + ") (PENDING, INPROGRESS, FINISHED, CANCELLED): ");
        String statusInput = scanner.nextLine();
        if (!statusInput.isEmpty()) {
            project.get().setProjectStatus(EtatProject.valueOf(statusInput.toUpperCase()));
        }

        System.out.print("Client ID (current: " + (project.get().getClient() != null ? project.get().getClient().getId() : "null") + "): ");
        String clientIdInput = scanner.nextLine();
        if (!clientIdInput.isEmpty()) {
            Client client = new Client();
            client.setId(Integer.parseInt(clientIdInput));
            project.get().setClient(client);
        }

        Projet updatedProject = projetService.updateProjet(project.get());

        if (updatedProject != null) {
            printer.printSuccess("Project updated successfully");
        } else {
            printer.printError("Failed to update Project");
        }
    }

}
