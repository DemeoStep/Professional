DROP DATABASE IF EXISTS veterinal;
CREATE DATABASE veterinal;
USE veterinal;

create table if not exists AnimalDetails
(
    id bigint auto_increment
        primary key,
    age int not null,
    breed varchar(255) null,
    tail bit not null,
    weight bigint not null
);

create table if not exists Aviary
(
    id bigint auto_increment
        primary key,
    biome varchar(255) null,
    size double not null
);

create table if not exists Vet
(
    id bigint auto_increment
        primary key,
    clinic varchar(255) null,
    name varchar(255) null,
    phone varchar(255) null,
    constraint UK_Vet_Name
        unique (name),
    constraint UK_Vet_Phone
        unique (phone)
);

create table if not exists Animal
(
    id bigint auto_increment
        primary key,
    name varchar(255) null,
    aviary_id bigint null,
    details_id bigint null,
    vet_id bigint null,
    constraint UK_Animal_Name
        unique (name),
    constraint FK_AnimalDetails
        foreign key (details_id) references AnimalDetails (id) on delete cascade,
    constraint FK_Aviary
        foreign key (aviary_id) references Aviary (id) on delete set null,
    constraint FK_Vet
        foreign key (vet_id) references Vet (id)on delete set null
);