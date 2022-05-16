create table item
(
    id          int auto_increment
        primary key,
    del         int default 0 null,
    create_time varchar(19)   null,
    update_time varchar(19)   null,
    account_id  int           not null,
    type_id     int           null,
    num         int           null,
    name        varchar(255)  null
)
    auto_increment = 80;

INSERT INTO pacman.item (id, del, create_time, update_time, account_id, type_id, num, name) VALUES (1, 0, '2021-12-11 16:49:02', '2021-12-31 21:18:46', 11, 4, 33, 'double_exp_card');
INSERT INTO pacman.item (id, del, create_time, update_time, account_id, type_id, num, name) VALUES (2, 0, '2021-12-11 17:45:18', '2021-12-31 21:18:46', 11, 3, 31, 'Double coin capsule--7');
INSERT INTO pacman.item (id, del, create_time, update_time, account_id, type_id, num, name) VALUES (3, 0, '2021-12-11 17:45:18', '2021-12-31 21:18:46', 11, 5, 27, 'Coin Card');
INSERT INTO pacman.item (id, del, create_time, update_time, account_id, type_id, num, name) VALUES (6, 0, '2021-12-18 14:58:03', '2021-12-31 21:18:46', 11, 11, 28, 'Coin lottery ticket');
INSERT INTO pacman.item (id, del, create_time, update_time, account_id, type_id, num, name) VALUES (7, 0, '2021-12-18 14:58:03', '2021-12-31 21:18:46', 11, 12, 59, 'Diamond lottery ticket');
INSERT INTO pacman.item (id, del, create_time, update_time, account_id, type_id, num, name) VALUES (8, 0, '2021-12-18 17:47:11', '2021-12-31 21:18:46', 11, 9, 16, 'Treasure box');
INSERT INTO pacman.item (id, del, create_time, update_time, account_id, type_id, num, name) VALUES (9, 0, '2021-12-18 18:01:07', '2021-12-31 21:18:46', 11, 6, 11, 'Diamond Card');
INSERT INTO pacman.item (id, del, create_time, update_time, account_id, type_id, num, name) VALUES (10, 0, '2021-12-23 15:22:25', '2022-04-09 22:03:25', 6, 9, 50, 'Treasure box');
INSERT INTO pacman.item (id, del, create_time, update_time, account_id, type_id, num, name) VALUES (32, 0, '2021-12-24 17:14:04', '2021-12-29 18:49:41', 12, 9, 8, 'Treasure box');
INSERT INTO pacman.item (id, del, create_time, update_time, account_id, type_id, num, name) VALUES (33, 0, '2021-12-24 17:14:31', '2021-12-29 18:49:41', 12, 12, 4, 'Diamond lottery ticket');
INSERT INTO pacman.item (id, del, create_time, update_time, account_id, type_id, num, name) VALUES (34, 0, '2021-12-24 17:46:23', '2022-04-09 22:03:25', 6, 6, 0, 'Diamond Card');
INSERT INTO pacman.item (id, del, create_time, update_time, account_id, type_id, num, name) VALUES (35, 0, '2021-12-24 17:51:09', '2022-04-09 22:03:25', 6, 12, 7, 'Diamond lottery ticket');
INSERT INTO pacman.item (id, del, create_time, update_time, account_id, type_id, num, name) VALUES (36, 0, '2021-12-24 18:49:08', '2021-12-29 18:49:42', 12, 6, 0, 'Diamond Card');
INSERT INTO pacman.item (id, del, create_time, update_time, account_id, type_id, num, name) VALUES (37, 0, '2021-12-24 22:20:12', '2022-04-09 22:03:25', 6, 3, 0, 'Double coin capsule(7)');
INSERT INTO pacman.item (id, del, create_time, update_time, account_id, type_id, num, name) VALUES (38, 0, '2021-12-24 22:20:15', '2022-04-09 22:03:25', 6, 11, 3, 'Coin lottery ticket');
INSERT INTO pacman.item (id, del, create_time, update_time, account_id, type_id, num, name) VALUES (39, 0, '2021-12-24 22:29:24', '2022-04-09 22:03:25', 6, 4, 15, 'Double exp capsule(7)');
INSERT INTO pacman.item (id, del, create_time, update_time, account_id, type_id, num, name) VALUES (40, 0, '2021-12-25 00:30:41', '2021-12-29 18:49:42', 12, 11, 19, 'Coin lottery ticket');
INSERT INTO pacman.item (id, del, create_time, update_time, account_id, type_id, num, name) VALUES (41, 0, '2021-12-25 00:30:48', '2021-12-29 18:49:42', 12, 3, 9, 'Double coin capsule(7)');
INSERT INTO pacman.item (id, del, create_time, update_time, account_id, type_id, num, name) VALUES (42, 0, '2021-12-25 14:50:42', '2021-12-29 18:49:42', 12, 4, 10, 'Double exp capsule(7)');
INSERT INTO pacman.item (id, del, create_time, update_time, account_id, type_id, num, name) VALUES (43, 0, '2021-12-25 15:32:28', '2021-12-25 15:42:03', 14, 9, 14, 'Treasure box');
INSERT INTO pacman.item (id, del, create_time, update_time, account_id, type_id, num, name) VALUES (44, 0, '2021-12-25 15:32:40', '2021-12-25 15:42:03', 14, 6, 0, 'Diamond Card');
INSERT INTO pacman.item (id, del, create_time, update_time, account_id, type_id, num, name) VALUES (45, 0, '2021-12-25 15:32:42', '2021-12-25 15:42:03', 14, 5, 8, 'Coin Card');
INSERT INTO pacman.item (id, del, create_time, update_time, account_id, type_id, num, name) VALUES (46, 0, '2021-12-25 15:32:47', '2021-12-25 15:42:03', 14, 12, 13, 'Diamond lottery ticket');
INSERT INTO pacman.item (id, del, create_time, update_time, account_id, type_id, num, name) VALUES (47, 0, '2021-12-25 15:32:53', '2021-12-25 15:42:03', 14, 11, 5, 'Coin lottery ticket');
INSERT INTO pacman.item (id, del, create_time, update_time, account_id, type_id, num, name) VALUES (48, 0, '2021-12-25 15:33:00', '2022-04-09 22:03:25', 6, 5, 0, 'Coin Card');
INSERT INTO pacman.item (id, del, create_time, update_time, account_id, type_id, num, name) VALUES (49, 0, '2021-12-25 15:42:24', '2022-04-09 21:04:14', 15, 9, 25, 'Treasure box');
INSERT INTO pacman.item (id, del, create_time, update_time, account_id, type_id, num, name) VALUES (50, 0, '2021-12-25 15:42:35', '2022-04-09 21:04:14', 15, 11, 26, 'Coin lottery ticket');
INSERT INTO pacman.item (id, del, create_time, update_time, account_id, type_id, num, name) VALUES (51, 0, '2021-12-25 15:42:37', '2022-04-09 21:04:14', 15, 5, 25, 'Coin Card');
INSERT INTO pacman.item (id, del, create_time, update_time, account_id, type_id, num, name) VALUES (52, 0, '2021-12-25 15:42:38', '2022-04-09 21:04:14', 15, 12, 21, 'Diamond lottery ticket');
INSERT INTO pacman.item (id, del, create_time, update_time, account_id, type_id, num, name) VALUES (53, 0, '2021-12-25 15:42:40', '2022-04-09 21:04:14', 15, 6, 5, 'Diamond Card');
INSERT INTO pacman.item (id, del, create_time, update_time, account_id, type_id, num, name) VALUES (54, 0, '2021-12-25 15:42:44', '2022-04-09 21:04:14', 15, 3, 10, 'Double coin capsule(7)');
INSERT INTO pacman.item (id, del, create_time, update_time, account_id, type_id, num, name) VALUES (55, 0, '2021-12-25 15:42:59', '2022-04-09 21:04:14', 15, 4, 15, 'Double exp capsule(7)');
INSERT INTO pacman.item (id, del, create_time, update_time, account_id, type_id, num, name) VALUES (56, 0, '2021-12-25 16:42:07', '2022-04-09 21:29:22', 16, 9, 1, 'Treasure box');
INSERT INTO pacman.item (id, del, create_time, update_time, account_id, type_id, num, name) VALUES (57, 0, '2021-12-25 16:42:10', '2022-04-09 21:29:22', 16, 5, 0, 'Coin Card');
INSERT INTO pacman.item (id, del, create_time, update_time, account_id, type_id, num, name) VALUES (58, 0, '2021-12-25 16:42:11', '2022-04-09 21:29:22', 16, 11, 0, 'Coin lottery ticket');
INSERT INTO pacman.item (id, del, create_time, update_time, account_id, type_id, num, name) VALUES (59, 0, '2021-12-25 16:42:14', '2022-04-09 21:29:22', 16, 3, 7, 'Double coin capsule(7)');
INSERT INTO pacman.item (id, del, create_time, update_time, account_id, type_id, num, name) VALUES (60, 0, '2021-12-25 16:57:58', '2022-04-09 21:29:22', 16, 4, 15, 'Double exp capsule(7)');
INSERT INTO pacman.item (id, del, create_time, update_time, account_id, type_id, num, name) VALUES (61, 0, '2021-12-25 16:58:00', '2022-04-09 21:29:22', 16, 6, 0, 'Diamond Card');
INSERT INTO pacman.item (id, del, create_time, update_time, account_id, type_id, num, name) VALUES (62, 0, '2021-12-25 17:29:06', '2022-04-09 21:29:22', 16, 12, 0, 'Diamond lottery ticket');
INSERT INTO pacman.item (id, del, create_time, update_time, account_id, type_id, num, name) VALUES (63, 0, '2021-12-25 18:14:10', '2021-12-31 10:26:47', 17, 9, 4, 'Treasure box');
INSERT INTO pacman.item (id, del, create_time, update_time, account_id, type_id, num, name) VALUES (64, 0, '2021-12-25 18:14:14', '2021-12-31 10:26:47', 17, 11, 3, 'Coin lottery ticket');
INSERT INTO pacman.item (id, del, create_time, update_time, account_id, type_id, num, name) VALUES (65, 0, '2021-12-25 21:59:23', '2021-12-30 19:53:15', 13, 9, 1, 'Treasure box');
INSERT INTO pacman.item (id, del, create_time, update_time, account_id, type_id, num, name) VALUES (66, 0, '2021-12-25 21:59:26', '2021-12-30 19:53:15', 13, 12, 0, 'Diamond lottery ticket');
INSERT INTO pacman.item (id, del, create_time, update_time, account_id, type_id, num, name) VALUES (67, 0, '2021-12-25 22:05:42', '2021-12-30 19:53:15', 13, 4, 2, 'Double exp capsule(3)');
INSERT INTO pacman.item (id, del, create_time, update_time, account_id, type_id, num, name) VALUES (68, 0, '2021-12-25 22:12:30', '2021-12-31 23:43:34', 4, 9, 21, 'Treasure box');
INSERT INTO pacman.item (id, del, create_time, update_time, account_id, type_id, num, name) VALUES (69, 0, '2021-12-25 22:13:15', '2021-12-31 23:43:34', 4, 11, 0, 'Coin lottery ticket');
INSERT INTO pacman.item (id, del, create_time, update_time, account_id, type_id, num, name) VALUES (70, 0, '2021-12-25 22:13:17', '2021-12-31 23:43:34', 4, 5, 1, 'Coin Card');
INSERT INTO pacman.item (id, del, create_time, update_time, account_id, type_id, num, name) VALUES (71, 0, '2021-12-26 13:24:09', '2021-12-31 00:20:50', 18, 9, 1, 'Treasure box');
INSERT INTO pacman.item (id, del, create_time, update_time, account_id, type_id, num, name) VALUES (72, 0, '2021-12-26 17:08:18', '2021-12-29 18:49:42', 12, 5, 0, 'Coin Card');
INSERT INTO pacman.item (id, del, create_time, update_time, account_id, type_id, num, name) VALUES (73, 0, '2021-12-27 00:45:53', '2021-12-31 23:43:34', 4, 4, 5, 'Double exp capsule(3)');
INSERT INTO pacman.item (id, del, create_time, update_time, account_id, type_id, num, name) VALUES (74, 0, '2021-12-27 13:54:36', '2021-12-31 23:43:34', 4, 12, 0, 'Diamond lottery ticket');
INSERT INTO pacman.item (id, del, create_time, update_time, account_id, type_id, num, name) VALUES (75, 0, '2021-12-27 23:11:46', '2021-12-31 23:43:34', 4, 3, 1, 'Double coin capsule(3)');
INSERT INTO pacman.item (id, del, create_time, update_time, account_id, type_id, num, name) VALUES (76, 0, '2021-12-30 16:13:08', '2021-12-31 23:43:34', 4, 6, 0, 'Diamond Card');
INSERT INTO pacman.item (id, del, create_time, update_time, account_id, type_id, num, name) VALUES (77, 0, '2021-12-30 22:14:04', '2022-01-01 12:40:50', 20, 9, 0, 'Treasure box');
INSERT INTO pacman.item (id, del, create_time, update_time, account_id, type_id, num, name) VALUES (78, 0, '2021-12-31 20:58:16', '2021-12-31 21:10:36', 23, 9, 0, 'Treasure box');
INSERT INTO pacman.item (id, del, create_time, update_time, account_id, type_id, num, name) VALUES (79, 0, '2022-04-09 21:25:26', '2022-04-09 21:42:31', 24, 9, 0, 'Treasure box');
