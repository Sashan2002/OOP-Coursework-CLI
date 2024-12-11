package configuration;
/**
 * The Configurations class represents a configuration setup for ticket management,
 * including total tickets, release rates, retrieval rates, pool capacity, vendors, and customers.
 */
public class Configurations {


    private int totalTickets;// The total number of tickets available
    private int releaseRates; // The rate at which tickets are released
    private int retrievalRate; // The rate at which tickets are retrieved
    private int maxPoolTickets; // The maximum number of tickets allowed in the pool
    private int numberOfVendors; // The number of ticket vendors
    private int numberOfCustomers; // The number of ticket customers

    /**
     * Gets the number of vendors.
     * @return the number of vendors
     */
    public int getNumberOfVendors() {
        return numberOfVendors;
    }

    /**
     * Sets the number of vendors.
     * @param numberOfVendors the number of vendors to set
     */
    public void setNumberOfVendors(int numberOfVendors) {
        this.numberOfVendors = numberOfVendors;
    }

    /**
     * Gets the number of customers.
     * @return the number of customers
     */
    public int getNumberOfCustomers() {
        return numberOfCustomers;
    }

    /**
     * Sets the number of customers.
     * @param numberOfCustomers the number of customers to set
     */
    public void setNumberOfCustomers(int numberOfCustomers) {
        this.numberOfCustomers = numberOfCustomers;
    }


    /**
     * Parameterized constructor to initialize the configuration with specified values.
     *
     * @param totalTickets the total number of tickets
     * @param releaseRates the rate at which tickets are released
     * @param retrievalRate the rate at which tickets are retrieved
     * @param maxPoolTickets the maximum capacity of tickets in the pool
     * @param numberOfVendors the number of ticket vendors
     * @param numberOfCustomers the number of ticket customers
     */
    public Configurations(int totalTickets, int releaseRates, int retrievalRate, int maxPoolTickets, int numberOfVendors, int numberOfCustomers) {
        this.totalTickets = totalTickets;
        this.releaseRates = releaseRates;
        this.retrievalRate = retrievalRate;
        this.maxPoolTickets = maxPoolTickets;
        this.numberOfVendors = numberOfVendors;
        this.numberOfCustomers = numberOfCustomers;
    }

    /**
     * Default constructor to create an empty configuration object.
     */
    public Configurations() {}

    /**
     * Converts the configuration object to a string representation.
     * @return a string representing the configuration
     */
    @Override
    public String toString() {
        return "Configuration {" +
                "totalTickets=" + totalTickets +
                ", releaseRate=" + releaseRates +
                ", retrievalRate=" + retrievalRate +
                ", maximumTicketCapacity=" + maxPoolTickets +
                ", numberOfVendors=" + numberOfVendors +
                ", numberOfCustomers=" + numberOfCustomers +
                '}';
    }

    /**
     * Gets the total number of tickets.
     * @return the total number of tickets
     */
    public int getTotalTickets() {
        return totalTickets;
    }

    /**
     * Sets the total number of tickets.
     * @param totalTickets the total number of tickets to set
     */
    public void setTotalTickets(int totalTickets) {
        this.totalTickets = totalTickets;
    }

    /**
     * Gets the release rate of tickets.
     * @return the release rate of tickets
     */
    public int getReleaseRates() {
        return releaseRates;
    }

    /**
     * Sets the release rate of tickets.
     * @param releaseRates the release rate to set
     */
    public void setReleaseRates(int releaseRates) {
        this.releaseRates = releaseRates;
    }

    /**
     * Gets the retrieval rate of tickets.
     * @return the retrieval rate of tickets
     */
    public int getRetrievalRate() {
        return retrievalRate;
    }

    /**
     * Sets the retrieval rate of tickets.
     * @param retrievalRate the retrieval rate to set
     */
    public void setRetrievalRate(int retrievalRate) {
        this.retrievalRate = retrievalRate;
    }

    /**
     * Gets the maximum number of tickets in the pool.
     * @return the maximum number of tickets in the pool
     */
    public int getMaxPoolTickets() {
        return maxPoolTickets;
    }

    /**
     * Sets the maximum number of tickets in the pool.
     * @param maxPoolTickets the maximum pool tickets to set
     */
    public void setMaxPoolTickets(int maxPoolTickets) {
        this.maxPoolTickets = maxPoolTickets;
    }


}
