CREATE TABLE orders
(
    id         NUMBER PRIMARY KEY,
    user_id    NUMBER,
    room_id    NUMBER,
    date_from  DATE NOT NULL,
    date_to    DATE NOT NULL,
    money_paid NUMBER(*, 2),
    CONSTRAINT user_fk FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT room_fk FOREIGN KEY (room_id) REFERENCES room (id)
);
