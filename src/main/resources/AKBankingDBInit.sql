drop table users;

create type user_enum as enum ('CUSTOMER', 'EMPLOYEE');

create table users(
    id integer primary key check (id > 1000000 and id < 9999999),
    first_name varchar(24) not null,
    last_name varchar(24) not null,
    email varchar(50) unique not null,
    password varchar(24) not null,
    user_type user_enum default 'CUSTOMER'
);

create table currencies(
    currency_code varchar(3) primary key,
    currency_name varchar(24) not null,
    rate_per_usd double precision not null,
    currency_symbol varchar(2)
);

create table accounts(
    account_number integer check (account_number > 10000000 and account_number < 99999999),
    owner_id integer,
    balance integer check (balance >= 0) not null,
    constraint owner_id
        foreign key(owner_id) references users(id)
                     on delete cascade
);

create table transactions(
    transaction_id integer primary key,
    account_id integer,
    amount integer not null,
    credit boolean not null,
    description varchar(80)
);

insert into users values
    (1234568, 'John', 'Doe', 'john@doe.com', 'Password1'),
    (8529631, 'Bernie', 'Madoff', 'FeelTheBern@madeoff.com', 'InfiniteW341th'),
    (7854123, 'Richard', 'Nixon', 'theking@whitehouse.gov', 'NotACr00k');

select * from users;

select * from accounts;

select * from currencies;

select * from transactions;

truncate users;
truncate accounts;
truncate currencies;
truncate transactions;