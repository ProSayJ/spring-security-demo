create table users
(
    username varchar(50)  not null PRIMARY key,
    password varchar(500) not null,
    enabled  boolean      not null
);
create table authorities
(
    username  varchar(50) not null,
    authority varchar(50) not null,
    CONSTRAINT `FK_authorities_users` FOREIGN KEY (`username`) REFERENCES `users` (`username`)
);
create unique index ix_auth_username on authorities (username, authority);

insert into users values  ("test","123",true);
insert into authorities values  ("test","ROLE_USER");

