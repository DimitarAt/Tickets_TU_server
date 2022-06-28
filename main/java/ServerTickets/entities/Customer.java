package ServerTickets.entities;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name="customer",uniqueConstraints =
        {@UniqueConstraint (name = "user_name_constraint",columnNames = ("name"))})
public class Customer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name="name",unique = true)
    private String name;
    @Column(name="user_first_name")
    private String firstName;
    @Column(name="user_fam_name")
    private String famName;
    @Column (name="phone")
    private long phone;
    @Column (name="e-mail")
    private String eMail;
    @Column (name="password")
    private String password;
    @Column (name="token")
    private String token;
    @Column (name="authorities")
    private String authorities;


    public Customer(String name, String firstName, String famName, long phone, String eMail, String password, String authorities) {
        this.name = name;
        this.firstName = firstName;
        this.famName = famName;
        this.phone = phone;
        this.eMail = eMail;
        this.password = password;
        this.authorities = "USER";
    }



    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFamName() {
        return famName;
    }

    public void setFamName(String famName) {
        this.famName = famName;
    }

    public Customer(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAuthorities() {
        return authorities;
    }

    public void setAuthorities(String authorities) {
        this.authorities = authorities;
    }
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
