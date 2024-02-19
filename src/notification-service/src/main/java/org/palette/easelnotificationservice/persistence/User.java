package org.palette.easelnotificationservice.persistence;

import jakarta.persistence.*;
import lombok.*;
import org.palette.easelnotificationservice.persistence.enums.AlarmAllowedType;

import java.util.ArrayList;
import java.util.List;

import static org.palette.easelnotificationservice.persistence.enums.AlarmAllowedType.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(access = AccessLevel.PRIVATE)
@Getter
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "android_fcm_token")
    private String androidFcmToken;

    @ElementCollection(
            targetClass = AlarmAllowedType.class,
            fetch = FetchType.EAGER
    )
    @CollectionTable(
            name = "alarm_allowed_type",
            joinColumns = @JoinColumn(name = "id")
    )
    @Column(name = "alarm_allowed_status")
    @Enumerated(EnumType.STRING)
    private List<AlarmAllowedType> alarmAllowedStatus = new ArrayList<>();

    public static User build(String nickname) {
        return User.builder()
                .nickname(nickname)
                .androidFcmToken("")
                .alarmAllowedStatus(DEFAULT_ALARM_ALLOWED_STATUS).build();
    }

    public void disableAlarmAllowing(AlarmAllowedType alarmAllowedType) {
        this.alarmAllowedStatus.remove(alarmAllowedType);
    }

    public void enableAlarmAllowing(AlarmAllowedType alarmAllowedType) {
        this.alarmAllowedStatus.add(alarmAllowedType);
    }

    private static final List<AlarmAllowedType> DEFAULT_ALARM_ALLOWED_STATUS = new ArrayList<>() {{
        add(PAINT_CREATED);
        add(PAINT_CREATED);
        add(MENTIONED);
        add(REPLIED);
        add(RETWEETED);
        add(LIKED);
        add(PHOTO_TAGGED);
        add(MOMENT);
        add(NEW_FOLLOWER);
        add(MY_CONTACTS_BEEN_JOINED);
        add(DM);
        add(DM_MENTIONED);
        add(TOPIC);
        add(RECOMMEND);
        add(SPACE);
        add(OTHER_REALTIME_LIVE);
        add(NEWS_SPORTS);
        add(NEW_FEATURE);
        add(EMERGENCY);
        add(PROFESSIONAL_ALARM);
    }};
}
