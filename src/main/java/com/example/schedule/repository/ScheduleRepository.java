package com.example.schedule.repository;

import com.example.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    //Schedule 다건조회 시 일정목록을 수정일 기준으로 내림차순 정렬
    List<Schedule> findAllByOrderByModifiedAtDesc();
}
