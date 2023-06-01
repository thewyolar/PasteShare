create table roles (
    created_at timestamp(6) not null,
    id uuid not null primary key,
    updated_at timestamp(6) not null,
    name varchar(255),
    status varchar(255) check (status in ('ACTIVE','NOT_ACTIVE','DELETED'))
);

create table pastes (
    title varchar(255) not null,
    created_at timestamp(6) not null,
    id uuid not null primary key,
    updated_at timestamp(6) not null,
    user_id uuid not null,
    content text not null,
    status varchar(255) check (status in ('ACTIVE','NOT_ACTIVE','DELETED'))
);

create table users (
    created_at timestamp(6) not null,
    id uuid not null primary key,
    updated_at timestamp(6) not null,
    avatar_url varchar(255),
    email varchar(255) not null,
    location varchar(255),
    password varchar(255) not null,
    status varchar(255) check (status in ('ACTIVE','NOT_ACTIVE','DELETED')),
    username varchar(255) not null
);

create table user_roles (
    role_id uuid not null,
    user_id uuid not null,
    primary key (role_id, user_id)
);

alter table if exists texts
    add constraint user_id_fk foreign key (user_id) references users;

alter table if exists user_roles
    add constraint role_id_fk foreign key (role_id) references roles;

alter table if exists user_roles
    add constraint user_id_fk foreign key (user_id) references users;