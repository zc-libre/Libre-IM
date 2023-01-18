INSERT INTO "sys_menu" ("id", "parent_id", "title", "name", "seq", "path", "permission", "component", "icon",
                        "is_frame", "type", "cache", "hidden", "status", "remark", "gmt_create", "gmt_modified",
                        "gmt_create_name", "gmt_modified_name", "is_deleted")
VALUES (5, 3, '角色管理', 'Role', 1, '/role', 'admin', '@/views/system/role/index', 'role', 0, 1, 0, 0, 1, NULL,
        '2022-08-30 01:11:13', '2022-12-11 21:24:55.8818', NULL, 'admin', 0);
INSERT INTO "sys_menu" ("id", "parent_id", "title", "name", "seq", "path", "permission", "component", "icon",
                        "is_frame", "type", "cache", "hidden", "status", "remark", "gmt_create", "gmt_modified",
                        "gmt_create_name", "gmt_modified_name", "is_deleted")
VALUES (4, 3, '用户管理', 'User', 2, '/user', 'admin', '@/views/system/user/index', 'user', 0, 1, 0, 0, 1, NULL,
        '2022-08-30 01:11:11', '2022-12-11 21:25:03.653572', NULL, 'admin', 0);
INSERT INTO "sys_menu" ("id", "parent_id", "title", "name", "seq", "path", "permission", "component", "icon",
                        "is_frame", "type", "cache", "hidden", "status", "remark", "gmt_create", "gmt_modified",
                        "gmt_create_name", "gmt_modified_name", "is_deleted")
VALUES (6, 3, '菜单管理', 'Menu', 0, '/menu', 'admin', '@/views/system/menu/index', 'menu', 0, 1, 0, 0, 1, NULL,
        '2022-09-04 03:33:25', '2022-12-11 21:26:24.53417', NULL, 'admin', 0);
INSERT INTO "sys_menu" ("id", "parent_id", "title", "name", "seq", "path", "permission", "component", "icon",
                        "is_frame", "type", "cache", "hidden", "status", "remark", "gmt_create", "gmt_modified",
                        "gmt_create_name", "gmt_modified_name", "is_deleted")
VALUES (3, 0, '系统管理', 'system', NULL, 'system', 'admin', 'Layout', 'system', 0, 0, 0, 0, 1, NULL,
        '2022-08-30 01:11:08', '2022-12-11 23:03:33.03201', NULL, 'admin', 0);
INSERT INTO "sys_menu" ("id", "parent_id", "title", "name", "seq", "path", "permission", "component", "icon",
                        "is_frame", "type", "cache", "hidden", "status", "remark", "gmt_create", "gmt_modified",
                        "gmt_create_name", "gmt_modified_name", "is_deleted")
VALUES (1601944933424537601, 0, '监控管理', NULL, 0, 'monitor', NULL, NULL, 'monitor', 0, 0, 0, 0, 1, NULL,
        '2022-12-11 22:20:09.486603', '2022-12-11 22:49:39.336321', 'admin', 'admin', 1);


INSERT INTO "sys_role_menu" ("id", "role_id", "menu_id")
VALUES (1568885409261223937, 1, 3);
INSERT INTO "sys_role_menu" ("id", "role_id", "menu_id")
VALUES (1568885409261223938, 1, 4);
INSERT INTO "sys_role_menu" ("id", "role_id", "menu_id")
VALUES (1568885409261223939, 1, 5);
INSERT INTO "sys_role_menu" ("id", "role_id", "menu_id")
VALUES (1568885409265418242, 1, 6);


INSERT INTO "sys_user_role" ("id", "user_id", "role_id", "gmt_create", "gmt_modified", "gmt_create_name",
                             "gmt_modified_name")
VALUES (1, 1, 1, NULL, NULL, NULL, NULL);

INSERT INTO "sys_user" ("id", "username", "password", "nick_name", "avatar", "phone", "email", "gender", "enabled",
                        "locked", "is_admin", "gmt_create", "gmt_modified", "gmt_create_name", "gmt_modified_name",
                        "is_deleted")
VALUES (1, 'admin', '$2a$10$nSTyJiH6jRyRUWgL7O.9cuDWZGYSwLskoXfKHNistSsrM3r8YsW4C', 'libre', NULL, '15191910116',
        'zc150622@gmail.com', 0, 1, 0, 1, '2022-09-04 16:57:54', '2022-08-27 11:59:05.635033', 'admin', 'System', 0);

insert into sys_role (id, role_name, parent_id, status, permission, gmt_create, gmt_modified, gmt_create_name,
                      gmt_modified_name, is_deleted)
values (1, '超级管理员', 0, 1, 'admin', '2022-09-04 18:21:02.000000', '2022-09-04 18:21:05.000000', 'admin', 'admin',
        0);
