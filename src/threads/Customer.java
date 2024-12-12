package threads;

import core.AbstractTicketHandler;
import core.TicketPool;

/**
 * The Customer class represents a thread that simulates a customer retrieving tickets from the TicketPool.
 * It extends the AbstractTicketHandler class and implements the Runnable interface.
 */
public class Customer extends AbstractTicketHandler implements Runnable {

    /**
     * The rate at which tickets are retrieved, in milliseconds.
     */
    private int retrievalRate;

    /**
     * The total number of tickets the customer is allowed to retrieve.
     */
    private  final int totalTickets;

    /**
     * Constructor for the Customer class.
     *
     * @param ticketPool    the TicketPool from which tickets will be retrieved
     * @param retrievalRate the rate at which tickets are retrieved (in milliseconds)
     * @param totalTickets  the total number of tickets the customer can retrieve
     */
    public Customer(TicketPool ticketPool, int retrievalRate, int totalTickets) {
        super(ticketPool);
        this.retrievalRate = retrievalRate;
        this.totalTickets = totalTickets;
    }


    /**
     * Handles ticket retrieval logic by invoking the run method.
     * This method overrides the handlerTickets method in the AbstractTicketHandler class.
     */
    @Override
    protected void handlerTickets() {

        run(); // Delegates ticket retrieval to the run method
    }

    /**
     * The run method defines the logic for the customer thread.
     * It continuously retrieves tickets from the TicketPool until a stop condition is met.
     */
    @Override
    public synchronized void run() {

        // Continue retrieving tickets as long as the stop flag is not set
        // and the total tickets sold is within the allowed limit
        while (!ticketpool.shouldStop() && ticketpool.getSoldTicketCounter()<= totalTickets){
            ticketpool.removeTicket(); // Remove a ticket from the pool
            try {
                // Pause the thread for the specified retrieval rate
                Thread.sleep(retrievalRate * 500);
            } catch (InterruptedException e) {
                // Handle interruption during sleep
                Thread.currentThread().interrupt(); // Restore the interrupted status
                break; // Exit the loop if interrupted
            }
        }
    }
}
