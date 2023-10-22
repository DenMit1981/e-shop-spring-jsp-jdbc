INSERT INTO user(login, password)
VALUES ('Den', '1234'),
       ('Peter', '5678'),
       ('Asya', '4321'),
       ('Sveta', '8765'),
       ('Jimmy', '1111');

 INSERT INTO good(title, price)
 VALUES ('Book', 5.5),
        ('Phone', 100),
        ('Juice', 2),
        ('Phone', 200),
        ('Beer', 1.5),
        ('Computer', 500),
        ('Wisky', 4.2);

INSERT INTO orders(user_id, total_price)
VALUES (1, 75),
       (3, 134.56),
       (1, 54),
       (2, 175),
       (1, 25),
       (5, 63),
       (4, 88);
