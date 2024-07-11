drop table users;

create type user_enum as enum ('CUSTOMER', 'EMPLOYEE')

create table users(
    id integer primary key check (id > 1000000 and id < 9999999),
    first_name varchar(50) not null,
    last_name varchar(50) not null,
    email varchar(50) unique not null,
    password varchar(24) not null,
    user_type user_enum default 'CUSTOMER'
);

insert into users values(1234568, 'John', 'Doe', 'john@doe.com', 'Password1');
