package org.palette.easelsearchservice.service;

import lombok.RequiredArgsConstructor;
import org.palette.dto.event.PaintCreatedEvent;
import org.palette.dto.event.detail.HashtagRecord;
import org.palette.dto.event.detail.MentionRecord;
import org.palette.easelsearchservice.dto.request.SearchRequest;
import org.palette.easelsearchservice.dto.response.PaintResponse;
import org.palette.easelsearchservice.persistence.SearchPaint;
import org.palette.easelsearchservice.persistence.SearchRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {
    private final SearchRepository searchRepository;

    public List<PaintResponse> searchAllPaints(final SearchRequest searchRequest) {
        Page<SearchPaint> searchPaints = searchRepository.findByTextContaining(
                searchRequest.keyword(),
                PageRequest.of(searchRequest.page(), searchRequest.size())
        );

        return convertToPaintResponse(searchPaints);
    }

    private List<PaintResponse> convertToPaintResponse(Page<SearchPaint> searchPaints) {
        return searchPaints.stream()
                .map(paint -> new PaintResponse(paint.getId(), paint.getText()))
                .toList();
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
