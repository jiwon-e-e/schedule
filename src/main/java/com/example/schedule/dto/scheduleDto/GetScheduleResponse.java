package com.example.schedule.dto.scheduleDto;

import com.example.schedule.dto.commentDto.GetCommentResponse;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class GetScheduleResponse {
    private final Long id;
    private final String name;
    private final String contents;
    private final String writer;
    private final List<GetCommentResponse> commentList;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public GetScheduleResponse(Long id, String name, String contents, String writer, List<GetCommentResponse> commentList, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.name = name;
        this.contents = contents;
        this.writer = writer;
        this.commentList = commentList;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
