drop table users;

create type user_enum as enum ('CUSTOMER', 'EMPLOYEE');

create table users
(
    id         integer primary key check (id > 1000000 and id < 9999999), /*7 digit code*/
    first_name varchar(24)        not null,
    last_name  varchar(24)        not null,
    email      varchar(50) unique not null,
    password   varchar(24)        not null,
    user_type  user_enum default 'CUSTOMER'
);

create table currencies
(
    currency_code   varchar(3) primary key,
    currency_name   varchar(24)      not null,
    rate_per_usd    double precision not null,
    currency_symbol varchar(3)
);

create table accounts
(
    account_number integer check (account_number > 10000000 and account_number < 99999999), /*8 digit code*/
    owner_id       integer,
    balance        integer check (balance >= 0) not null,
    constraint owner_id
        foreign key (owner_id) references users (id)
            on delete cascade
);

create table transactions
(
    transaction_id integer primary key, /*9 digit code*/
    account_id     integer,
    amount         integer not null check (amount >= 0),
    credit         boolean not null,
    description    varchar(80),
    constraint account_id
        foreign key (account_id) references users (id)
            on delete cascade
);

insert into users
values (1234568, 'John', 'Doe', 'john@doe.com', 'Password1'),
       (8529631, 'Bernie', 'Madoff', 'FeelTheBern@madeoff.com', 'InfiniteW341th'),
       (7854123, 'Richard', 'Nixon', 'theking@whitehouse.gov', 'NotACr00k'),
       (1111111, 'Amsal', 'Kassam', 'redacted@gmail.com', 'UnCr4Ck4B13', 'EMPLOYEE');

insert into accounts
values (12345678, 1234568, 10000),
       (12345679, 1234568, 20000),
       (74185296, 8529631, 9999999),
       (94516238, 7854123, 150000);

insert into currencies
values ('USD', 'US Dollar', 1, '$'),
       ('PKR', 'Pakistani Rupee', 278.52, 'Rs.'),
       ('JPY', 'Japanese Yen', 157.88, '¥'),
       ('EUR', 'Euro', 0.92, '€'),
       ('GBP', 'British Pound Sterling', 0.77, '£');

insert into transactions
values (123456789, 1234568, 100, true, 'John Doe deposits $1 in cash'),
       (987654321, 1234568, 100, false, 'Charge of $1 from CoffeeShop'),
       (111111111, 1111111, 100000, true, 'Payroll - Amsal Kassam');

select *
from users;

select *
from accounts;

select *
from accounts
         left join users u on u.id = accounts.owner_id;

select *
from currencies;

select *
from transactions;

truncate users, accounts, currencies, transactions;

drop table transactions;