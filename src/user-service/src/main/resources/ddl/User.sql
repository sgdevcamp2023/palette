create table if not exists `user-service`.user
(
    id                    bigint auto_increment primary key,
    email                 varchar(70)  not null,
    username              varchar(50)  not null,
    password              varchar(100) not null,
    introduce             varchar(255) null,
    nickname              varchar(255) null,
    profile_image_path    varchar(255)             default '',
    background_image_path varchar(255)             default '',
    website_path          varchar(255)             default '',
    paint_pin             longtext     not null,
    dm_pin                longtext     not null,
    authed                bit                      default false,
    role                  enum ('NORMAL', 'ADMIN') default 'NORMAL',
    is_activated          bit                      default null,
    accessed_at           datetime(6)              default null,
    created_at            datetime(6)              default null,
    updated_at            datetime(6)              default null,
    deleted_at            datetime(6)              default null,
    constraint unique (email),
    constraint unique (username)
);
