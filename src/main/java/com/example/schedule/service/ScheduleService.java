package com.example.schedule.service;

import com.example.schedule.dto.CreateScheduleRequest;
import com.example.schedule.dto.CreateScheduleResponse;
import com.example.schedule.dto.GetScheduleResponse;
import com.example.schedule.entity.Schedule;
import com.example.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

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

    public GetScheduleResponse getOneSchedule(Long id) {
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(
                ()-> new IllegalStateException("존재하지 않는 게시물입니다.")
        );

        return new GetScheduleResponse(
                schedule.getId(),
                schedule.getName(),
                schedule.getContents(),
                schedule.getWriter(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt()
        );
    }


    public List<GetScheduleResponse> getUserSchedule(String writer) {
        List<Schedule> schedules = scheduleRepository.findAll();
        List<GetScheduleResponse> dtos = new ArrayList<>();
        //System.out.println("*** writer : "+writer);
        if (writer.isEmpty()||writer==null){
            System.out.println("Writer is empty now!");
            for (Schedule schedule : schedules) {
                GetScheduleResponse dto = new GetScheduleResponse(
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
                GetScheduleResponse dto = new GetScheduleResponse(
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
}
