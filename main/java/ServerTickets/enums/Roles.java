package ServerTickets.enums;

public enum Roles {ADMIN("AD"),ORGANIZER("OR"),AGENT("AG"),USER("US");

    private String roleCode;

    private Roles(String roleCode){
        this.roleCode=roleCode;
    }

    public String getRoleCode() {
        return roleCode;
    }
}
