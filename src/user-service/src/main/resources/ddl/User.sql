create table user
(
    id                    bigint auto_increment primary key,
    email                 varchar(70) unique key,
    username              varchar(255) unique key,
    password              varchar(100)             not null default '',
    nickname              varchar(50)              not null default '',
    introduce             varchar(160)             not null default '',
    profile_image_path    varchar(100)             not null default '',
    background_image_path varchar(100)             not null default '',
    website_path          varchar(100)             not null default '',
    paint_pin             text                     not null,
    dm_pin                text                     not null,
    role                  enum ('NORMAL', 'ADMIN') not null default 'NORMAL',
    authed                bit                      not null default false,
    accessed_at           datetime(6)              null,
    created_at            datetime(6)              null,
    deleted_at            datetime(6)              null,
    updated_at            datetime(6)              null
);
