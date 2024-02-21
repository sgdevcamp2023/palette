package org.palette.easelsearchservice.service;

import lombok.RequiredArgsConstructor;
import org.palette.dto.event.PaintCreatedEvent;
import org.palette.dto.event.detail.HashtagRecord;
import org.palette.dto.event.detail.MentionRecord;
import org.palette.easelsearchservice.dto.request.SearchRequest;
import org.palette.easelsearchservice.persistence.SearchPaint;
import org.palette.easelsearchservice.persistence.SearchPaintRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchPaintService {
    private final SearchPaintRepository searchPaintRepository;

    public List<Long> searchAllPaints(final SearchRequest searchRequest) {
        Page<SearchPaint> searchPaints = searchPaintRepository.findByTextContaining(
                searchRequest.keyword(),
                PageRequest.of(searchRequest.page(), searchRequest.size())
        );

        return searchPaints.stream()
                .map(SearchPaint::getId)
                .toList();
    }

    public void createPaint(final PaintCreatedEvent paintCreatedEvent) {
        searchPaintRepository.save(
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
