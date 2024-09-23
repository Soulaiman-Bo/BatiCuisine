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
import java.util.Optional;
import java.util.Scanner;

public class ProjectView {
    private static final Scanner scanner = new Scanner(System.in);
    private static final InputValidator validator = new InputValidator();


    static public void projectMain(){
        projectLoop:
        while (true) {
            ConsolePrinter.projectMenu();
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
                    ConsolePrinter.printError("Invalid choice. Please try again.");
            }
        }
    }

    static public void createProject(){
        Integer clientId = validator.validateInteger(" ==> Entre Client's ID: ");

        ClientRepository clientRepository = new ClientRepositoryImpl();
        ClientService clientService = new ClientService(clientRepository);
        Optional<Client> client =  clientService.getClientById(clientId);

        if(client.isPresent()){
            ConsolePrinter.printClient(client.get());

            String projectName = validator.validateString("==> Entre Project Name: ");
            Double profit = validator.validateDouble(" ==> Entre Profit Margin (%): ");

            ProjetService projetService = new ProjetService();

            Projet projet = new Projet(null, projectName, profit, 0.0,0.0, EtatProject.PENDING);
            projet.setClient(client.get());

            materialsLoop:
            while (true){
                scanner.nextLine();
                String componentsChoice = validator.validateYesNo(" ==> Do you want to add Materials to this Project? ");
                switch (componentsChoice){
                    case "Y":
                    case "y":
                        Materiaux materiaux =  ComponentsView.addMaterialsView();
                        projet.getComposants().add(materiaux);
                        ConsolePrinter.printSuccess("Material Has Been Added Successfully");
                        break;
                    case "N":
                    case "n":
                        break materialsLoop;
                    default:
                        ConsolePrinter.printError("Entre the Right Choice");
                }
            }

            laborLoop:
            while (true){
                scanner.nextLine();
                System.out.print(" ==> Do you want to add Labor to this Project? [y/n]: ");
                String laborChoice = validator.validateYesNo(" ==> Do you want to add Labor to this Project?");
                switch (laborChoice){
                    case "Y":
                    case "y":
                        MainDoeuvre mainDoeuvre =  ComponentsView.addLaborView();
                        projet.getComposants().add(mainDoeuvre);
                        ConsolePrinter.printSuccess("Labor Has Been Added Successfully");
                        break;
                    case "N":
                    case "n":
                        break laborLoop;
                }
            }

            CostBreakdown costBreakdown = calculateCost(projet.getComposants());

            String marginChoice = validator.validateString(" ==> Do you want to apply a profit margin?");

            if(marginChoice.equals("y")){
                costBreakdown.setProfit(costBreakdown.getBaseCost() * (projet.getProfit() / 100));
            }

            String discountChoice = validator.validateString(" ==> Do you want to apply a Discount to this Client?");

            if(discountChoice.equals("y")){
                Double discount = validator.validateDouble(" ==> Entre Discount percentage (%): ");
                projet.setDiscount(discount);
            }else {
                projet.setDiscount(0.0);
            }

            costBreakdown.setDiscount(costBreakdown.getProfit() * (projet.getDiscount() / 100));

            projet.setTotalCost(costBreakdown.getTotalCost());
            projet.setProjectStatus(EtatProject.INPROGRESS);


            ConsolePrinter.printCostDetails(costBreakdown);
            String saveChoice = validator.validateYesNo(" ==> Do you want to save this project? [y/n]: ");

            if(saveChoice.equals("y")){
                projetService.createProjetWithComponents(projet);
            }

            DevisService devisService = new DevisService();
            DevisView.addDevisView(projet);

        }else {
            ConsolePrinter.printError("Client not found");
        }

    }

    static public void getDetailsOfProject(){
        Integer projectId = validator.validateInteger(" ==> Entre Projects's ID: ");
        scanner.nextLine();

        ProjetService projectService = new ProjetService();
        Projet project = projectService.getProjetWithComponents(projectId);

        if(project != null){
            ConsolePrinter.printProjectDetails(project);
        } else {
            ConsolePrinter.printError("Project not found");
        }
    }

    static public void getAllProjects() {
        ProjetService projetService = new ProjetService();
        List<Projet> projetList =  projetService.getAllProjets();
        projetList.forEach(ConsolePrinter::printProject);

    }

    static public void getProjectById(){
        int projectID = validator.validateInteger(" ==> Entre the ID of Project: ");

        ProjetService projetService = new ProjetService();
        Optional<Projet> projetList =  projetService.getProjetById(projectID);
        projetList.ifPresentOrElse(ConsolePrinter::printProject, () -> ConsolePrinter.printError("Project not found."));
    }

    static public void deleteProject(){
        int projectID = validator.validateInteger(" ==> Entre the ID of Project To Delete: ");

        ProjetService projetService = new ProjetService();
        boolean isDeleted =  projetService.deleteProjet(projectID);

        if (isDeleted) {
            ConsolePrinter.printSuccess("Deleted Successfully");
        } else {
            ConsolePrinter.printError("Failed to Delete");
        }
    }

    static public void updateProject() {
        int projectID = validator.validateInteger(" ==> Enter the ID of Project you want to Update: ");

        ProjetService projectService = new ProjetService();
        Optional<Projet> project = projectService.getProjetById(projectID);

        if (project.isEmpty()) {
            ConsolePrinter.printError("Project not found with ID: " + projectID);
            return;
        }

        System.out.println("Current Project details:");
        ConsolePrinter.printProjectDetails(project.get());

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

        Projet updatedProject = projectService.updateProjet(project.get());

        if (updatedProject != null) {
            ConsolePrinter.printSuccess("Project updated successfully");
        } else {
            ConsolePrinter.printError("Failed to update Project");
        }
    }

    static private CostBreakdown calculateCost(List<Composants> composants) {
        return composants.stream()
                .map(composant -> {
                    double baseCost = calculateBaseCost(composant);
                    double taxAmount = baseCost * (composant.getTaxRate() / 100);
                    return new CostBreakdown(baseCost, taxAmount);
                })
                .reduce(new CostBreakdown(0, 0), (subtotal, element) -> new CostBreakdown(
                        subtotal.getBaseCost() + element.getBaseCost(),
                        subtotal.getTaxAmount() + element.getTaxAmount()
                ));
    }

    static public double calculateBaseCost(Composants composant) {
        if (composant instanceof Materiaux materiaux) {
            return materiaux.getUnitCost()
                    * materiaux.getQuantity()
                    * materiaux.getQualityCoefficient()
                    + materiaux.getTransportCost();
        } else if (composant instanceof MainDoeuvre mainDoeuvre) {
            return mainDoeuvre.getHourlyRate()
                    * mainDoeuvre.getWorkHoursCount()
                    * mainDoeuvre.getProductivityRate();
        }
        return 0.0; // or throw an exception for unknown component types
    }

}
