create sequence hibernate_sequence start 1 increment 1

create table cruisers (
    desk_id int8 not null,
    end_position bytea,
    ship_type int4,
    start_position bytea
)

create table desk (
    id int8 not null,
    end_position bytea,
    ship_type int4,
    start_position bytea,
    room_id int8,
    user_id int8,
    primary key (id)
)

create table destroyers (
    desk_id int8 not null,
    end_position bytea,
    ship_type int4,
    start_position bytea
)

create table room (
    id int8 not null,
    create_time timestamp,
    room uuid,
    room_status varchar(255),
    host_id int8,
    opponent_id int8
    primary key (id)
)

create table submarines (
    desk_id int8 not null,
    end_position bytea,
    ship_type int4,
    start_position bytea
)

create table user_roles (
    user_id int8 not null,
    roles varchar(255)
)

create table usr (
    id int8 not null,
    time_create timestamp,
    username varchar(255),
    primary key (id)
)

alter table if exists cruisers 
       add constraint cruisers_desk_id_fk 
       foreign key (desk_id) 
       references desk

alter table if exists desk 
       add constraint desk_room_id_fk
       foreign key (room_id) 
       references room

alter table if exists desk 
       add constraint desk_user_id_fk
       foreign key (user_id) 
       references usr

alter table if exists destroyers 
       add constraint destroyers_desk_id_fk 
       foreign key (desk_id) 
       references desk

alter table if exists room 
       add constraint room_host_id_fk
       foreign key (host_id) 
       references usr

alter table if exists room 
       add constraint room_opponent_id_fk
       foreign key (opponent_id) 
       references usr

alter table if exists submarines 
       add constraint submarines_desk_id_fk 
       foreign key (desk_id) 
       references desk

alter table if exists user_roles 
       add constraint user_roles_user_id_fk
       foreign key (user_id) 
       references usr
