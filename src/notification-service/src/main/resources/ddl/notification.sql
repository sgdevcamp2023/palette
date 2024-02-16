create table user
(
    id                bigint auto_increment primary key,
    android_fcm_token varchar(255) null,
    nickname          varchar(255) null
);

create table alarm_allowed_type
(
    id                   bigint                                                                                                                                                                                                                                                                                 not null,
    alarm_allowed_status enum ('PAINT_CREATED', 'MENTIONED', 'REPLIED', 'RETWEETED', 'LIKED', 'PHOTO_TAGGED', 'MOMENT', 'NEW_FOLLOWER', 'MY_CONTACTS_BEEN_JOINED', 'DM', 'DM_MENTIONED', 'TOPIC', 'RECOMMEND', 'SPACE', 'OTHER_REALTIME_LIVE', 'NEWS_SPORTS', 'NEW_FEATURE', 'EMERGENCY', 'PROFESSIONAL_ALARM') null,
    foreign key (id) references user (id)
);


