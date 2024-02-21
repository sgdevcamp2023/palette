package org.palette.easelsearchservice.persistence;

import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Document(indexName = "search_user")
public class SearchUser {

    @Field(type = FieldType.Text, index = false)
    private Long id;

    @Field(type = FieldType.Text)
    private String nickname;

    @Field(type = FieldType.Text)
    private String username;

    @Field(type = FieldType.Text)
    private String introduce;

    @Field(type = FieldType.Auto)
    private String profileImagePath;


    public static SearchUser build(
            Long id,
            String nickname,
            String username,
            String introduce,
            String profileImagePath
    ) {
        return SearchUser.builder()
                .id(id)
                .nickname(nickname)
                .username(username)
                .introduce(introduce)
                .profileImagePath(profileImagePath)
                .build();
    }
}

