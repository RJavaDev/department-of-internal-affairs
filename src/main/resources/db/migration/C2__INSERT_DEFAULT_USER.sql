-- create default user
INSERT INTO d_user(status, created_date, firstname, lastname, middle_name, password, role, username)
VALUES
      ('CREATED', now(), 'admin', 'admin', 'admin','$2a$10$aVQfzG6HGVdP3b.L.4AwZ.OkH1qC4HI/eciQ9d9hAxJb4PgtIBL5y','ADMIN', 'admin')