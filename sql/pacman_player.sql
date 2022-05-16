create table player
(
    id                     int auto_increment
        primary key,
    account_id             int           not null,
    nickname               varchar(255)  null comment '玩家昵称',
    level                  int default 1 null comment '等级',
    choose_hero            int default 0 null comment '头像',
    coin                   int default 0 null,
    exp                    int default 0 null,
    del                    int default 0 null,
    create_time            varchar(19)   null,
    update_time            varchar(19)   null,
    total                  int default 0 null,
    win                    int default 0 null,
    diamond                int default 0 null,
    kill_num               int default 0 null,
    death                  int default 0 null,
    item                   int default 0 null,
    double_coin_left       int default 0 null,
    double_exp_left        int default 0 null,
    last_free_lottery_time varchar(19)   null,
    head                   int default 1 null,
    constraint account_id
        unique (account_id)
)
    auto_increment = 17;

INSERT INTO pacman.player (id, account_id, nickname, level, choose_hero, coin, exp, del, create_time, update_time, total, win, diamond, kill_num, death, item, double_coin_left, double_exp_left, last_free_lottery_time, head) VALUES (1, 6, 'hahaha', 19, 744, 125499, 238, 0, '2021-11-28 14:39:58', '2022-04-09 22:03:24', 110, 59, 2208, 26, 86, 33, 5, 2, '2022-04-09 21:09:03', 3);
INSERT INTO pacman.player (id, account_id, nickname, level, choose_hero, coin, exp, del, create_time, update_time, total, win, diamond, kill_num, death, item, double_coin_left, double_exp_left, last_free_lottery_time, head) VALUES (2, 10, 'pacman', 1, 396, 0, 0, 0, '2021-11-28 22:55:07', '2021-12-30 19:53:14', 0, 0, 0, 0, 0, 0, 0, 0, '2021-12-27 15:40:32', 1);
INSERT INTO pacman.player (id, account_id, nickname, level, choose_hero, coin, exp, del, create_time, update_time, total, win, diamond, kill_num, death, item, double_coin_left, double_exp_left, last_free_lottery_time, head) VALUES (3, 4, 'YHC NB', 13, 46, 97636789, 205, 0, '2021-11-29 21:20:40', '2021-12-31 23:43:33', 78, 33, 1102661, 0, 62, 0, 0, 0, '2021-12-30 17:31:37', 2);
INSERT INTO pacman.player (id, account_id, nickname, level, choose_hero, coin, exp, del, create_time, update_time, total, win, diamond, kill_num, death, item, double_coin_left, double_exp_left, last_free_lottery_time, head) VALUES (4, 11, 'hdreamer', 11, 103, 6411919, 334, 0, '2021-12-04 21:32:45', '2021-12-31 21:18:45', 42, 33, 996729, 0, 6, 0, 0, 0, '2021-12-19 18:21:35', 1);
INSERT INTO pacman.player (id, account_id, nickname, level, choose_hero, coin, exp, del, create_time, update_time, total, win, diamond, kill_num, death, item, double_coin_left, double_exp_left, last_free_lottery_time, head) VALUES (5, 12, 'caster', 2, 482, 49500, 150, 0, '2021-12-24 17:03:38', '2021-12-29 18:49:41', 7, 4, 300, 0, 0, 0, 98, 30, '2021-12-26 00:34:18', 2);
INSERT INTO pacman.player (id, account_id, nickname, level, choose_hero, coin, exp, del, create_time, update_time, total, win, diamond, kill_num, death, item, double_coin_left, double_exp_left, last_free_lottery_time, head) VALUES (6, 13, 'pacman', 1, 403, 3200, 100, 0, '2021-12-25 12:01:53', '2021-12-30 19:53:15', 1, 1, 0, 0, 0, 0, 0, 0, '2021-12-25 21:56:15', 1);
INSERT INTO pacman.player (id, account_id, nickname, level, choose_hero, coin, exp, del, create_time, update_time, total, win, diamond, kill_num, death, item, double_coin_left, double_exp_left, last_free_lottery_time, head) VALUES (7, 14, 'pacman', 1, 307, 0, 0, 0, '2021-12-25 15:32:27', '2021-12-25 15:42:02', 0, 0, 0, 0, 0, 0, 0, 0, '2021-12-24 15:32:27', 1);
INSERT INTO pacman.player (id, account_id, nickname, level, choose_hero, coin, exp, del, create_time, update_time, total, win, diamond, kill_num, death, item, double_coin_left, double_exp_left, last_free_lottery_time, head) VALUES (8, 15, 'pacman', 1, 310, 5000, 0, 0, '2021-12-25 15:42:23', '2022-04-09 21:04:13', 0, 0, 600, 0, 0, 0, 0, 0, '2021-12-29 18:48:21', 1);
INSERT INTO pacman.player (id, account_id, nickname, level, choose_hero, coin, exp, del, create_time, update_time, total, win, diamond, kill_num, death, item, double_coin_left, double_exp_left, last_free_lottery_time, head) VALUES (9, 16, 'fanzero', 2, 361, 19600, 20, 0, '2021-12-25 16:42:05', '2022-04-09 21:29:21', 10, 1, 70, 0, 17, 0, 1, 1, '2022-04-09 21:20:46', 2);
INSERT INTO pacman.player (id, account_id, nickname, level, choose_hero, coin, exp, del, create_time, update_time, total, win, diamond, kill_num, death, item, double_coin_left, double_exp_left, last_free_lottery_time, head) VALUES (10, 17, 'pacman', 2, 394, 19700, 81, 0, '2021-12-25 18:14:10', '2021-12-31 10:26:47', 10, 2, 50, 0, 3, 0, 0, 0, '2021-12-24 18:16:32', 1);
INSERT INTO pacman.player (id, account_id, nickname, level, choose_hero, coin, exp, del, create_time, update_time, total, win, diamond, kill_num, death, item, double_coin_left, double_exp_left, last_free_lottery_time, head) VALUES (11, 18, 'pacman', 1, 481, 4100, 133, 0, '2021-12-26 11:24:23', '2021-12-31 00:20:50', 2, 1, 0, 0, 0, 0, 0, 0, '2021-12-25 11:24:23', 1);
INSERT INTO pacman.player (id, account_id, nickname, level, choose_hero, coin, exp, del, create_time, update_time, total, win, diamond, kill_num, death, item, double_coin_left, double_exp_left, last_free_lottery_time, head) VALUES (12, 20, 'test_2001_12_30', 1, 634, 4400, 99, 0, '2021-12-30 20:36:29', '2022-01-01 12:40:50', 3, 1, 0, 0, 4, 0, 0, 0, '2021-12-30 20:42:41', 3);
INSERT INTO pacman.player (id, account_id, nickname, level, choose_hero, coin, exp, del, create_time, update_time, total, win, diamond, kill_num, death, item, double_coin_left, double_exp_left, last_free_lottery_time, head) VALUES (13, 21, 'pacman', 1, 642, 0, 0, 0, '2021-12-31 01:07:30', '2021-12-31 01:17:05', 0, 0, 0, 0, 0, 0, 0, 0, '2021-12-31 01:07:59', 1);
INSERT INTO pacman.player (id, account_id, nickname, level, choose_hero, coin, exp, del, create_time, update_time, total, win, diamond, kill_num, death, item, double_coin_left, double_exp_left, last_free_lottery_time, head) VALUES (14, 22, 'pacman', 1, 644, 0, 0, 0, '2021-12-31 01:25:21', '2021-12-31 01:27:16', 0, 0, 0, 0, 0, 0, 0, 0, '2021-12-30 01:25:21', 2);
INSERT INTO pacman.player (id, account_id, nickname, level, choose_hero, coin, exp, del, create_time, update_time, total, win, diamond, kill_num, death, item, double_coin_left, double_exp_left, last_free_lottery_time, head) VALUES (15, 23, 'G', 1, 720, 1080, 72, 0, '2021-12-31 20:50:28', '2021-12-31 21:10:35', 2, 0, 0, 0, 0, 0, 0, 0, '2021-12-31 20:51:58', 1);
INSERT INTO pacman.player (id, account_id, nickname, level, choose_hero, coin, exp, del, create_time, update_time, total, win, diamond, kill_num, death, item, double_coin_left, double_exp_left, last_free_lottery_time, head) VALUES (16, 24, 'xxxxxx', 1, 723, 1150, 166, 0, '2022-04-09 21:13:55', '2022-04-09 21:42:31', 5, 1, 0, 0, 9, 0, 0, 0, '2022-04-09 21:14:11', 4);
