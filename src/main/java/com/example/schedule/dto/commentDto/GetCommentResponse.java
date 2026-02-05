package com.example.schedule.dto.commentDto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetCommentResponse {
    private final Long id;
    private final Long scheduleId;
    private final String contents;
    private final String writer;
    private final LocalDateTime createdAt;

    public GetCommentResponse(Long id, Long scheduleId, String contents, String writer, LocalDateTime createdAt) {
        this.id = id;
        this.scheduleId = scheduleId;
        this.contents = contents;
        this.writer = writer;
        this.createdAt = createdAt;
    }
}
