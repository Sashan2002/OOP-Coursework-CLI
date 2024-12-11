"# OOP-Coursework-CLI" 
To combine and create a cohesive README file from the two provided documents for the Real-Time Ticketing System and the Event Ticketing System, the following structure can be used:

---

# Real-Time Ticketing System Documentation

## Overview
The Real-Time Ticketing System is a multi-threaded application crafted for dynamic ticket allocation and retrieval across event management or similar systems. It ensures operational efficiency through thread-safe mechanisms, synchronized ticket handling, and flexible configurations. This system is extensible to support multiple vendors and customers, making it practical for real-world use cases.

## Features
- **Customizable Ticketing Framework**: Users can configure total tickets, release and retrieval rates, maximum pool capacity, and the number of vendors and customers.
- **Thread-Safe Operations**: Implements synchronized ticket pool management to prevent race conditions.
- **Scalable Architecture**: Utilizes abstract classes and interfaces to facilitate extensibility.
- **Comprehensive Logging**: Records every action for enhanced debugging and monitoring.
- **Graceful Shutdown**: Employs a stop mechanism to ensure orderly termination of operations.

## Components
### 1. Configurations
Defines system parameters for customizable ticket management.
- **Key Attributes**:
  - `totalTickets`: Total number of available tickets.
  - `releaseRates`: Rate of ticket release per cycle.
  - `retrievalRate`: Rate of ticket retrieval per cycle.
  - `maxPoolTickets`: Maximum number of tickets in the pool at a time.
  - `numberOfVendors`: Number of ticket vendors.
  - `numberOfCustomers`: Number of ticket customers.
- **Methods**:
  - `Getters` and `setters` for all attributes.
  - `toString()`: Generates a string representation of the configuration.

### 2. AbstractTicketHandler
An abstract class defining the structure for ticket handling operations. Derived classes implement specific logic.
- **Key Attributes**:
  - `ticketPool`: Shared resource for ticket management.
- **Methods**:
  - `AbstractTicketHandler(TicketPool ticketPool)`: Constructor initializing with a TicketPool instance.
  - `protected abstract void handleTickets()`: Defines ticket handling behavior.

### 3. TicketOperations
An interface specifying core ticket management methods.
- **Methods**:
  - `void addTicket(Ticket ticket)`: Adds a ticket to the pool.
  - `void removeTicket()`: Removes a ticket from the pool.

### 4. TicketPool
Implements TicketOperations and manages the ticket pool using thread-safe techniques.
- **Key Attributes**:
  - `soldTicketCounter`: Tracks total tickets sold.
  - `maxPoolTickets`: Pool capacity limit.
  - `counter`: Tracks tickets added to the pool.
  - `ticketPool`: Synchronized list storing tickets.
  - `stopFlag`: Indicator for halting operations.
- **Methods**:
  - `addTicket(Ticket ticket)`: Adds a ticket, blocking if the pool is full.
  - `removeTicket()`: Removes a ticket, blocking if the pool is empty.
  - `setStopFlag(boolean stopFlag)`: Sets the flag for terminating operations.
  - `shouldStop()`: Checks if the system should halt.

### 5. Utility and Logging
- **Logger**: Logs all operations, including ticket additions, removals, and delays due to full or empty pools.
  - `log(String message)`: Logs a message to the console and appends it to the log file.
  - `clearLogFile()`: Clears the log file by overwriting it with an empty string.
  - `writeToFile(String message, boolean append)`: Writes a message to the log file, appending or overwriting as specified.
 
### 6. Customer Class
### Customer
The Customer class simulates a customer thread that retrieves tickets from the TicketPool. It extends AbstractTicketHandler and implements Runnable.
- **Constructor**:
  ```java
  public Customer(TicketPool ticketPool, int retrievalRate, int totalTickets) {
      super(ticketPool);
      this.retrievalRate = retrievalRate;
      this.totalTickets = totalTickets;
  }
  ```
- **Methods**:
  - `handlerTickets()`: Overrides the method from AbstractTicketHandler to start the thread.
  - `run()`: Implements the ticket retrieval logic, pausing at intervals defined by retrievalRate.

### 8. Vendor Class
### Vendor
The Vendor class represents a thread that adds tickets to the TicketPool. It extends AbstractTicketHandler and implements Runnable.
- **Constructor**:
  ```java
  public Vendor(TicketPool ticketPool, int ticketReleaseRate, int totalTicket) {
      super(ticketPool);
      this.ticketReleaseRate = ticketReleaseRate;
      this.totalTicket = totalTicket;
  }
  ```
- **Methods**:
  - `handlerTickets()`: Overrides the method from AbstractTicketHandler to start the thread.
  - `run()`: Implements the ticket releasing logic, pausing at intervals defined by ticketReleaseRate.

### 9. CommandLineInterface Class
### CommandLineInterface
The CommandLineInterface class provides a console-based interface for initializing and managing system configurations. It uses Gson for JSON serialization and deserialization.
- **Methods**:
  - `configurations()`: Initializes system configurations by gathering user input or loading a saved configuration.

### 10. Ticket Class
### Ticket
The Ticket class represents a ticket with a unique ID and vendor ID.
- **Constructor**:
  ```java
  public Ticket(String ticketID, String vendorID) {
      this.ticketID = ticketID;
      this.vendorID = vendorID;
  }
  ```

### Setup and Configuration
### Prerequisites
- Java Development Kit (JDK) 8 or higher.
- A Java-compatible IDE or text editor.

### Installation
- Clone or download the project repository.
- Include necessary libraries in the project.

### Configuration
Adjust parameters in the Configurations class to meet desired ticketing needs.

### Running the System
- Implement ticket handlers by extending AbstractTicketHandler.
- Initialize TicketPool and Configurations.
- Start threads to execute ticket operations.

## Extensibility
- **Add Custom Ticket Handlers**: Derive from AbstractTicketHandler to implement specific ticket management logic.
- **Expand System Functionality**: Enhance the TicketPool class or introduce new interfaces to add advanced features.

