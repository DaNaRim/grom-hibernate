package lesson4.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "room")
public class Room {

    private Long id;
    private Integer numberOfGuests;
    private Double price;
    private Boolean breakfastIncluded;
    private Boolean petsAllowed;
    private Date dateAvailableFrom;
    private Hotel hotel;

    public Room() {
    }

    public Room(Integer numberOfGuests, Double price, Boolean breakfastIncluded, Boolean petsAllowed,
                Date dateAvailableFrom, Hotel hotel) {
        this.numberOfGuests = numberOfGuests;
        this.price = price;
        this.breakfastIncluded = breakfastIncluded;
        this.petsAllowed = petsAllowed;
        this.dateAvailableFrom = dateAvailableFrom;
        this.hotel = hotel;
    }

    @Id
    @SequenceGenerator(name = "roomSeq", sequenceName = "room_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "roomSeq")
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    @Column(name = "number_of_guests")
    public Integer getNumberOfGuests() {
        return numberOfGuests;
    }

    @Column(name = "price")
    public double getPrice() {
        return price;
    }

    @Column(name = "breakfast_included")
    public int getBreakfastIncluded() {
        return breakfastIncluded ? 1 : 0;
    }

    @Transient
    public Boolean isBreakfastIncluded() {
        return breakfastIncluded;
    }

    @Column(name = "pets_allowed")
    public int getPetsAllowed() {
        return petsAllowed ? 1 : 0;
    }

    @Transient
    public Boolean isPetsAllowed() {
        return petsAllowed;
    }

    @Column(name = "date_available_from")
    public Date getDateAvailableFrom() {
        return dateAvailableFrom;
    }

    @ManyToOne
    @JoinColumn(name = "hotel_id", nullable = false)
    public Hotel getHotel() {
        return hotel;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNumberOfGuests(Integer numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setBreakfastIncluded(int breakfastIncluded) {
        this.breakfastIncluded = breakfastIncluded == 1;
    }

    public void setPetsAllowed(int petsAllowed) {
        this.petsAllowed = petsAllowed == 1;
    }

    public void setDateAvailableFrom(Date dateAvailableFrom) {
        this.dateAvailableFrom = dateAvailableFrom;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                '}';
    }
}
