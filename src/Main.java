import configuration.Configurations;
import core.TicketPool;
import logging.Logger;
import threads.Customer;
import threads.Vendor;
import ui.CommandLineInterface;

import java.util.Scanner;

/**
 * Main class for running the Real-Time Event Ticketing System.
 * This class initializes the system, creates vendor and customer threads,
 * and listens for commands to manage the application lifecycle.
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        // Clear the log file at the start of the application
        Logger.clearLogFile();
        // Initialize system configurations using the Command Line Interface
        Configurations configurations= CommandLineInterface.configurations();
        // Create a shared TicketPool instance with a maximum size defined in the configuration
        TicketPool ticketPool=new TicketPool(configurations.getMaxPoolTickets());
        // Array to hold vendor threads
        Thread[] vendorThreads= new Thread[configurations.getNumberOfVendors()];

        // Create and start vendor threads
        for (int i = 0; i < configurations.getNumberOfVendors(); i++) {
            vendorThreads[i]= new Thread(new Vendor(ticketPool, configurations.getReleaseRates(),configurations.getTotalTickets()));
            vendorThreads[i].setName("Vendor " + i);
            vendorThreads[i].start(); // Start the vendor thread
            //System.out.println("Vendor "+ i +" : "+ vendorThreads[i].getName());

        }

        // Array to hold customer threads
        Thread[] customerThreads= new Thread[configurations.getNumberOfCustomers()];
        // Create and start customer threads
        for (int i = 0; i < configurations.getNumberOfCustomers(); i++) {
            customerThreads[i]= new Thread(new Customer(ticketPool, configurations.getReleaseRates(),configurations.getTotalTickets()));
            customerThreads[i].setName("Customer "+ i);
            customerThreads[i].start(); // Start the customer thread
           // System.out.println("Customer "+ i +" : "+ customerThreads[i].getName());

        }

        // The following section to add VIP customers with higher priority
        for (int i = 0; i < configurations.getNumberOfCustomers(); i++) {
            // Create a new thread for each VIP customer
            customerThreads[i]= new Thread(new Customer(ticketPool, configurations.getReleaseRates(),configurations.getTotalTickets()));
            // Set a custom name for the VIP customer thread for easier identification
            customerThreads[i].setName("VIP Customer "+ i);
            // Set the thread's priority to maximum to ensure it gets more CPU time
            customerThreads[i].setPriority(Thread.MAX_PRIORITY);
            // Start the VIP customer thread to begin ticket retrieval
            customerThreads[i].start();
            // Log the thread's name to the console for tracking
            //System.out.println("Customer "+ i +" : "+ customerThreads[i].getName());

        }



        //Command listener thread to handle user commands during runtime
        Thread commandListener = new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                String command = scanner.nextLine().trim();
                if (command.equalsIgnoreCase("q")) {

                    // Signal all threads to stop by setting the stop flag in the ticket
                    ticketPool.setStopFlag(true);
                    System.out.println("Stop command received. Shutting down...");

                    // Interrupt all threads to wake them up
                    for (Thread vendor : vendorThreads) {
                        vendor.interrupt();
                    }

                    // Interrupt all customer threads to wake them up
                    for (Thread customer : customerThreads) {
                        customer.interrupt();
                    }
                    break; // Exit the loop
                }
            }
        });
        commandListener.start(); // Start the command listener thread

        // Wait for all vendor threads to finish execution
        for (Thread vendor: vendorThreads) {
            vendor.join();

        }

        // Wait for all customer threads to finish execution
        for (Thread customer: customerThreads) {
            customer.join();

        }
        // Wait for the command listener thread to finish execution
        commandListener.join();

        // Print termination message
        System.out.println("All threads terminated. Exiting application.");
    }
}