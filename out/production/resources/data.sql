insert into user (name) values ('Dima');
insert into user (name) values ('Andrey');
insert into user (name) values ('Artem');
insert into user (name) values ('Nadya');

insert into board (title) values ('Win hackathon');
insert into board (title) values ('Trip to Moscow');

insert into sprint (board_id, end_date, start_date) values (1, '2024-09-26', '2024-09-29');
insert into sprint (board_id, end_date, start_date) values (1, '2024-09-27', '2024-09-28');
insert into sprint (board_id, end_date, start_date) values (2, '2024-10-20', '2024-10-29');

insert into task (board_id, date, owner_id, sprint_id, status, title) values (1, '2024-09-27', 1, 1, 'DOING', 'Do backend');
insert into task (board_id, date, owner_id, sprint_id, status, title) values (1, '2024-09-27', 2, 2, 'DONE', 'Do frontend');
insert into task (board_id, date, owner_id, sprint_id, status, title) values (1, '2024-09-27', 3, 1, 'TODO', 'Do frontend');
insert into task (board_id, date, owner_id, sprint_id, status, title) values (1, '2024-09-27', 4, 1, 'TODO', 'Do frontend');
insert into task (board_id, date, owner_id, sprint_id, status, title) values (2, '2024-10-21', 4, 3, 'TODO', 'Pack the bags to Moscow');
insert into task (board_id, date, owner_id, sprint_id, status, title) values (2, '2024-10-22', 4, 3, 'TODO', 'Go to airport');