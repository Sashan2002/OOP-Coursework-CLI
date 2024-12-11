package util;

public class Ticket {
    private String ticketID;
    private String vendorID;
    public Ticket(String ticketID, String vendorID) {
        this.ticketID = ticketID;
        this.vendorID = vendorID;
    }

    public String getTicketID() {
        return ticketID;
    }

    public void setTicketID(String ticketID) {
        this.ticketID = ticketID;
    }

    public String getVendorID() {
        return vendorID;
    }

    public void setVendorID(String vendorID) {
        this.vendorID = vendorID;
    }




}
