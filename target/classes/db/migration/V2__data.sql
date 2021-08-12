INSERT INTO users(username, email, password)
VALUES ('user1', 'user1@gmail.com', '$2a$10$/NWjaUsMSsBI9o9.JiN9sOtk3juajcPZZVDLz7PHSBauJ/oYZORqq');
INSERT INTO users(username, email, password)
VALUES ('user2', 'user2@gmail.com', '$2a$10$e5Me/PNckmd/FIPunpd7ieDbH21g6FNKDztWWNFiSypiKrlagsyyu');
INSERT INTO users(username, email, password)
VALUES ('user3', 'user3@gmail.com', '$2a$10$b7offEb.QUvhDrgniUDDT.xSnM7EOLQLk3NS.QuEy/YGoVrrfeD1.');
INSERT INTO users(username, email, password)
VALUES ('user4', 'user4@gmail.com', '$2a$10$Ht3uAlcu/GFeATseuWAFqOiLnxtTT0if1pcWpa5v6d14ETylOiOuu');
INSERT INTO users(username, email, password)
VALUES ('user5', 'user5@gmail.com', '$2a$10$TI5x0FJxJAPhljodszn3FuOceyngBtuVI0l7oRKAu29sst0dvqrw.');

INSERT INTO products(title, price, description, user_id)
VALUES ('title1', 24.99, 'description1', 1);
INSERT INTO products(title, price, description, user_id)
VALUES ('title2', 34.99, 'description2', 2);
INSERT INTO products(title, price, description, user_id)
VALUES ('title3', 44.99, 'description3', 3);
INSERT INTO products(title, price, description, user_id)
VALUES ('title4', 124.99, 'description4', 1);
INSERT INTO products(title, price, description, user_id)
VALUES ('title5', 30.99, 'description5', 2);
INSERT INTO products(title, price, description, user_id)
VALUES ('title6', 12.99, 'description6', 5);
INSERT INTO products(title, price, description, user_id)
VALUES ('title7', 104.99, 'description7', 4);

INSERT INTO rating(user_id, product_id, is_like)
VALUES (1, 2, TRUE);
INSERT INTO rating(user_id, product_id, is_like)
VALUES (1, 3, FALSE);
INSERT INTO rating(user_id, product_id, is_like)
VALUES (1, 5, TRUE);