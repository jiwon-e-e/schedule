package com.example.schedule.dto.commentDto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CreateCommentRequest {
    @Size(max = 100)
    @NotBlank(message = "댓글 내용이 꼭 필요해요..")
    private String contents;
    @NotBlank(message = "작성자가 꼭 필요해요..")
    private String writer;
    @NotBlank(message = "비밀번호가 꼭 필요해요..")
    private String pw;
}
