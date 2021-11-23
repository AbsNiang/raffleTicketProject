package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Locale;
import java.util.Random;

public class Main {

    private static HashMap<Integer, String> raffleList = new HashMap<>();

    public static void main(String[] args) {
        boolean active = true;
        //loops through the methods and program until the user wants to exit
        while (active) {
            System.out.println();
            System.out.println("_____________________________________________________________________________");
            System.out.println("Would you like to 'check' a ticket or 'purchase' one?");
            System.out.println("Alternatively, if you would like to close the program type 'end'.");
            int option = mainMenu(input());
            if (option == 3) {
                active = false;
            } else {
                options(option);
            }
        }
    }

    public static int mainMenu(String input) {
        //checks what option to do
        switch (input.toLowerCase(Locale.ROOT)) {
            case "check":
                return 0;
            case "purchase":
                return 1;
            case "end":
                return 3;
        }
        return 2;
    }

    public static String input() {
        //collects user input as we aren't allowed to use Scanner
        try {
            BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
            return bf.readLine();
        } catch (IOException e) {
            System.out.println("Error with option " + e);
        }
        return "";
    }

    public static void options(int option) {
        if (option == 0) {
            //collects personal data to ensure the correct ticket is checked
            System.out.println("What is your first name?");
            String firstName = input().toLowerCase();
            System.out.println("What is your last name?");
            String lastName = input().toLowerCase();
            System.out.println("What is your ticket number?");
            //goes back to the main menu if an error is encountered (if an integer isn't inputted)
            try {
                int ticketNumb = Integer.parseInt(input());
                ticketWinnerCheck(firstName, lastName, ticketNumb);
            } catch (NumberFormatException e) {
                System.out.println("You have not typed an integer. Returning to main menu.");
            }
        } else if (option == 1) {
            //collects the name to put it as a value to the key (the raffle ticket)
            System.out.println("What is your first name?");
            String firstName = input().toLowerCase();
            System.out.println("What is your last name?");
            String lastName = input().toLowerCase();
            raffleGenerator(firstName, lastName);
        } else if (option == 2) {
            System.out.println("You have not typed a valid word.");
            System.out.println("Returning to main menu.");
        }
    }

    public static void raffleGenerator(String fn, String ln) {
        Random random = new Random();
        int randNumb = (random.nextInt(1000));
        boolean duplicateNumber = true;
        //re-generates a random number if the random number (raffle ticket) is already in use
        while (duplicateNumber) {
            if (raffleList.containsKey(randNumb)) {
                randNumb = (random.nextInt(1000));
            } else {
                duplicateNumber = false;
            }
        }
        raffleList.put(randNumb, (fn + ln));
        System.out.println("Your number is " + randNumb + " and under the name of " + fn + " " + ln);
    }

    public static void ticketWinnerCheck(String fn, String ln, int ticketNumber) {
        //checks if the ticket belongs to the person
        boolean primeNumb = true;
        try {
            if (raffleList.get(ticketNumber).equals(fn + ln)) {
                System.out.println("This ticket has been verified to be under your name.");
                for (int i = 2; i < ticketNumber / 2; i++) {
                    //checks to ensure the raffle ticket is not divisible by any numbers (checks if it is prime)
                    if (ticketNumber % i == 0) {
                        System.out.println("Unfortunately this is not a winning number so you have not won the raffle.");
                        primeNumb = false;
                        break;
                    }
                }
            }
            if (primeNumb) {
                System.out.println("Congratulations you have won the raffle.");
            }
        } catch (NullPointerException e) {
            System.out.println("This number does not belong to you.");
        }
    }
}
