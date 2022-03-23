package lesson4.model;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {

    private Long id;
    private String userName;
    private String password;
    private String country;
    private UserType userType = UserType.USER;

    public User() {
    }

    public User(String userName, String password, String country) {
        this.userName = userName;
        this.password = password;
        this.country = country;
    }

    @Id
    @SequenceGenerator(name = "userSeq", sequenceName = "user_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userSeq")
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    @Column(name = "username")
    public String getUserName() {
        return userName;
    }

    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    @Column(name = "country")
    public String getCountry() {
        return country;
    }

    @Column(name = "user_type") //TODO check enum
    public String getUserType() {
        return userType.toString();
    }

    @Transient
    public UserType getUserTypeEnum() {
        return userType;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setUserType(String userType) {
        this.userType = userType.equals("ADMIN") ? UserType.ADMIN : UserType.USER;
    }

    public void setUserTypeEnum(UserType userType) {
        this.userType = userType;
    }
}
