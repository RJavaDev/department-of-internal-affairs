--create categories
insert into d_category(status, created_date, name, score)
VALUES
    ('CREATED', NOW(), 'IIO-citizen', 1),
    ('CREATED', NOW(), 'PROF-citizen', 2),
    ('CREATED', NOW(), 'statement', 3),
    ('CREATED', NOW(), 'caught-wanted-citizen', 4),
    ('CREATED', NOW(), 'caught-lost-citizen',5),
    ('CREATED', NOW(), 'total-checked-object-guards',6),
    ('CREATED', NOW(), 'checked-hunting-weapons',7)