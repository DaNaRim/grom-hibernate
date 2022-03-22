package lesson3.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "room")
public class Room {

    private long id;
    private int numberOfGuests;
    private double price;
    private int breakfastIncluded;
    private int petsAllowed;
    private Date dateAvailableFrom;
    private Hotel hotel;

    public Room() {
    }

    public Room(int numberOfGuests,
                double price,
                int breakfastIncluded,
                int petsAllowed,
                Date dateAvailableFrom,
                Hotel hotel) {
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
    public long getId() {
        return id;
    }

    @Column(name = "number_of_guests")
    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    @Column(name = "price")
    public double getPrice() {
        return price;
    }

    @Column(name = "breakfast_included")
    public int getBreakfastIncluded() {
        return breakfastIncluded;
    }

    @Column(name = "pets_allowed")
    public int getPetsAllowed() {
        return petsAllowed;
    }

    @Column(name = "date_available_from")
    public Date getDateAvailableFrom() {
        return dateAvailableFrom;
    }

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "hotel_id")
    public Hotel getHotel() {
        return hotel;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setNumberOfGuests(int numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setBreakfastIncluded(int breakfastIncluded) {
        this.breakfastIncluded = breakfastIncluded;
    }

    public void setPetsAllowed(int petsAllowed) {
        this.petsAllowed = petsAllowed;
    }

    public void setDateAvailableFrom(Date dateAvailableFrom) {
        this.dateAvailableFrom = dateAvailableFrom;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }
}
