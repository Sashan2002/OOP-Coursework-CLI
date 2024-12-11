package core;

import util.Ticket;

/**
 * The TicketOperations interface defines the operations that can be performed
 * on a ticketing system, such as adding and removing tickets.
 */
public interface TicketOperations {

    /**
     * Adds a ticket to the ticketing system.
     *
     * @param ticket the Ticket object to be added
     */
    void addTicket(Ticket ticket);

    /**
     * Removes a ticket from the ticketing system.
     * Implementations should define the criteria for ticket removal.
     */
    void removeTicket();
}
