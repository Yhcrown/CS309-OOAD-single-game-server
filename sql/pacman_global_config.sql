create table global_config
(
    id   int          null,
    name varchar(255) null,
    val  varchar(255) null,
    des  varchar(255) null
);

INSERT INTO pacman.global_config (id, name, val, des) VALUES (1, 'hero_max_lv_star', '20', null);
INSERT INTO pacman.global_config (id, name, val, des) VALUES (2, 'player_lv_up_exp', '400', null);
INSERT INTO pacman.global_config (id, name, val, des) VALUES (3, 'hero_lv_up_cost', '100', null);
INSERT INTO pacman.global_config (id, name, val, des) VALUES (4, 'one_lottery_coin', '3000', null);
INSERT INTO pacman.global_config (id, name, val, des) VALUES (5, 'ten_lottery_coin', '25000', null);
INSERT INTO pacman.global_config (id, name, val, des) VALUES (6, 'one_lottery_diamond', '60', null);
INSERT INTO pacman.global_config (id, name, val, des) VALUES (7, 'ten_lottery_diamond', '500', null);
INSERT INTO pacman.global_config (id, name, val, des) VALUES (8, 'coin_one_rate', '88', null);
INSERT INTO pacman.global_config (id, name, val, des) VALUES (9, 'coin_two_rate', '98', null);
INSERT INTO pacman.global_config (id, name, val, des) VALUES (10, 'free_lottery_time', '86400', null);
INSERT INTO pacman.global_config (id, name, val, des) VALUES (11, 'coin_lottery_item_type_id', '11', null);
INSERT INTO pacman.global_config (id, name, val, des) VALUES (12, 'diamond_lottery_item_type_id', '12', null);
