package com.example.schedule.service;

import com.example.schedule.Exception.NonExistentException;
import com.example.schedule.Exception.WrongPasswordException;
import com.example.schedule.dto.scheduleDto.*;
import com.example.schedule.entity.Schedule;
import com.example.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final CommentService commentService;

    @Transactional
    public CreateScheduleResponse save(CreateScheduleRequest request) {
        Schedule schedule = new Schedule(
                request.getName(),
                request.getContents(),
                request.getWriter(),
                request.getPw()
        );
        Schedule newSchedule = scheduleRepository.save(schedule);

        return new CreateScheduleResponse(
                newSchedule.getId(),
                newSchedule.getName(),
                newSchedule.getContents(),
                newSchedule.getWriter(),
                newSchedule.getCreatedAt(),
                newSchedule.getModifiedAt()
        );
    }

    @Transactional(readOnly = true)
    public GetScheduleResponse getOneSchedule(Long id) {
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(
                ()-> new NonExistentException("존재하지 않는 게시물입니다.")
        );

        return new GetScheduleResponse(
                schedule.getId(),
                schedule.getName(),
                schedule.getContents(),
                schedule.getWriter(),
                commentService.findCommentsByScheduleId(id),
                schedule.getCreatedAt(),
                schedule.getModifiedAt()
        );
    }

    @Transactional(readOnly = true)
    public List<GetSchedulesResponse> getUserSchedule(String writer) {
        List<Schedule> schedules = scheduleRepository.findAll();
        List<GetSchedulesResponse> dtos = new ArrayList<>();
        if (writer==null||writer.isEmpty()){
            for (Schedule schedule : schedules) {
                GetSchedulesResponse dto = new GetSchedulesResponse(
                        schedule.getId(),
                        schedule.getName(),
                        schedule.getContents(),
                        schedule.getWriter(),
                        schedule.getCreatedAt(),
                        schedule.getModifiedAt()
                );
                dtos.add(dto);
            }
            return dtos;
        }

        for (Schedule schedule : schedules) {
            if (schedule.getWriter().equalsIgnoreCase(writer)){
                GetSchedulesResponse dto = new GetSchedulesResponse(
                        schedule.getId(),
                        schedule.getName(),
                        schedule.getContents(),
                        schedule.getWriter(),
                        schedule.getCreatedAt(),
                        schedule.getModifiedAt()
                );
                dtos.add(dto);
            }
        }
        return dtos;
    }

    @Transactional
    public UpdateScheduleResponse updateSchedule(Long id, UpdateScheduleRequest request) {
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(
                ()-> new NonExistentException("존재하지 않는 게시물입니다.")
        );

        if (!request.getPw().equalsIgnoreCase(schedule.getPw())){
            throw new WrongPasswordException("비밀번호가 일치하지 않습니다.");
        }

        schedule.update(
                request.getName(),
                request.getWriter()
        );

        return new UpdateScheduleResponse(
                schedule.getId(),
                schedule.getName(),
                schedule.getWriter(),
                schedule.getModifiedAt()
        );
    }

    @Transactional
    public void deleteSchedule(Long id, DeleteScheduleRequest request) {
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(
                ()-> new NonExistentException("존재하지 않는 게시물입니다.")
        );

        if (!request.getPw().equalsIgnoreCase(schedule.getPw())){
            throw new WrongPasswordException("비밀번호가 일치하지 않습니다.");
        }

        scheduleRepository.deleteById(id);
    }
}
