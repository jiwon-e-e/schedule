package com.example.schedule.dto.commentDto;

import lombok.Getter;

@Getter
public class GetCommentResponse {
    private final Long id;
    private final Long scheduleId;
    private final String contents;
    private final String writer;

    public GetCommentResponse(Long id, Long scheduleId, String contents, String writer) {
        this.id = id;
        this.scheduleId = scheduleId;
        this.contents = contents;
        this.writer = writer;
    }
}
