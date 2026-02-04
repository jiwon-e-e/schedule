package com.example.schedule.service;

import com.example.schedule.Exception.WrongPasswordException;
import com.example.schedule.dto.commentDto.CreateCommentRequest;
import com.example.schedule.dto.commentDto.CreateCommentResponse;
import com.example.schedule.dto.commentDto.DeleteCommentRequest;
import com.example.schedule.dto.commentDto.GetCommentResponse;
import com.example.schedule.entity.Comment;
import com.example.schedule.repository.CommentRepository;
import com.example.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final ScheduleRepository scheduleRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public CreateCommentResponse save(CreateCommentRequest request, Long scheduleId) {
        Comment comment = new Comment(
                request.getContents(),
                request.getWriter(),
                request.getPw(),
                scheduleId
        );

        Comment newComment = commentRepository.save(comment);

        return new CreateCommentResponse(
                newComment.getId(),
                newComment.getScheduleId(),
                newComment.getContents(),
                newComment.getWriter(),
                newComment.getCreatedAt(),
                newComment.getModifiedAt()
        );
    }

    @Transactional
    public void delete(Long id, DeleteCommentRequest request) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new IllegalStateException("존재하지 않는 댓글입니다.")
        );

        if (comment.getPw().equalsIgnoreCase(request.getPw())){
            throw new WrongPasswordException("비밀번호가 일치하지 않습니다.");
        }

        commentRepository.delete(comment);
    }


    public List<GetCommentResponse> findCommentsByScheduleId(Long scheduleId){
        List<Comment> commentList = commentRepository.findByScheduleId(scheduleId);
        List<GetCommentResponse> dtos = new ArrayList<>();

        for (Comment comment : commentList) {
            GetCommentResponse dto = new GetCommentResponse(
                    comment.getId(),
                    comment.getScheduleId(),
                    comment.getContents(),
                    comment.getWriter()
            );
            dtos.add(dto);
        }

        return dtos;
    }


}
