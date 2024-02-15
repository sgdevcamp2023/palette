create table user
(
    id                    bigint auto_increment primary key,
    authed                bit                      null,
    is_activated          bit                      null,
    accessed_at           datetime(6)              null,
    created_at            datetime(6)              null,
    deleted_at            datetime(6)              null,
    updated_at            datetime(6)              null,
    username              varchar(50)              not null,
    email                 varchar(70)              not null,
    password              varchar(100)             not null,
    background_image_path varchar(255)             not null,
    introduce             varchar(255)             null,
    nickname              varchar(255)             null,
    profile_image_path    varchar(255)             not null,
    website_path          varchar(255)             not null,
    dm_pin                longtext                 not null,
    paint_pin             longtext                 not null,
    role                  enum ('NORMAL', 'ADMIN') null,
    constraint UK_ob8kqyqqgmefl0aco34akdtpe unique (email),
    constraint UK_sb8bbouer5wak8vyiiy4pf2bx unique (username)
);
