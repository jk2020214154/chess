
use chess;

create table chess.user
(
id       int auto_increment
primary key,
username varchar(100)     null,
password varchar(100)     null,
rating   int default 1500 null,
photo    varchar(1000)    null,
constraint id
unique (id)
);

create table if not exists chess.record
(
    id int auto_increment not null
        primary key,
    user_id int not null,
    `current` varchar(5) ,
    target varchar(5) not null ,
    type   int not null
);