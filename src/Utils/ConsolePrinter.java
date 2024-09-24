package Utils;

import Domain.Entities.*;
import Utils.Types.CostBreakdown;

import java.util.stream.Stream;

public class ConsolePrinter {

    public ConsolePrinter() {
    }

    public void mainMenu() {
        System.out.println("\u001B[31m");
        System.out.println(" +----------------------------------------+");
        System.out.println(" |           professionnel Menu           |");
        System.out.println(" +----------------------------------------+");
        System.out.println(" |  1. Create a new project               |");
        System.out.println(" |  2. Get Details of a Project           |");
        System.out.print("\u001B[0m");
        System.out.print("\u001B[32m");
        System.out.println(" +----------------------------------------+");
        System.out.println(" |               Client Menu              |");
        System.out.println(" +----------------------------------------+");
        System.out.println(" |  3. Register a Client                  |");
        System.out.println(" |  4. Accept/Refuse Devis                |");
        System.out.print("\u001B[0m");
        System.out.print("\u001B[33m");
        System.out.println(" +----------------------------------------+");
        System.out.println(" |            Additional Menu             |");
        System.out.println(" +----------------------------------------+");
        System.out.println(" |  5. Devis Management                   |");
        System.out.println(" |  6. Project Management                 |");
        System.out.println(" |  7. Client Management                  |");
        System.out.print("\u001B[0m");
        System.out.print("\u001B[36m");
        System.out.println(" +----------------------------------------+");
        System.out.println(" |  8. Exit                               |");
        System.out.println(" +----------------------------------------+");
        System.out.print("\u001B[0m");
        System.out.print(" ==> Enter your choice: ");
    }

    public void clientMenu() {
        System.out.println(" +----------------------------------------+");
        System.out.println(" |               Client Menu              |");
        System.out.println(" +----------------------------------------+");
        System.out.println(" |  1. Get All Clients                    |");
        System.out.println(" |  2. Get Client By Id                   |");
        System.out.println(" |  3. Delete Client                      |");
        System.out.println(" |  4. Update Client                      |");
        System.out.println(" |  5. Exit                               |");
        System.out.println(" +----------------------------------------+");
        System.out.print(" ==> Enter your choice: ");
    }

    public void devisMenu() {
        System.out.println(" +----------------------------------------+");
        System.out.println(" |               Devis Menu               |");
        System.out.println(" +----------------------------------------+");
        System.out.println(" |  1. Get All Devis                      |");
        System.out.println(" |  2. Get Devis By Id                    |");
        System.out.println(" |  3. Delete Devis                       |");
        System.out.println(" |  4. Update Devis                       |");
        System.out.println(" |  5. Exit                               |");
        System.out.println(" +----------------------------------------+");
        System.out.print(" ==> Enter your choice: ");
    }

    public void projectMenu() {
        System.out.println(" +----------------------------------------+");
        System.out.println(" |             Project Menu               |");
        System.out.println(" +----------------------------------------+");
        System.out.println(" |  1. Get All Projects                   |");
        System.out.println(" |  2. Get Project By Id                  |");
        System.out.println(" |  3. Delete Project                     |");
        System.out.println(" |  4. Update Project                     |");
        System.out.println(" |  5. Exit                               |");
        System.out.println(" +----------------------------------------+");
        System.out.print(" ==> Enter your choice: ");
    }

    public void printError(String message) {
        String redText = "\033[0;31m";
        String resetText = "\033[0m";

        System.out.println(redText + "--------------------------------------");
        System.out.println(message);
        System.out.println("--------------------------------------" + resetText);
    }

    public void printClient(Client client) {
        String redText = "\033[0;31m";
        String resetText = "\033[0m";
        System.out.println(redText);
        System.out.println(" +----------------------------------------+");
        System.out.println(" |                 Client                 |");
        System.out.println(" +----------------------------------------+");
        System.out.println(" | Name          | " + client.getName());
        System.out.println(" | Address       | " + client.getAddress());
        System.out.println(" | PhoneNumber   | " + client.getPhoneNumber());
        System.out.println(" | Professional  | " + client.getProfessional());
        System.out.println(" +----------------------------------------+");
        System.out.println(resetText);

    }

    public void printCostDetails(CostBreakdown costBreakdown) {
        String redText = "\033[0;33m";
        String resetText = "\033[0m";

        System.out.println(redText);
        System.out.println(" +----------------------------------------+");
        System.out.println(" |           Project's Cost [MAD]          |");
        System.out.println(" +----------------------------------------+");
        System.out.println(" | Base Cost       | " + costBreakdown.getBaseCost());
        System.out.println(" | Tax Amount      | " + costBreakdown.getTaxAmount());
        System.out.println(" | Profit Amount   | " + costBreakdown.getProfit());
        System.out.println(" | Discount Amount | " + costBreakdown.getDiscount());
        System.out.println(" +----------------------------------------+");
        System.out.println(" | Total Cost      | " + costBreakdown.getTotalCost());
        System.out.println(" +----------------------------------------+");
        System.out.println(resetText);

    }

    public void printProjectDetails(Projet projet) {
        String redText = "\033[0;31m";
        String resetText = "\u001B[36m";
        String yellowText = "\033[0m";

        System.out.print(resetText);
// Top border for Project section
        System.out.println(" +----------------------------------------------------------------------------------+");
        System.out.println(" |                                     Project                                      |");
        System.out.println(" +----------------------------------------------------------------------------------+");
        System.out.print(yellowText);
// Two columns and two rows for project information
        System.out.printf(" | %-17s %-21s | %-17s %-19s  |%n", "Project ID", projet.getId(), "Project Name", projet.getProjectName());
        System.out.printf(" | %-17s %-21s | %-17s %-19s  |%n", "Project Status", projet.getProjectStatus().toString(), "Client ID", projet.getClient().getId());
        System.out.print(resetText);
        if (!projet.getComposants().isEmpty()) {
            System.out.println(" +----------------------------------------------------------------------------------+");
            System.out.println(" |                                    Materiaux                                     |");
            System.out.println(" +----------------------------------------------------------------------------------+");
            System.out.print(redText);
            System.out.printf(" | %-15s%-10s%-10s%-15s%-10s%-10s%-10s |%n", "Item", "price", "Qty", "Transport", "Tax%", "CoEf", "Total");
            System.out.print(resetText);


            System.out.print(yellowText);
            projet.getComposants().stream().filter(composants -> composants instanceof Materiaux).forEach(composants -> {

                CostBreakdown costBreakdown = CostCalculator.calculateBaseCostOfMateriaux((Materiaux) composants);
                System.out.printf(" | %-15s%-10s%-10s%-15s%-10s%-10s%-10s |%n",
                        composants.getName(),
                        ((Materiaux) composants).getUnitCost(),
                        ((Materiaux) composants).getQuantity(),
                        ((Materiaux) composants).getTransportCost(),
                        composants.getTaxRate(),
                        ((Materiaux) composants).getQualityCoefficient(),
                        costBreakdown.getTotalCost());
            });
            System.out.print(resetText);

            Stream<Composants> filteredMainDoeuvre = projet.getComposants().stream().filter(composant -> composant instanceof MainDoeuvre);

            if (filteredMainDoeuvre.findAny().isPresent()) {
                System.out.println(" +----------------------------------------------------------------------------------+");
                System.out.println(" |                                   MainDoeuvre                                    |");
                System.out.println(" +----------------------------------------------------------------------------------+");
                System.out.print(redText);
                System.out.printf(" | %-15s%-15s%-10s%-15s%-15s%-10s |%n", "Name", "Price", "Hours", "Productivity", "Tax[%]", "Total");
                System.out.print(resetText);
                System.out.print(yellowText);
                projet.getComposants().stream().filter(composant -> composant instanceof MainDoeuvre).forEach(composants -> {
                    CostBreakdown costBreakdown = CostCalculator.calculateBaseCostOfMainDoeuvre((MainDoeuvre) composants);
                    System.out.printf(" | %-15s%-15.1f%-10.1f%-15.1f%-15.1f%-10.1f |%n",
                            composants.getName(),
                            ((MainDoeuvre) composants).getHourlyRate(),
                            ((MainDoeuvre) composants).getWorkHoursCount(),
                            ((MainDoeuvre) composants).getProductivityRate(),
                            composants.getTaxRate(),
                            costBreakdown.getTotalCost());
                });
                System.out.print(resetText);
            }

            CostBreakdown costBreakdown = CostCalculator.calculateCost(projet.getComposants());
            double profitAmount = costBreakdown.getBaseCost() * (projet.getProfit() / 100);
            costBreakdown.setProfit(profitAmount);
            double discountAmount = costBreakdown.getProfit() * (projet.getDiscount() / 100);
            costBreakdown.setDiscount(discountAmount);


            System.out.println(" +----------------------------------------------------------------------------------+");
            System.out.print(redText);
            System.out.printf(" | %-40s%-20s%-20s |%n", "", "Percentage", "Amount [MAD]");
            System.out.print(resetText);
            System.out.print(yellowText);
            System.out.printf(" | %-40s%-20s%-20.2f |%n", "Profit Margin [%]:", "% " + projet.getProfit(), profitAmount);
            System.out.printf(" | %-40s%-20s%-20.2f |%n", "Tax Amount [MAD]:", "", costBreakdown.getTaxAmount());
            System.out.printf(" | %-40s%-20s%-20.2f |%n", "Base Cost [MAD]:", "", costBreakdown.getBaseCost());
            System.out.printf(" | %-40s%-20s%-20.2f |%n", "Discount [% Of Profit]:", "% " + projet.getDiscount(), discountAmount);
            System.out.printf(" | %-40s%-20s%-20.2f |%n", "Total Cost [MAD]:", "", costBreakdown.getTotalCost());
            System.out.print(resetText);
            System.out.println(" +----------------------------------------------------------------------------------+");
        }
    }

    public void printProject(Projet projet) {
        System.out.println(" +----------------------------------------+");
        System.out.println(" |                 Project                |");
        System.out.println(" +----------------------------------------+");
        System.out.println(" | Project ID         | " + projet.getId());
        System.out.println(" | Project Name       | " + projet.getProjectName());
        System.out.println(" | Project Status     | " + projet.getProjectStatus().toString());
        System.out.println(" | Project Cost       | " + projet.getTotalCost().toString());
        System.out.println(" | Client ID          | " + projet.getClient().getId());
        System.out.println(" +----------------------------------------+");
    }

    public void printDevis(Devis devis) {
        String redText = "\033[0;33m";
        String resetText = "\033[0m";
        System.out.println(redText);
        System.out.println(" +----------------------------------------+");
        System.out.println(" |                  Devis                 |");
        System.out.println(" +----------------------------------------+");
        System.out.println(" | Devis's ID        | " + devis.getId());
        if (devis.getProjet().getClient() != null) {
            System.out.println(" | Client's Name     | " + devis.getProjet().getClient().getName());
        }
        if (devis.getProjet().getId() != null) {
            System.out.println(" | Project's ID      | " + devis.getProjet().getId());
        }
        if (devis.getProjet().getProjectName() != null) {
            System.out.println(" | Project's Name    | " + devis.getProjet().getProjectName());
        }
        System.out.println(" | Project's Cost    | " + devis.getEstimatedPrice());
        System.out.println(" | Devis is Accepted | " + (devis.getAccepted() ? "Accepted" : "Rejected"));
        System.out.println(" | Valid Until       | " + devis.getValidityDate().toString());
        System.out.println(" +----------------------------------------+");
        System.out.println(resetText);
    }

    public void printSuccess(String message) {
        String greenText = "\033[0;32m";
        String resetText = "\033[0m";
        System.out.println(greenText + "--------------------------------------");
        System.out.println(" " + message);
        System.out.println("--------------------------------------" + resetText);
    }
}

