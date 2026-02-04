package com.example.schedule.controller;

import com.example.schedule.dto.commentDto.CreateCommentRequest;
import com.example.schedule.dto.commentDto.CreateCommentResponse;
import com.example.schedule.dto.commentDto.DeleteCommentRequest;
import com.example.schedule.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedules/{scheduleId}")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/comments")
    ResponseEntity<CreateCommentResponse> createComment (
            @RequestBody CreateCommentRequest request,
            @PathVariable Long scheduleId){
        CreateCommentResponse response = commentService.save(request, scheduleId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/comments/{id}")
    ResponseEntity<Void> deleteComment(@PathVariable Long id, @RequestBody DeleteCommentRequest request){
        commentService.delete(id,request);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
