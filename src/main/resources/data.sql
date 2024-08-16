INSERT INTO roles(insert_date_time, insert_user_username, is_deleted, last_update_date_time, last_update_user_username, description)
VALUES ('2022-01-05 00:00:00', 'admin@admin.com', false, '2022-01-05 00:00:00', 'admin@admin.com', 'Admin'),
       ('2022-01-05 00:00:00', 'admin@admin.com', false, '2022-01-05 00:00:00', 'admin@admin.com', 'Manager'),
       ('2022-01-05 00:00:00', 'admin@admin.com', false, '2022-01-05 00:00:00', 'admin@admin.com', 'Employee');


INSERT INTO users(insert_date_time, insert_user_username, is_deleted, last_update_date_time, last_update_user_username,
                  first_name, last_name, user_name, gender, phone, role_id, pass_word)
VALUES ('2022-01-05 00:00:00', 'admin@admin.com', false, '2022-01-05 00:00:00', 'admin@admin.com', 'admin', 'admin', 'admin@admin.com', 'MALE', '0000000000', 1, '$2a$10$nAB5j9G1c3JHgg7qzhiIXO7cqqr5oJ3LXRNQJKssDUwHXzDGUztNK'),
       ('2022-01-05 00:00:00', 'admin@admin.com', false, '2022-01-05 00:00:00', 'admin@admin.com', 'Harold', 'Finch', 'harold@manager.com', 'MALE', '0123456789', 2, '$2a$10$nAB5j9G1c3JHgg7qzhiIXO7cqqr5oJ3LXRNQJKssDUwHXzDGUztNK'),
       ('2022-01-05 00:00:00', 'admin@admin.com', false, '2022-01-05 00:00:00', 'admin@admin.com', 'Samantha', 'Groves', 'samantha@manager.com', 'MALE', '9876543210', 2, '$2a$10$nAB5j9G1c3JHgg7qzhiIXO7cqqr5oJ3LXRNQJKssDUwHXzDGUztNK'),
       ('2022-01-05 00:00:00', 'admin@admin.com', false, '2022-01-05 00:00:00', 'admin@admin.com', 'John', 'Reese', 'john@employee.com', 'MALE', '7894561230', 3, '$2a$10$nAB5j9G1c3JHgg7qzhiIXO7cqqr5oJ3LXRNQJKssDUwHXzDGUztNK'),
       ('2022-01-05 00:00:00', 'admin@admin.com', false, '2022-01-05 00:00:00', 'admin@admin.com', 'Sameen', 'Shaw', 'sameen@employee.com', 'MALE', '0321654987', 3, '$2a$10$nAB5j9G1c3JHgg7qzhiIXO7cqqr5oJ3LXRNQJKssDUwHXzDGUztNK'),
       ('2022-01-05 00:00:00', 'admin@admin.com', false, '2022-01-05 00:00:00', 'admin@admin.com', 'Grace', 'Hendricks', 'grace@employee.com', 'MALE', '7410258963', 3, '$2a$10$nAB5j9G1c3JHgg7qzhiIXO7cqqr5oJ3LXRNQJKssDUwHXzDGUztNK'),
       ('2022-01-05 00:00:00', 'admin@admin.com', false, '2022-01-05 00:00:00', 'admin@admin.com', 'Lionel', 'Fusco', 'lionel@employee.com', 'MALE', '3698520147', 3, '$2a$10$nAB5j9G1c3JHgg7qzhiIXO7cqqr5oJ3LXRNQJKssDUwHXzDGUztNK');

-- Abc1

INSERT INTO projects(insert_date_time, insert_user_username, is_deleted, last_update_date_time, last_update_user_username, project_code, project_name,
                     project_detail, project_status, start_date, end_date, manager_id)
VALUES ('2022-01-05 00:00:00', 'harold@manager.com', false, '2022-01-05 00:00:00', 'harold@manager.com', 'SP00', 'Spring Core', 'Spring Core Project', 'OPEN', '2022-01-05', '2022-06-12', 2),
       ('2022-01-05 00:00:00', 'harold@manager.com', false, '2022-01-05 00:00:00', 'harold@manager.com', 'SP01', 'Spring Boot', 'Spring Boot Project', 'IN_PROGRESS', '2022-01-05', '2022-06-12', 2),
       ('2022-01-05 00:00:00', 'samantha@manager.com', false, '2022-01-05 00:00:00', 'samantha@manager.com', 'SP02', 'Spring MVC', 'Spring MVC Project', 'IN_PROGRESS', '2022-01-05', '2022-06-12', 3),
       ('2022-01-05 00:00:00', 'samantha@manager.com', false, '2022-01-05 00:00:00', 'samantha@manager.com', 'SP03', 'Spring Data', 'Spring Data Project', 'OPEN', '2022-01-05', '2022-06-12', 3);

INSERT INTO tasks(task_code, insert_date_time, insert_user_username, is_deleted, last_update_date_time, last_update_user_username, task_subject, task_detail, task_status, assigned_date, project_id, employee_id)
VALUES ('ST00','2022-01-05 00:00:00', 'harold@manager.com', false, '2022-01-05 00:00:00', 'harold@manager.com', 'Dependency Injection', 'Injecting dependencies', 'OPEN', '2022-01-05', 1, 4),
       ('ST01','2022-01-05 00:00:00', 'harold@manager.com', false, '2022-01-05 00:00:00', 'harold@manager.com', '@SpringBootApplication', 'Adding @SpringBootApplication annotation', 'IN_PROGRESS', '2022-01-05', 1, 4),
       ('ST02','2022-01-05 00:00:00', 'harold@manager.com', false, '2022-01-05 00:00:00', 'harold@manager.com', 'Controller', 'Creating controllers', 'COMPLETE', '2022-01-05', 1, 4),
       ('ST03','2022-01-05 00:00:00', 'harold@manager.com', false, '2022-01-05 00:00:00', 'harold@manager.com', 'Entity', 'Creating entities', 'OPEN', '2022-01-05', 1, 4),
       ('ST04','2022-01-05 00:00:00', 'harold@manager.com', false, '2022-01-05 00:00:00', 'harold@manager.com', 'Dependency Injection', 'Injecting dependencies', 'OPEN', '2022-01-05', 2, 5),
       ('ST05','2022-01-05 00:00:00', 'harold@manager.com', false, '2022-01-05 00:00:00', 'harold@manager.com', '@SpringBootApplication', 'Adding @SpringBootApplication annotation', 'COMPLETE', '2022-01-05', 2, 5),
       ('ST06','2022-01-05 00:00:00', 'harold@manager.com', false, '2022-01-05 00:00:00', 'harold@manager.com', 'Controller', 'Creating controllers', 'IN_PROGRESS', '2022-01-05', 2, 5),
       ('ST07','2022-01-05 00:00:00', 'harold@manager.com', false, '2022-01-05 00:00:00', 'harold@manager.com', 'Entity', 'Creating entities', 'COMPLETE', '2022-01-05', 2, 5),
       ('ST08','2022-01-05 00:00:00', 'samantha@manager.com', false, '2022-01-05 00:00:00', 'samantha@manager.com', 'Dependency Injection', 'Injecting dependencies', 'COMPLETE', '2022-01-05', 3, 6),
       ('ST09','2022-01-05 00:00:00', 'samantha@manager.com', false, '2022-01-05 00:00:00', 'samantha@manager.com', '@SpringBootApplication', 'Adding @SpringBootApplication annotation', 'COMPLETE', '2022-01-05', 3, 6),
       ('ST10','2022-01-05 00:00:00', 'samantha@manager.com', false, '2022-01-05 00:00:00', 'samantha@manager.com', 'Controller', 'Creating controllers', 'IN_PROGRESS', '2022-01-05', 3, 6),
       ('ST11','2022-01-05 00:00:00', 'samantha@manager.com', false, '2022-01-05 00:00:00', 'samantha@manager.com', 'Entity', 'Creating entities', 'COMPLETE', '2022-01-05', 3, 6),
       ('ST12','2022-01-05 00:00:00', 'samantha@manager.com', false, '2022-01-05 00:00:00', 'samantha@manager.com', 'Dependency Injection', 'Injecting dependencies', 'COMPLETE', '2022-01-05', 4, 7),
       ('ST13','2022-01-05 00:00:00', 'samantha@manager.com', false, '2022-01-05 00:00:00', 'samantha@manager.com', '@SpringBootApplication', 'Adding @SpringBootApplication annotation', 'COMPLETE', '2022-01-05', 4, 7),
       ('ST14','2022-01-05 00:00:00', 'samantha@manager.com', false, '2022-01-05 00:00:00', 'samantha@manager.com', 'Controller', 'Creating controllers', 'COMPLETE', '2022-01-05', 4, 7),
       ('ST15','2022-01-05 00:00:00', 'samantha@manager.com', false, '2022-01-05 00:00:00', 'samantha@manager.com', 'Entity', 'Creating entities', 'COMPLETE', '2022-01-05', 4, 7);
