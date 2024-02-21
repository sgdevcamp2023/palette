package org.palette.easelsearchservice.persistence;

import lombok.*;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "search_paint")
public class SearchPaint {
    @Field(type = FieldType.Auto)
    private Long id;

    @Field(type = FieldType.Auto)
    private Long authorId;

    @Field(type = FieldType.Text)
    private String authorUsername;

    @Field(type = FieldType.Text)
    private String authorNickname;

    @Field(type = FieldType.Text)
    private String text;

    @Field(type = FieldType.Keyword)
    private List<String> hashtagRecords;

    @Field(type = FieldType.Keyword)
    private List<String> mentionRecords;

    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second)
    private LocalDateTime createdAt;
}
