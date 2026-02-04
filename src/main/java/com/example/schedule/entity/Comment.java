package com.example.schedule.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table (name = "comments")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 100, nullable = false)
    private String contents;
    @Column(nullable = false)
    private String writer;
    @Column(nullable = false)
    private String pw;
    @Column(nullable = false)
    private Long scheduleId;

    public Comment(String contents, String writer, String pw,Long scheduleId) {
        this.contents = contents;
        this.writer = writer;
        this.pw = pw;
        this.scheduleId=scheduleId;
    }

    public void update(String contents, String writer) {
        this.contents = contents;
        this.writer = writer;
    }
}
