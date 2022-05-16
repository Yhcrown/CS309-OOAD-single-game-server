create table hero_config
(
    id          int default 0 not null
        primary key,
    name        varchar(255)  not null,
    init_speed  int default 0 null,
    init_skill  int default 0 null,
    init_exp    int           null,
    init_coin   int           null,
    lv_up_speed int           null,
    lv_up_skill int           null,
    lv_up_coin  int           null,
    lv_up_exp   int           null
);

INSERT INTO pacman.hero_config (id, name, init_speed, init_skill, init_exp, init_coin, lv_up_speed, lv_up_skill, lv_up_coin, lv_up_exp) VALUES (1, 'lucky', 80, 120, 10, 10, 1, 1, 1, 1);
INSERT INTO pacman.hero_config (id, name, init_speed, init_skill, init_exp, init_coin, lv_up_speed, lv_up_skill, lv_up_coin, lv_up_exp) VALUES (2, 'panda', 90, 110, 10, 10, 1, 1, 1, 1);
INSERT INTO pacman.hero_config (id, name, init_speed, init_skill, init_exp, init_coin, lv_up_speed, lv_up_skill, lv_up_coin, lv_up_exp) VALUES (3, 'cookie', 100, 100, 0, 20, 1, 1, 0, 2);
INSERT INTO pacman.hero_config (id, name, init_speed, init_skill, init_exp, init_coin, lv_up_speed, lv_up_skill, lv_up_coin, lv_up_exp) VALUES (4, 'cream', 100, 100, 0, 20, 1, 1, 0, 2);
INSERT INTO pacman.hero_config (id, name, init_speed, init_skill, init_exp, init_coin, lv_up_speed, lv_up_skill, lv_up_coin, lv_up_exp) VALUES (5, 'ketchup', 120, 80, 20, 0, 1, 1, 2, 0);
INSERT INTO pacman.hero_config (id, name, init_speed, init_skill, init_exp, init_coin, lv_up_speed, lv_up_skill, lv_up_coin, lv_up_exp) VALUES (6, 'heck', 110, 90, 20, 0, 1, 1, 2, 0);
