package Utils;

import Domain.Entities.*;
import Presentation.ProjectView;
import Utils.Types.CostBreakdown;

public class ConsolePrinter {

    public static void mainMenu(){
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

    public static void clientMenu(){
        System.out.println(" +----------------------------------------+");
        System.out.println(" |               Client Menu              |");
        System.out.println(" +----------------------------------------+");
        System.out.println(" |  1. Get All Clients                    |");
        System.out.println(" |  2. Get Client By Id                   |");
        System.out.println(" |  2. Delete Client                      |");
        System.out.println(" |  3. Update Client                      |");
        System.out.println(" |  4. Exit                               |");
        System.out.println(" +----------------------------------------+");
        System.out.print(" ==> Enter your choice: ");
    }

    public static void devisMenu(){
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

    public static void projectMenu(){
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

    public static void printError(String message){
        String redText = "\033[0;31m";
        String resetText = "\033[0m";

        System.out.println(redText + "--------------------------------------");
        System.out.println( message );
        System.out.println("--------------------------------------" + resetText);
    }

    public static void printClient(Client client){
        String redText = "\033[0;31m";
        String resetText = "\033[0m";
        System.out.println(redText);
        System.out.println(" +----------------------------------------+");
        System.out.println(" |                 Client                 |");
        System.out.println(" +----------------------------------------+");
        System.out.println(" | Name          | " + client.getName());
        System.out.println(" | Address       | "  + client.getAddress());
        System.out.println(" | PhoneNumber   | "  + client.getPhoneNumber());
        System.out.println(" | Professional  | "  + client.getProfessional());
        System.out.println(" +----------------------------------------+");
        System.out.println(resetText);

    }

    public static void printCostDetails(CostBreakdown costBreakdown){
        String redText = "\033[0;33m";
        String resetText = "\033[0m";

        System.out.println(redText);
        System.out.println(" +----------------------------------------+");
        System.out.println(" |           Project's Cost [MAD]          |");
        System.out.println(" +----------------------------------------+");
        System.out.println(" | Base Cost       | " + costBreakdown.getBaseCost());
        System.out.println(" | Tax Amount      | "  + costBreakdown.getTaxAmount());
        System.out.println(" | Profit Amount   | "  + costBreakdown.getProfit());
        System.out.println(" | Discount Amount | "  + costBreakdown.getDiscount());
        System.out.println(" +----------------------------------------+");
        System.out.println(" | Total Cost      | "  + costBreakdown.getTotalCost());
        System.out.println(" +----------------------------------------+");
        System.out.println(resetText);

    }

    public static void printProjectDetails(Projet projet){
        String redText = "\033[0;31m";
        String resetText = "\033[0m";
        System.out.println(redText);
        System.out.println(" +----------------------------------------+");
        System.out.println(" |                 Project                |");
        System.out.println(" +----------------------------------------+");
        System.out.println(" | Project Name       | " + projet.getProjectName());
        System.out.println(" | Project Status     | " + projet.getProjectStatus().toString());

        if(!projet.getComposants().isEmpty()){

            System.out.println(" +----------------------------------------+");
            System.out.println(" |              Materiaux                 |");
            System.out.println(" +----------------------------------------+");
            projet.getComposants().stream().filter(composants -> composants instanceof Materiaux).forEach(composants -> {
                double baseCost = ProjectView.calculateBaseCost(composants);
                double taxAmount = baseCost * (composants.getTaxRate() / 100);
                CostBreakdown costBreakdown = new CostBreakdown(baseCost, taxAmount);
                System.out.println(" | "  + composants.getName() + "       | "  + costBreakdown.getTotalCost() );
            });

            System.out.println(" +----------------------------------------+");
            System.out.println(" |              MainDoeuvre               |");
            System.out.println(" +----------------------------------------+");
            projet.getComposants().stream().filter(composants -> composants instanceof MainDoeuvre).forEach(composants -> {
                double baseCost = ProjectView.calculateBaseCost(composants);
                double taxAmount = baseCost * (composants.getTaxRate() / 100);
                CostBreakdown costBreakdown = new CostBreakdown(baseCost, taxAmount);
                System.out.println(" | "  + composants.getName() + "       | "  + costBreakdown.getTotalCost());
            });

        }
        System.out.println(" +----------------------------------------+");
        System.out.println(resetText);

    }

    public static void  printDevis(Devis devis){
        String redText = "\033[0;33m";
        String resetText = "\033[0m";
        System.out.println( redText);
        System.out.println(" +----------------------------------------+");
        System.out.println(" |                  Devis                 |");
        System.out.println(" +----------------------------------------+");
        System.out.println(" | Devis's ID        | " + devis.getId());
        if (devis.getProjet().getClient() != null){
            System.out.println(" | Client's Name     | " + devis.getProjet().getClient().getName());
        }
        if (devis.getProjet().getId() != null){
            System.out.println(" | Project's ID      | " + devis.getProjet().getId());
        }
        if (devis.getProjet().getProjectName() != null){
            System.out.println(" | Project's Name    | " + devis.getProjet().getProjectName());
        }
        System.out.println(" | Project's Cost    | " + devis.getEstimatedPrice());
        System.out.println(" | Devis is Accepted | "  + (devis.getAccepted() ? "Accepted" : "Rejected"));
        System.out.println(" | Valid Until       | "  + devis.getValidityDate().toString());
        System.out.println(" +----------------------------------------+");
        System.out.println(resetText);
    }

    public static void printSuccess(String message){
        String greenText = "\033[0;32m";
        String resetText = "\033[0m";
        System.out.println(greenText + "--------------------------------------");
        System.out.println(" " +  message );
        System.out.println("--------------------------------------" + resetText  );
    }
}
