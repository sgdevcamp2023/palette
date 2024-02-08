package org.palette.easeltimelineservice.service;

import org.palette.dto.event.detail.LinkRecord;
import org.palette.dto.event.detail.MediaRecord;
import org.palette.dto.event.detail.MentionRecord;

import java.util.List;

public record Includes(
        List<MediaRecord> medias,
        List<MentionRecord> users,
        List<LinkRecord> links
) {
}
