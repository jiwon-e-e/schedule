package com.example.schedule.dto.commentDto;

import lombok.Getter;

@Getter
public class CreateCommentRequest {
    private String contents;
    private String writer;
    private String pw;
}
