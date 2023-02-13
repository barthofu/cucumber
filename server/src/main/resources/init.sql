alter table room_user
    add constraint room_user_pk
        primary key (room_id, user_id);
