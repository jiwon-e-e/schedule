package com.example.schedule.service;

import com.example.schedule.Exception.NonExistentException;
import com.example.schedule.Exception.TooManyCommentsException;
import com.example.schedule.Exception.WrongPasswordException;
import com.example.schedule.dto.commentDto.CreateCommentRequest;
import com.example.schedule.dto.commentDto.CreateCommentResponse;
import com.example.schedule.dto.commentDto.DeleteCommentRequest;
import com.example.schedule.dto.commentDto.GetCommentResponse;
import com.example.schedule.entity.Comment;
import com.example.schedule.repository.CommentRepository;
import com.example.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final ScheduleRepository scheduleRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public CreateCommentResponse save(CreateCommentRequest request, Long scheduleId) {
        if (getCommentCountByScheduleId(scheduleId)>=10){
            throw new TooManyCommentsException("댓글은 10개까지 작성 가능합니다.");
        }

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
                () -> new NonExistentException("존재하지 않는 댓글입니다.")
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

    public int getCommentCountByScheduleId(Long scheduleId){
        List<Comment> commentList = commentRepository.findByScheduleId(scheduleId);
        return commentList.size();
    }

}
