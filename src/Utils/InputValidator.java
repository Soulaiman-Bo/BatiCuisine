package Utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class InputValidator {
    private Scanner scanner;

    public InputValidator() {
        scanner = new Scanner(System.in);
    }

    public double validateDouble(String prompt) {
        double value = 0.0;
        boolean valid = false;
        while (!valid) {
            System.out.print(prompt);
            if (scanner.hasNextDouble()) {
                value = scanner.nextDouble();
                valid = true;
            } else {
                System.out.println("Invalid input. Please enter a valid double.");
                scanner.next();
            }
        }
        return value;
    }

    public int validateInteger(String prompt) {
        int value = 0;
        boolean valid = false;

        while (!valid) {
            System.out.print(prompt);
            if (scanner.hasNextInt()) {
                value = scanner.nextInt();
                scanner.next();
                valid = true;
            } else {
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.next(); // Clear invalid input
            }
        }
        return value;
    }

    public String validateString(String prompt) {
        String input = "";

        while (input.isEmpty()) {
            System.out.print(prompt);
            input = scanner.nextLine();
            if (input.isEmpty()) {
                System.out.println("Invalid input. String cannot be empty.");
            }
        }
        return input;
    }

    public LocalDate validateLocalDate(String prompt) {
        LocalDate date = null;
        boolean valid = false;

        while (!valid) {
            System.out.print(prompt);
            String dateInput = scanner.nextLine();
            try {
                date = LocalDate.parse(dateInput, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                valid = true;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please enter the date in yyyy-MM-dd format.");
            }
        }
        return date;
    }

    public String validateYesNo(String prompt) {
        String input = "";
        boolean valid = false;

        while (!valid) {
            System.out.print(prompt + " [y/n]: ");
            input = scanner.nextLine().trim().toLowerCase();

            if (input.equals("y") || input.equals("n")) {
                valid = true;
            } else {
                System.out.println("Invalid input. Please enter 'y' or 'n'.");
            }
        }
        return input;
    }

}
