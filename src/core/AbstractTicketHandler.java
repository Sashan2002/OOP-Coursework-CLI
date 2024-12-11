package core;
/**
 * AbstractTicketHandler serves as a base class for handling ticket-related operations.
 * It defines a contract for ticket handling and provides a shared TicketPool resource.
 */

public abstract class AbstractTicketHandler {

    // Shared resource for managing tickets
    protected TicketPool ticketpool;

    /**
     * Constructor to initialize the AbstractTicketHandler with a TicketPool.
     *
     * @param ticketPool the TicketPool instance to be used by the handler
     */
    public AbstractTicketHandler(TicketPool ticketPool){
        this.ticketpool = ticketPool;
    }


    /**
     * Abstract method to handle tickets.
     * Concrete subclasses must implement this method to define specific ticket handling logic.
     */
    protected abstract void handlerTickets();
}
