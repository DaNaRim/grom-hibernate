package lesson3.model;

import javax.persistence.*;

@Entity
@Table(name = "hotel")
public class Hotel {

    private long id;
    private String name;
    private String country;
    private String city;
    private String street;

    public Hotel() {
    }

    public Hotel(String name, String country, String city, String street) {
        this.name = name;
        this.country = country;
        this.city = city;
        this.street = street;
    }

    @Id
    @SequenceGenerator(name = "hotelSeq", sequenceName = "hotel_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hotelSeq")
    @Column(name = "id")
    public long getId() {
        return id;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    @Column(name = "country")
    public String getCountry() {
        return country;
    }

    @Column(name = "city")
    public String getCity() {
        return city;
    }

    @Column(name = "street")
    public String getStreet() {
        return street;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setStreet(String street) {
        this.street = street;
    }
}
