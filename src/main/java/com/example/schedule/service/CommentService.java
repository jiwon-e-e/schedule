package com.example.schedule.service;

import com.example.schedule.Exception.NonExistentException;
import com.example.schedule.Exception.TooManyCommentsException;
import com.example.schedule.Exception.WrongPasswordException;
import com.example.schedule.dto.commentDto.CreateCommentRequest;
import com.example.schedule.dto.commentDto.CreateCommentResponse;
import com.example.schedule.dto.commentDto.DeleteCommentRequest;
import com.example.schedule.dto.commentDto.GetCommentResponse;
import com.example.schedule.entity.Comment;
import com.example.schedule.entity.Schedule;
import com.example.schedule.repository.CommentRepository;
import com.example.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final ScheduleRepository scheduleRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public CreateCommentResponse save(CreateCommentRequest request, Long scheduleId) {
        // 매개변수로 받아온 scheduleId 가 존재하는지 확인
        // 존재하지 않으면 NonExistentExcepton
        boolean existence = scheduleRepository.existsById(scheduleId);
        if (!existence){
            throw new NonExistentException("존재하지 않는 게시물입니다.");
        }

        //댓글 개수를 확인하여 10개를 넘겼으면 TooManyCommentsException
        if (getCommentCountByScheduleId(scheduleId)>=10){
            throw new TooManyCommentsException("댓글은 10개까지 작성 가능합니다.");
        }

        // comment 생성
        Comment comment = new Comment(
                request.getContents(),
                request.getWriter(),
                request.getPw(),
                // 매개변수로 받아왔던 scheduleId 를 생성자에 넣어주기
                scheduleId
        );

        // 저장
        Comment newComment = commentRepository.save(comment);

        // 반환
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
        // 조회할 댓글 ID 를 매개변수로 받아와서
        // 해당 ID 를 가진 댓글이 존재하는지 확인
        // 존재하지 않으면 NonExistentException, 존재하면 다음단계 실행
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new NonExistentException("존재하지 않는 댓글입니다.")
        );

        // request DTO 에 담겨온 pw 를 기준으로 기존 pw 와 동일한지 확인
        // 동일하지 않으면 WrongPasswordException, 동일하면 delete 실행
        if (comment.getPw().equalsIgnoreCase(request.getPw())){
            throw new WrongPasswordException("비밀번호가 일치하지 않습니다.");
        }

        //반환값 없음
        commentRepository.delete(comment);
    }


    public List<GetCommentResponse> findCommentsByScheduleId(Long scheduleId){
        // CommentList 에 scheduleId 로 찾은 list 저장
        List<Comment> commentList = commentRepository.findByScheduleId(scheduleId);
        //dtos 이름으로 반환할 List 를 새로 선언
        List<GetCommentResponse> dtos = new ArrayList<>();

        //DTO 형태로 바꿔서 dtos 에 add
        for (Comment comment : commentList) {
            GetCommentResponse dto = new GetCommentResponse(
                    comment.getId(),
                    comment.getScheduleId(),
                    comment.getContents(),
                    comment.getWriter(),
                    comment.getCreatedAt()
            );
            dtos.add(dto);
        }

        return dtos;
    }

    public int getCommentCountByScheduleId(Long scheduleId){
        // 해당 schedule ID 를 가진 Comment List 를 저장
        List<Comment> commentList = commentRepository.findByScheduleId(scheduleId);
        // List 의 size 반환
        return commentList.size();
    }


}
