package com.example.schedule.repository;

import com.example.schedule.dto.commentDto.DeleteCommentRequest;
import com.example.schedule.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    //특정 Schedule ID 를 가지는 CommentList를 반환
    List<Comment> findByScheduleId(Long scheduleId);
}
