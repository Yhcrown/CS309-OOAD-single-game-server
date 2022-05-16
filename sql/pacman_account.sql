create table account
(
    id          int auto_increment comment 'id'
        primary key,
    account     varchar(255) default '' not null comment '账号',
    password    varchar(255) default '' not null comment '密码（MD5加密）',
    del         int          default 0  null comment '逻辑删除',
    create_time varchar(19)  default '' null comment '创建时间',
    update_time varchar(19)  default '' null comment '更新时间',
    constraint account
        unique (account) comment '账号索引'
)
    engine = MyISAM
    auto_increment = 25;

INSERT INTO pacman.account (id, account, password, del, create_time, update_time) VALUES (4, '876680538', 'ba8c8eef2ce4a75eb264485baabbf6ae', 0, '2021-11-03 17:44:04', '2021-11-03 17:44:04');
INSERT INTO pacman.account (id, account, password, del, create_time, update_time) VALUES (5, '12345678', '4f0b36a34946153c358f8b243428a1eb', 0, '2021-11-03 21:17:26', '2021-11-03 21:17:26');
INSERT INTO pacman.account (id, account, password, del, create_time, update_time) VALUES (6, 'fanzero', '3c3013a95a2a7d5c837b8a1ac569b00e', 0, '2021-11-03 21:45:44', '2021-11-03 21:45:44');
INSERT INTO pacman.account (id, account, password, del, create_time, update_time) VALUES (7, '1145141919', '25d55ad283aa400af464c76d713c07ad', 0, '2021-11-03 23:19:12', '2021-11-03 23:19:12');
INSERT INTO pacman.account (id, account, password, del, create_time, update_time) VALUES (8, 'NORINCO', '25d55ad283aa400af464c76d713c07ad', 0, '2021-11-26 10:50:55', '2021-11-26 10:50:55');
INSERT INTO pacman.account (id, account, password, del, create_time, update_time) VALUES (9, 'abcd1234', '25d55ad283aa400af464c76d713c07ad', 0, '2021-11-26 12:08:08', '2021-11-26 12:08:08');
INSERT INTO pacman.account (id, account, password, del, create_time, update_time) VALUES (10, '11911203', '3c3013a95a2a7d5c837b8a1ac569b00e', 0, '2021-11-28 22:44:03', '2021-11-28 22:44:03');
INSERT INTO pacman.account (id, account, password, del, create_time, update_time) VALUES (11, 'hdreamer', 'f5bb0c8de146c67b44babbf4e6584cc0', 0, '2021-12-04 21:32:28', '2021-12-04 21:32:28');
INSERT INTO pacman.account (id, account, password, del, create_time, update_time) VALUES (12, 'caster', '25d55ad283aa400af464c76d713c07ad', 0, '2021-12-24 17:03:24', '2021-12-24 17:03:24');
INSERT INTO pacman.account (id, account, password, del, create_time, update_time) VALUES (13, 'hdreamer2', 'f5bb0c8de146c67b44babbf4e6584cc0', 0, '2021-12-25 12:01:49', '2021-12-25 12:01:49');
INSERT INTO pacman.account (id, account, password, del, create_time, update_time) VALUES (14, 'tester2', '25d55ad283aa400af464c76d713c07ad', 0, '2021-12-25 15:32:20', '2021-12-25 15:32:20');
INSERT INTO pacman.account (id, account, password, del, create_time, update_time) VALUES (15, 'test3', '25d55ad283aa400af464c76d713c07ad', 0, '2021-12-25 15:42:17', '2021-12-25 15:42:17');
INSERT INTO pacman.account (id, account, password, del, create_time, update_time) VALUES (16, 'fanzeros', '3c3013a95a2a7d5c837b8a1ac569b00e', 0, '2021-12-25 16:41:59', '2021-12-25 16:41:59');
INSERT INTO pacman.account (id, account, password, del, create_time, update_time) VALUES (17, 'fanzeross', '3c3013a95a2a7d5c837b8a1ac569b00e', 0, '2021-12-25 18:14:01', '2021-12-25 18:14:01');
INSERT INTO pacman.account (id, account, password, del, create_time, update_time) VALUES (18, 'fanzerosss', '3c3013a95a2a7d5c837b8a1ac569b00e', 0, '2021-12-26 11:24:16', '2021-12-26 11:24:16');
INSERT INTO pacman.account (id, account, password, del, create_time, update_time) VALUES (19, 'lzp001', '25d55ad283aa400af464c76d713c07ad', 0, '2021-12-26 20:59:33', '2021-12-26 20:59:33');
INSERT INTO pacman.account (id, account, password, del, create_time, update_time) VALUES (20, 'lzptest', '25d55ad283aa400af464c76d713c07ad', 0, '2021-12-28 12:16:38', '2021-12-28 12:16:38');
INSERT INTO pacman.account (id, account, password, del, create_time, update_time) VALUES (21, 'fanze', '3c3013a95a2a7d5c837b8a1ac569b00e', 0, '2021-12-31 01:06:54', '2021-12-31 01:06:54');
INSERT INTO pacman.account (id, account, password, del, create_time, update_time) VALUES (22, '8766805382', 'ba8c8eef2ce4a75eb264485baabbf6ae', 0, '2021-12-31 01:17:34', '2021-12-31 01:17:34');
INSERT INTO pacman.account (id, account, password, del, create_time, update_time) VALUES (23, 'EterSs', '7ea11ec90b47232170f2e674b66d8add', 0, '2021-12-31 20:49:38', '2021-12-31 20:49:38');
INSERT INTO pacman.account (id, account, password, del, create_time, update_time) VALUES (24, 'test1234', '25d55ad283aa400af464c76d713c07ad', 0, '2022-04-09 21:13:32', '2022-04-09 21:13:32');
