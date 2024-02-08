package org.palette.easeltimelineservice.service;

import org.palette.dto.event.detail.HashtagRecord;
import org.palette.dto.event.detail.MentionRecord;

import java.util.List;

public record Entities(
        List<HashtagRecord> hashtags,
        List<MentionRecord> mentions
) {
}
