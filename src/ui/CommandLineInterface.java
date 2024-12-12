package ui;

import configuration.Configurations;
import logging.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.Scanner;

/**
 * The CommandLineInterface class provides an interactive console-based interface
 * for initializing and managing configurations for the real-time event ticketing system.
 * It allows users to input configuration details, save them, and load previous configurations.
 */
public class CommandLineInterface {
    /**
     * Path to the JSON file where configurations are stored.
     */
    private static final String CONFIG_FILE = "resources/configuration.json";

    /**
     * Gson instance for JSON serialization and deserialization with pretty printing enabled.
     */
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    /**
     * Initializes the system configurations by gathering user input or loading a saved configuration.
     *
     * @return the initialized {@link Configurations} object.
     */
    public static Configurations configurations(){
        Scanner input = new Scanner(System.in);
        Logger.log("**************************************************************");
        Logger.log("********WELCOME TO MY REAL-TIME EVENT TICKETING SYSTEM********");
        Logger.log("**************************************************************");

        // Check if a previous configuration exists
        Configurations previousConfig = loadConfiguration();
        if (previousConfig != null) {
            System.out.println("A saved configuration was found:");
            System.out.println(previousConfig);
            System.out.print("Do you want to use this configuration? (yes/no): ");
            String userInput = input.nextLine().trim().toLowerCase();

            if (userInput.equals("yes")) {
                Logger.log("Using the saved configuration.");
                return previousConfig; // Return the loaded configuration
            }
        }

        Logger.log("Initializing system configurations...");

        // Gather inputs for system configuration
        int totalTickets = getInputs(input, "Please enter the total number of tickets available (must be greater than 0): ");
        int maxPoolTickets;

        // Ensure the maximum pool size does not exceed the total tickets
        while(true){
            maxPoolTickets = getInputs(input, "Please enter the maximum size of the ticket pool (must be greater than 0): ");
            if (maxPoolTickets<=totalTickets){
                break;
            }else{
                System.out.println("The maximum pool size cannot be greater than the total number of tickets. Please try again.");
            }
        }

        // Gather remaining configuration details
        int retrievalRate = getInputs(input, "Please enter the rate at which tickets are retrieved (must be greater than 0): ");
        int releaseRate = getInputs(input, "Please enter the rate at which tickets are released (must be greater than 0): ");
        int numberOfVendors = getInputs(input, "Please enter the number of vendors: ");
        int numberOfCustomers = getInputs(input, "Please enter the number of customers: ");

        Logger.log("System configurations successfully initialized.");



        // Create a new configuration object
        Configurations newConfig = new Configurations(totalTickets, releaseRate, retrievalRate, maxPoolTickets, numberOfVendors, numberOfCustomers);
        // Save the new configuration for future use
        saveConfiguration(newConfig); // Save the new configuration for future use
        Logger.log("Configuration has been saved.");
        return newConfig;
    }


    /**
     * Prompts the user for an integer input with the given prompt message.
     * Ensures the input is a valid positive integer.
     *
     * @param input  the Scanner instance for user input
     * @param prompt the message to display to the user
     * @return the valid integer input from the user
     */
    private static int getInputs(Scanner input, String prompt){

        int value;

        while (true){
            System.out.print(prompt);
            try {
                value = Integer.parseInt(input.next());
                if (value > 0) {
                    return value;

            }else {
                    System.out.println("The number of routers must be positive. Please try again. ");
                }
            }catch (NumberFormatException e){
                System.out.println("Invalid input. Please enter a valid number. ");
            }

        }

    }

    /**
     * Loads a previously saved configuration from the JSON file.
     *
     * @return the {@link Configurations} object if a configuration file exists and is valid;
     *         null otherwise.
     */
    private static Configurations loadConfiguration() {
        try (Reader reader = new FileReader(CONFIG_FILE)) {
            return gson.fromJson(reader, Configurations.class); // Deserialize JSON to Configuration object
        } catch (FileNotFoundException e) {
            System.out.println("No existing configuration file found.");
            return null; // Return null if file doesn't exist
        } catch (IOException e) {
            System.err.println("Failed to load configuration: " + e.getMessage());
            return null;
        }
    }

    /**
     * Saves the provided configuration to a JSON file for future use.
     *
     * @param config the {@link Configurations} object to save
     */
    private static void saveConfiguration(Configurations config) {
        try (Writer writer = new FileWriter(CONFIG_FILE)) {
            gson.toJson(config, writer); // Serialize Configuration object to JSON
            System.out.println("Configuration saved to " + CONFIG_FILE);
        } catch (IOException e) {
            System.err.println("Failed to save configuration: " + e.getMessage());
        }
    }
}
