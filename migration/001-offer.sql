CREATE TABLE offer
(
    id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    user_id INT NOT NULL,
    title VARCHAR(255) NOT NULL,
    description VARCHAR(755) NOT NULL,
    headline VARCHAR(40),
    condition VARCHAR(40) NOT NULL,
    available_from DATETIME NOT NULL,
    available_to DATETIME NOT NULL,
    starting_price INT NOT NULL,
    currency VARCHAR(40) NOT NULL,
    category VARCHAR(255) NOT NULL
);

CREATE UNIQUE INDEX offer_id_uindex ON offer (id);