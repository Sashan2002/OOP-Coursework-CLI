package core;

import logging.Logger;
import util.Ticket;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * The TicketPool class implements the TicketOperations interface to manage a pool of tickets.
 * It provides thread-safe operations to add and remove tickets, ensuring proper synchronization.
 */
public class TicketPool implements TicketOperations {

    /**
     * Counter for the total number of tickets sold.
     */
    private int soldTicketCounter=0;

    /**
     * Maximum number of tickets allowed in the pool.
     */
    private final int maxPoolTickets;

    /**
     * Counter for the total number of tickets added to the pool.
     */
    private int counter = 0;

    /**
     * Gets the total number of tickets sold.
     *
     * @return the total number of tickets sold
     */
    public int getSoldTicketCounter() {
        return soldTicketCounter;
    }

    /**
     * Gets the total number of tickets added to the pool.
     *
     * @return the total number of tickets added
     */
    public int getCounter() {
        return counter;
    }

    /**
     * Synchronized list to store tickets in the pool.
     */
    private final List<Ticket> ticketPool= Collections.synchronizedList(new LinkedList<>());

    /**
     * Constructor to initialize the TicketPool with a maximum ticket capacity.
     *
     * @param maxPoolTickets the maximum number of tickets that can be stored in the pool
     */
    public TicketPool(int maxPoolTickets) {
        this.maxPoolTickets = maxPoolTickets;
    }


    /**
     * Flag to indicate if ticket processing should stop.
     */
    private volatile boolean stopFlag = false;


    /**
     * Adds a ticket to the ticket pool.
     * If the pool is full, the thread waits until space becomes available.
     *
     * @param ticket the Ticket object to be added
     */
    @Override
    public synchronized void addTicket(Ticket ticket) {
        if (ticketPool.size() >= maxPoolTickets){
            // If the pool is full, log the status and wait for space to become available
            Logger.log("Ticket pool is full. Waiting for space to become available...");
            try {
                wait(); // Releases the lock and waits for notification

            } catch (InterruptedException e) {
                // Handle interruption gracefully and rethrow as RuntimeException
                Logger.log("Thread interrupted while waiting to add a ticket: " + Thread.currentThread().getName());
                throw new RuntimeException(e);
            }

        }
        ticketPool.add(ticket);
        counter++;
        Logger.log("Ticket successfully added to the pool. Current ticket count: " + counter);

        Logger.log(Thread.currentThread().getName() + " added a new ticket to the pool: " + ticket.getVendorID());

        notifyAll();

    }

    /**
     * Removes a ticket from the ticket pool.
     * If the pool is empty, the thread waits until a ticket becomes available.
     */
    @Override
    public synchronized void removeTicket() {

        if (!ticketPool.isEmpty()){
            // Remove the first ticket in the queue
            Ticket ticket= ticketPool.remove(0);
            soldTicketCounter++; // Increment the counter for tickets sold

            Logger.log(Thread.currentThread().getName()+" successfully removed a ticket from the pool. Vendor ID: " + ticket.getTicketID() + ", Ticket ID: " + ticket.getVendorID());
            Logger.log("Total tickets sold so far: " + soldTicketCounter);
            notifyAll(); // Notify threads waiting to add tickets
        }else{
            // If the pool is empty, log the status and wait for tickets to be added
            Logger.log("The ticket pool is currently empty. Waiting for tickets to be added...");
            try {
                wait(); // Releases the lock and waits for notification

            } catch (InterruptedException e) {
                // Handle interruption gracefully and rethrow as RuntimeException
                Logger.log("Thread interrupted while waiting to remove a ticket: " + Thread.currentThread().getName());
                throw new RuntimeException(e);
            }
        }

    }

    /**
     * Sets the stop flag to indicate whether ticket processing should stop.
     *
     * @param stopFlag true to stop ticket processing, false to continue
     */
    public synchronized void setStopFlag(boolean stopFlag) {
        this.stopFlag = stopFlag;
        notifyAll();
    }

    /**
     * Checks if ticket processing should stop.
     *
     * @return true if processing should stop, false otherwise
     */
    public boolean shouldStop(){
        return stopFlag;
    }
}




