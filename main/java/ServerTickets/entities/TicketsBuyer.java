package ServerTickets.entities;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name ="buyers")
public class TicketsBuyer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name="name",nullable = false, length = 30)
    private  String name;

    @Column(name="phone",nullable = false)
    private  long phone;

    @Column(name="e_mail",nullable = true, length = 30)
    private  String eMail;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }
}