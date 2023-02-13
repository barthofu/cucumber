create table "user" (
    id serial primary key,
    username varchar(50) not null,
    password varchar not null
);

create table user_favorite (
    "from" integer not null constraint favorite_from_fkey references "user"(id),
    "to" integer not null constraint favorite_to_fkey references "user"(id),
    constraint user_favorite_pk primary key ("from", "to")
);

create table room (
    id serial primary key,
    closed boolean default false,
    started_at timestamp not null
);

create table room_user (
    room_id integer not null references room(id),
    user_id integer not null references "user"(id),
    constraint room_user_pk primary key (room_id, user_id)
);

create table message (
    id serial primary key,
    room_id integer not null references room(id),
    "from" integer not null references "user"(id),
    "to" integer not null references "user"(id),
    content text not null,
    created_at timestamp not null
);
