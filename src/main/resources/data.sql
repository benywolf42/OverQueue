use overqueue;

INSERT INTO role (ID, NAME) VALUES 
(1, 'ROLE_USER'),
(2, 'ROLE_ADMIN');

INSERT INTO user (USERNAME, PASSWORD, NAME, STATUS, PHONE_NUMBER) VALUES 
('admin', '$2a$10$hKDVYxLefVHV/vtuPhWD3OigtRyOykRLDdUAp80Z1crSoS1lFqaFS', 'Admin', 1, '11969960808'),
('user', '$2a$10$ByIUiNaRfBKSV6urZoBBxe4UbJ/sS6u1ZaPORHF9AtNWAuVPVz1by', 'User', 1, '11957510809');

insert into user_roles(USER_ID, ROLES_ID) values
(1,2),
(1,1),
(2,1);

insert into status(name) values("cancel");
insert into status(name) values("done");
insert into status(name) values("pending");


-- CRIAÇÃO DAS FILAS
insert into queue(chairs_quantity_on_table, has_priority, is_enabled) values 
(2, 0, 0);
insert into queue(chairs_quantity_on_table, has_priority, is_enabled) values 
(2, 1, 0);
insert into queue(chairs_quantity_on_table, has_priority, is_enabled) values
(4, 0, 0);
insert into queue(chairs_quantity_on_table, has_priority, is_enabled) values
(4, 1, 0);
insert into queue(chairs_quantity_on_table, has_priority, is_enabled) values
(6, 0, 0);
insert into queue(chairs_quantity_on_table, has_priority, is_enabled) values
(6, 1, 0);
insert into queue(chairs_quantity_on_table, has_priority, is_enabled) values
(8, 0, 0);
insert into queue(chairs_quantity_on_table, has_priority, is_enabled) values
(8, 1, 0);
insert into queue(chairs_quantity_on_table, has_priority, is_enabled) values
(10, 0, 0);
insert into queue(chairs_quantity_on_table, has_priority, is_enabled) values
(10, 1, 0);
insert into queue(chairs_quantity_on_table, has_priority, is_enabled) values
(12, 0, 0);
insert into queue(chairs_quantity_on_table, has_priority, is_enabled) values
(12, 1, 0);
insert into queue(chairs_quantity_on_table, has_priority, is_enabled) values
(14, 0, 0);
insert into queue(chairs_quantity_on_table, has_priority, is_enabled) values
(14, 1, 0);
insert into queue(chairs_quantity_on_table, has_priority, is_enabled) values
(16, 0, 0);
insert into queue(chairs_quantity_on_table, has_priority, is_enabled) values
(16, 1, 0);
insert into queue(chairs_quantity_on_table, has_priority, is_enabled) values
(18, 0, 0);
insert into queue(chairs_quantity_on_table, has_priority, is_enabled) values
(18, 1, 0);
insert into queue(chairs_quantity_on_table, has_priority, is_enabled) values
(20, 0, 0);
insert into queue(chairs_quantity_on_table, has_priority, is_enabled) values
(20, 1, 0);