package threads;

import core.AbstractTicketHandler;

import core.TicketPool;
import logging.Logger;
import util.Ticket;

/**
 * The Vendor class represents a ticket vendor that adds tickets to the ticket pool.
 * Implements the Runnable interface to run as a thread.
 */
public class Vendor extends AbstractTicketHandler implements Runnable {

    /**
     * The rate at which tickets are released, in milliseconds.
     */
    private final int ticketReleaseRate;

    /**
     * The total number of tickets the vendor can release.
     */
    private final int totalTicket;

    /**
     * Constructor to initialize the Vendor with a ticket pool, release rate, and total tickets.
     *
     * @param ticketPool        the ticket pool shared among vendors and customers
     * @param ticketReleaseRate the rate at which tickets are released (in milliseconds)
     * @param totalTicket       the total number of tickets the vendor will release
     */
    public Vendor(TicketPool ticketPool, int ticketReleaseRate, int totalTicket) {
        super(ticketPool);
        this.ticketReleaseRate = ticketReleaseRate;
        this.totalTicket = totalTicket;
    }

    /**
     * Handles ticket-related operations by invoking the run method.
     * Part of the AbstractTicketHandler implementation.
     */
    @Override
    public void handlerTickets() {
        run();

    }

    /**
     * The main logic for the vendor thread.
     * Continuously adds tickets to the ticket pool until the stop flag is set or the total ticket limit is reached.
     */
    @Override
    public void run() {

        // Continue running until the stop flag is set or all tickets are added
        while(!ticketpool.shouldStop() && ticketpool.getCounter()<totalTicket){

            // Create a new ticket with the current thread name and a unique ID
            Ticket ticket =new Ticket(Thread.currentThread().getName(), ("ID "+ System.nanoTime()));

            // Add the ticket to the pool
            ticketpool.addTicket(ticket);

            try {
                // Sleep for the specified release rate to simulate ticket release timing
                Thread.sleep(ticketReleaseRate * 500);
            } catch (InterruptedException e) {

                // Log and handle thread interruption gracefully
                Logger.log("Thread interrupted: " + Thread.currentThread().getName());
                Thread.currentThread().interrupt();
                break;
                //throw new RuntimeException(e);

            }
        }


        // Log a message indicating that the vendor thread has completed its task
        Logger.log("All tickets have been sold. Thread " + Thread.currentThread().getName() + " has completed its task.");

    }
}
