create table item_config
(
    id   int auto_increment
        primary key,
    name varchar(255) null,
    type int          null,
    prop varchar(255) null,
    des  varchar(255) null
)
    auto_increment = 10;

INSERT INTO pacman.item_config (id, name, type, prop, des) VALUES (1, 'Double exp capsule(3)', 4, '3', null);
INSERT INTO pacman.item_config (id, name, type, prop, des) VALUES (2, 'Double coin capsule(3)', 3, '3', null);
INSERT INTO pacman.item_config (id, name, type, prop, des) VALUES (3, 'Cream fragment', 2, '1', null);
INSERT INTO pacman.item_config (id, name, type, prop, des) VALUES (4, 'Cookie fragment', 2, '1', null);
INSERT INTO pacman.item_config (id, name, type, prop, des) VALUES (5, 'Coin Card', 5, '2000', null);
INSERT INTO pacman.item_config (id, name, type, prop, des) VALUES (6, 'Diamond Card', 6, '50', null);
INSERT INTO pacman.item_config (id, name, type, prop, des) VALUES (7, 'Coin lottery ticket', 11, null, null);
INSERT INTO pacman.item_config (id, name, type, prop, des) VALUES (8, 'Diamond lottery ticket', 12, null, null);
INSERT INTO pacman.item_config (id, name, type, prop, des) VALUES (9, 'Treasure box', 9, null, null);
