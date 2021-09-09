CREATE TABLE users
(
    id        NUMBER PRIMARY KEY,
    username  NVARCHAR2(20)                NOT NULL UNIQUE,
    password  NVARCHAR2(50)                NOT NULL,
    country   NVARCHAR2(50)                NOT NULL,
    user_type NVARCHAR2(20) DEFAULT 'USER' NOT NULL,
    CHECK (user_type IN ('USER', 'ADMIN'))
);
