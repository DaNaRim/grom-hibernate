CREATE TABLE room
(
    id                  NUMBER PRIMARY KEY,
    number_of_guests    NUMBER CHECK (number_of_guests > 0 ),
    price               DECIMAL CHECK (price >= 0 ),
    breakfast_included  NUMBER CHECK (breakfast_included IN (1, 0)),
    pets_allowed        NUMBER CHECK (pets_allowed IN (1, 0)),
    date_available_from DATE,
    hotel_id            NUMBER,
    CONSTRAINT hotel_fk FOREIGN KEY (hotel_id) REFERENCES hotel (id)
);
