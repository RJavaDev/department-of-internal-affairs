--create categories
insert into d_category(status, created_date, name, score)
VALUES
    ('CREATED', NOW(), 'IIO_CITIZEN', 1),
    ('CREATED', NOW(), 'PROF_CITIZEN', 2),
    ('CREATED', NOW(), 'STATEMENT', 3),
    ('CREATED', NOW(), 'CAUGHT_WANTED_CITIZEN', 4),
    ('CREATED', NOW(), 'CAUGHT_LOST_CITIZEN',5),
    ('CREATED', NOW(), 'TOTAL_CHECKED_OBJECT_GUARDS',6),
    ('CREATED', NOW(), 'CHECKED_HUNTING_WEAPONS',7)