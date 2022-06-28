package ServerTickets.DTO;




public class SeatsDTO {
    private long id;
    private String description;
    private String type;
    private int number;
    private int price=0;

    public SeatsDTO(long id, String description, String type, int number) {
        this.id = id;
        this.description = description;
        this.type = type;
        this.number = number;
        this.price = 0;
    }

    public SeatsDTO() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
