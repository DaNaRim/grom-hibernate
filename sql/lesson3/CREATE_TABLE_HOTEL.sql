CREATE TABLE hotel
(
    id      NUMBER PRIMARY KEY,
    name    NVARCHAR2(50) NOT NULL,
    country NVARCHAR2(50) NOT NULL,
    city    NVARCHAR2(50) NOT NULL,
    street  NVARCHAR2(50) NOT NULL
);
