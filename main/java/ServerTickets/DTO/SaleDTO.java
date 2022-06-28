package ServerTickets.DTO;

public class SaleDTO {
    private long seatsId;
    private int seatsSold;
    private String buyer;
    private String distributor;

    public long getSeatsId() {
        return seatsId;
    }

    public void setSeatsId(long seatsId) {
        this.seatsId = seatsId;
    }

    public int getSeatsSold() {
        return seatsSold;
    }

    public void setSeatsSold(int seatsSold) {
        this.seatsSold = seatsSold;
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public String getDistributor() {
        return distributor;
    }

    public void setDistributor(String distributor) {
        this.distributor = distributor;
    }
}
