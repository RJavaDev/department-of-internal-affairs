--create categories
insert into d_category(status, created_date, name, score)
VALUES
    ('CREATED', NOW(), 'category-1', 1),
    ('CREATED', NOW(), 'category-2', 2),
    ('CREATED', NOW(), 'category-3', 3)