package com.hahsm.common.ioconsole;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.hahsm.datastructure.adt.List;

public final class ConsoleHelper {
    public static void Clear() {
        // try {
        // if (System.getProperty("os.name").contains("Windows")) {
        // new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        // } else {
        // new ProcessBuilder("clear").inheritIO().start().waitFor();
        // }
        // } catch (Exception e) {
        // e.printStackTrace();
        // }
        // try {
        // new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        // } catch (IOException | InterruptedException e ) {
        // e.printStackTrace();
        // }
        try {
            final String os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                Runtime.getRuntime().exec("cls");
            } else {
                Runtime.getRuntime().exec("clear");
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    public static int getInteger(final Scanner scanner, final String prompt) {
        return getInteger(scanner, prompt, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public static int getInteger(final Scanner scanner, final String prompt, final int lowerLimit, int upperLimit) {
        assert scanner != null;
        int userInput = 0;

        while (true) {
            System.out.print(prompt);

            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter an integer");
                System.out.print(prompt);
                scanner.next();
            }
            userInput = scanner.nextInt();

            if (userInput < lowerLimit || userInput > upperLimit) {
                System.out.println("Expected an integer between [" + lowerLimit + ", " + upperLimit + "].");
            } else {
                break;
            }
        }

        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }
        return userInput;
    }

    public static int getFromMenu(final Scanner scanner, final String header, final List<Object> options,
            final int start) {
        assert options != null;

        System.out.println(header);
        for (int i = 0; i < options.size(); ++i) {
            System.out.printf("\t%d. %s%n", i + start, options.get(i));
        }

        return ConsoleHelper.getInteger(scanner, "Please enter your choice: ", start, start + options.size() - 1);
    }

    public static boolean getYesNoInput(final Scanner scanner, final String prompt) {
        String input = "";

        while (true) {
            System.out.print(prompt);
            input = scanner.next().trim().toLowerCase();

            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }

            if (input.equals("y")) {
                return true;
            } else if (input.equals("n")) {
                return false;
            }
            System.out.println("Invalid input. Please enter only 'y' or 'n'.");
        }
    }

    public static String getString(final Scanner scanner, final String prompt) {
        String input = null;
        while (input == null || input.trim().isEmpty()) {
            System.out.print(prompt);
            input = scanner.nextLine();

            if (input == null || input.trim().isEmpty()) {
                System.out.println("Input cannot be null or empty. Please try again.");
            }
        }

        return input.trim();
    }

    public static void waitForEnter(final String message) {
        System.out.println(message);
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
