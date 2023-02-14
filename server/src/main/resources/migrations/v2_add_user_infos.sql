alter table "user"
    add column description text,
    add column age integer,
    add column avatar varchar(255),
    add column created_at timestamp default now();
