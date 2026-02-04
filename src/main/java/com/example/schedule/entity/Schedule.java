package com.example.schedule.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Table(name="schedules")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Schedule extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 30, nullable = false)
    private String name;
    @Column(length = 200, nullable = false)
    private String contents;
    @Column(nullable = false)
    private String writer;
    @Column(nullable = false)
    private String pw;

    public Schedule(String name, String contents, String writer, String pw) {
        this.name = name;
        this.contents = contents;
        this.writer = writer;
        this.pw = pw;
    }

    public void update(String name, String writer) {
        this.name = name;
        this.writer = writer;
    }
}
