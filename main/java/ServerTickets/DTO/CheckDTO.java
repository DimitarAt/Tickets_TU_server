package ServerTickets.DTO;

public class CheckDTO {
    private String name;
    private boolean exists=false;

    public CheckDTO(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isExists() {
        return exists;
    }

    public void setExists(boolean exists) {
        this.exists = exists;
    }


}
