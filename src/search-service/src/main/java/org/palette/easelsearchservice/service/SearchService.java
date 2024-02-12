package org.palette.easelsearchservice.service;

import lombok.RequiredArgsConstructor;
import org.palette.dto.event.PaintCreatedEvent;
import org.palette.dto.event.detail.HashtagRecord;
import org.palette.dto.event.detail.MentionRecord;
import org.palette.easelsearchservice.dto.response.PaintResponse;
import org.palette.easelsearchservice.persistence.SearchPaint;
import org.palette.easelsearchservice.persistence.SearchRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {
    private final SearchRepository searchRepository;

    public PaintResponse searchAllPaints() {
        return null;
    }

    public void createPaint(final PaintCreatedEvent paintCreatedEvent) {
        searchRepository.save(
                SearchPaint.builder()
                        .id(paintCreatedEvent.id())
                        .authorId(paintCreatedEvent.authorId())
                        .authorUsername(paintCreatedEvent.authorUsername())
                        .authorNickname(paintCreatedEvent.authorNickname())
                        .text(paintCreatedEvent.text())
                        .hashtagRecords(convertHashtagToStrings(paintCreatedEvent.hashtagRecords()))
                        .mentionRecords(convertMentionToStrings(paintCreatedEvent.mentionRecords()))
                        .createdAt(paintCreatedEvent.createdAt())
                .build());
    }

    private List<String> convertHashtagToStrings(List<HashtagRecord> hashtagRecords) {
        return hashtagRecords.stream()
                .map(HashtagRecord::tag)
                .toList();
    }

    private List<String> convertMentionToStrings(List<MentionRecord> mentionRecords) {
        return mentionRecords.stream()
                .map(MentionRecord::mention)
                .toList();
    }
}
