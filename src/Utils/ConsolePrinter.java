package Utils;

import Entities.Client;
import Utils.Types.CostBreakdown;

public class ConsolePrinter {
    public static void mainMenu(){
        System.out.println(" +----------------------------------------+");
        System.out.println(" |               Main Menu                |");
        System.out.println(" +----------------------------------------+");
        System.out.println(" |  1. Create a new project               |");
        System.out.println(" |  2. Add a Client                       |");
//        System.out.println(" |  2. Show existing projects             |");
//        System.out.println(" |  3. Calculate the cost of a project    |");
        System.out.println(" |  4. Exit                               |");
        System.out.println(" +----------------------------------------+");
        System.out.print(" ==> Enter your choice: ");
    }
    public static void clientMenu(){
        System.out.println(" +----------------------------------------+");
        System.out.println(" |               Client Menu              |");
        System.out.println(" +----------------------------------------+");
        System.out.println(" |  1. Search for an existing customer    |");
        System.out.println(" |  2. Add a new customer                 |");
        System.out.println(" |  4. Exit                               |");
        System.out.println(" +----------------------------------------+");
        System.out.print(" ==> Enter your choice: ");
    }

    public static void printError(String message){
        String redText = "\033[0;31m";
        String resetText = "\033[0m";

        System.out.println("\n" + redText + "--------------------------------------");
        System.out.println( message );
        System.out.println("--------------------------------------" + resetText + "\n" );
    }

    public static void printClient(Client client){
        String redText = "\033[0;31m";
        String resetText = "\033[0m";
        System.out.println("\n" + redText);
        System.out.println(" +----------------------------------------+");
        System.out.println(" |                 Client                 |");
        System.out.println(" +----------------------------------------+");
        System.out.println(" | Name          | " + client.getName());
        System.out.println(" | Address       | "  + client.getAddress());
        System.out.println(" | PhoneNumber   | "  + client.getPhoneNumber());
        System.out.println(" | Professional  | "  + client.getProfessional());
        System.out.println(" +----------------------------------------+");
        System.out.println(resetText + "\n" );

    }

    public static void printCostDetails(CostBreakdown costBreakdown){
        String redText = "\033[0;33m";
        String resetText = "\033[0m";
        System.out.println("\n" + redText);
        System.out.println(" +----------------------------------------+");
        System.out.println(" |           Project's Cost [MAD]          |");
        System.out.println(" +----------------------------------------+");
        System.out.println(" | Base Cost       | " + costBreakdown.getBaseCost());
        System.out.println(" | Tax Amount      | "  + costBreakdown.getTaxAmount());
        System.out.println(" | Profit Amount   | "  + costBreakdown.getProfit());
        System.out.println(" +----------------------------------------+");
        System.out.println(" | Total Cost      | "  + costBreakdown.getTotalCost());
        System.out.println(" +----------------------------------------+");
        System.out.println(resetText + "\n" );

    }

    public static void printSuccess(String message){
        String greenText = "\033[0;32m";
        String resetText = "\033[0m";
        System.out.println("\n" + greenText + "--------------------------------------");
        System.out.println(" " +  message );
        System.out.println("--------------------------------------" + resetText + "\n" );

    }
}
