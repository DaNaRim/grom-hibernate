CREATE TABLE room
(
    id                  NUMBER PRIMARY KEY,
    number_of_guests    NUMBER CHECK (number_of_guests > 0),
    price               NUMBER(*, 2) CHECK (price >= 0),
    breakfast_included  NUMBER(1) CHECK (breakfast_included IN (0, 1)),
    pets_allowed        NUMBER(1) CHECK (pets_allowed IN (0, 1)),
    date_available_from DATE NOT NULL,
    hotel_id            NUMBER,
    CONSTRAINT hotel_fk FOREIGN KEY (hotel_id) REFERENCES hotel (id)
);
